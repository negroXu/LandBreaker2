package com.landbreaker.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private MyThread mThread;
	private float mFPS;

	private PhysicsThread physics;

	public BaseSurfaceView(Context context, float fps) {
		super(context);
		// TODO Auto-generated constructor stub
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mFPS = fps;
	}

	public BaseSurfaceView(Context context, float fps, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mFPS = fps;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mThread = new MyThread(mHolder, (int) (1000.0 / mFPS + 0.5));
		mThread.isRun = true;

		physics = new PhysicsThread() {

			@Override
			public void runPhysics() {
				// TODO Auto-generated method stub
				physics();
			}
		};
		physics.isRun = true;

		mThread.start();
		physics.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		mThread.isRun = false;
		physics.isRun = false;
	}

	public void draw(Canvas canvas) {

	}

	public void physics() {

	}

	public void setFPS(float f) {
		mFPS = f;
		mThread.setPFS(1000 / f);
	}

	public float getFPS() {
		return mFPS;
	}

	class MyThread extends Thread {
		private SurfaceHolder holder;
		public boolean isRun;
		private TimeCounter timer;

		MyThread(SurfaceHolder h, int i) {
			holder = h;
			timer = new TimeCounter(i);
		}

		public void setPFS(float f) {
			timer = new TimeCounter((int) (1000.0 / f + 0.5));
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRun) {
				Canvas canvas = null;
				if (timer.isTick()) {
					try {
						synchronized (holder) {
							canvas = holder.lockCanvas();
							// 绘制
							canvas.drawColor(0xff000000);
							draw(canvas);

						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (canvas != null)
							holder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}

	}

}
