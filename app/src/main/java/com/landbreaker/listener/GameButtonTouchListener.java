package com.landbreaker.listener;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

// 按键监听
public class GameButtonTouchListener implements OnTouchListener {

	private Bitmap bm_up;
	private Bitmap bm_down;
	private boolean isDown = false;
	private boolean isClick = true;

	public GameButtonTouchListener(Bitmap[] bms) {
		this.bm_up = bms[0];
		this.bm_down = bms[1];
	}

	public GameButtonTouchListener(Bitmap bm_up, Bitmap bm_down) {
		this.bm_up = bm_up;
		this.bm_down = bm_down;
	}

	public GameButtonTouchListener() {
		isClick = false;
	}

	public void setButtonUp(View view) {
		ImageView iv = (ImageView) view;
		isDown = false;
		iv.setImageBitmap(bm_up);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub
		ImageView iv = (ImageView) view;
		if (event.getPointerId(0) != 0)
			return true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (isClick) {
				iv.setImageBitmap(bm_down);
				isDown = true;
				downEvent(iv.getTag());
			} else {
				touchEvent(iv.getTag());
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isDown && isClick) {
				iv.setImageBitmap(bm_up);
				isDown = false;
				Rect r = new Rect();
				iv.getLocalVisibleRect(r);
				if (r.contains((int) event.getX(), (int) event.getY())) {
					touchEvent(iv.getTag());
				}
			}
			break;
		default:
			break;
		}

		return true;
	}

	/**
	 * 触发事件
	 * 
	 * @param tag
	 */
	public void touchEvent(Object tag) {

	}

	public void downEvent(Object tag) {

	}

}