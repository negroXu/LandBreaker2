package com.landbreaker.bean;

import com.google.gson.annotations.SerializedName;

public class Bag {
	@SerializedName("id")
	public long id;
	@SerializedName("player_id")
	public long player_id;
	@SerializedName("type")
	public int type;
	@SerializedName("num")
	public int num;
	@SerializedName("curr_num")
	public int curr_num;
	@SerializedName("description")
	public String description;
	@SerializedName("globalItemList")
	public String globalItemList;
	@SerializedName("globalSystemItemList")
	public String globalSystemItemList;
	@Override
	public String toString() {
		return "Bag{" +
				"id=" + id +
				", player_id=" + player_id +
				", type=" + type +
				", num=" + num +
				", curr_num=" + curr_num +
				", description='" + description + '\'' +
				", globalItemList=" + globalItemList + ",globalSystemItemList=" + globalSystemItemList +
				"}";
	}
}
