package com.jianglibo.wx.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianglibo.wx.api.util.WXBizMsgCrypt;
import com.jianglibo.wx.config.ApplicationConfig;

@Controller
public class WxMiniapp {
	
//	/miniapp/signature=e76330857ac93dddaed9b0f4cf95438b141abea5&echostr=16322968932946230268×tamp=1497311940&nonce=971462508
//	signature: daa35cebd01b112931e7a6a1b960a9ad5383b6f6timestamp: 1497313081nonce: 2797966136echostr: 6347584983506354171
	
	@Autowired
	private ApplicationConfig appConfig;
	
	private String s = "not called.";
	
	@RequestMapping(path = "/miniapp", method=RequestMethod.GET, produces="text/html; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> dispatch(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, HttpServletRequest request) {
		s = request.getRequestURI() + request.getQueryString();
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(appConfig.getMiniAppApiToken(),
					appConfig.getMiniAppEncodingAESKey(), appConfig.getMiniAppId());
			return ResponseEntity.ok().body(wxcpt.verifyUrl(signature, timestamp, nonce, echostr));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println(s);
			pw.println("signature: " + signature);
			pw.println("timestamp: " + timestamp);
			pw.println("nonce: " + nonce);
			pw.println("echostr: " + echostr);
			e.printStackTrace(pw);
			s = sw.toString();
		}
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(path = "/miniappd", method=RequestMethod.GET, produces="text/html; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> d() {
		return ResponseEntity.ok().body(s);
	}
	
	
}
