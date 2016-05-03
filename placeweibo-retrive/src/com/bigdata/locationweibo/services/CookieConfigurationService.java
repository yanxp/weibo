package com.bigdata.locationweibo.services;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@SuppressWarnings("deprecation")
public class CookieConfigurationService {

	@SuppressWarnings("unchecked")
	public static CloseableHttpClient configeClient(CookieStore cookies){
		
		CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
			public CookieSpec create(HttpContext context) {
				return new BrowserCompatSpec() {
		 		    //重写默认的策略验证
					@Override
					public void validate(Cookie cookie, CookieOrigin origin)
							throws MalformedCookieException {
					}
				};
			}
		};
		@SuppressWarnings("rawtypes")
		Registry reg = RegistryBuilder
		.create()
		.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
		.register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory())
		.register("mySpec", easySpecProvider)
		.build();
		
		RequestConfig requestConfig = RequestConfig
			    .custom()
				.setCookieSpec("mySpec")
				.build();
		
		CloseableHttpClient client = null;
		if (cookies == null) {
			 client = HttpClients
		               .custom()
		               .setDefaultCookieSpecRegistry(reg)
		               .setDefaultRequestConfig(requestConfig)
		               .build();
		} else {
			client = HttpClients
					.custom()
					.setDefaultCookieSpecRegistry(reg)
					.setDefaultRequestConfig(requestConfig)
					.setDefaultCookieStore(cookies)
					.build();
		}
		return client;
	}
	
	public static DefaultHttpClient configDefaultClient(){
		DefaultHttpClient client = new DefaultHttpClient();
		CookieSpecFactory csf = new CookieSpecFactory(){
            public CookieSpec newInstance(HttpParams params){
                return new BrowserCompatSpec(){
                    @Override
                    public void validate(Cookie cookie, CookieOrigin origin)
                    throws MalformedCookieException{
                        //Oh, I am easy
                    }
                };
            }
        };
		client.getCookieSpecs().register("easy", csf);
	    client.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
	    return client;
	}
}
