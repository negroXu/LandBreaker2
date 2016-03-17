package com.landbreaker.testdata;

import com.google.gson.annotations.SerializedName;

public class GlobalSystemItemList {

	@SerializedName("id")
	public String id;
	@SerializedName("num")
	public int num;
	@SerializedName("systemItem_id")
	public int systemItem_id;
	@SerializedName("player_id")
	public int player_id;
	@SerializedName("bag_id")
	public int bag_id;
	@SerializedName("start_time")
	public double start_time;
	@SerializedName("end_time")
	public double end_time;
	@SerializedName("create_time")
	public double create_time;

}
