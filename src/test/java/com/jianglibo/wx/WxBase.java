package com.jianglibo.wx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.message.WxInBoundUtil;

public abstract class WxBase extends Tbase {
	
	private static String mt = "application/vnd.api+json;charset=UTF-8";
	
	private static Path dtosPath = Paths.get("fixturesingit", "wx");
	
	@Autowired
	protected WxInBoundUtil wxUtil;

	protected ResponseEntity<String> response;
	
	@Autowired
	@Qualifier("indentOm")
	protected ObjectMapper indentOm;
	
	public String getFixtureWithExplicitName(String fname) throws IOException {
		if (fname.indexOf('.') == -1) {
			fname = fname + ".xml";
		}
		return new String(Files.readAllBytes(dtosPath.resolve(fname)));
	}

}
