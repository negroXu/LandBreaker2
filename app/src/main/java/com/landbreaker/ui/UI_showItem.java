package com.landbreaker.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.landbreaker.base.BaseSprite;
import com.landbreaker.config.Config;

public class UI_showItem extends BaseSprite {

	public UI_showItem(Bitmap image) {
		super(image);
		// TODO Auto-generated constructor stub
		this.setVisibility(false);
		this.setTouchable(true);
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouch(event);

	}

	public boolean touchEvent(MotionEvent event) {
		if (!isVisibility)
			return false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.setVisibility(false);
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (!isVisibility)
			return;
		canvas.drawARGB(Config.Value.curtain_Alpha_50, 0, 0, 0);
		super.draw(canvas);
	}

	@Override
	public void physics() {
		// TODO Auto-generated method stub
		super.physics();
	}

	public void showItem(Bitmap bm) {
		this.setImage(bm);
		this.setVisibility(true);
	}

}
