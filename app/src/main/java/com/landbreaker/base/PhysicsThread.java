package com.landbreaker.base;

import com.landbreaker.config.Config;

public class PhysicsThread extends Thread {

	private TimeCounter mTimer;
	public boolean isRun = true;

	public PhysicsThread() {

		mTimer = new TimeCounter(Config.default_physicsTime);
		mTimer.pause();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		mTimer.resume();
		while (isRun) {
			if (mTimer.isTick()) {
				// physics
				runPhysics();
			}

		}
		mTimer.pause();
	}

	public void runPhysics() {

	}

}
