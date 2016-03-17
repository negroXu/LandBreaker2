package com.landbreaker.base;

public class TimeCounter {

	private long mCurrentTime;
	private long mIntervalTime;
	private long mLastTime;

	private long mTempTime = 0;

	private boolean isPause = false;

	public TimeCounter(long intervalTime) {
		mIntervalTime = intervalTime;
		mLastTime = System.currentTimeMillis();
		mCurrentTime = mLastTime;
	}

	public boolean isTick() {

		if (isPause)
			return false;

		mCurrentTime = System.currentTimeMillis();
		if (mCurrentTime - mLastTime >= mIntervalTime) {
			mLastTime += mIntervalTime;
			return true;
		}

		return false;
	}

	public void pause() {
		if (isPause)
			return;
		mTempTime = System.currentTimeMillis() - mLastTime;
		isPause = true;
	}

	public void resume() {
		if (!isPause)
			return;
		mLastTime = System.currentTimeMillis() - mTempTime;
		isPause = false;
	}

}
