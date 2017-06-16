package com.jianglibo.wx.katharsis.dto;

public interface Dto<T, E> {

	Long getId();
	
	default Long a() {
		return 0L;
	}
	
}
