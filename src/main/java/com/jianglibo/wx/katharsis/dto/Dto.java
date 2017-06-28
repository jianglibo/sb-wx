package com.jianglibo.wx.katharsis.dto;

import com.jianglibo.wx.domain.BaseEntity;

public interface Dto<T, E extends BaseEntity> {

	Long getId();
	
	String getDtoApplyTo();
	String getDtoAction();
	
	default Long a() {
		return 0L;
	}
	
}
