package com.bigdata.locationweibo.api;

public class PreLoginInfo {
	private long servertime;
	private String nonce;
	private String pubkey;
	private String pcid;
	private int retcode;
	private String rsakv;
	private int showpin;
	
	public long getServertime() {
		return servertime;
	}
	public void setServertime(long servertime) {
		this.servertime = servertime;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getPubkey() {
		return pubkey;
	}
	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}
	public String getPcid() {
		return pcid;
	}
	public void setPcid(String pcid) {
		this.pcid = pcid;
	}
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public String getRsakv() {
		return rsakv;
	}
	public void setRsakv(String rsakv) {
		this.rsakv = rsakv;
	}
	public int getShowpin() {
		return showpin;
	}
	public void setShowpin(int showpin) {
		this.showpin = showpin;
	}

	
}
