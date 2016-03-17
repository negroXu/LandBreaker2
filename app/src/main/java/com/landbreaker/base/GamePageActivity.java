package com.landbreaker.base;

import java.util.ArrayList;
import java.util.List;

import com.landbreaker.R;
import com.landbreaker.adapter.GamePagerAdapter;
import com.landbreaker.config.Config;
import com.landbreaker.logic.GameUISetting;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 带有翻页的gameActivity
 */
public class GamePageActivity extends GameActivity {
	protected final static int LEFT_BUTTON = -2;
	protected final static int RIGHT_BUTTON = -1;

	protected ViewPager mViewPager = null;

	protected ImageView mIv_left = null;
	protected ImageView mIv_right = null;

	protected List<View> mList_view = null;
	protected GamePagerAdapter mPagerAdapter = null;

	protected Bitmap bm_left = null;
	protected Bitmap bm_right = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initViewPager(false);
	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		bm_left = mImgReader.load("fames/jiantouzuo.png");
		bm_right = mImgReader.load("fames/jiantouyou.png");
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		addViewInCenterLayerFromXML(R.layout.activity_page, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mViewPager = (ViewPager) findViewById(R.id.vp_page);
		mIv_left = (ImageView) findViewById(R.id.iv_page_left);
		mIv_right = (ImageView) findViewById(R.id.iv_page_right);
		mList_view = new ArrayList<View>();
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		setImageView(mIv_left, Config.PagerUILayout.left, bm_left, LEFT_BUTTON, new ItemOnTouchListener(bm_left));
		setImageView(mIv_right, Config.PagerUILayout.right, bm_right, RIGHT_BUTTON, new ItemOnTouchListener(bm_right));
	}

	/**
	 * 初始化翻页page
	 * @param custom 是否自定义大小
	 */
	protected void initViewPager(boolean custom) {
		// TODO Auto-generated method stub
		if (!custom) {
			mViewPager.getLayoutParams().height = (int) ((Config.PagerUILayout.page_bottom - Config.PagerUILayout.page_top) * Config.Size_Scale);
			GameUISetting.setViewY(mViewPager, Config.PagerUILayout.page_top);
		}

		addViewInPager();

		mPagerAdapter = new GamePagerAdapter(mList_view);
		mViewPager.setAdapter(mPagerAdapter);
	}

	/**
	 * 在页面中加入view
	 */
	protected void addViewInPager() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		switch (key) {
		case LEFT_BUTTON:
			_left();
			break;
		case RIGHT_BUTTON:
			_right();
			break;
		default:
			break;
		}
	}

	/**
	 * 翻页向左
	 */
	protected void _left() {
		mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
	}

	/**
	 * 翻页向右
	 */
	protected void _right() {
		mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
	}
}
