package com.jianglibo.wx.webapp.authorization;

import java.util.HashMap;
import java.util.Map;

public class SessionServerBodyInterface {
	private final String interfaceName;
	
	private final Map<String, Object> para = new HashMap<>();
	
	public SessionServerBodyInterface(String interfaceName) {
		super();
		this.interfaceName = interfaceName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public Map<String, Object> getPara() {
		return para;
	}
}
