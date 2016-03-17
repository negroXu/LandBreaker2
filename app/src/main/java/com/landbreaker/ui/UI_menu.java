package com.landbreaker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.landbreaker.activity.AchievementActivity;
import com.landbreaker.activity.ItemActivity;
import com.landbreaker.activity.MissionActivity;
import com.landbreaker.activity.NewActivity;
import com.landbreaker.activity.RankingActivity;
import com.landbreaker.activity.SettingActivity;
import com.landbreaker.activity.ShopActivity;
import com.landbreaker.base.BaseSprite;
import com.landbreaker.base.GameButton;
import com.landbreaker.config.Config;

/**
 * 系统菜单
 * 
 * @author kaiyu
 * 
 */
public class UI_menu {

	private Context mContext;

	private GameButton mUI_back;
	private BaseSprite mUI_menu_bg;

	private GameButton mUI_ranking;
	private GameButton mUI_items;
	private GameButton mUI_achievement;
	private GameButton mUI_shop;
	private GameButton mUI_dig;
	private GameButton mUI_mission;
	private GameButton mUI_news;
	private GameButton mUI_setup;
	private GameButton[] mList_item = new GameButton[] { mUI_ranking, mUI_items, mUI_achievement, mUI_shop, mUI_dig,
			mUI_mission, mUI_news, mUI_setup };

	private PointF mP_back;
	private PointF[] mP_items;
	private PointF mP_bg;
	private boolean isVisibility = false;
	private boolean isTouchable = false;

	private final static int RANKING = 0;
	private final static int ITEMS = 1;
	private final static int FAMES = 2;
	private final static int SHOP = 3;
	private final static int DIG = 4;
	private final static int MISSINON = 5;
	private final static int NEWS = 6;
	private final static int SETUP = 7;

	/**
	 * 创建系统菜单
	 * 
	 * @param bm_up
	 *            数组最后位为返回按键图片
	 * @param bm_down
	 *            数组最后位为返回按键图片
	 * @param bg
	 */
	public UI_menu(Bitmap[] bm_up, Bitmap[] bm_down, Bitmap bg, Context context) {
		mContext = context;

		mUI_back = new GameButton(bm_up[bm_up.length - 1]);
		mUI_back.setImageDown(bm_down[bm_down.length - 1]);
		mUI_menu_bg = new GameButton(bg);

		// init item
		for (int i = 0; i < mList_item.length; i++) {
			mList_item[i] = new GameButton(bm_up[i]);
			mList_item[i].setImageDown(bm_down[i]);
		}

		isVisibility = false;
		isTouchable = false;

	}

	/**
	 * 
	 * @param p_bg
	 * @param p_items
	 * @param p_back
	 * @param s
	 *            缩放系数 scaleSize
	 */
	public void setPosition(Point p_bg, Point[] p_items, Point p_back, float s) {
		mP_back = new PointF(p_back.x * s, p_back.y * s);
		mUI_back.setPosition(mP_back.x, mP_back.y);

		mP_bg = new PointF(p_bg.x * s, p_bg.y * s);
		mUI_menu_bg.setPosition(mP_bg.x, mP_bg.y);

		mP_items = new PointF[p_items.length];
		for (int i = 0; i < p_items.length; i++) {
			mP_items[i] = new PointF(p_items[i].x * s, p_items[i].y * s);
			mList_item[i].setPosition(mP_items[i].x, mP_items[i].y);
		}
	}

	public void show() {
		isVisibility = true;
		isTouchable = true;
	}

	public void close() {
		isVisibility = false;
		isTouchable = false;
	}

	public boolean isShowing() {
		return isVisibility;
	}

	public void draw(Canvas canvas) {
		if (!isVisibility)
			return;
		canvas.drawARGB(Config.Value.curtain_Alpha_80, 0, 0, 0);// 80%黑背景

		mUI_back.draw(canvas);

		mUI_menu_bg.draw(canvas);
		for (int i = 0; i < mList_item.length; i++)
			mList_item[i].draw(canvas);

	}

	public void touchEvent(MotionEvent event) {
		if (!isTouchable)
			return;
		_touchEvent_back(event);
		_touchEvnet_item(event);
	}

	private void _touchEvent_back(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mUI_back.isContact(event.getX(), event.getY()))
				mUI_back.down();
			break;
		case MotionEvent.ACTION_UP:
			if (mUI_back.isDown()) {
				mUI_back.up();
				if (mUI_back.isContact(event.getX(), event.getY())) {
					close();
				}
			}
			break;
		default:
			break;
		}
	}

	private void _touchEvnet_item(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < mList_item.length; i++)
				if (mList_item[i].isContact(event.getX(), event.getY()))
					mList_item[i].down();
			break;
		case MotionEvent.ACTION_UP:
			for (int i = 0; i < mList_item.length; i++)
				if (mList_item[i].isDown()) {
					mList_item[i].up();
					if (mList_item[i].isContact(event.getX(), event.getY())) {
						//
						startEvent(i);
						break;
					}
				}
			break;
		default:
			break;
		}
	}

	// 菜单点击事件
	private void startEvent(int i) {
		// TODO Auto-generated method stub
		switch (i) {
		case RANKING:
			toRanking();
			break;
		case ITEMS:
			toItems();
			break;
		case FAMES:
			toAchievement();
			break;
		case SHOP:
			toShop();
			break;
		case DIG:
			close();
			break;
		case MISSINON:
			toMission();
			break;
		case NEWS:
			toNews();
			break;
		case SETUP:
			toSetting();
			break;
		default:
			break;
		}
	}

	private void toRanking() {
		Intent intent = new Intent(mContext, RankingActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

	private void toItems() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext, ItemActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

	private void toAchievement() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext, AchievementActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

	private void toShop() {
		Intent intent = new Intent(mContext, ShopActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

	private void toMission() {
		Intent intent = new Intent(mContext, MissionActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

	private void toNews() {
		Intent intent = new Intent(mContext, NewActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

	private void toSetting() {
		Intent intent = new Intent(mContext, SettingActivity.class);
		((Activity) mContext).startActivityForResult(intent, 0);
	}

}
