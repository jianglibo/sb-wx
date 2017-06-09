package com.jianglibo.wx.message.event;

import com.jianglibo.wx.message.WxInEvent;

public class LocationEvent extends WxInEvent {
	
	private double latitude;
	private double longitude;
	private double precision;
	
	public LocationEvent() {
		super();
		setEvent(WxEventType.LOCATION);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	
}
