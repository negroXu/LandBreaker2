package com.landbreaker.base;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.listener.GameButtonTouchListener;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.view.GameBackground;
import com.landbreaker.view.GameMenuView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import net.tsz.afinal.FinalBitmap;

public class GameActivity extends Activity {
	/**
	 * 图片读取器
	 */
	protected ImgReader mImgReader = null;
	/**
	 * 动态背景
	 */
	protected GameBackground mGbg_bg = null;
	protected ImageView mIv_menu = null;// 返回按键
	protected Bitmap[] bms_menu = null;
	protected Bitmap bm_bg = null;
	protected RelativeLayout mRl_center = null;// 中间层
	protected RelativeLayout mRl_front = null;// 顶层

	protected GameMenuView mMenu = null;

	protected boolean hasMenu = true;
	protected final static int MENU_BUTTON = 0;
	protected FinalBitmap finalBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Config.Size_Scale = Config.scaleSize(this);
		setContentView(R.layout.activity_game);
		mImgReader = new ImgReader(this);
		mMenu = new GameMenuView(this);

		finalBitmap = Config.finalBitmap(this);

		init();
		loadImage();
		setItem();
		addViewInFront(mMenu, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	/**
	 * 初始化控件
	 */
	protected void init() {
		// TODO Auto-generated method stub
		mRl_center = (RelativeLayout) findViewById(R.id.rl_center);
		mRl_front = (RelativeLayout) findViewById(R.id.rl_front);
		mGbg_bg = (GameBackground) findViewById(R.id.gbg_page_bg);
		mIv_menu = (ImageView) findViewById(R.id.iv_page_back);
		if (hasMenu)
			mMenu.init(this);
	}

	/**
	 * 读取图片
	 */
	protected void loadImage() {
		// TODO Auto-generated method stub

		bm_bg = mImgReader.load("bg/beijingjiaochan.png");
		bms_menu = new Bitmap[] { mImgReader.load("gameui/menu_up.png"), mImgReader.load("gameui/menu_down.png") };
		if (hasMenu)
			mMenu.loadImage(mImgReader);
	}

	/**
	 * 设置控件参数
	 */
	protected void setItem() {
		// TODO Auto-generated method stub

		mGbg_bg.setDrawOption(bm_bg, Config.getWindowsRect(this), -1, -1);
		setImageView(mIv_menu, Config.MenuUILayout.back, bms_menu[0], MENU_BUTTON, new ItemOnTouchListener(bms_menu));
		if (hasMenu)
			mMenu.setItem();
	}

	// 设置控件layout 使用bitmap
	protected void setImageView(ImageView view, Point center, Bitmap bm, Object tag, GameButtonTouchListener listener) {
		view.setImageBitmap(bm);
		view.setTag(tag);
		view.setOnTouchListener(listener);
		if (bm != null)
			GameUISetting.setViewWidthHeight(view, bm);
		if (center != null)
			GameUISetting.setViewCenterPosition(view, center, bm.getWidth(), bm.getHeight());

	}

	// 设置控件layout  使用URL
	protected void setImageViewURL(ImageView view, Point center, Bitmap bm ,String url, Object tag, GameButtonTouchListener listener) {

		finalBitmap.display(view,url);
//		view.setImageBitmap(bm);
		view.setTag(tag);
		view.setOnTouchListener(listener);
		if (bm != null)
			GameUISetting.setViewWidthHeight(view, bm);
		if (center != null)
			GameUISetting.setViewCenterPosition(view, center, bm.getWidth(), bm.getHeight());

	}

	/**
	 * 按钮控件点击监听
	 * 
	 * @author kaiyu
	 * 
	 */
	// ---touch监听---//================================
	protected class ItemOnTouchListener extends GameButtonTouchListener {

		public ItemOnTouchListener(Bitmap[] bms) {
			super(bms);
			// TODO Auto-generated constructor stub
		}

		public ItemOnTouchListener(Bitmap bm) {
			super(bm, bm);
		}

		public ItemOnTouchListener() {
			super();
		}

		@Override
		public void touchEvent(Object tag) {
			// TODO Auto-generated method stub
			super.touchEvent(tag);
			int key = (Integer) tag;
			switch (key) {
			case MENU_BUTTON:
				_menu();
				break;
			default:
				break;
			}
			_touchEventAction(key);
		}
	}

	/**
	 * 返回按键事件
	 */
	protected void _back() {
		finish();
	}

	protected void _menu() {
		mMenu.show();
	}

	/**
	 * 其他事件接口
	 * 
	 * @param key
	 */
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub

	}

	/**
	 * 添加view
	 * 
	 * @param view
	 * @param width
	 * @param height
	 */
	protected void addViewInFront(View view, int width, int height) {
		mRl_front.addView(view, new RelativeLayout.LayoutParams(width, height));
	}

	/**
	 * 从资源文件中添加view
	 * 
	 * @param res
	 * @param width
	 * @param height
	 */
	protected void addViewInFrontFromXML(int res, int width, int height) {
		mRl_front.addView(getLayoutInflater().inflate(res, null), new RelativeLayout.LayoutParams(width, height));
	}

	/**
	 * 添加view至中间层
	 * 
	 * @param view
	 * @param width
	 * @param height
	 */
	protected void addViewInCenterLayer(View view, int width, int height) {
		mRl_center.addView(view, new RelativeLayout.LayoutParams(width, height));
	}

	/**
	 * 从资源文件添加view至中间层
	 * 
	 * @param res
	 * @param width
	 * @param height
	 */
	protected void addViewInCenterLayerFromXML(int res, int width, int height) {
		mRl_center.addView(getLayoutInflater().inflate(res, null), new RelativeLayout.LayoutParams(width, height));
	}

}
