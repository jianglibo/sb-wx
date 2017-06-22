package com.jianglibo.wx.katharsis.dto;

import com.jianglibo.wx.domain.BaseEntity;

public interface Dto<T, E extends BaseEntity> {

	Long getId();
	
	default Long a() {
		return 0L;
	}
	
}
