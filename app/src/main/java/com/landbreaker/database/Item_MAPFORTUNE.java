package com.landbreaker.database;

public class Item_MAPFORTUNE {

	public int id;
	public String name;
	public String pic;
	public double activeRate;
	public double rate;

	public String toString() {
		return "" + id + "_" + name + "_" + pic + "_" + activeRate + "_" + rate;

	}
}
