package com.jianglibo.wx.message.in;

import com.jianglibo.wx.message.WxInMessage;

public class LocationMessage extends WxInMessage {

	private double location_X;
	private double location_Y;
	private int scale;
	private String label;
	
	public LocationMessage() {
		super();
		setMsgType(WxMessageType.location);
	}
	public double getLocation_X() {
		return location_X;
	}
	public void setLocation_X(double location_X) {
		this.location_X = location_X;
	}
	public double getLocation_Y() {
		return location_Y;
	}
	public void setLocation_Y(double location_Y) {
		this.location_Y = location_Y;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
