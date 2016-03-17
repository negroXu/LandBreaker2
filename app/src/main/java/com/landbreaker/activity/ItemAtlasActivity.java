package com.landbreaker.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.landbreaker.R;
import com.landbreaker.base.GamePageActivity;
import com.landbreaker.config.Config;
import com.landbreaker.listener.GameButtonTouchListener;
import com.landbreaker.listener.GamePageChangeListener;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.ItemAtlasData;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.ItemAtlasDetailView;
import com.landbreaker.view.ItemPageScrollView;
import com.landbreaker.view.ItemPageScrollView.ScrollViewListener;

public class ItemAtlasActivity extends GamePageActivity implements ScrollViewListener {

	private ImageView mIv_rank = null;

	private ImageView mIv_info_bg = null;
	private TextView mTv_info_itemName = null;
	private TextView mTv_info_text = null;

	private Bitmap[] bms_rank = null;
	private Bitmap[] bms_item_bg = null;
	private Bitmap bm_item_lock = null;
	private Bitmap bm_info_bg = null;
	private Bitmap[] bms_backspace = null;

	private List<View> mList_item_view = null;
	private List<ItemAtlasData> mList_data = null;

	private ItemAtlasDetailView mDetailView = null;

	private int selectedItem = -1;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		hasMenu = false;
		super.init();

		mIv_rank = new ImageView(this);
		addViewInCenterLayer(mIv_rank, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		mDetailView = new ItemAtlasDetailView(this);
		addViewInFront(mDetailView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mDetailView.init(this);

		addViewInFrontFromXML(R.layout.item_info_text, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_info_bg = (ImageView) findViewById(R.id.iv_info_textbg);
		mTv_info_itemName = (TextView) findViewById(R.id.tv_info_itemName);
		mTv_info_text = (TextView) findViewById(R.id.tv_info_text);
	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		bms_backspace = new Bitmap[] { mImgReader.load("login/fanhui_up.png"), mImgReader.load("login/fanhui_down.png") };
		bm_info_bg = mImgReader.load("item/shuoming.png");
		bms_item_bg = new Bitmap[] { mImgReader.load("item/tujiangezi_up.png"),
				mImgReader.load("item/tujiangezi_down.png") };
		bm_item_lock = mImgReader.load("item/tujiangezisuoding.png");
		bms_rank = mImgReader.loadAtlasRank();
		mDetailView.loadImage(mImgReader);
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		setImageView(mIv_menu, Config.MenuUILayout.back, bms_backspace[0], MENU_BUTTON, new ItemOnTouchListener(
				bms_backspace));
		setImageView(mIv_rank, Config.ItemAtlasUILayout.rank, bms_rank[0], null, null);
		setImageView(mIv_info_bg, Config.ShopUILayout.info_bg, bm_info_bg, null, null);
		mDetailView.setItem();
		setInfoText();
	}

	private void setInfoText() {
		// TODO Auto-generated method stub
		mTv_info_itemName.setText(R.string.info_name_default);
		mTv_info_text.setText(R.string.info_text_default);
		mTv_info_itemName.setTextSize(14);
		mTv_info_text.setTextSize(14);
		GameUISetting.setViewLeftTop(mTv_info_itemName, Config.ShopUILayout.info_name.x,
				Config.ShopUILayout.info_name.y - mTv_info_itemName.getLineHeight());
		GameUISetting.setViewLeftTop(mTv_info_text, Config.ShopUILayout.info_text.x, Config.ShopUILayout.info_text.y
				- mTv_info_text.getLineHeight());
	}

	@Override
	protected void initViewPager(boolean custom) {
		// TODO Auto-generated method stub
		super.initViewPager(true);
		mViewPager.setOnPageChangeListener(new GamePageChangeListener(mIv_rank, bms_rank, mIv_left, mIv_right,
				mList_view.size()) {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				super.onPageScrollStateChanged(arg0);
				if (arg0 != 1)
					clearItemSelected(selectedItem);
			}
		});

		mViewPager.setCurrentItem(0);
		mIv_left.setVisibility(View.INVISIBLE);
		if (mList_view.size() < 2)
			mIv_right.setVisibility(View.INVISIBLE);
		setItemData();
	}

	@Override
	protected void addViewInPager() {
		// TODO Auto-generated method stub
		super.addViewInPager();
		initItemView();
	}

	@Override
	protected void _menu() {
		// TODO Auto-generated method stub
		finish();
	}

	private void setItemData() {
		mList_data = TestData.getItemAtlasData(this);
		for (ItemAtlasData data : mList_data) {
			View view = mList_item_view.get(data.item_id);
			ImageView bg = (ImageView) view.findViewById(R.id.iv_items_bg);
			ImageView it = (ImageView) view.findViewById(R.id.iv_items_item);
			it.setImageBitmap(data.item_ic);
			setImageView(bg, null, bms_item_bg[0], data, new AtlasItemTouchListener(bms_item_bg, data.item_id));
		}
	}

	/**
	 * 设置背包
	 */
	private void initItemView() {
		mList_item_view = new ArrayList<View>();

		for (int page = 0; page < bms_rank.length; page++) {
			RelativeLayout rl_m = new RelativeLayout(this);
			ItemPageScrollView sv = (ItemPageScrollView) getLayoutInflater().inflate(R.layout.item_itempage, null);
			rl_m.addView(
					sv,
					LayoutParams.MATCH_PARENT,
					(int) ((Config.PagerUILayout.page_bottom_item - Config.PagerUILayout.page_top_item) * Config.Size_Scale));

			GameUISetting.setViewLeftTop(sv, 0, Config.PagerUILayout.page_top_item);
			RelativeLayout rl = new RelativeLayout(this);
			Point p = new Point();
			for (int i = 0; i < (Config.Value.atlas_max + 3) / 4; i++) {
				p.y = Config.ItemUILayout.item_leftTop.y + Config.ItemUILayout.item_marginTop * i;
				for (int j = 0; j < 4; j++) {
					View view = getLayoutInflater().inflate(R.layout.item_items, null);
					p.x = Config.ItemUILayout.item_leftTop.x + Config.ItemUILayout.item_marginLeft * j;
					ImageView bg = (ImageView) view.findViewById(R.id.iv_items_bg);
					ImageView it = (ImageView) view.findViewById(R.id.iv_items_item);
					it.getLayoutParams().width = (int) (Config.ItemUILayout.item_max_width * Config.Size_Scale);
					it.getLayoutParams().height = (int) (Config.ItemUILayout.item_max_width * Config.Size_Scale);
					setImageView(bg, null, bm_item_lock, null, null);
					rl.addView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					GameUISetting.setViewLeftTop(view, p);
					mList_item_view.add(view);
				}
			}
			sv.setScrollViewListener(this);
			sv.addView(rl);
			rl.getLayoutParams().height = (int) ((p.y + Config.ItemUILayout.item_max_width) * Config.Size_Scale);
			mList_view.add(rl_m);
		}
	}

	private void clearItemSelected(int index) {

		if (index < 0)
			return;
		ImageView bg = (ImageView) mList_item_view.get(index).findViewById(R.id.iv_items_bg);
		bg.setImageBitmap(bms_item_bg[0]);
	}

	private class AtlasItemTouchListener extends GameButtonTouchListener {
		private int index;

		public AtlasItemTouchListener(Bitmap[] bms, int index) {
			super(bms);
			this.index = index;
		}

		@Override
		public void touchEvent(Object tag) {
			// TODO Auto-generated method stub
			ItemAtlasData data = (ItemAtlasData) tag;
			showDetail(data);
			selectedItem = -1;
		}

		@Override
		public void downEvent(Object tag) {
			// TODO Auto-generated method stub
			selectedItem = index;
		}
	}

	public void showDetail(ItemAtlasData data) {
		// TODO Auto-generated method stub
		mDetailView.setItemData(data, mImgReader.load(data.item_ic_path));
		mDetailView.show();
	}

	@Override
	public void onScrollChanged(ItemPageScrollView scrollView, int x, int y, int oldx, int oldy) {
		// TODO Auto-generated method stub
		clearItemSelected(selectedItem);
	}
}
