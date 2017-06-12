package com.jianglibo.wx.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.Tbase;
import com.jianglibo.wx.repository.NutchBuilderTemplateRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
public class TestNutchBuilderTemplate extends Tbase {
    
    @Autowired
    private NutchBuilderTemplateRepository nbtp;

    @Test
    public void tCreate() {
        NutchBuilderTemplate nbt = new NutchBuilderTemplate();
        nbt.setName("notexists");
        nbtp.save(nbt);
    }
}
