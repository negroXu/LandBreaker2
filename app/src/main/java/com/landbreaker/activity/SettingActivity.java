package com.landbreaker.activity;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.landbreaker.R;
import com.landbreaker.base.GameActivity;
import com.landbreaker.config.Config;
import com.landbreaker.view.GameMenuView;

public class SettingActivity extends GameActivity {

	private ImageView mIv_BGM = null;
	private ImageView mIv_SE = null;
	private ImageView mIv_BGM_switch = null;
	private ImageView mIv_SE_switch = null;
	private ImageView mIv_language = null;

	private Bitmap bm_BGM = null;
	private Bitmap bm_SE = null;
	private Bitmap[] bms_switch = null;
	private Bitmap[] bms_language = null;

	private final static int BGM_SWITCH = 1;
	private final static int SE_SWITCH = 2;
	private final static int LANGUAGE_BUTTON = 3;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		addViewInCenterLayerFromXML(R.layout.activity_setting, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_BGM = (ImageView) findViewById(R.id.iv_setting_bgm);
		mIv_SE = (ImageView) findViewById(R.id.iv_setting_se);
		mIv_BGM_switch = (ImageView) findViewById(R.id.iv_setting_bgmSwitch);
		mIv_SE_switch = (ImageView) findViewById(R.id.iv_setting_seSwitch);
		mIv_language = (ImageView) findViewById(R.id.iv_setting_language);
		mMenu.setActiviyTag(GameMenuView.SETTING);
	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		bm_BGM = mImgReader.load("set/bgm.png");
		bm_SE = mImgReader.load("set/se.png");
		bms_switch = new Bitmap[] { mImgReader.load("set/on.png"), mImgReader.load("set/off.png") };
		bms_language = new Bitmap[] { mImgReader.load("set/yuyanxuanze_up.png"),
				mImgReader.load("set/yuyanxuanze_down.png") };
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();

		setImageView(mIv_BGM, Config.SettingUILayout.bgm, bm_BGM, null, null);
		setImageView(mIv_SE, Config.SettingUILayout.se, bm_SE, null, null);

		setImageView(mIv_BGM_switch, Config.SettingUILayout.bgm_switch, Config.Value.BGM_ON ? bms_switch[0]
				: bms_switch[1], BGM_SWITCH, new ItemOnTouchListener());

		setImageView(mIv_SE_switch, Config.SettingUILayout.se_switch, Config.Value.SE_ON ? bms_switch[0]
				: bms_switch[1], SE_SWITCH, new ItemOnTouchListener());

		setImageView(mIv_language, Config.SettingUILayout.language, bms_language[0], LANGUAGE_BUTTON,
				new ItemOnTouchListener(bms_language));
	}

	@Override
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		switch (key) {
		case LANGUAGE_BUTTON:

			break;
		case BGM_SWITCH:
			switchBGM();
			break;
		case SE_SWITCH:
			switchSE();
			break;
		default:
			break;
		}
	}

	private void switchBGM() {
		// TODO Auto-generated method stub
		if (Config.Value.BGM_ON)
			mIv_BGM_switch.setImageBitmap(bms_switch[1]);
		else
			mIv_BGM_switch.	setImageBitmap(bms_switch[0]);
		Config.Value.BGM_ON = !Config.Value.BGM_ON;
	}

	private void switchSE() {
		// TODO Auto-generated method stub
		if (Config.Value.SE_ON)
			mIv_SE_switch.setImageBitmap(bms_switch[1]);
		else
			mIv_SE_switch.setImageBitmap(bms_switch[0]);
		Config.Value.SE_ON = !Config.Value.SE_ON;
	}

}
