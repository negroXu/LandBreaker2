package com.landbreaker.base;

import java.util.ArrayList;
import java.util.List;

import com.landbreaker.action.GameAction;
import com.landbreaker.config.Config;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * 精灵类
 * 
 * @author kaiyu
 * 
 */
public class BaseSprite {

	// protected Point mPos;

	protected String mName = null;
	protected Object mTag = null;

	protected PointF mPos;// 位置
	protected PointF mV;// 速度
	protected List<PointF> mA;// 加速度

	protected List<GameAction> mList_action;// 动作列表

	protected Bitmap mImage;// 显示的Image
	protected Paint mPaint;// 画笔

	protected Matrix mMatrix;// 绘图矩阵

	protected Matrix mMatrix_translate;
	protected Matrix mMatrix_rotate;
	protected Matrix mMatrix_scale;
	protected Matrix mMatrix_default;

	protected Matrix mDrawMatrix;

	protected Point mSize;// 精灵大小
	protected RectF mContactRect;// 精灵判定矩形
	protected boolean isTouchable = false;
	protected boolean isVisibility = true;
	protected boolean isPhysical = true;

	/**
	 * 根据图片创建精灵
	 */
	public BaseSprite(Bitmap image) {

		mImage = image;
		if (mImage != null)
			mSize = new Point((int) (mImage.getWidth() * Config.Size_Scale),
					(int) (mImage.getHeight() * Config.Size_Scale));
		else
			mSize = new Point(0, 0);
		mV = new PointF(0, 0);
		mA = new ArrayList<PointF>();

		mList_action = new ArrayList<GameAction>();

		mPaint = new Paint();
		mPaint.setFilterBitmap(true);
		// mPaint.setDither(true);
		mMatrix = new Matrix();
		mMatrix.reset();
		mMatrix_translate = new Matrix();
		mMatrix_translate.reset();
		mMatrix_rotate = new Matrix();
		mMatrix_rotate.reset();
		mMatrix_scale = new Matrix();
		mMatrix_scale.reset();
		mDrawMatrix = new Matrix();
		mMatrix_default = new Matrix();
		mMatrix_default.setScale(Config.Size_Scale, Config.Size_Scale);
		setDrawMatrix();
		mContactRect = new RectF(0, 0, mSize.x, mSize.y);
		mPos = new PointF(mContactRect.centerX(), mContactRect.centerY());
	}

	public void setImage(Bitmap image) {
		mImage = image;
		if (mImage != null)
			mSize = new Point((int) (mImage.getWidth() * Config.Size_Scale),
					(int) (mImage.getHeight() * Config.Size_Scale));
		else
			mSize = new Point(0, 0);
		float cx = mContactRect.centerX();
		float cy = mContactRect.centerY();

		mContactRect.set(mContactRect.left, mContactRect.top, mContactRect.left + mSize.x, mContactRect.top + mSize.y);
		mPos = new PointF(mContactRect.centerX(), mContactRect.centerY());
		setPosition(cx, cy);
	}

	public void draw(Canvas canvas) {
		if (!isVisibility)
			return;
		canvas.drawBitmap(mImage, mDrawMatrix, mPaint);
	}

	public void onTouch(MotionEvent event) {
		if (!isTouchable || !isVisibility)
			return;
	}

	public void physics() {
		if (mList_action.size() > 0)
			mList_action.get(0).actOnce();

		/*
		 * if (!isPhysical) return; if (mV.x != 0 || mV.y != 0) {
		 * this.setPosition(mV.x + getPosition().x, mV.y + getPosition().y); }
		 * for (int i = 0; i < mA.size(); i++) { mV.x += mA.get(i).x; mV.y +=
		 * mA.get(i).y; }
		 */
		// ro();
	}

	/*
	 * int i = 1; void ro() { this.setRotate(i%360); i+=2; }
	 */

	// ---矩阵变换---////////////////////////////////未完善

	/**
	 * 中心旋转
	 * 
	 * @param degrees
	 */
	public void setRotate(float degrees) {// 中心旋转
		mMatrix_rotate.setRotate(degrees, mContactRect.centerX(), mContactRect.centerY());
		setDrawMatrix();
	}

	/**
	 * 绕cx，cy旋转
	 * 
	 * @param degrees
	 * @param cx
	 * @param cy
	 */
	public void setRotate(float degrees, float cx, float cy) {
		mMatrix_rotate.setRotate(degrees, cx, cy);
		setDrawMatrix();
	}

	public void resetRotate() {
		mMatrix_rotate.reset();
		setDrawMatrix();
	}

	public void setScale(float sx, float sy) {// 中心缩放
		mMatrix_scale.setScale(sx, sy, mContactRect.centerX(), mContactRect.centerY());
		setDrawMatrix();
	}

	public void setScale(float sx, float sy, float cx, float cy) {
		mMatrix_scale.setScale(sx, sy, cx, cy);
		setDrawMatrix();
	}

	public void resetScale() {
		mMatrix_scale.reset();
		setDrawMatrix();
	}

	private void setDrawMatrix()// 未完善 相乘顺序
	{
		mMatrix.reset();
		mMatrix.postConcat(mMatrix_default);
		mMatrix.postConcat(mMatrix_translate);
		mMatrix.postConcat(mMatrix_rotate);
		mMatrix.postConcat(mMatrix_scale);
		mDrawMatrix.set(mMatrix);
	}

	public void resetMatrix() {
		mDrawMatrix.reset();
		mMatrix.reset();
		mMatrix_rotate.reset();
		mMatrix_scale.reset();
		mMatrix_translate.reset();
		mContactRect.offsetTo(0, 0);

	}

	// ----judge---//////////////////

	public boolean isContact(float x, float y) {
		if (isTouchable && isVisibility)
			return mContactRect.contains((int) (x + 0.5), (int) (y + 0.5));
		else
			return false;
	}

	// ---get_set---//////////////////////////////////

	public void setSpeed(float x, float y) {
		mV.set(x, y);
	}

	public PointF getSpeed() {
		PointF v = new PointF();
		v.set(mV);
		return v;
	}

	public void addAccSpeed(PointF a) {
		mA.add(a);
	}

	public void resetAccList() {
		mA.clear();
	}

	public List<PointF> getAccList() {
		return mA;
	}

	public void setPosition(float x, float y) {
		mMatrix_translate.setTranslate(x + (mContactRect.left - mContactRect.centerX()), y
				+ (mContactRect.top - mContactRect.centerY()));
		mContactRect.offsetTo(x + (mContactRect.left - mContactRect.centerX()),
				y + (mContactRect.top - mContactRect.centerY()));
		setDrawMatrix();
	}

	public void setPosition(PointF p) {
		mMatrix_translate.setTranslate(p.x + (mContactRect.left - mContactRect.centerX()), p.y
				+ (mContactRect.top - mContactRect.centerY()));
		mContactRect.offsetTo(p.x + (mContactRect.left - mContactRect.centerX()), p.y
				+ (mContactRect.top - mContactRect.centerY()));
		setDrawMatrix();
	}

	public float getX() {
		return mContactRect.centerX();
	}

	public float getY() {
		return mContactRect.centerY();
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void setTag(Object tag) {
		mTag = tag;
	}

	public Object getTag() {
		return mTag;
	}

	public PointF getPosition() {
		return new PointF(mContactRect.centerX(), mContactRect.centerY());
	}

	public void setVisibility(boolean flag) {
		isVisibility = flag;
	}

	public void setIsFilterBitmap(boolean flag) {
		mPaint.setFilterBitmap(flag);
	}

	public boolean getVisibility() {
		return isVisibility;
	}

	public void setTouchable(boolean flag) {
		isTouchable = flag;
	}

	public boolean getTouchable() {
		return isTouchable;
	}

	public RectF getRect() {
		return new RectF(mContactRect);
	}

	// ---Action---//////////////////////////

	public void runAction(GameAction action) {
		action.setSprite(this);
		mList_action.add(action);
	}

	public void clearAction() {
		mList_action.clear();

	}

	public void finishAction() {
		for (GameAction a : mList_action)
			a.finish();
	}

	public boolean removeAction(GameAction act) {
		return mList_action.remove(act);
	}

}
