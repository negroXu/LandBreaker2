package com.landbreaker.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.landbreaker.R;
import com.landbreaker.application.AppData;
import com.landbreaker.base.GamePageActivity;
import com.landbreaker.bean.MapData;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.file.ToastUtils;
import com.landbreaker.internet.InternetApi;
import com.landbreaker.internet.URLS;
import com.landbreaker.listener.GameButtonTouchListener;
import com.landbreaker.listener.GamePageChangeListener;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.ShopData;
import com.landbreaker.testdata.ShopItemData;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.ShopItemDetailView;
import com.landbreaker.view.GameMenuView;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopActivity extends GamePageActivity implements Interface_MyThread{

	private ImageView mIv_money_bg = null;
	private ImageView mIv_money_ic = null;
	private TextView mTv_money_amount = null;
	private RelativeLayout mRl_money_layout = null;

	private ImageView mIv_diamond_bg = null;
	private ImageView mIv_diamond_ic = null;
	private TextView mTv_diamond_amount = null;
	private ImageView mIv_diamond_plus = null;
	private RelativeLayout mRl_diamond_layout = null;

	private ImageView mIv_info_bg = null;
	private TextView mTv_info_itemName = null;
	private TextView mTv_info_text = null;

	private ShopItemDetailView mDetailView = null;

	private Bitmap[] bms_item_bg_normal = null;
	private Bitmap bm_item_valuebg_normal = null;
	private Bitmap[] bms_item_bg_rare = null;
	private Bitmap bm_item_valuebg_rare = null;
	private Bitmap bm_gold = null;
	private Bitmap bm_diamond = null;
	private Bitmap bm_money_bg = null;
	private Bitmap bm_diamond_bg = null;
	private Bitmap bm_info_bg = null;
	private Bitmap bm_diamond_plus = null;

	private List<ShopItemData> mList_data = null;
	private List<ShopData> mList_shopdata = null;
	private List<View> mList_item = null;

	private final static int DIAMOND_PLUS = 1;
	private final static int REQUEST_CODE_GETSHOP = 2;
	private final static int REFRESH_MONEY = 3;

	private String token;
	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		bms_item_bg_normal = new Bitmap[] { mImgReader.load("shop/daojugezi_j.png"),
				mImgReader.load("shop/daojugezi_j_down.png") };
		bms_item_bg_rare = new Bitmap[] { mImgReader.load("shop/daojugezi_z.png"),
				mImgReader.load("shop/daojugezi_z_down.png") };
		bm_item_valuebg_normal = mImgReader.load("shop/jiagekuang_j.png");
		bm_item_valuebg_rare = mImgReader.load("shop/jiagekuang_z.png");
		bm_gold = mImgReader.load("item/xiaopan.png");
		bm_diamond = mImgReader.load("item/zuanshi.png");
		bm_money_bg = mImgReader.load("item/xinxi_jinqian.png");
		bm_diamond_bg = mImgReader.load("item/xinxi_zuanshi.png");
		bm_info_bg = mImgReader.load("item/shuoming.png");
		bm_diamond_plus = mImgReader.load("item/jia.png");
		mDetailView.loadImage(mImgReader);
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		addViewInCenterLayerFromXML(R.layout.item_money, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_money_bg = (ImageView) findViewById(R.id.iv_money_bg);
		mIv_money_ic = (ImageView) findViewById(R.id.iv_money_ic);
		mTv_money_amount = (TextView) findViewById(R.id.tv_money_amount);
		mRl_money_layout = (RelativeLayout) findViewById(R.id.rl_money_total);
		mIv_diamond_bg = (ImageView) findViewById(R.id.iv_diamond_bg);
		mIv_diamond_ic = (ImageView) findViewById(R.id.iv_diamond_ic);
		mTv_diamond_amount = (TextView) findViewById(R.id.tv_diamond_amount);
		mIv_diamond_plus = (ImageView) findViewById(R.id.iv_diamond_plus);
		mRl_diamond_layout = (RelativeLayout) findViewById(R.id.rl_diamond_total);

		mDetailView = new ShopItemDetailView(this);
		addViewInFront(mDetailView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mDetailView.init(this);

		addViewInFrontFromXML(R.layout.item_info_text, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_info_bg = (ImageView) findViewById(R.id.iv_info_textbg);
		mTv_info_itemName = (TextView) findViewById(R.id.tv_info_itemName);
		mTv_info_text = (TextView) findViewById(R.id.tv_info_text);

		mMenu.setActiviyTag(GameMenuView.SHOP);

		token = ((AppData)getApplication()).userData.playerList[0].token;
	}

	@Override
	protected void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		mIv_money_bg.setImageBitmap(bm_money_bg);
		GameUISetting.setViewWidthHeight(mIv_money_bg, bm_money_bg);
		setImageView(mIv_money_ic, Config.ShopUILayout.total_money_ic, bm_gold, null, null);
		mIv_diamond_bg.setImageBitmap(bm_diamond_bg);
		GameUISetting.setViewWidthHeight(mIv_diamond_bg, bm_diamond_bg);
		setImageView(mIv_diamond_ic, Config.ShopUILayout.total_money_ic, bm_diamond, null, null);
		setImageView(mIv_diamond_plus, Config.ShopUILayout.total_diamond_plus, bm_diamond_plus, DIAMOND_PLUS,
				new ItemOnTouchListener());
		GameUISetting.setViewLeftTop(mRl_money_layout, Config.ShopUILayout.total_money_bg);
		GameUISetting.setViewLeftTop(mRl_diamond_layout, Config.ShopUILayout.total_diamond_bg);
		setImageView(mIv_info_bg, Config.ShopUILayout.info_bg, bm_info_bg, null, null);
		mDetailView.setItem();

		initInfoText();
	}

	private void initInfoText() {
		// TODO Auto-generated method stub
		mTv_money_amount.setText("" + ((AppData)getApplication()).userData.playerList[0].goldcoin);
		mTv_diamond_amount.setText("" + ((AppData)getApplication()).userData.playerList[0].money);
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

		mViewPager.setOnPageChangeListener(new GamePageChangeListener(mIv_left, mIv_right, mList_view.size()) {

			private int page = 0;

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				super.onPageScrollStateChanged(arg0);
				if (arg0 == 1)
					page = mViewPager.getCurrentItem();
				else
					clearItemSelected(page);
			}
		});
		mViewPager.setCurrentItem(0);
		mIv_left.setVisibility(View.INVISIBLE);
		if (mList_view.size() < 2)
			mIv_right.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void addViewInPager() {
		// TODO Auto-generated method stub
		super.addViewInPager();
		mList_item = new ArrayList<View>();

		Log.d("addViewInPager","run");

		if(((AppData)getApplication()).mListShopData != null){
			initItemView(((AppData)getApplication()).mListShopData);
		}else{
			requestShopData();
		}

//		mList_data = TestData.getShopItemData(ShopActivity.this);
//		initItemView(mList_data);
	}

	private void initItemView(List<ShopData> mList_data) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mList_data.size(); i += 4) {
			RelativeLayout rl = new RelativeLayout(this);
			for (int j = 0; j < 4; j++) {
				if (i + j >= mList_data.size())
					break;
				View view = getLayoutInflater().inflate(R.layout.item_shop_item, null);
				initShopItem(view, mList_data.get(i + j));
				rl.addView(view, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				GameUISetting.setViewLeftTop(view, Config.ShopUILayout.list_itemLeftTop[j]);
				mList_item.add(view);
			}
			mList_view.add(rl);
		}
	}

	/**
	 * 发起请求商场数据
	 * */
	private void requestShopData(){
		InternetApi.getShop(this, ShopActivity.this, REQUEST_CODE_GETSHOP);
	}
	/**
	 * 设置商店物品
	 * @param view
	 * @param shopItemData
	 */
	private void initShopItem(View view, ShopData shopItemData) {
		// TODO Auto-generated method stub
		ImageView iv_bg = (ImageView) view.findViewById(R.id.iv_gameitem_bg);
		ImageView iv_value_bg = (ImageView) view.findViewById(R.id.iv_gameitem_moneybg);
		ImageView iv_value_ic = (ImageView) view.findViewById(R.id.iv_gameitem_gold);
		ImageView iv_item = (ImageView) view.findViewById(R.id.iv_gameitem_item);
		TextView tv_value = (TextView) view.findViewById(R.id.tv_gameitem_money);
		ImageView iv_value_ic_diamond = (ImageView) view.findViewById(R.id.iv_gameitem_diamond);
		TextView tv_value_diamond = (TextView) view.findViewById(R.id.tv_gameitem_diamond);

		RelativeLayout rl1 = (RelativeLayout) view.findViewById(R.id.rl_gameitem_money);
		if (shopItemData.type != 5) {
			// 灰色底框
			setImageView(iv_bg, null, bms_item_bg_normal[0], shopItemData, new ShopItemOnTouchListener(
					bms_item_bg_normal));
			iv_value_bg.setImageBitmap(bm_item_valuebg_normal);
			GameUISetting.setViewWidthHeight(iv_value_bg, bm_item_valuebg_normal);

		} else{
			// 红色底框
			setImageView(iv_bg, null, bms_item_bg_rare[0], shopItemData, new ShopItemOnTouchListener(bms_item_bg_rare));
			iv_value_bg.setImageBitmap(bm_item_valuebg_rare);
			GameUISetting.setViewWidthHeight(iv_value_bg, bm_item_valuebg_rare);
		}

		setImageView(iv_value_ic, Config.ShopUILayout.item_value_ic, bm_gold, null, null);
		setImageView(iv_value_ic_diamond, Config.ShopUILayout.item_value_ic_diamond, bm_diamond, null, null);
		tv_value.setText("" + shopItemData.buy_price);
		tv_value_diamond.setText("" + shopItemData.buy_money);
		// 设置价格坐标
		tv_value.setX(Config.ShopUILayout.item_value_ic.x);
//		tv_value.setY(10);
		tv_value_diamond.setX(50);
//		tv_value_diamond.setY(10);

		// 道具图片
//		setImageView(iv_item, Config.ShopUILayout.item_ic, shopItemData.item_ic, null, null);
		setImageViewURL(iv_item, Config.ShopUILayout.item_ic, ImgReader.loadSmaller("shop/itembig_zuanshi50.png", ShopActivity.this, Config.ShopUILayout.item_scale_jz), URLS.server + shopItemData.pic_url, null, null);

		GameUISetting.setViewLeftTop(rl1, Config.ShopUILayout.item_money_bg_leftTop);

	}

	/**
	 * 清除道具被选择的状态
	 * @param page
	 */
	private void clearItemSelected(int page) {
		// TODO Auto-generated method stub
		int offset = page;
		int last = offset * 4 + 4;
		if (last > mList_item.size())
			last = mList_item.size();
		for (int i = offset * 4; i < last; i++) {
			ImageView iv = (ImageView) mList_item.get(i).findViewById(R.id.iv_gameitem_bg);
			ShopData data = (ShopData) iv.getTag();
			if (data.type == 4 || data.type == 3)
				iv.setImageBitmap(bms_item_bg_normal[0]);
			else if (data.type == 5)
				iv.setImageBitmap(bms_item_bg_rare[0]);
		}
	}

	/**
	 * 请求回调
	 * @param result
	 * @param flag
     */
	@Override
	public void Callback_MyThread(String result, int flag) {
		try{
			JSONObject jsonObject = new JSONObject(result);
			Log.d("result",result);
			if(jsonObject.getBoolean("success")){
				Message msg = new Message();
				msg.what = flag;
				switch (flag){
					case REQUEST_CODE_GETSHOP:
						msg.obj = jsonObject.getString("data");
						break;

				}

				mHandler.sendMessage(msg);
			}else{
				//失败提示 为处理
				ToastUtils.showMessage(ShopActivity.this,jsonObject.getString("msg"));
			}
		}catch(JSONException e){

		}
	}

	// ==Method===========================
	private android.os.Handler mHandler = new android.os.Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case REQUEST_CODE_GETSHOP:
					try {
						Log.d("getShop","success");
						mList_shopdata = (LinkedList) new Gson().fromJson(
								new JSONObject((String) msg.obj).getString("basicSystemItemList"),new TypeToken<LinkedList<ShopData>>() {
						}.getType());
						Log.d("name",mList_shopdata.get(1).name);

						if(mList_shopdata != null){
							((AppData)getApplication()).mListShopData = mList_shopdata;
							initViewPager(false);
						}
						break;
					}catch (JSONException e){
					}
					break;
				case REFRESH_MONEY:
					// 刷新金币钻石显示数量
					mTv_money_amount.setText("" + ((AppData)getApplication()).userData.playerList[0].goldcoin);
					mTv_diamond_amount.setText("" + ((AppData)getApplication()).userData.playerList[0].money);
					break;
			}
		}
	};

	private class ShopItemOnTouchListener extends GameButtonTouchListener {
		public ShopItemOnTouchListener(Bitmap[] bms) {
			super(bms);
		}

		@Override
		public void touchEvent(Object tag) {
			// TODO Auto-generated method stub
			ShopData data = (ShopData) tag;
			showDetail(data);
			// 恢复详细窗口初始状态
			mDetailView.initItem();
		}

	}

	@Override
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		super._touchEventAction(key);
		switch (key) {
		case DIAMOND_PLUS:
			Toast.makeText(this, "买买买", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	public void showDetail(ShopData data) {
		// TODO Auto-generated method stub
		mDetailView.setValue(ShopItemData.GOLD, data.buy_price);
		mDetailView.setValue(ShopItemData.DIAMOND, data.buy_money);
		mDetailView.setItemIc(mImgReader.load("shop/itembig_zuanshi50.png"),URLS.server + data.pic_url);
		mDetailView.setItemInfo(data.name, data.description,token,data.id);
		mDetailView.show();
	}

}
