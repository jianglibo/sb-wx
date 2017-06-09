package com.jianglibo.wx.menu;

public class PicPhotoOrAlbumBtn extends MenuButton {
	
	private final String key;

	public PicPhotoOrAlbumBtn(String name, String key) {
		super(ButtonType.pic_photo_or_album, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
