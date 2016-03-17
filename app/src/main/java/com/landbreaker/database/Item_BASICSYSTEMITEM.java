package com.landbreaker.database;

public class Item_BASICSYSTEMITEM {

	public long id;
	public int type;
	public int status;
	public String NAME;
	public String pic_url;
	public String description;
	public int LEVEL;
	public double rate;
	public int sell_price;
	public int buy_price;
	public int buy_money;

	public Item_BASICSYSTEMITEM() {

	}

	public Item_BASICSYSTEMITEM(long id, int type,int status, String name, String pic_url, String description, int level, double rate,
								int sell_price, int buy_price,int buy_money) {

		this.id = id;
		this.type = type;
		this.status = status;
		this.NAME = name;
		this.pic_url = pic_url;
		this.description = description;
		this.LEVEL = level;
		this.rate = rate;
		this.sell_price = sell_price;
		this.buy_price = buy_price;
		this.buy_money =  buy_money;
	}

	public String toString() {
		return "id=" + id + ",status=" + status + ",NAME=" + NAME + ",description=" + description;
	}
}
