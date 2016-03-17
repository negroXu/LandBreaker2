package com.landbreaker.action;

import android.graphics.PointF;

import com.landbreaker.base.BaseSprite;
import com.landbreaker.config.Config;

public class GameMoveBy extends GameAction {
	private float dest_X;
	private float offset_X;
	private float dest_Y;
	private float offset_Y;
	private int mDuration;
	private int mCounter;
	private int counting;

	private PointF p;

	public GameMoveBy(float dx, float dy, int duration) {
		dest_X = dx;
		dest_Y = dy;
		mDuration = duration;
		mCounter = (int) (mDuration / Config.default_physicsTime);
	}

	@Override
	public void setSprite(BaseSprite sprite) {
		// TODO Auto-generated method stub
		super.setSprite(sprite);
		offset_X = dest_X / mCounter;
		offset_Y = dest_Y / mCounter;

		counting = mCounter;
		isRun = true;

	}

	@Override
	public void actOnce() {
		// TODO Auto-generated method stub
		super.actOnce();
		if (counting > 0) {
			if (counting == mCounter) {
				p = new PointF(mSprite.getX(), mSprite.getY());
			}
			counting--;
			mSprite.setPosition(mSprite.getPosition().x + offset_X, mSprite.getPosition().y + offset_Y);

		} else {
			correction();
			isFinish = true;
		}
	}

	@Override
	protected void finishEvent() {
		// TODO Auto-generated method stub
		super.finishEvent();

	}

	private void correction() {
		mSprite.setPosition(p.x + dest_X, p.y + dest_Y);
	}
}
