package com.landbreaker.view;

import com.landbreaker.activity.AchievementActivity;
import com.landbreaker.activity.ItemActivity;
import com.landbreaker.activity.MissionActivity;
import com.landbreaker.activity.NewActivity;
import com.landbreaker.activity.RankingActivity;
import com.landbreaker.activity.SettingActivity;
import com.landbreaker.activity.ShopActivity;
import com.landbreaker.base.GamePopView;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class GameMenuView extends GamePopView {

	public final static int RANKING = 1;
	public final static int ITEMS = 2;
	public final static int FAMES = 3;// 称号
	public final static int SHOP = 4;
	public final static int DIG = 5;
	public final static int MISSION = 6;
	public final static int NEWS = 7;
	public final static int SETTING = 8;

	private ImageView mIv_bg = null;
	public ImageView mIv_ranking = null;
	public ImageView mIv_item = null;
	public ImageView mIv_achievement = null;
	public ImageView mIv_shop = null;
	public ImageView mIv_dig = null;
	public ImageView mIv_mission = null;
	public ImageView mIv_news = null;
	public ImageView mIv_setting = null;
	public ImageView[] mIvs_item = new ImageView[] { mIv_ranking, mIv_item, mIv_achievement, mIv_shop, mIv_dig,
			mIv_mission, mIv_news, mIv_setting };

	private Bitmap bm_bg = null;
	private Bitmap[] bms_item_up;
	private Bitmap[] bms_item_down;

	private int TAG = 0;

	public GameMenuView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(Context context) {
		// TODO Auto-generated method stub
		super.init(context);
		mIv_bg = new ImageView(context);
		addViewInThis(mIv_bg, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < mIvs_item.length; i++) {
			mIvs_item[i] = new ImageView(context);
			addViewInThis(mIvs_item[i], LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}

	}

	@Override
	public void loadImage(ImgReader reader) {
		// TODO Auto-generated method stub
		super.loadImage(reader);
		bm_bg = reader.load("menu/beijingkuang.png");
		bms_item_up = reader.loadMenuItem_up();
		bms_item_down = reader.loadMenuItem_down();
	}

	@Override
	public void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		setImageView(mIv_bg, Config.MenuUILayout.menu_bg, bm_bg, null, null);
		for (int i = 0; i < mIvs_item.length; i++) {
			setImageView(mIvs_item[i], Config.MenuUILayout.menu_item[i], bms_item_up[i], i + 1,
					new ViewItemOnTouchListener(bms_item_up[i], bms_item_down[i]));
		}
	}

	@Override
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		if (TAG == key) {
			hidden();
			return;
		}
		super._touchEventAction(key);
		switch (key) {
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
			toDig();
			break;
		case MISSION:
			toMission();
			break;
		case NEWS:
			toNews();
			break;
		case SETTING:
			toSetting();
			break;
		default:
			break;
		}
	}

	private void toRanking() {
		Intent intent = new Intent(mContext, RankingActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	private void toItems() {
		Intent intent = new Intent(mContext, ItemActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	private void toAchievement() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext, AchievementActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	private void toShop() {
		Intent intent = new Intent(mContext, ShopActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	private void toDig() {
		((Activity) mContext).finish();
	}

	private void toMission() {
		Intent intent = new Intent(mContext, MissionActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	private void toNews() {
		Intent intent = new Intent(mContext, NewActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	private void toSetting() {
		Intent intent = new Intent(mContext, SettingActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).finish();
	}

	public void setActiviyTag(int tag) {
		TAG = tag;
	}

}
