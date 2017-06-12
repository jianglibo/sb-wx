package com.jianglibo.wx.util;

import java.io.UnsupportedEncodingException;

import org.springframework.util.Assert;
import org.springframework.web.util.UriUtils;


import com.jianglibo.wx.domain.UcToken;

public class PasswordRecoverTemplate extends SendCloudTemplate {
    
    public static final String VURL = "vurl";
    
    public PasswordRecoverTemplate(UcToken uctk, String host, String rdUrl) throws UnsupportedEncodingException {
        super("password_recover", VURL);
        Assert.notNull(uctk.getTk(), "uctoken's tk is null, have you save it before use?");
        setSubjectTpl("��������?");
        withVar(VURL, createVurl(host, uctk, rdUrl));
    }
    
    private String createVurl(String host, UcToken uctk, String rdUrl) throws UnsupportedEncodingException {
        rdUrl = rdUrl == null ? "" : rdUrl;
        if (rdUrl.indexOf('?') != -1) {
            rdUrl = rdUrl + "&uctk=" + uctk.getTk();
        } else {
            rdUrl = rdUrl + "?uctk=" + uctk.getTk();
        }
        return host + "/tkconsumer?uctk=" + uctk.getTk() + "&rd=" + UriUtils.encodeQueryParam(rdUrl, "UTF-8");
    }

    @Override
    protected String getSubject() {
        return getSubjectTpl();
    }
}
