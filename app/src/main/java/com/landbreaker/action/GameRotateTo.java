package com.landbreaker.action;

import com.landbreaker.base.BaseSprite;
import com.landbreaker.config.Config;

public class GameRotateTo extends GameAction {

	private float center_x;
	private float center_y;
	private float from_degrees;
	private float to_degrees;
	private float offset_degrees;

	private int mDuration;
	private int mCounter;
	private int counting;

	public GameRotateTo(float cx, float cy, float from_degrees, float to_degrees, int duration) {
		center_x = cx;
		center_y = cy;
		this.from_degrees = from_degrees;
		this.to_degrees = to_degrees;
		mDuration = duration;
		mCounter = (int) (mDuration / Config.default_physicsTime);
		offset_degrees = (this.to_degrees - this.from_degrees) / mCounter;
	}

	@Override
	public void setSprite(BaseSprite sprite) {
		// TODO Auto-generated method stub
		super.setSprite(sprite);

		counting = mCounter;
		isRun = true;

	}

	@Override
	public void actOnce() {
		// TODO Auto-generated method stub
		super.actOnce();
		if (counting > 0) {
			int k = mCounter - counting;
			mSprite.setRotate(from_degrees + offset_degrees * k, center_x, center_y);
			counting--;
		} else {
			correction();
			isFinish = true;
		}
	}

	private void correction() {
		// TODO Auto-generated method stub
		mSprite.setRotate(to_degrees, center_x, center_y);
	}

}
