package com.jianglibo.wx.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianglibo.wx.api.util.AesException;
import com.jianglibo.wx.api.util.MySHA1;
import com.jianglibo.wx.config.ApplicationConfig;

@Controller
public class WxMiniapp {
	
	private static Logger logger = LoggerFactory.getLogger(WxMiniapp.class);
	
	@Autowired
	private ApplicationConfig appConfig;
	
	private String s = "not called.";
	
	@RequestMapping(path = "/miniapp", method=RequestMethod.GET, produces="text/html; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> get(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, HttpServletRequest request) {
		s = request.getRequestURI() + request.getQueryString();
		
		try {
			String sha1 = MySHA1.getSHA1(appConfig.getMiniAppApiToken(), timestamp, nonce);
			if (signature.equals(sha1)) {
				return ResponseEntity.ok(echostr);
			} else {
				logger.error("received signature: {}, timestamp: {}, nonce: {}, echostr, but verify failed.", signature, timestamp,nonce, echostr);
			}
		} catch (AesException e1) {
			logger.error(e1.getMessage());
		}
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(path = "/miniapp", method=RequestMethod.POST, produces="text/html; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> post(@RequestBody String body, HttpServletRequest request) {
		s = request.getRequestURI() + request.getQueryString();
		logger.info("get post: {}", s);
		logger.info("get body: {}", body);
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(path = "/miniappd", method=RequestMethod.GET, produces="text/html; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> d() {
		return ResponseEntity.ok().body(s);
	}
}
