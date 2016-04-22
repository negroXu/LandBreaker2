package com.landbreaker.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.mtp.MtpConstants;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.landbreaker.R;
import com.landbreaker.activity.ShopActivity;
import com.landbreaker.application.AppData;
import com.landbreaker.base.GamePopView;
import com.landbreaker.bean.MapData;
import com.landbreaker.bean.PlayerList;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.file.ToastUtils;
import com.landbreaker.internet.InternetApi;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.ShopItemData;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopItemDetailView extends GamePopView implements Interface_MyThread{

	public ImageView mIv_item_ic = null;
	public ImageView mIv_value_bg = null;
	public ImageView mIv_value_diamond_bg = null;
	public ImageView mIv_value_ic = null;
	public ImageView mIv_value_diamond_ic = null;
	public ImageView mIv_buy_button = null;
	public TextView mTv_value = null;
	public TextView mTv_diamond_value = null;

	public Bitmap[] bms_buy = null;
	public Bitmap[] bms_type = null;
	public Bitmap[] bm_value_bg = null;

	public int type;
	private int paytype = 0;
	private String token;
	private int system_id;
	private int buy_price_gold = 0;
	private int buy_price_diamond = 0;

	private TextView tv_name;
	private TextView tv_info;

	public final static int BUY = 3;
	public final static int CHOOSEGLOD = 1;
	public final static int CHOISEDIAMOND = 2;
	public final static int REQUEST_SNCY = 4;


	public ShopItemDetailView(Context context) {
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
		mIv_value_diamond_bg = (ImageView) findViewById(R.id.iv_item_diamondbg);
		mIv_value_ic = (ImageView) findViewById(R.id.iv_item_gold);
		mIv_value_diamond_ic = (ImageView) findViewById(R.id.iv_item_diamond);
		mIv_buy_button = (ImageView) findViewById(R.id.iv_item_exchange);
		mTv_value = (TextView) findViewById(R.id.tv_item_money);
		mTv_diamond_value = (TextView) findViewById(R.id.tv_item_diamond);
	}

	@Override
	public void loadImage(ImgReader reader) {
		// TODO Auto-generated method stub
		super.loadImage(reader);
		bms_buy = new Bitmap[] { reader.load("shop/goumaidaoju_up.png"), reader.load("shop/goumaidaoju_down.png") };
		bms_type = new Bitmap[] { reader.load("item/xiaopan.png"), reader.load("item/zuanshi.png") };
		bm_value_bg = new Bitmap[]{reader.load("item/daoju_jiazhi.png"),reader.load("shop/jiagekuang_z.png"),reader.load("shop/jiagekuang_j.png")};
	}

	@Override
	public void setItem() {
		// TODO Auto-generated method stub
		super.setItem();
		setImageView(mIv_buy_button, Config.ItemDetailUILayout.item_exchange, bms_buy[0], BUY,
				new ViewItemOnTouchListener(bms_buy));
		setImageView(mIv_value_ic, Config.ItemDetailUILayout.item_value_ic, bms_type[0], null, null);
		setImageView(mIv_value_diamond_ic, Config.ItemDetailUILayout.item_value_ic, bms_type[1], null, null);
		mIv_value_bg.setImageBitmap(bm_value_bg[2]);
		mIv_value_bg.setTag(CHOOSEGLOD);
		mIv_value_bg.setOnTouchListener(new ViewItemOnTouchListener());
		mIv_value_diamond_bg.setImageBitmap(bm_value_bg[2]);
		mIv_value_diamond_bg.setTag(CHOISEDIAMOND);
		mIv_value_diamond_bg.setOnTouchListener(new ViewItemOnTouchListener());
		GameUISetting.setViewWidthHeight(mIv_value_bg, bm_value_bg[0]);
		GameUISetting.setViewWidthHeight(mIv_value_diamond_bg, bm_value_bg[0]);
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_item_money_layout);
		RelativeLayout rl_diamond = (RelativeLayout) findViewById(R.id.rl_item_diamond_layout);
		GameUISetting.setViewLeftTop(rl, Config.ItemDetailUILayout.item_value_leftTop);
		GameUISetting.setViewLeftTop(rl_diamond, Config.ItemDetailUILayout.item_value_leftTop_diamond);
		this.setBackgroundResource(R.color.bg_transparent_height);

		tv_name = ((TextView) ((Activity) mContext).findViewById(R.id.tv_info_itemName));
		tv_info = ((TextView) ((Activity) mContext).findViewById(R.id.tv_info_text));
	}

	public void initItem(){
		// 金币和钻石框恢复原状
		mIv_value_bg.setImageBitmap(bm_value_bg[2]);
		mIv_value_diamond_bg.setImageBitmap(bm_value_bg[2]);
	}

	public void setValue(int type, int value) {
		this.type = type;
		switch (type) {
		case ShopItemData.GOLD:
			mIv_value_ic.setVisibility(View.VISIBLE);
			mIv_value_ic.setImageBitmap(bms_type[0]);
			mTv_value.setText("" + value);
			buy_price_gold = value;
			break;
		case ShopItemData.DIAMOND:
			mIv_value_diamond_ic.setVisibility(View.VISIBLE);
			mIv_value_diamond_ic.setImageBitmap(bms_type[1]);
			mTv_diamond_value.setText("" + value);
			buy_price_diamond = value;
			break;

		default:
			break;
		}
	}

	public void setItemIc(Bitmap bm,String picUrl) {
		paytype = 0;
		setImageViewUrl(mIv_item_ic, Config.ItemDetailUILayout.item_ic, bm, picUrl, null, null);
	}

	@Override
	protected void _touchEventAction(int key) {
		// TODO Auto-generated method stub
		super._touchEventAction(key);
		switch (key){
			case CHOOSEGLOD:
				// 选中金币支付
				paytype = CHOOSEGLOD;
				ToastUtils.showMessage(mContext,R.string.choose_gold);
				mIv_value_bg.setImageBitmap(bm_value_bg[1]);
				mIv_value_diamond_bg.setImageBitmap(bm_value_bg[2]);

				break;
			case CHOISEDIAMOND:
				// 选中钻石支付
				paytype = CHOISEDIAMOND;
				ToastUtils.showMessage(mContext,R.string.choose_diamond);
				mIv_value_bg.setImageBitmap(bm_value_bg[2]);
				mIv_value_diamond_bg.setImageBitmap(bm_value_bg[1]);
				break;
			case BUY:
				if(paytype == 0){
					Toast.makeText(mContext, R.string.no_pay_type, Toast.LENGTH_SHORT).show();
				}else{
					try{
						JSONArray feedArrar = new JSONArray();
						JSONObject item = new JSONObject();
						item.put("time",(System.currentTimeMillis() - 60000)+ "");//new Timestamp(System.currentTimeMillis())
						item.put("type","2");
						item.put("system_id",system_id + "");
						item.put("qty", "1");
						item.put("by",paytype + "");
						feedArrar.put(item);
						Log.d("feedArrar",feedArrar.toString());

						InternetApi.sync(mContext, token, feedArrar.toString(),this, REQUEST_SNCY);
					}catch (JSONException e){}
				}
				this.hidden();
				break;
		}
	}

	public void setItemInfo(String name, String info, String token, int system_id) {
		// TODO Auto-generated method stub
		tv_name.setText(name);
		tv_info.setText(info);
		this.token = token;
		this.system_id = system_id;
	}

	@Override
	public void hidden() {
		// TODO Auto-generated method stub
		super.hidden();
		tv_name.setText(R.string.info_name_default);
		tv_info.setText(R.string.info_text_default);
	}

	// ==Method===========================
	private android.os.Handler mHandler = new android.os.Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case REQUEST_SNCY:
					ToastUtils.showMessage(mContext,R.string.buy_success);
					break;
			}
		}
	};

	@Override
	public void Callback_MyThread(String result, int flag) {
		try{
			JSONObject jsonObject = new JSONObject(result);
			if(jsonObject.getBoolean("success")){
				Message msg = new Message();
				msg.what = flag;
				mHandler.sendMessage(msg);
			}else{
			}
		}catch(JSONException e){
		}
	}
}
