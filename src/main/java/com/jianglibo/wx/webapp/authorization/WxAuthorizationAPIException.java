package com.jianglibo.wx.webapp.authorization;

class WxAuthorizationAPIException extends Exception {
	private static final long serialVersionUID = -3088657611850871775L;
	
	public WxAuthorizationAPIException(String message, Exception inner) {
		super(message, inner);
	}
}
