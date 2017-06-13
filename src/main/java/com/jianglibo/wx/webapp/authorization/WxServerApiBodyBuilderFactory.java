package com.jianglibo.wx.webapp.authorization;

public class WxServerApiBodyBuilderFactory {

	public static IdskeyBodyBuilder getIdskeyBodyBuilder() {
		return new IdskeyBodyBuilder();
	}
	
	public static AuthBodyBuilder getAuthBodyBuilder() {
		return new AuthBodyBuilder();
	}
	
	
	public static class IdskeyBodyBuilder {
		private WxServerApiBody sab;
		private WxServerApiInterface idskey = new WxServerApiInterface("qcloud.cam.id_skey");
		
		public IdskeyBodyBuilder withCode(String code) {
			idskey.getPara().put("code", code);
			return this;
		}
		
		public IdskeyBodyBuilder withEncryptData(String encryptData) {
			idskey.getPara().put("encrypt_data", encryptData);
			return this;
		}

		public IdskeyBodyBuilder withIv(String iv) {
			idskey.getPara().put("iv", iv);
			return this;
		}
		
		public WxServerApiBody build() {
			sab = new WxServerApiBody();
			sab.setWxinterface(idskey);
			return sab;
		}
	}
	
	public static class AuthBodyBuilder {
		private WxServerApiBody sab;
		private WxServerApiInterface idskey = new WxServerApiInterface("qcloud.cam.auth");
		
		public AuthBodyBuilder withId(String id) {
			idskey.getPara().put("id", id);
			return this;
		}
		
		public AuthBodyBuilder withSkey(String skey) {
			idskey.getPara().put("skey", skey);
			return this;
		}
		
		public WxServerApiBody build() {
			sab = new WxServerApiBody();
			sab.setWxinterface(idskey);
			return sab;
		}
	}
}
