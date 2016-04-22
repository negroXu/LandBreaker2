package com.landbreaker.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.landbreaker.R;
import com.landbreaker.application.AppData;
import com.landbreaker.base.GameActivity;
import com.landbreaker.config.Config;
import com.landbreaker.database.Item_BASICITEM;
import com.landbreaker.database.Table_BASICITEM;
import com.landbreaker.file.ImgReader;
import com.landbreaker.file.ToastUtils;
import com.landbreaker.internet.InternetApi;
import com.landbreaker.listener.GameButtonTouchListener;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.BagData;
import com.landbreaker.testdata.BagItemData;
import com.landbreaker.testdata.GlobalItemList;
import com.landbreaker.testdata.ShopData;
import com.landbreaker.testdata.TestData;
import com.landbreaker.view.BagItemDetailView;
import com.landbreaker.view.GameMenuView;
import com.landbreaker.view.ItemPageScrollView;
import com.landbreaker.view.ItemPageScrollView.ScrollViewListener;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemActivity extends GameActivity implements ScrollViewListener,Interface_MyThread,callbackDetail {
	//金币框
	private ImageView mIv_money_bg = null;
	private ImageView mIv_money_ic = null;
	private TextView mTv_money_amount = null;
	private RelativeLayout mRl_money_layout = null;
	//钻石框
	private ImageView mIv_diamond_bg = null;
	private ImageView mIv_diamond_ic = null;
	private TextView mTv_diamond_amount = null;
	private ImageView mIv_diamond_plus = null;
	private RelativeLayout mRl_diamond_layout = null;

	private ImageView mIv_info_bg = null;
	private TextView mTv_info_itemName = null;
	private TextView mTv_info_text = null;

	private ImageView mIv_item_atlas = null;// 图鉴
	private ItemPageScrollView mSv_item_page = null;
	private Table_BASICITEM table;

	private Bitmap bm_gold = null;
	private Bitmap bm_diamond = null;
	private Bitmap bm_money_bg = null;
	private Bitmap bm_diamond_bg = null;
	private Bitmap bm_info_bg = null;
	private Bitmap bm_diamond_plus = null;
	private Bitmap[] bms_item_bg = null;
	private Bitmap bm_item_lock = null;
	private Bitmap[] bms_item_atlas = null;

	private BagItemDetailView mDetailView = null;
	private List<View> mView_bag = null;
	private List<BagItemData> mList_data = null;
	private List<BagData> mList_bagdata = null;

	private final static int DIAMOND_PLUS = 1;
	private final static int ITEM_MAP = 2;
	private final static int REQUEST_CODE_GETBAGDATA = 3;
	private final static int REFRESH_CODE_GETBAGDATA = 6;
	public final static int EXCHANGE = 4;
	public final static int USE = 5;
	private final static int REFRESH_GOLDCOUN = 7;

	private int indexBagType1 = 0;
	private int selectedItem = -1;

	private int bag_id;
	private ProgressDialog progressDialog = null;

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

		// 取数据库
		table = new Table_BASICITEM(this);

		initItemBag();
		mDetailView = new BagItemDetailView(this);
		addViewInFront(mDetailView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mDetailView.init(this);

		addViewInFrontFromXML(R.layout.item_info_text, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_info_bg = (ImageView) findViewById(R.id.iv_info_textbg);
		mTv_info_itemName = (TextView) findViewById(R.id.tv_info_itemName);
		mTv_info_text = (TextView) findViewById(R.id.tv_info_text);

		mIv_item_atlas = new ImageView(this);
		addViewInFront(mIv_item_atlas, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mMenu.setActiviyTag(GameMenuView.ITEMS);
	}

	@Override
	protected void loadImage() {
		// TODO Auto-generated method stub
		super.loadImage();
		bm_gold = mImgReader.load("item/xiaopan.png");
		bm_diamond = mImgReader.load("item/zuanshi.png");
		bm_money_bg = mImgReader.load("item/xinxi_jinqian.png");
		bm_diamond_bg = mImgReader.load("item/xinxi_zuanshi.png");
		bm_info_bg = mImgReader.load("item/shuoming.png");
		bm_diamond_plus = mImgReader.load("item/jia.png");
		bms_item_bg = new Bitmap[] { mImgReader.load("item/daojugezi_up.png"),
				mImgReader.load("item/daojugezi_down.png") };
		bm_item_lock = mImgReader.load("item/gezisuoding2.png");
		bms_item_atlas = new Bitmap[] { mImgReader.load("item/tujian_up.png"), mImgReader.load("item/tujian_down.png") };
		mDetailView.loadImage(mImgReader);
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
		setImageView(mIv_item_atlas, Config.ItemUILayout.item_atlas, bms_item_atlas[0], ITEM_MAP,
				new ItemOnTouchListener(bms_item_atlas));

		// 发起请求获取背包数据
		requestGetBagData(REQUEST_CODE_GETBAGDATA);
//		progressDialog = new ProgressDialog(this);
//		progressDialog.show(this,null,"加载中...",true,true);
//		progressDialog.dismiss();

		mDetailView.setItem();
		mDetailView.setItemAtlas(mIv_item_atlas, mRl_center, mRl_front);
		setInfoText();
	}

	private void setInfoText() {
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
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		super._touchEventAction(key);
		switch (key) {
		case DIAMOND_PLUS:
			//购买钻石
			Toast.makeText(this, "买买买", Toast.LENGTH_SHORT).show();
			break;
		case ITEM_MAP:
			// 跳转道具图鉴页面
			toItemAtlasActivity();
			break;
		default:
			break;
		}
	}

	private void toItemAtlasActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ItemActivity.this, ItemAtlasActivity.class);
		startActivity(intent);
	}

	/**
	 * 初始化背包
	 */
	private void initItemBag() {
		int max = Config.Value.bag_max;
		mView_bag = new ArrayList<View>();

		mSv_item_page = (ItemPageScrollView) getLayoutInflater().inflate(R.layout.item_itempage, null);
		addViewInCenterLayer(
				mSv_item_page,
				LayoutParams.MATCH_PARENT,
				(int) ((Config.PagerUILayout.page_bottom_item - Config.PagerUILayout.page_top_item) * Config.Size_Scale));
		GameUISetting.setViewLeftTop(mSv_item_page, 0, Config.PagerUILayout.page_top_item);

		for (int i = 0; i < max; i++) {
			View item = getLayoutInflater().inflate(R.layout.item_items, null);
			mView_bag.add(item);
		}

	}

	/**
	 * 设置包内的道具
	 */
	private void setItemInBag() {

		Point p = new Point();

		RelativeLayout rl = new RelativeLayout(this);
		for (int i = 0; i < (Config.Value.bag_max + 3) / 4; i++) {
			p.y = Config.ItemUILayout.item_leftTop.y + Config.ItemUILayout.item_marginTop * i;
			for (int j = 0; j < 4; j++) {
				int k = i * 4 + j;
				if (k >= mView_bag.size())
					break;
				View view = mView_bag.get(k);
				p.x = Config.ItemUILayout.item_leftTop.x + Config.ItemUILayout.item_marginLeft * j;
				ImageView bg = (ImageView) view.findViewById(R.id.iv_items_bg);
				ImageView it = (ImageView) mView_bag.get(k).findViewById(R.id.iv_items_item);
				ImageView num = (ImageView) mView_bag.get(k).findViewById(R.id.iv_items_num);
				it.getLayoutParams().width = (int) (Config.ItemUILayout.item_max_width * Config.Size_Scale);
				it.getLayoutParams().height = (int) (Config.ItemUILayout.item_max_width * Config.Size_Scale);
				num.getLayoutParams().width = (int) (Config.ItemUILayout.item_num_width * Config.Size_Scale);
				num.getLayoutParams().height = (int) (Config.ItemUILayout.item_num_width * Config.Size_Scale);
				num.setX(Config.ItemUILayout.item_max_width/2 + 15);
				num.setY(Config.ItemUILayout.item_max_width/2 + 15);
				// 已开启槽数
				if(k < mList_bagdata.get(indexBagType1).num){
					setImageView(bg, null, bms_item_bg[0], null, null);
				}else{
					setImageView(bg, null, bm_item_lock, null, null);
				}

				rl.addView(view);
				GameUISetting.setViewLeftTop(view, p);
			}
		}
		mSv_item_page.setScrollViewListener(this);
		mSv_item_page.addView(rl);

		rl.getLayoutParams().height = (int) ((p.y + Config.ItemUILayout.item_max_width) * Config.Size_Scale);
		setItemData();
	}

	private void refreshItemBag(){
		for (int i = 0; i < Config.Value.bag_max; i++) {
			View view = mView_bag.get(i);
			ImageView bg = (ImageView) view.findViewById(R.id.iv_items_bg);
			ImageView it = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_item);
			ImageView num = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_num);
			// 已开启槽数
			if(i < mList_bagdata.get(indexBagType1).num){
				setImageView(bg, null, bms_item_bg[0], null, null);
				it.setImageBitmap(null);
//				num.setText("");
				num.setImageBitmap(null);
			}else{
				setImageView(bg, null, bm_item_lock, null, null);
			}
		}

	}

	private void setItemData() {
		//测试数据
//		mList_data = TestData.getBagItemData(this);
		Item_BASICITEM item = table.get(10001);

			if(mList_bagdata.get(indexBagType1).type == 1){
				// globalItemList
				for (int i = 0; i < mList_bagdata.get(indexBagType1).globalItemList.length; i++) {
					ImageView bg = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_bg);
					ImageView it = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_item);
					ImageView num = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_num);

					setImageView(bg, null, bms_item_bg[0], mList_bagdata.get(indexBagType1).globalItemList[i].item_id, new BagItemTouchListener(bms_item_bg, i,mList_bagdata.get(indexBagType1).globalItemList[i].guid,mList_bagdata.get(indexBagType1).globalItemList[i].box_id,true,mList_bagdata.get(indexBagType1).globalItemList[i].get_time));

					num.setImageBitmap(mImgReader.loadNumBitmap(mList_bagdata.get(indexBagType1).globalItemList[i].item_num));
					it.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
					it.setImageBitmap(mImgReader.load("prop/" + mList_bagdata.get(indexBagType1).globalItemList[i].item_id + ".png"));
				}

				// globalSystemItemList
				for (int i = 0; i < mList_bagdata.get(indexBagType1).globalSystemItemList.length; i++) {
					ImageView bg = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_bg);
					ImageView it = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_item);
					ImageView num = (ImageView) mView_bag.get(i).findViewById(R.id.iv_items_num);

					setImageView(bg, null, bms_item_bg[0], mList_bagdata.get(indexBagType1).globalSystemItemList[i].systemItem_id, new BagItemTouchListener(bms_item_bg, i,mList_bagdata.get(indexBagType1).globalSystemItemList[i].id +"",0,false,0));

//					num.setText("" + mList_bagdata.get(indexBagType1).globalSystemItemList[i].num);
					num.setImageBitmap(mImgReader.loadNumBitmap(mList_bagdata.get(indexBagType1).globalItemList[i].item_num));
					it.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
					it.setImageBitmap(mImgReader.load("prop/" + mList_bagdata.get(indexBagType1).globalSystemItemList[i].systemItem_id + ".png"));
				}
			}
	}

	private void requestGetBagData(int i){
		InternetApi.getBagData(this, ((AppData)getApplication()).userData.playerList[0].token,ItemActivity.this, i);// ((AppData)getApplication()).userData.playerList[0].token
	}

	@Override
	public void Callback_MyThread(String result, int flag) {
		try{

			JSONObject jsonObject = new JSONObject(result);
			Log.d("result",result);
			if(jsonObject.getBoolean("success")){
				Message msg = new Message();
				msg.what = flag;
				switch (flag){
					case REQUEST_CODE_GETBAGDATA:
					case REFRESH_CODE_GETBAGDATA:
						msg.obj = jsonObject.getString("data");
						break;
				}
				mHandler.sendMessage(msg);
			}else{
				//失败提示 为处理
				Toast.makeText(ItemActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
			}
		}catch(JSONException e){
		}
	}

	private android.os.Handler mHandler = new android.os.Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case REQUEST_CODE_GETBAGDATA:

					mList_bagdata = (LinkedList) new Gson().fromJson(
                            (String) msg.obj,new TypeToken<LinkedList<BagData>>() {
                            }.getType());

					// 取得背包TYPE 为 1的位置
					for(int i = 0; i < mList_bagdata.size(); i++){
						if(mList_bagdata.get(i).type == 1){
							indexBagType1 = i;
							bag_id = mList_bagdata.get(i).id;
						}
					}

					if(mList_bagdata != null){
						setItemInBag();
					}
					break;
				case REFRESH_CODE_GETBAGDATA:

					mList_bagdata = (LinkedList) new Gson().fromJson(
							(String) msg.obj,new TypeToken<LinkedList<BagData>>() {
							}.getType());

					if(mList_bagdata != null){
						refreshItemBag();
						setItemData();
					}
					break;
				case REFRESH_GOLDCOUN:
					mTv_money_amount.setText(((AppData)getApplication()).userData.playerList[0].goldcoin + "");
					break;
			}
		}
	};



	private class BagItemTouchListener extends GameButtonTouchListener {

		private int index;
		private String guid;
		private boolean isglobalItemList;
		private int box_id;
		private double get_time;

		public BagItemTouchListener(Bitmap[] bms, int index, String guid, int box_id,boolean isglobalItemList,double get_time) {
			super(bms);
			this.index = index;
			this.guid = guid;
			this.box_id = box_id;
			this.isglobalItemList = isglobalItemList;
			this.get_time = get_time;
		}

		@Override
		public void touchEvent(Object tag) {
			// TODO Auto-generated method stub
			int item_id = (Integer) tag;
			showDetail(item_id,guid,box_id,isglobalItemList,this.get_time);
			selectedItem = -1;
		}

		@Override
		public void downEvent(Object tag) {
			// TODO Auto-generated method stub
			selectedItem = index;
		}
	}

	public void showDetail(int item_id,String guid, int box_id, boolean isglobalItemList, double get_time ) {
		// TODO Auto-generated method stub
		mDetailView.setItemData(bag_id, guid, item_id, box_id,isglobalItemList, mImgReader.load("prop/" + item_id + ".png"),this,get_time);
		mDetailView.show();
	}

	/**
	 * 从物品详情返回背包
	 * 在“使用”和“兑换金币”才执行刷新背包
	 * @param flag
     */
	@Override
	public void callDetail(int flag, int income) {
	// 刷新数据
		requestGetBagData(REFRESH_CODE_GETBAGDATA);
		switch (flag){
			case EXCHANGE:
				((AppData)getApplication()).userData.playerList[0].goldcoin += income;
				Message msg = new Message();
				msg.what = REFRESH_GOLDCOUN;
				mHandler.sendMessage(msg);
				ToastUtils.showMessage(ItemActivity.this,R.string.sell_success);
				break;
			case USE:
				ToastUtils.showMessage(ItemActivity.this, R.string.use_success);
				break;
		}

	}

	private void clearItemSelected(int index) {
		if (index >= mList_bagdata.get(indexBagType1).globalItemList.length || index < 0)
			return;
		ImageView bg = (ImageView) mView_bag.get(index).findViewById(R.id.iv_items_bg);
		bg.setImageBitmap(bms_item_bg[0]);
	}

	@Override
	public void onScrollChanged(ItemPageScrollView scrollView, int x, int y, int oldx, int oldy) {
		// TODO Auto-generated method stub
		clearItemSelected(selectedItem);
	}

}
interface  callbackDetail{
	public void callDetail(int flag,int income);
}
