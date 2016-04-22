package com.landbreaker.database;

public class Item_BASICARCHIVEMENT {

	public int id;
	public int STATUS;
	public int type;
	public String NAME;
	public String description;
	public String pic_url;
	public int LEVEL;
	public int point;
	public String requirement;
	public String reward;

    public static final int STATUS_ACTIVE = 1;
	public int getId(){
		return id;
	}

}
