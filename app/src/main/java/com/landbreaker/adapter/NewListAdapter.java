package com.landbreaker.adapter;

import java.util.List;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.testdata.AnnouncementData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class NewListAdapter extends BaseAdapter {
	private List<AnnouncementData> mData;
	private LayoutInflater mInflater;

	public NewListAdapter(Context context, List<AnnouncementData> data) {
		mInflater = LayoutInflater.from(context);
		mData = data;
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

			convertView = mInflater.inflate(R.layout.item_new, null);
			holder.img = (ImageView) convertView.findViewById(R.id.iv_new);
			holder.item = (TextView) convertView.findViewById(R.id.tv_new);
			holder.img.getLayoutParams().width = (int) (Config.MissionUILayout.list_img_width * Config.Size_Scale);
			holder.img.getLayoutParams().height = (int) (Config.NewUILayout.img_defaultHeight * Config.Size_Scale);

			LinearLayout.LayoutParams lp = (LayoutParams) holder.item.getLayoutParams();
			lp.setMargins((int) (Config.NewUILayout.title_margin * Config.Size_Scale),
					(int) (Config.NewUILayout.item_interval * Config.Size_Scale),
					(int) (Config.NewUILayout.title_margin * Config.Size_Scale),
					(int) (Config.NewUILayout.item_interval * Config.Size_Scale));
			holder.item.setLayoutParams(lp);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AnnouncementData data = mData.get(position);
		holder.img.setImageBitmap(data.img);
		holder.item.setText(data.title);
		return convertView;
	}

	public final class ViewHolder {

		public ImageView img;

		public TextView item;
	}

}
