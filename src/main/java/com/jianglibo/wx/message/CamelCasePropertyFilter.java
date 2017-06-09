package com.jianglibo.wx.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.util.NameTransformer;

public class CamelCasePropertyFilter extends SimpleBeanPropertyFilter {

	@Override
	public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider provider,
			BeanPropertyWriter writer) throws Exception {
		writer.rename(new NameTransformer(){
			@Override
			public String transform(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String reverse(String transformed) {
				// TODO Auto-generated method stub
				return null;
			}});
		super.serializeAsField(bean, jgen, provider, writer);
	}
}
