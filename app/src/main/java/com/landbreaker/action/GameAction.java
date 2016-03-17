package com.landbreaker.action;

import com.landbreaker.base.BaseSprite;

public class GameAction {

	// -------------------------未完善

	protected BaseSprite mSprite;
	protected String mName;
	protected boolean isRun = false;
	protected GameCallBack mInterface;
	protected boolean isFinish = false;
	protected Object mTag;
	protected int mKey = -99919;

	public GameAction() {

	}

	public void setSprite(BaseSprite sprite) {
		mSprite = sprite;
	}

	public void setCallBack(GameCallBack cb) {
		mInterface = cb;
	}

	public void setCallBack(GameCallBack cb, int key) {
		mInterface = cb;
		mKey = key;
	}

	public void setTag(Object tag) {
		mTag = tag;
	}

	public Object getTag() {
		return mTag;
	}

	public void actOnce() {
		if (isFinish)
			finishEvent();
		if (!isRun)
			return;
	}

	public boolean isRun() {
		return isRun;
	}

	public void pause() {
		isRun = false;
	}

	public void resume() {
		isRun = true;
	}

	public interface GameCallBack {
		void _finish(BaseSprite sp);

		void _finishWithKey(int key);
	}

	protected void finishEvent() {
		isRun = false;
		mSprite.removeAction(this);
		if (this.mInterface != null) {
			this.mInterface._finish(mSprite);
			this.mInterface._finishWithKey(mKey);

		}
	}

	public void finish() {
		isFinish = true;
	}

}
