package com.jianglibo.wx.menu;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class MenuWrapper {

	private List<MenuButton> button;

	private static PropertyFilter btnTypeFilter = new SimpleBeanPropertyFilter() {
		@Override
		public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider,
				PropertyWriter writer) throws Exception {
			if (include(writer)) {
				if (!("type".equals(writer.getName()) || "sub_button".equals(writer.getName()))) {
					writer.serializeAsField(pojo, jgen, provider);
					return;
				}

				MenuButton mb = (MenuButton) pojo;
				if ("type".equals(writer.getName())) {
					if (mb.getType() != ButtonType.container) {
						writer.serializeAsField(pojo, jgen, provider);
						return;
					}
				} else {
					if (!mb.getSub_button().isEmpty()) {
						writer.serializeAsField(pojo, jgen, provider);
						return;
					}
				}
			} else if (!jgen.canOmitFields()) { // since 2.3
				writer.serializeAsOmittedField(pojo, jgen, provider);
			}
		}
	};

	private static FilterProvider filters = new SimpleFilterProvider().addFilter("btnContainerTypeFilter",
			btnTypeFilter);

	public MenuWrapper(MenuButton... buttons) {
		super();
		this.button = new ArrayList<>();
		for (MenuButton mb : buttons) {
			this.button.add(mb);
		}
	}

	public MenuWrapper addButton(MenuButton menuButton) {
		button.add(menuButton);
		return this;
	}

	public MenuWrapper addButton(Iterable<MenuButton> menuButtons) {
		for (MenuButton mb : menuButtons) {
			button.add(mb);
		}
		return this;
	}

	public List<MenuButton> getButton() {
		return button;
	}

	public String serialize(ObjectMapper objectMapper) throws JsonProcessingException {
		return objectMapper.writer(filters).writeValueAsString(this);
	}
}
