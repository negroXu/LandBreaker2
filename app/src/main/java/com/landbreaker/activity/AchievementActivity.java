package com.landbreaker.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landbreaker.R;
import com.landbreaker.adapter.AchievementListAdapter;
import com.landbreaker.base.GamePageActivity;
import com.landbreaker.config.Config;
import com.landbreaker.database.Item_BASICARCHIVEMENT;
import com.landbreaker.database.Item_GLOBALARCHIVEMENT_IN_PROGRESS;
import com.landbreaker.database.Table_BASICARCHIVEMENT;
import com.landbreaker.database.Table_GLOBALARCHIVEMENT_IN_PROGRESS;
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


    //数据库
    private Table_BASICARCHIVEMENT table_basicarchivement = null;
    private Table_GLOBALARCHIVEMENT_IN_PROGRESS table_globalarchivement_in_progress= null;

    private List<Item_BASICARCHIVEMENT> basicArchivementList = new ArrayList<Item_BASICARCHIVEMENT>();
    private Map<Integer, Item_BASICARCHIVEMENT> basicArchivementMap = new HashMap<Integer, Item_BASICARCHIVEMENT>();
    List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> basicArchivementListCompleted = new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>();
    List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> basicArchivementListNoCompleted = new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>();
    private List<Item_BASICARCHIVEMENT> mList_data_completed = new ArrayList<Item_BASICARCHIVEMENT>();;
    private List<Item_BASICARCHIVEMENT> mList_data_no_completed = new ArrayList<Item_BASICARCHIVEMENT>();;


    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        mIv_state = (ImageView) getLayoutInflater().inflate(R.layout.item_achievement_ranking_head, null);
        addViewInCenterLayer(mIv_state, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mMenu.setActiviyTag(GameMenuView.FAMES);

        // 获取基础成就信息
        table_basicarchivement = new Table_BASICARCHIVEMENT(getApplicationContext());
        basicArchivementList = table_basicarchivement.getAll();
        table_basicarchivement.close();
        table_basicarchivement = null;
        for (Item_BASICARCHIVEMENT basicArchivement : basicArchivementList) {
            basicArchivementMap.put(basicArchivement.getId(), basicArchivement);
        }

        // 成就进度
        table_globalarchivement_in_progress = new Table_GLOBALARCHIVEMENT_IN_PROGRESS(getApplicationContext());
        // 未完成
        basicArchivementListNoCompleted.addAll(table_globalarchivement_in_progress.getSTATUS(1));
        // 已完成
        basicArchivementListCompleted.addAll(table_globalarchivement_in_progress.getSTATUS(2));
        table_globalarchivement_in_progress.close();
        table_globalarchivement_in_progress = null;

        for(Item_GLOBALARCHIVEMENT_IN_PROGRESS item_globalarchivement_in_progress : basicArchivementListNoCompleted){
            mList_data_no_completed.add(basicArchivementMap.get(item_globalarchivement_in_progress.arch_id));
        }

        for(Item_GLOBALARCHIVEMENT_IN_PROGRESS item_globalarchivement_in_progress : basicArchivementListCompleted){
            mList_data_completed.add(basicArchivementMap.get(item_globalarchivement_in_progress.arch_id));
        }
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
//        // TODO Auto-generated method stub
//        mList_data_all = TestData.getAchievementData(this);
//        mList_data_completed = new ArrayList<AchievementData>();
//        mList_data_no_completed = new ArrayList<AchievementData>();
//        for (AchievementData data : mList_data_all) {
//            if (data.isComplete)
//                mList_data_completed.add(data);
//            else
//                mList_data_no_completed.add(data);
//        }

        ListView all = (ListView) getLayoutInflater().inflate(R.layout.list_achievement, null);
        all.setDividerHeight((int) (Config.AchievementUILayout.item_dividerHeight * Config.Size_Scale));
        all.setAdapter(new AchievementListAdapter(this, basicArchivementList, bm_item_bg, bm_item_ico_bg, bm_item_isComplete,false));
        mList_view.add(all);

        ListView no_complete = (ListView) getLayoutInflater().inflate(R.layout.list_achievement, null);
        no_complete.setDividerHeight((int) (Config.AchievementUILayout.item_dividerHeight * Config.Size_Scale));
        no_complete.setAdapter(new AchievementListAdapter(this, mList_data_no_completed, bm_item_bg, bm_item_ico_bg,
                bm_item_isComplete,false));
        mList_view.add(no_complete);

        ListView complete = (ListView) getLayoutInflater().inflate(R.layout.list_achievement, null);
        complete.setDividerHeight((int) (Config.AchievementUILayout.item_dividerHeight * Config.Size_Scale));
        complete.setAdapter(new AchievementListAdapter(this, mList_data_completed, bm_item_bg, bm_item_ico_bg,
                bm_item_isComplete,true));
        mList_view.add(complete);
    }

}
