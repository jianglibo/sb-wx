package com.jianglibo.wx.menu;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jianglibo.wx.WxBase;

public class TestMenuWrapper extends WxBase {

	@Test
	public void t() throws JsonProcessingException {
		ClickButton cb = new ClickButton("今日歌曲", "V1001_TODAY_MUSIC");
		ContainerButton cnb = new ContainerButton("菜单");
		cnb.addSubMenu(new ViewButton("搜索", "http://www.soso.com/"));
		cnb.addSubMenu(new MiniProgramButton("wxa", "http://mp.weixin.qq.com", "wx286b93c14bbf93aa", "pages/lunar/index.html"));
		cnb.addSubMenu(new ClickButton("赞一下我们", "V1001_GOOD"));
		MenuWrapper mw = new MenuWrapper(cb, cnb);
		
		String s = mw.serialize(indentOm);
		printme(s);
	}
}
