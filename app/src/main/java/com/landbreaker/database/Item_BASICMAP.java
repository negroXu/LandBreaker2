package com.landbreaker.database;

public class Item_BASICMAP {
	public int id;
	public int STATUS;
	public String NAME;
	public String pic_url;
	public int basic_goldcoin;
	public int basic_maney;
	public int LEVEL;
	public double rate;
	public String description;

	public String toString() {
		return "" + id + "_" + NAME;
	}
}
