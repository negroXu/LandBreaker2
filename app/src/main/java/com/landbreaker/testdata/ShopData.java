package com.landbreaker.testdata;

import com.google.gson.annotations.SerializedName;

public class ShopData {

	@SerializedName("id")
	public int id;
	@SerializedName("type")
	public int type;
	@SerializedName("status")
	public int status;
	@SerializedName("name")
	public String name;
	@SerializedName("pic_url")
	public String pic_url;
	@SerializedName("rate")
	public double rate;
	@SerializedName("level")
	public int level;
	@SerializedName("description")
	public String description;
	@SerializedName("sell_price")
	public int sell_price;
	@SerializedName("buy_price")
	public int buy_price;
	@SerializedName("buy_money")
	public int buy_money;
}
