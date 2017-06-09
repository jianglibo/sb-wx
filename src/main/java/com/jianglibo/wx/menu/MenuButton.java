package com.jianglibo.wx.menu;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("btnContainerTypeFilter")
public class MenuButton {
	
	private final ButtonType type;
	private final String name;
	
	private List<MenuButton> sub_button = new ArrayList<>();

	public MenuButton(ButtonType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	
	public void addSubMenu(MenuButton menuButton) {
		this.sub_button.add(menuButton);
	}

	public ButtonType getType() {
		return type;
	}


	public String getName() {
		return name;
	}

	public List<MenuButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<MenuButton> sub_button) {
		this.sub_button = sub_button;
	}
	
}
