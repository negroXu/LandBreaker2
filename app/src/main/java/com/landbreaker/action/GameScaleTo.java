package com.landbreaker.action;

import com.landbreaker.base.BaseSprite;
import com.landbreaker.config.Config;

public class GameScaleTo extends GameAction {

	private float center_x;
	private float center_y;
	private float from_x;
	private float from_y;
	private float to_x;
	private float to_y;
	private float offset_x;
	private float offset_y;

	private int mDuration;
	private int mCounter;
	private int counting;

	public GameScaleTo(float cx, float cy, float from_x, float from_y, float to_x, float to_y, int duration) {
		center_x = cx;
		center_y = cy;
		this.from_x = from_x;
		this.from_y = from_y;
		this.to_x = to_x;
		this.to_y = to_y;
		mDuration = duration;
		mCounter = (int) (mDuration / Config.default_physicsTime);
		offset_x = (to_x - from_x) / mCounter;
		offset_y = (to_y - from_y) / mCounter;
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
			mSprite.setScale(from_x + offset_x * k, from_y + offset_y * k, center_x, center_y);
			counting--;
		} else {
			correction();
			isFinish = true;
		}
	}

	private void correction() {
		// TODO Auto-generated method stub
		mSprite.setScale(to_x, to_y, center_x, center_y);
	}

	@Override
	protected void finishEvent() {
		// TODO Auto-generated method stub
		super.finishEvent();

	}
}
