package com.landbreaker.ui;

import android.graphics.Bitmap;

import com.landbreaker.base.BaseSprite;

public class UI_Luck extends BaseSprite {// 幸运签

	private int mLuck_tag;

	public UI_Luck(Bitmap image, int tag) {
		super(image);
		// TODO Auto-generated constructor stub
		mLuck_tag = tag;
	}

	public void setLuckTag(int tag) {
		mLuck_tag = tag;
	}

	public int getLuckTag() {
		return mLuck_tag;
	}

}
