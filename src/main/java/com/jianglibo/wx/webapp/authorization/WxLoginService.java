package com.jianglibo.wx.webapp.authorization;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jianglibo.wx.webapp.WxStateConfiguration;


@Component
public class WxLoginService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WxStateConfiguration wxStateConfiguration;
	
	public LoginReturn login(String code, String encryptedData, String iv) {
		WxServerApiBody sab = WxServerApiBodyBuilderFactory.getIdskeyBodyBuilder()
				.withCode(code)
				.withEncryptData(encryptedData)
				.withIv(iv)
				.build();
		ResponseEntity<IdskeyResponse> resp =  restTemplate.postForEntity(wxStateConfiguration.getAuthServerUrl(), sab, IdskeyResponse.class);
		IdskeyResponse ikr = resp.getBody();
		LoginReturn lr =  new LoginReturn();
		lr.setId(ikr.getId());
		lr.setSkey(ikr.getSkey());
		lr.setSession(new HashMap<>());
		return lr;
	}
	
	
	public AuthResonseFromWxServer check(String id, String skey) {
		WxServerApiBody sab = WxServerApiBodyBuilderFactory.getAuthBodyBuilder()
				.withId(id)
				.withSkey(skey)
				.build();
		ResponseEntity<AuthResonseFromWxServer> resp =  restTemplate.postForEntity(wxStateConfiguration.getAuthServerUrl(), sab, AuthResonseFromWxServer.class);
		AuthResonseFromWxServer ars = resp.getBody();
		return ars;
	}
	
	public static class AuthResonseFromWxServer {
		private int returnCode;
		private String returnMessage;
		private AuthReturnData returnData;
		public int getReturnCode() {
			return returnCode;
		}
		public void setReturnCode(int returnCode) {
			this.returnCode = returnCode;
		}
		public String getReturnMessage() {
			return returnMessage;
		}
		public void setReturnMessage(String returnMessage) {
			this.returnMessage = returnMessage;
		}
		public AuthReturnData getReturnData() {
			return returnData;
		}
		public void setReturnData(AuthReturnData returnData) {
			this.returnData = returnData;
		}
	}
	
	public static class AuthReturnData {
		private WxUserInfo user_info;

		public WxUserInfo getUser_info() {
			return user_info;
		}

		public void setUser_info(WxUserInfo user_info) {
			this.user_info = user_info;
		}
	}
	
	public static class LoginReturn {
		private String id;
		private String skey;
		private Map<String, Object> session;
		
		@JsonProperty(value=WxConstants.WX_SESSION_MAGIC_ID)
		private int wXsessionMagicId = 1;
		
		public int getwXsessionMagicId() {
			return wXsessionMagicId;
		}
		public void setwXsessionMagicId(int wXsessionMagicId) {
			this.wXsessionMagicId = wXsessionMagicId;
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSkey() {
			return skey;
		}
		public void setSkey(String skey) {
			this.skey = skey;
		}
		public Map<String, Object> getSession() {
			return session;
		}
		public void setSession(Map<String, Object> session) {
			this.session = session;
		}
	}
	
	public static class IdskeyResponse {
		private String id;
		private String skey;
		
		
		private WxUserInfo user_info;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSkey() {
			return skey;
		}
		public void setSkey(String skey) {
			this.skey = skey;
		}
		public WxUserInfo getUser_info() {
			return user_info;
		}
		public void setUser_info(WxUserInfo user_info) {
			this.user_info = user_info;
		}

	}
}
