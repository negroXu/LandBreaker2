package com.landbreaker.adapter;

import java.util.List;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.database.Item_BASICARCHIVEMENT;
import com.landbreaker.database.Item_GLOBALARCHIVEMENT_IN_PROGRESS;
import com.landbreaker.logic.GameUISetting;
import com.landbreaker.testdata.AchievementData;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tsz.afinal.FinalBitmap;

public class AchievementListAdapter extends BaseAdapter {
	private List<Item_BASICARCHIVEMENT> mData;
	private LayoutInflater mInflater;
	private Bitmap bg = null;
	private Bitmap complete = null;
	private Bitmap ico_bg = null;
	private FinalBitmap finalBitmap;
	private boolean isCompleted;

	public AchievementListAdapter(Context context, List<Item_BASICARCHIVEMENT> data, Bitmap bg, Bitmap ico_bg,
								  Bitmap ico_complete, boolean isCompleted) {
		mData = data;
		mInflater = LayoutInflater.from(context);
		this.bg = bg;
		this.complete = ico_complete;
		this.ico_bg = ico_bg;
		this.finalBitmap = Config.finalBitmap(context);
		this.isCompleted = isCompleted;
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

			convertView = mInflater.inflate(R.layout.item_achievement, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_achievement_ico);
			holder.icon_bg = (ImageView) convertView.findViewById(R.id.iv_achievement_ico_bg);
			holder.requirement = (TextView) convertView.findViewById(R.id.tv_achievement_requirement);
			holder.complete = (ImageView) convertView.findViewById(R.id.iv_achievement_complete);
			holder.name = (TextView) convertView.findViewById(R.id.tv_achievement_name);
			ImageView iv_bg = (ImageView) convertView.findViewById(R.id.iv_achievement_itembg);
			iv_bg.setImageBitmap(bg);
			GameUISetting.setViewWidthHeight(iv_bg, bg);
			holder.complete.setImageBitmap(complete);
			GameUISetting.setViewWidthHeight(holder.complete, complete);
			GameUISetting.setViewX(holder.complete, Config.AchievementUILayout.icon_complete.x);
			GameUISetting.setViewY(holder.complete, Config.AchievementUILayout.icon_complete.y);

			holder.icon_bg.setImageBitmap(ico_bg);
			GameUISetting.setViewWidthHeight(holder.icon_bg, ico_bg);
			GameUISetting.setViewX(holder.icon_bg, Config.AchievementUILayout.icon_bg_left);

			GameUISetting.setViewX(holder.icon, Config.AchievementUILayout.icon_left);

			LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll_achievement_text);
			GameUISetting.setViewX(ll, Config.AchievementUILayout.text_topLeft.x);
			GameUISetting.setViewY(ll, Config.AchievementUILayout.text_topLeft.y);

			holder.name.setMaxWidth((int) (Config.AchievementUILayout.text_width * Config.Size_Scale));
			holder.requirement.setMaxWidth((int) (Config.AchievementUILayout.text_width * Config.Size_Scale));

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Item_BASICARCHIVEMENT data = mData.get(position);

//		holder.icon.setImageBitmap(data.pic_url);
		finalBitmap.display(holder.icon,data.pic_url);
//		GameUISetting.setViewWidthHeight(holder.icon, );
		holder.name.setText(data.NAME);
		holder.requirement.setText(data.requirement);

		if (isCompleted)
			holder.complete.setVisibility(View.VISIBLE);
		else
			holder.complete.setVisibility(View.INVISIBLE);

		return convertView;
	}

	public final class ViewHolder {
		public ImageView icon;
		public ImageView icon_bg;
		public ImageView complete;
		public TextView name;
		public TextView requirement;
	}

}
