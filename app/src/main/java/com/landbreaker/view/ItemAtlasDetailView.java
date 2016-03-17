package com.landbreaker.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.landbreaker.R;
import com.landbreaker.base.GamePopView;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.testdata.ItemAtlasData;

public class ItemAtlasDetailView extends GamePopView {
	public ImageView mIv_item_ic = null;
	public ImageView mIv_rank = null;

	public int rank;
	private TextView tv_name;
	private TextView tv_info;

	public Bitmap[] bms_rank = null;

	public ItemAtlasDetailView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(Context context) {
		// TODO Auto-generated method stub
		super.init(context);
		addViewInThisFromXML(R.layout.item_itemdetail, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mIv_item_ic = (ImageView) findViewById(R.id.iv_item_display);

		mIv_rank = (ImageView) findViewById(R.id.iv_item_rank);
	}

	@Override
	public void loadImage(ImgReader reader) {
		// TODO Auto-generated method stub
		super.loadImage(reader);

		bms_rank = reader.loadItemRank();

	}

	@Override
	public void setItem() {
		// TODO Auto-generated method stub
		super.setItem();

		setImageView(mIv_rank, Config.ItemDetailUILayout.item_rank, bms_rank[0], null, null);
		this.setBackgroundResource(R.color.bg_transparent_height);

		tv_name = ((TextView) ((Activity) mContext).findViewById(R.id.tv_info_itemName));
		tv_info = ((TextView) ((Activity) mContext).findViewById(R.id.tv_info_text));
	}

	public void setItemData(ItemAtlasData data, Bitmap bm) {
		setImageView(mIv_item_ic, Config.ItemDetailUILayout.item_ic, bm, null, null);
		tv_name.setText(data.item_name);
		tv_info.setText(data.item_info);
		mIv_rank.setImageBitmap(bms_rank[data.item_rank]);
	}

	@Override
	public void hidden() {
		// TODO Auto-generated method stub
		super.hidden();
		tv_name.setText(R.string.info_name_default);
		tv_info.setText(R.string.info_text_default);
	}
}
