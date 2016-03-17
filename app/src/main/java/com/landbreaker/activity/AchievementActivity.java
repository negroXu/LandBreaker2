package com.landbreaker.activity;

import java.util.ArrayList;
import java.util.List;

import com.landbreaker.R;
import com.landbreaker.adapter.AchievementListAdapter;
import com.landbreaker.base.GamePageActivity;
import com.landbreaker.config.Config;
import com.landbreaker.listener.GamePageChangeListener;
import com.landbreaker.testdata.AchievementData;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.GameMenuView;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

//称号成就界面
public class AchievementActivity extends GamePageActivity {

    private ImageView mIv_state = null;

    private Bitmap[] bms_state = null;
    private Bitmap bm_item_bg = null;
    private Bitmap bm_item_ico_bg = null;
    private Bitmap bm_item_isComplete = null;

    private List<AchievementData> mList_data_all = null;
    private List<AchievementData> mList_data_completed = null;
    private List<AchievementData> mList_data_no_completed = null;

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        mIv_state = (ImageView) getLayoutInflater().inflate(R.layout.item_achievement_ranking_head, null);
        addViewInCenterLayer(mIv_state, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mMenu.setActiviyTag(GameMenuView.FAMES);
    }

    @Override
    protected void loadImage() {
        // TODO Auto-generated method stub
        super.loadImage();
        bms_state = new Bitmap[]{mImgReader.load("fames/zi_all.png"), mImgReader.load("fames/zi_weidacheng.png"),
                mImgReader.load("fames/zi_yidacheng.png")};
        bm_item_bg = mImgReader.load("fames/beijingkuang.png");
        bm_item_ico_bg = mImgReader.load("fames/daojuxianshikuang.png");
        bm_item_isComplete = mImgReader.load("fames/get.png");
    }

    @Override
    protected void setItem() {
        // TODO Auto-generated method stub
        super.setItem();
        setImageView(mIv_state, Config.RankingUILayout.type, bms_state[1], null, null);
    }

    @Override
    protected void initViewPager(boolean custom) {
        // TODO Auto-generated method stub
        super.initViewPager(custom);
        mViewPager.setOnPageChangeListener(new GamePageChangeListener(mIv_state, bms_state, mIv_left, mIv_right,
                mList_view.size()));
        mViewPager.setCurrentItem(1);
    }

    @Override
    protected void addViewInPager() {
        // TODO Auto-generated method stub
        super.addViewInPager();
        initViewList();
    }

    private void initViewList() {
        // TODO Auto-generated method stub
        mList_data_all = TestData.getAchievementData(this);
        mList_data_completed = new ArrayList<AchievementData>();
        mList_data_no_completed = new ArrayList<AchievementData>();
        for (AchievementData data : mList_data_all) {
            if (data.isComplete)
                mList_data_completed.add(data);
            else
                mList_data_no_completed.add(data);
        }

        ListView all = (ListView) getLayoutInflater().inflate(R.layout.list_achievement, null);
        all.setDividerHeight((int) (Config.AchievementUILayout.item_dividerHeight * Config.Size_Scale));
        all.setAdapter(new AchievementListAdapter(this, mList_data_all, bm_item_bg, bm_item_ico_bg, bm_item_isComplete));
        mList_view.add(all);

        ListView no_complete = (ListView) getLayoutInflater().inflate(R.layout.list_achievement, null);
        no_complete.setDividerHeight((int) (Config.AchievementUILayout.item_dividerHeight * Config.Size_Scale));
        no_complete.setAdapter(new AchievementListAdapter(this, mList_data_no_completed, bm_item_bg, bm_item_ico_bg,
                bm_item_isComplete));
        mList_view.add(no_complete);

        ListView complete = (ListView) getLayoutInflater().inflate(R.layout.list_achievement, null);
        complete.setDividerHeight((int) (Config.AchievementUILayout.item_dividerHeight * Config.Size_Scale));
        complete.setAdapter(new AchievementListAdapter(this, mList_data_completed, bm_item_bg, bm_item_ico_bg,
                bm_item_isComplete));
        mList_view.add(complete);
    }


}
