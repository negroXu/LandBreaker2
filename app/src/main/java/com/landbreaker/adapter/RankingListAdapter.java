package com.landbreaker.adapter;

import java.util.List;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.RankData;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RankingListAdapter extends BaseAdapter {

	private List<RankData> mData;
	private LayoutInflater mInflater;
	private Bitmap bg = null;

	public RankingListAdapter(Context context, List<RankData> data, Bitmap bg) {
		mData = data;
		mInflater = LayoutInflater.from(context);
		this.bg = bg;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.item_ranking, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_ranking_logo);
			holder.point = (TextView) convertView.findViewById(R.id.tv_ranking_point);
			holder.rank = (TextView) convertView.findViewById(R.id.tv_ranking_rank);
			ImageView iv_bg = (ImageView) convertView.findViewById(R.id.iv_ranking_itembg);
			iv_bg.setImageBitmap(bg);
			GameUISetting.setViewWidthHeight(iv_bg, bg);
			GameUISetting.setViewX(holder.icon, Config.RankingUILayout.list_ico_left);

			holder.icon.getLayoutParams().width = (int) (Config.RankingUILayout.list_ico_size.x * Config.Size_Scale);
			holder.icon.getLayoutParams().height = (int) (Config.RankingUILayout.list_ico_size.y * Config.Size_Scale);
			LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll_ranking_text);
			GameUISetting.setViewX(ll, Config.RankingUILayout.list_text_left);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		RankData data = mData.get(position);

		holder.icon.setImageBitmap(data.icon);
		holder.rank.setText(data.Rank);
		holder.point.setText(data.Point);

		return convertView;
	}

	public final class ViewHolder {
		public ImageView icon;
		public TextView rank;
		public TextView point;
	}

}
