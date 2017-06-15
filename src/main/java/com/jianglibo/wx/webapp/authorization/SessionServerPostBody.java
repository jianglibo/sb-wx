package com.jianglibo.wx.webapp.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionServerPostBody {
	
	private int version = 1;
	private String componentName = "MA";

	@JsonProperty(value="interface")
	private SessionServerBodyInterface wxinterface;
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public SessionServerBodyInterface getWxinterface() {
		return wxinterface;
	}

	public void setWxinterface(SessionServerBodyInterface wxinterface) {
		this.wxinterface = wxinterface;
	}
}
