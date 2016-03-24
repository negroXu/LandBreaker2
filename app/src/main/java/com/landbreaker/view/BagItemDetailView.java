package com.landbreaker.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.landbreaker.R;
import com.landbreaker.activity.ItemActivity;
import com.landbreaker.application.AppData;
import com.landbreaker.base.GamePopView;
import com.landbreaker.config.Config;
import com.landbreaker.database.Item_BASICITEM;
import com.landbreaker.database.Item_BASICSYSTEMITEM;
import com.landbreaker.database.Table_BASICITEM;
import com.landbreaker.database.Table_BASICSYSTEMITEM;
import com.landbreaker.file.ImgReader;
import com.landbreaker.internet.InternetApi;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.BagData;
import com.landbreaker.testdata.BagItemData;
import com.landbreaker.testdata.GlobalItemList;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BagItemDetailView extends GamePopView implements Interface_MyThread{
	public ImageView mIv_item_ic = null;
	public ImageView mIv_value_bg = null;
	public ImageView mIv_value_ic = null;
	public ImageView mIv_exchange_button = null;
	public ImageView mIv_use_button = null;
	public ImageView mIv_rank = null;
	public TextView mTv_value = null;

	public Bitmap[] bms_exchange = null;
	public Bitmap[] bms_use = null;
	public Bitmap[] bms_rank = null;
	public Bitmap bm_value_bg = null;
	public Bitmap bm_gold = null;

	public int rank;

	private TextView tv_name;
	private TextView tv_info;
	private ImageView iv_atlas;
	private RelativeLayout rl_center;
	private RelativeLayout rl_front;

	private Table_BASICITEM table;
	private Table_BASICSYSTEMITEM table_basicsystemitem;
	private ImgReader reader;
	private ItemActivity itemActivity = null;
	private String guid;
	private int bag_id;
	private int item_id;
	private int box_id;
	private int sell_price;
	private double get_time;

	public final static int EXCHANGE = 4;
	public final static int USE = 5;

	public enum openBoxBy{
		Null,Money,Key
	};

	public openBoxBy openBoxBy = null;

	public BagItemDetailView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(Context context) {
		// TODO Auto-generated method stub
		super.init(context);
		addViewInThisFromXML(R.layout.item_itemdetail, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_item_ic = (ImageView) findViewById(R.id.iv_item_display);
		mIv_value_bg = (ImageView) findViewById(R.id.iv_item_moneybg);
		mIv_value_ic = (ImageView) findViewById(R.id.iv_item_gold);
		mIv_exchange_button = (ImageView) findViewById(R.id.iv_item_exchange);
		mIv_use_button = (ImageView) findViewById(R.id.iv_item_use);
		mIv_rank = (ImageView) findViewById(R.id.iv_item_rank);
		mTv_value = (TextView) findViewById(R.id.tv_item_money);

		// 取数据库
		table = new Table_BASICITEM(context);
		table_basicsystemitem = new Table_BASICSYSTEMITEM(context);
		// 初始化reader
		reader = new ImgReader(context);
	}

	@Override
	public void loadImage(ImgReader reader) {
		// TODO Auto-generated method stub
		super.loadImage(reader);
		bms_exchange = new Bitmap[] { reader.load("item/duihuanjinbi_up.png"),
				reader.load("item/duihuanjinbi_down.png") };
		bms_use = new Bitmap[] { reader.load("item/shiyong_up.png"), reader.load("item/shiyong_down.png") };
		bms_rank = reader.loadItemRank();
		bm_value_bg = reader.load("item/daoju_jiazhi.png");
		bm_gold = reader.load("item/xiaopan.png");
	}

	@Override
	public void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		setImageView(mIv_exchange_button, Config.ItemDetailUILayout.item_exchange, bms_exchange[0], EXCHANGE,
				new ViewItemOnTouchListener(bms_exchange));
		setImageView(mIv_use_button, Config.ItemDetailUILayout.item_use, bms_use[0], USE, new ViewItemOnTouchListener(
				bms_use));
		setImageView(mIv_rank, Config.ItemDetailUILayout.item_rank, bms_rank[0], null, null);

		setImageView(mIv_value_ic, Config.ItemDetailUILayout.item_value_ic, bm_gold, null, null);
		mIv_value_bg.setImageBitmap(bm_value_bg);
		GameUISetting.setViewWidthHeight(mIv_value_bg, bm_value_bg);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_item_money_layout);
		GameUISetting.setViewLeftTop(rl, Config.ItemDetailUILayout.item_value_leftTop);
		this.setBackgroundResource(R.color.bg_transparent_height);

		tv_name = ((TextView) ((Activity) mContext).findViewById(R.id.tv_info_itemName));
		tv_info = ((TextView) ((Activity) mContext).findViewById(R.id.tv_info_text));
	}

	public void setItemAtlas(ImageView atlas, RelativeLayout center, RelativeLayout front) {
		iv_atlas = atlas;
		rl_center = center;
		rl_front = front;
	}

	/**
	 * @param bag_id
	 * @param guid
	 * @param item_id
	 * @param box_id
	 * @param isglobalItemList
	 * @param bm
     * @param itemActivity
     */
	public void setItemData(int bag_id,String guid, int item_id,int box_id,boolean isglobalItemList, Bitmap bm, ItemActivity itemActivity, double get_time) {
		this.guid = guid;
		this.bag_id = bag_id;
		this.item_id = item_id;
		this.box_id = box_id;
		this.get_time = get_time;

		// 10000以内为系统物品  其他为掉落物品
		if(item_id > 10000){
			Item_BASICITEM item = table.get(item_id);
			if(item == null) return;
			initView(bm,item.sell_price,item.NAME,item.description,item.LEVEL);
		}else if(item_id < 10000 && item_id > 0){
			Item_BASICSYSTEMITEM item= table_basicsystemitem.get(item_id);
			if(item == null) return;
			initView(bm,item.sell_price,item.NAME,item.description,item.LEVEL);
		}else{
			// 宝箱
			Item_BASICSYSTEMITEM item= table_basicsystemitem.get(box_id);
			if(item == null) return;
			initView(bm, item.sell_price,item.NAME,item.description,item.LEVEL);
		}

		this.itemActivity = itemActivity;

		// 收集类物品  且不是宝箱  关闭使用按钮
		if(isglobalItemList && box_id == 0){
			mIv_use_button.setVisibility(View.GONE);
		}else{
			mIv_use_button.setVisibility(View.VISIBLE);
		}

		// 宝箱
		if(box_id != 0 && this.get_time != 0){
			// 剩余时间
			double leftTime = this.get_time + Config.box_time[box_id] - new Date().getTime();
			leftTime = leftTime < 0 ? 0:leftTime;

			//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

			Log.d("leftTime","" + leftTime + ":" + leftTime);
			// 所需宝石
			int qty =(int)(Math.ceil(Config.box_price[box_id] * (leftTime / Config.box_time[box_id])));
			Log.d("所需宝石", qty + "");
		}
	}

	private String timeFormat(double t){
		return (t / 3600) + (t % 3600 / 60) + (t % 3600 % 60) + "";
	}

	private void initView(Bitmap bm, int sell_price, String name, String description, int level){
		setImageView(mIv_item_ic, Config.ItemDetailUILayout.item_ic, bm, null, null);
		this.sell_price = sell_price;
		mTv_value.setText("" + sell_price);
		tv_name.setText(name);
		tv_info.setText(description);
		mIv_rank.setImageBitmap(bms_rank[level]);

	}

	@Override
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		super._touchEventAction(key);
		switch (key) {
		case EXCHANGE:
			// 发起请求
			try{
				JSONArray feedArrar = new JSONArray();
				JSONObject item = new JSONObject();
				item.put("time",(System.currentTimeMillis() - 60000) + "");//new Timestamp(System.currentTimeMillis())
				item.put("type","3");
				item.put("system_id",guid);
				item.put("bag_id",bag_id+"");
				item.put("qty", "1");
				feedArrar.put(item);
				Log.d("feedArrar",feedArrar.toString());

				// 执行同步
				InternetApi.sync(mContext,((AppData) itemActivity.getApplication()).userData.playerList[0].token,feedArrar.toString(),BagItemDetailView.this,EXCHANGE); // ((AppData)getApplication()).userData.playerList[0].token
			}catch (JSONException e){
			}

			this.hidden();
			break;
		case USE:
			// 发起请求
			try{
				JSONArray feedArrar = new JSONArray();
				JSONObject item = new JSONObject();
				item.put("time",(System.currentTimeMillis() - 60000) + "");//new Timestamp(System.currentTimeMillis())
				item.put("type","4");
				item.put("system_id", guid);
				item.put("bag_id",bag_id + "");
				item.put("qty", "1");

				if(box_id != 0){
					// 宝箱打开方式
					switch (openBoxBy){
						case Null:
							item.put("by","null");
							break;
						case Key:
							item.put("by","1");
							break;
						case Money:
							item.put("by","2");
							break;
					}
				}else{
					item.put("by","null");
				}

				feedArrar.put(item);
				Log.d("feedArrar",feedArrar.toString());

				// 执行同步
				InternetApi.sync(mContext,((AppData) itemActivity.getApplication()).userData.playerList[0].token,feedArrar.toString(),BagItemDetailView.this,USE); // ((AppData)getApplication()).userData.playerList[0].token
			}catch (JSONException e){
			}

			this.hidden();
			break;
		default:
			break;
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		changeAtlasLayer(rl_front, rl_center);
	}

	@Override
	public void hidden() {
		// TODO Auto-generated method stub
		super.hidden();
		tv_name.setText(R.string.info_name_default);
		tv_info.setText(R.string.info_text_default);
		changeAtlasLayer(rl_center, rl_front);
	}

	private void changeAtlasLayer(RelativeLayout res, RelativeLayout des) {
		LayoutParams params = (LayoutParams) iv_atlas.getLayoutParams();
		res.removeView(iv_atlas);
		des.addView(iv_atlas, params);
	}

	@Override
	public void Callback_MyThread(String result, int flag) {
		// 回调 
		if(itemActivity != null){
			itemActivity.callDetail(flag,sell_price);
		}
	}
}
