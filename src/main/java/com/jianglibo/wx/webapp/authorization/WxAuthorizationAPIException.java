package com.jianglibo.wx.webapp.authorization;

class WxAuthorizationAPIException extends Exception {
	private static final long serialVersionUID = -3088657611850871775L;
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public WxAuthorizationAPIException(String message, Exception inner) {
		super(message, inner);
	}

	public WxAuthorizationAPIException(String message) {
		this(message, null);
	}
}
