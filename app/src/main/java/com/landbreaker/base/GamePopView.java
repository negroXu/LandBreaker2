package com.landbreaker.base;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.listener.GameButtonTouchListener;
import com.landbreaker.logic.GameUISetting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GamePopView extends RelativeLayout {
	protected ImageView mIv_back = null;
	protected Bitmap[] bms_back = null;
	protected boolean isShow = false;
	protected Context mContext;

	protected final static int BACK_BUTTON = 0;

	public GamePopView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GamePopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GamePopView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public void init(Context context) {
		mContext = context;
		setClickable(true);
		setBackgroundResource(R.color.bg_transparent_default);
		setVisibility(View.GONE);
		mIv_back = new ImageView(context);

		addViewInThis(mIv_back, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	}

	public void loadImage(ImgReader reader) {
		bms_back = new Bitmap[] { reader.load("menu/back_up.png"), reader.load("menu/back_down.png") };
	}

	public void setItem() {
		setImageView(mIv_back, Config.MenuUILayout.back, bms_back[0], BACK_BUTTON,
				new ViewItemOnTouchListener(bms_back));
	}

	public void show() {
		setVisibility(View.VISIBLE);
	}

	public void hidden() {
		setVisibility(View.GONE);
	}

	// 设置控件layout
	protected void setImageView(ImageView view, Point center, Bitmap bm, Object tag, ViewItemOnTouchListener listener) {
		view.setImageBitmap(bm);
		view.setTag(tag);
		view.setOnTouchListener(listener);
		GameUISetting.setViewCenterPosition(view, center, bm.getWidth(), bm.getHeight());
		if (bm != null)
			GameUISetting.setViewWidthHeight(view, bm);
	}

	// 设置控件layout URL
	protected void setImageViewUrl(ImageView view, Point center, Bitmap bm, String picUrl, Object tag, ViewItemOnTouchListener listener) {
//		view.setImageBitmap(bm);
		Config.finalBitmap(mContext).display(view, picUrl);
		view.setTag(tag);
		view.setOnTouchListener(listener);
		GameUISetting.setViewCenterPosition(view, center, bm.getWidth(), bm.getHeight());
		if (bm != null)
			GameUISetting.setViewWidthHeight(view, bm);
	}

	protected class ViewItemOnTouchListener extends GameButtonTouchListener {
		public ViewItemOnTouchListener(Bitmap[] bms) {
			super(bms);
			// TODO Auto-generated constructor stub
		}

		public ViewItemOnTouchListener(Bitmap bm_up, Bitmap bm_down) {
			super(bm_up, bm_down);
		}

		public ViewItemOnTouchListener() {
			super();
		}

		@Override
		public void touchEvent(Object tag) {
			// TODO Auto-generated method stub
			super.touchEvent(tag);
			int key = (Integer) tag;
			switch (key) {
			case BACK_BUTTON:
				_back();
				break;
			default:
				break;
			}
			_touchEventAction(key);
		}

	}

	protected void _back() {
		hidden();
	}

	protected void _touchEventAction(int key) {

	}

	/**
	 * 添加view
	 * 
	 * @param view
	 * @param width
	 * @param height
	 */
	protected void addViewInThis(View view, int width, int height) {
		addView(view, new RelativeLayout.LayoutParams(width, height));
	}

	/**
	 * 从资源文件添加view
	 * 
	 * @param res
	 * @param width
	 * @param height
	 */
	protected void addViewInThisFromXML(int res, int width, int height) {
		addView(((Activity) mContext).getLayoutInflater().inflate(res, null), new RelativeLayout.LayoutParams(width,
				height));
	}
}
