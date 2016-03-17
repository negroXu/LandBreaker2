package com.landbreaker.adapter;

import java.util.List;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.testdata.MissionData;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MissionListAdapter extends BaseAdapter {

	private List<MissionData> mData;
	private LayoutInflater mInflater;

	public MissionListAdapter(Context context, List<MissionData> data, Bitmap logo) {
		mData = data;
		mInflater = LayoutInflater.from(context);
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

			convertView = mInflater.inflate(R.layout.item_mission, null);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_mission);
			holder.item = (TextView) convertView.findViewById(R.id.tv_mission);
			holder.img.getLayoutParams().width = (int) (Config.MissionUILayout.list_img_width * Config.Size_Scale);
			holder.img.getLayoutParams().height = (int) (Config.MissionUILayout.list_img_height * Config.Size_Scale);

			RelativeLayout.LayoutParams lp = (LayoutParams) holder.item.getLayoutParams();
			lp.setMargins((int) (Config.MissionUILayout.list_text_left * Config.Size_Scale),
					(int) (Config.MissionUILayout.list_text_marginTop * Config.Size_Scale),
					(int) (Config.MissionUILayout.list_text_left * Config.Size_Scale), 0);
			holder.item.setLayoutParams(lp);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MissionData data = mData.get(position);
		holder.img.setImageBitmap(data.mission_pic);
		holder.item.setText(holder.item.getText() + data.mission_item);
		return convertView;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView item;
	}

}
