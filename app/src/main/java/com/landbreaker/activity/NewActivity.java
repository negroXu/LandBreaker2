package com.landbreaker.activity;

import java.util.List;

import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import com.landbreaker.R;
import com.landbreaker.adapter.NewListAdapter;
import com.landbreaker.base.GameActivity;
import com.landbreaker.config.Config;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.AnnouncementData;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.GameMenuView;

public class NewActivity extends GameActivity {

	private ListView mListView = null;
	private NewListAdapter mListAdapter = null;
	private List<AnnouncementData> mList_data = null;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		addViewInCenterLayerFromXML(R.layout.list_mission_and_new, LayoutParams.MATCH_PARENT,
				(int) ((Config.PagerUILayout.page_bottom - Config.PagerUILayout.page_top) * Config.Size_Scale));
		mListView = (ListView) findViewById(R.id.lv_mission_and_new);
		mMenu.setActiviyTag(GameMenuView.NEWS);
	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		GameUISetting.setViewY(mListView, Config.PagerUILayout.page_top);
		mListView.setDividerHeight((int) (Config.NewUILayout.item_interval * Config.Size_Scale));
		initListView();
	}

	private void initListView() {
		// TODO Auto-generated method stub

		mList_data = TestData.getAnnouncementDataData(this);
		mListAdapter = new NewListAdapter(this, mList_data);
		mListView.setAdapter(mListAdapter);

	}
}
