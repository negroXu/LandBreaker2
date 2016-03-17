package com.landbreaker.testdata;

import com.google.gson.annotations.SerializedName;

public class BagData {

	@SerializedName("id")
	public int id;
	@SerializedName("player_id")
	public int player_id;
	@SerializedName("type")
	public int type;
	@SerializedName("num")
	public int num;
	@SerializedName("curr_num")
	public int curr_num;
	@SerializedName("description")
	public String description;
	@SerializedName("globalItemList")
	public GlobalItemList[] globalItemList;
	@SerializedName("globalSystemItemList")
	public GlobalSystemItemList[] globalSystemItemList;
}
