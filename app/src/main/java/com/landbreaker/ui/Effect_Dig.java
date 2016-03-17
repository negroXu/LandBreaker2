package com.landbreaker.ui;

import android.graphics.Bitmap;

import com.landbreaker.base.BaseSprite;

public class Effect_Dig extends BaseSprite {

	private Bitmap[] mList_effectImage = null;

	private int mNum;// 当前效果序号

	public Effect_Dig(Bitmap[] effectImageList) {
		super(effectImageList[0]);
		// TODO Auto-generated constructor stub
		isTouchable = false;
		mList_effectImage = effectImageList;
		mNum = 0;
	}

	public void setEffect(int num) {
		if (num >= mList_effectImage.length || num < 0)
			return;
		mNum = num;
		mImage = mList_effectImage[mNum];
	}

	public int getEffectNum() {
		return mNum;
	}

}
