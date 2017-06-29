package com.jianglibo.wx.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;

public class TestBeanUtil {

	@Test
	public void t() {
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(UserDto.class);
		assertThat(pds.length, equalTo(37));
		for(PropertyDescriptor pd : pds) {
			System.out.println(pd.getName());
		}
		String[] xx = Stream.of(pds).map(pd -> pd.getName()).toArray(size -> new String[size]);
	}
	
	private BootUser originBu() {
		BootUser bu = new BootUser();
		bu.setName("abc");
		bu.setEmail("abc@email.com");
		bu.setPassword("123");
		return bu;
	}
	
	private UserDto originDto() {
		UserDto dto = new UserDto();
		dto.setName("abc1");
		dto.setEmail("abc@email.com1");
		dto.setPassword("1231");
		return dto;
	}
	
	@Test
	public void tcopy() {
		BootUser bu = originBu();
		UserDto dto = originDto();
		dto.setDtoApplyTo("name");
		PropertyCopyUtil.applyPatch(bu, dto);
		assertThat(bu.getName(), equalTo("abc1"));
		assertThat(bu.getEmail(), equalTo("abc@email.com"));
		assertThat(bu.getPassword(), equalTo("123"));
		
		bu = originBu();
		dto = originDto();
		dto.setDtoApplyTo("name,email,password");
		PropertyCopyUtil.applyPatch(bu, dto);
		assertThat(bu.getName(), equalTo("abc1"));
		assertThat(bu.getEmail(), equalTo("abc@email.com1"));
		assertThat(bu.getPassword(), equalTo("1231"));
	}
	
}
