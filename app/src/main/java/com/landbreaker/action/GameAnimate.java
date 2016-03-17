package com.landbreaker.action;

import com.landbreaker.base.BaseSprite;
import com.landbreaker.base.TimeCounter;

import android.graphics.Bitmap;

public class GameAnimate extends GameAction {

	private Bitmap[] animation;
	private int interval;
	private int index;
	private boolean isRool = false;
	private TimeCounter timeCounter;
	private boolean isReset = false;
	private int times = 1;

	public GameAnimate(Bitmap[] bms, int interval, boolean isRool, boolean isReset) {
		animation = bms;
		this.interval = interval;
		this.isRool = isRool;
		this.isReset = isReset;
		index = 0;
		timeCounter = new TimeCounter(this.interval);
		timeCounter.pause();
	}

	public GameAnimate(Bitmap[] bms, int interval, int times, boolean isReset) {
		animation = bms;
		this.interval = interval;
		this.isRool = false;
		this.times = times;
		this.isReset = isReset;
		index = 0;
		timeCounter = new TimeCounter(this.interval);
		timeCounter.pause();
	}

	@Override
	public void setSprite(BaseSprite sprite) {
		// TODO Auto-generated method stub
		super.setSprite(sprite);
		isRun = true;
	}

	@Override
	public void actOnce() {
		// TODO Auto-generated method stub
		super.actOnce();
		timeCounter.resume();
		if (index < animation.length)
			mSprite.setImage(animation[index]);
		if (timeCounter.isTick()) {
			index++;
			if (isRool)
				index = index % animation.length;
			if (index >= animation.length) {
				times--;
				if (times < 1)
					isFinish = true;
				else
					index = index % animation.length;
			}
		}
	}

	@Override
	protected void finishEvent() {
		// TODO Auto-generated method stub
		if (isReset)
			mSprite.setImage(animation[0]);
		super.finishEvent();

	}

}
