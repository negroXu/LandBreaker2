package com.landbreaker.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.ImageReader;

import com.landbreaker.base.BaseSprite;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.item.GameItem_Dig;

public class UI_digItem extends BaseSprite {// 工具

	public int id;
	private BaseSprite mDigit;
	private Bitmap[] mDigitList;
	private int mAmount = 0;
	private float tX;
	private float tY;

	public final static int USEABLE = 1;
	public final static int LOCK = -1;
	public final static int NOITEM = 0;
	private int state = 0;
	private Context mContext;

	private List<GameItem_Dig> list_item = null;

	public UI_digItem(Context mContext, Bitmap image, Bitmap[] digit, int id, List<GameItem_Dig> items) {
		super(image);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.id = id;
		mDigitList = digit;
		mDigit = new BaseSprite(digit[0]);
		tX = this.mContactRect.centerX() - mDigit.getPosition().x;
		tY = this.mContactRect.centerY() - mDigit.getPosition().y;
		list_item = items;
		this.setAmount(list_item);

	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		mDigit.setPosition(x + tX, y + tY);

	}

	@Override
	public void setPosition(PointF p) {
		// TODO Auto-generated method stub
		super.setPosition(p);
		mDigit.setPosition(p.x + tX, p.y + tY);

	}

	@Override
	public void setVisibility(boolean flag) {
		// TODO Auto-generated method stub
		super.setVisibility(flag);
		mDigit.setVisibility(flag);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		mDigit.draw(canvas);
	}

	public void setAmount(List<GameItem_Dig> list) {
		if (list == null) {
			mAmount = -1;
			mPaint.setColorFilter(Config.Value.ColoFilter_lock);
			mDigit.setVisibility(false);
			state = LOCK;
			return;
		}
//		if (list.size() > mDigitList.length)
//			mAmount = mDigitList.length;
		if (list.size() == 0) {
			mAmount = list.size();
			mDigit.setVisibility(false);
			// 道具变红
			mPaint.setColorFilter(Config.Value.ColoFilter_noItem);
			state = NOITEM;
			return;
		}
		mAmount = list.size();
		mPaint.setColorFilter(Config.Value.ColoFilter_normal);
		mDigit.setImage(new ImgReader(mContext).loadNumBitmap(mAmount));//mDigitList[mAmount - 1]
		mDigit.setVisibility(true);
		state = USEABLE;
	}

	public int canBeUse() {
		if (mAmount > 0)
			return USEABLE;
		else if (mAmount < 0)
			return LOCK;
		else
			return NOITEM;
	}

	public void setItemList(List<GameItem_Dig> list) {
		list_item = list;
		setAmount(list_item);
	}

	public int getAmount() {
		if (state != LOCK)
			return list_item.size();
		else
			return -1;
	}

	public List<GameItem_Dig> getItemList() {
		return list_item;
	}

	public void removeItem(GameItem_Dig item) {
		list_item.remove(item);
		//setAmount(list_item);
	}
	
	public void refreshItemStatus()
	{
		setAmount(list_item);
	}

}
