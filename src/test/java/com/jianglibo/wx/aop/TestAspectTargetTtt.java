package com.jianglibo.wx.aop;

import org.junit.Test;

import com.jianglibo.wx.Tbase;

public class TestAspectTargetTtt extends Tbase {
	
	@Test
	public void t() {
		AspectTargetTtt att = context.getBean(AspectTargetTtt.class);
		att.beAdvised("abc");
	}

}
