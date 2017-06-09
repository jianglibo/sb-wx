package com.jianglibo.wx.menu;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jianglibo.wx.WxBase;

public class TestMenuJson extends WxBase {
	
	@Test
	public void t() throws JsonProcessingException {
		List<Mb> menus = new ArrayList<>();
		ClickBtn ct = new ClickBtn();
		ct.setName("ct");
		ct.setKey("akey");
		ct.setType(ButtonType.click);
		menus.add(ct);
		
		ViewBtn vb = new ViewBtn();
		vb.setName("vt");
		vb.setType(ButtonType.view);
		vb.setUrl("http://a.b.c");
		menus.add(vb);
		
		String s = indentOm.writeValueAsString(menus);
		printme(s);
	}
	
	public static class Mb {
		private String name;
		private ButtonType type;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ButtonType getType() {
			return type;
		}
		public void setType(ButtonType type) {
			this.type = type;
		}
	}
	
	public static class ClickBtn extends Mb {
		private String key;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
	
	
	public static class ViewBtn extends Mb {
		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
	

}
