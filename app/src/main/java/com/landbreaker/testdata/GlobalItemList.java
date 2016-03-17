package com.landbreaker.testdata;

import com.google.gson.annotations.SerializedName;

public class GlobalItemList {

	@SerializedName("guid")
	public String guid;
	@SerializedName("item_id")
	public int item_id;
	@SerializedName("item_num")
	public int item_num;
	@SerializedName("system_id")
	public int system_id;
	@SerializedName("player_id")
	public int player_id;
	@SerializedName("bag_id")
	public int bag_id;
	@SerializedName("box_id")
	public int box_id;
	@SerializedName("create_time")
	public double create_time;
	@SerializedName("get_time")
	public double get_time;
}
