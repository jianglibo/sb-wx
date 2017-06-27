package com.jianglibo.wx.katharsis.exception;

import com.jianglibo.wx.constant.AppErrorCodes;

public class UnsupportRelationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title = "request not supported.";
	private String code = AppErrorCodes.UNSUPPORTED_RELATION;
	private String detail;
	
	public UnsupportRelationException(String detail) {
		super(detail);
		this.detail = detail;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
