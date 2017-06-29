package com.jianglibo.wx.katharsis.dto;

import java.util.Set;

public interface Dto {
	Long getId();
	String getDtoApplyTo();
	String getDtoAction();
	Set<String> calDtoApplyToSet();
}
