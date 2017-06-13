package com.jianglibo.wx.webapp.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.wx.webapp.authorization.WxLoginService.LoginReturn;

@Controller
public class WxLoginController {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WxLoginService wxLoginService;

	@RequestMapping(path="/wxlogin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> wxlogin(HttpEntity<byte[]> he) throws JsonProcessingException {
		String code = he.getHeaders().getFirst(WxConstants.WX_HEADER_CODE);
		String encryptedData = he.getHeaders().getFirst(WxConstants.WX_HEADER_ENCRYPTED_DATA);
		String iv = he.getHeaders().getFirst(WxConstants.WX_HEADER_IV);
		LoginReturn lr = wxLoginService.login(code, encryptedData, iv);
		return ResponseEntity.ok(objectMapper.writeValueAsString(lr));
	}

}
