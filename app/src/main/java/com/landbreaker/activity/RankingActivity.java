package com.landbreaker.activity;

import com.landbreaker.R;
import com.landbreaker.adapter.RankingListAdapter;
import com.landbreaker.base.GamePageActivity;
import com.landbreaker.config.Config;
import com.landbreaker.listener.GamePageChangeListener;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.GameMenuView;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class RankingActivity extends GamePageActivity {

	private ImageView mIv_type = null;

	private Bitmap[] bms_type = null;
	private Bitmap bm_item_bg = null;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		mIv_type = (ImageView) getLayoutInflater().inflate(R.layout.item_achievement_ranking_head, null);
		addViewInCenterLayer(mIv_type, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mMenu.setActiviyTag(GameMenuView.RANKING);

	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		bms_type = new Bitmap[] { mImgReader.load("rank/zi_geren.png"), mImgReader.load("rank/zi_quyu.png"),
				mImgReader.load("rank/zi_shijie.png") };
		bm_item_bg = mImgReader.load("rank/paihangbg.png");
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		setImageView(mIv_type, Config.RankingUILayout.type, bms_type[1], null, null);
	}

	@Override
	protected void addViewInPager() {
		// TODO Auto-generated method stub
		mList_view.add(initMyRankPage());
		mList_view.add(initRankList_area());
		mList_view.add(initRankList_world());
	}

	@Override
	protected void initViewPager(boolean custom) {
		// TODO Auto-generated method stub
		super.initViewPager(custom);
		mViewPager.setOnPageChangeListener(new GamePageChangeListener(mIv_type, bms_type, mIv_left, mIv_right,
				mList_view.size()));
		mViewPager.setCurrentItem(1);
	}

	private View initMyRankPage() {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.page_ranking, null);
		ImageView icon = (ImageView) view.findViewById(R.id.iv_ranking_my_ico);
		TextView name = (TextView) view.findViewById(R.id.tv_ranking_name);
		TextView collects = (TextView) view.findViewById(R.id.tv_ranking_collection);
		// layout
		icon.getLayoutParams().width = (int) (Config.RankingUILayout.myIco_size.x * Config.Size_Scale);
		icon.getLayoutParams().height = (int) (Config.RankingUILayout.myIco_size.y * Config.Size_Scale);
		GameUISetting.setViewY(icon, Config.RankingUILayout.myIco_top);
		GameUISetting.setViewY(name, Config.RankingUILayout.myName_top);

		setItemlayout(collects, Config.RankingUILayout.collection);

		// data
		icon.setImageBitmap(mImgReader.load("rank/gerentouxiang_moren.jpg"));

		return view;
	}

	private View initRankList_area() {
		// TODO Auto-generated method stub
		ListView view = (ListView) getLayoutInflater().inflate(R.layout.list_ranking, null);
		view.setAdapter(new RankingListAdapter(this, TestData.getRankData_Area(this), bm_item_bg));
		return view;
	}

	private View initRankList_world() {
		// TODO Auto-generated method stub
		ListView view = (ListView) getLayoutInflater().inflate(R.layout.list_ranking, null);
		view.setAdapter(new RankingListAdapter(this, TestData.getRankData_Area(this), bm_item_bg));
		return view;
	}

	private void setItemlayout(View view, Point position) {
		GameUISetting.setViewX(view, position.x);
		GameUISetting.setViewY(view, position.y);
	}

}
