package com.landbreaker.listener;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

public class GamePageChangeListener implements OnPageChangeListener {

	private ImageView mState;
	private Bitmap[] mBms;
	private ImageView mLeft;
	private ImageView mRight;
	private int pageLength;

	public GamePageChangeListener(ImageView state, Bitmap[] bms, ImageView left, ImageView right, int pageLength) {
		mState = state;
		mBms = bms;
		mLeft = left;
		mRight = right;
		this.pageLength = pageLength;
	}

	public GamePageChangeListener(ImageView left, ImageView right, int pageLength) {
		mState = null;
		mBms = null;
		mLeft = left;
		mRight = right;
		this.pageLength = pageLength;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageSelected(int index) {
		// TODO Auto-generated method stub
		if (mState != null)
			mState.setImageBitmap(mBms[index]);
		if (index == 0) {
			mLeft.setVisibility(View.INVISIBLE);
			mRight.setVisibility(View.VISIBLE);
		} else if (index == pageLength - 1) {
			mRight.setVisibility(View.INVISIBLE);
			mLeft.setVisibility(View.VISIBLE);
		} else {
			mLeft.setVisibility(View.VISIBLE);
			mRight.setVisibility(View.VISIBLE);
		}
	}

}
