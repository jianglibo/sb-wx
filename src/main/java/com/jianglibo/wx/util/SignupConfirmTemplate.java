package com.jianglibo.wx.util;


import com.jianglibo.wx.domain.UcToken;
import com.jianglibo.wx.vo.BootUserPrincipal;

public class SignupConfirmTemplate extends SendCloudTemplate {
    
    public static final String VURL = "vurl";
    
    private BootUserPrincipal personVo;

    public SignupConfirmTemplate(BootUserPrincipal personVo, String host, UcToken uctk) {
        super("regist_confirm", VURL);
        setSubjectTpl("�뼤�������˺ţ�%s?");
        this.personVo = personVo;
        withVar(VURL, host + "/email-verify?uctk=" + uctk.getTk());
    }

    @Override
    protected String getSubject() {
        return String.format(getSubjectTpl(), personVo.getUsername());
    }
}
