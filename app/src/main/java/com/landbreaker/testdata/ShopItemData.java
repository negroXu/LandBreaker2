package com.landbreaker.testdata;

import android.graphics.Bitmap;

public class ShopItemData {

	public final static int NORMAL = 1; // goldcoin
	public final static int RARE = 2; //money
	public final static int KHORIUM = 3;// 氪金

	public final static int GOLD = 8;
	public final static int DIAMOND = 9;

	public String item_name;
	public Bitmap item_ic;
	public int item_value;
	public String item_info;
	public int type;
	public String item_ic_path;

	public int buy_price = 0;
	public int buy_money = 0;
}
