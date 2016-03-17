package com.landbreaker.base;

import android.graphics.Bitmap;

public class GameButton extends BaseSprite {

	private Bitmap mImageDown;
	private Bitmap mImageUp;

	private boolean isDown = false;

	public GameButton(Bitmap image) {
		super(image);
		// TODO Auto-generated constructor stub
		mImageDown = image;
		mImageUp = image;
		isTouchable = true;
	}

	public void setImageDown(Bitmap bm) {
		mImageDown = bm;
	}

	public void setImageUp(Bitmap bm) {
		mImageUp = bm;
	}

	public void down() {
		mImage = mImageDown;
		isDown = true;
	}

	public void up() {
		mImage = mImageUp;
		isDown = false;
	}

	public boolean isDown() {
		return isDown;
	}

}
