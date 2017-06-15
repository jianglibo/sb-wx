package com.jianglibo.wx.webapp.authorization;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseToPhone {

	private Map<String, Object> session = new HashMap<>();
	
	@JsonProperty(value=WxConstants.WX_SESSION_MAGIC_ID)
	private int wXsessionMagicId = 1;
	
	public LoginResponseToPhone(){}
	
	public LoginResponseToPhone(String id, String skey) {
		setId(id);
		setSkey(skey);
	}
	
	public int getwXsessionMagicId() {
		return wXsessionMagicId;
	}

	public void setId(String id) {
		session.put("id", id);
	}
	public void setSkey(String skey) {
		session.put("skey", skey);
	}
	public Map<String, Object> getSession() {
		return session;
	}
}
