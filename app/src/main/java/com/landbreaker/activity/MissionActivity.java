package com.landbreaker.activity;

import java.util.List;

import android.graphics.Bitmap;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import com.landbreaker.R;
import com.landbreaker.adapter.MissionListAdapter;
import com.landbreaker.base.GameActivity;
import com.landbreaker.config.Config;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.MissionData;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.GameMenuView;

public class MissionActivity extends GameActivity {

	private ListView mListView = null;
	private Bitmap bm_mission_logo = null;
	private MissionListAdapter mListAdapter = null;
	private List<MissionData> mList_data = null;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		addViewInCenterLayerFromXML(R.layout.list_mission_and_new, LayoutParams.MATCH_PARENT,
				(int) ((Config.PagerUILayout.page_bottom - Config.PagerUILayout.page_top) * Config.Size_Scale));
		mListView = (ListView) findViewById(R.id.lv_mission_and_new);
		mMenu.setActiviyTag(GameMenuView.MISSION);
	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		// bm_mission_logo = mImgReader.load("set/yuangoulogo.png");
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		GameUISetting.setViewY(mListView, Config.PagerUILayout.page_top);
		mListView.setDividerHeight((int) ((Config.MissionUILayout.list_text_marginTop + 50) * Config.Size_Scale));
		initListView();
	}

	private void initListView() {
		// TODO Auto-generated method stub

		mList_data = TestData.getMissionData(this);
		mListAdapter = new MissionListAdapter(this, mList_data, bm_mission_logo);
		mListView.setAdapter(mListAdapter);

	}

}
