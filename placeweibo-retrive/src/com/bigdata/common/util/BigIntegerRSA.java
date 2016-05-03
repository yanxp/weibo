package com.bigdata.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BigIntegerRSA {
	private static final Log logger = LogFactory.getLog(BigIntegerRSA.class);
	public static String SINA_PUB = "EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB"
			+ "2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C"
			+ "55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0"
			+ "E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6"
			+ "442443";

	public String rsaCrypt(String modeHex, String exponentHex, String messageg) {
		BigInteger m = new BigInteger(modeHex, 16);
		BigInteger e = new BigInteger(exponentHex, 16);
		RSAPublicKeySpec spec = new RSAPublicKeySpec(m, e);
		byte[] encryptedContentKey = null;

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(spec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(1, publicKey);
			encryptedContentKey = cipher.doFinal(messageg.getBytes("GB2312"));
		} catch (InvalidKeySpecException e1) {
			logger.error(e1);
		} catch (NoSuchAlgorithmException e2) {
			logger.error(e2);
		} catch (NoSuchPaddingException e3) {
			logger.error(e3);
		} catch (InvalidKeyException e4) {
			logger.error(e4);
		} catch (IllegalBlockSizeException e5) {
			logger.error(e5);
		} catch (BadPaddingException e6) {
			logger.error(e6);
		} catch (UnsupportedEncodingException e7) {
			logger.error(e7);
		}

		return new String(Hex.encodeHex(encryptedContentKey));
	}
}
