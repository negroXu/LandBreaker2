package com.landbreaker.bean;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class MapData {
	@SerializedName("guid")
	public String guid;
	@SerializedName("map_id")
	public int map_id;
	@SerializedName("mapfortune_id")
	public int mapfortune_id;
	@SerializedName("systemitem_list")
	public String systemitem_list;
	@SerializedName("item_list")
	public Object item_list;
	@SerializedName("item_list_return")
	public String item_list_return;
	@SerializedName("latest_maplevel")
	public int latest_maplevel;
	@SerializedName("create_time")
	public long create_time;

	private HashMap<String, Object> myList = null;
	private HashMap<String, Object> systemitemList = null;

	public HashMap<String, Object> getItemDropList() {
		if (myList == null) {
			myList = new HashMap<String, Object>();
			String[] strs = this.item_list_return.split(";");
			for (String str : strs) {
				String[] part = str.split(":");
				myList.put(part[0], Integer.parseInt(part[1]));
			}
		}	

		return myList;
	}

	public HashMap<String, Object> getsystemitemList() {
		if (systemitemList == null) {
			systemitemList = new HashMap<String, Object>();
			String[] strs = this.systemitem_list.split(";");
			for (String str : strs) {
				String[] part = str.split(":");
				systemitemList.put(part[0], Integer.parseInt(part[1]));
			}
		}

		return systemitemList;
	}

	@Override
	public String toString() {
		return "MapData{" +
				"create_time=" + create_time +
				", guid='" + guid + '\'' +
				", map_id=" + map_id +
				", mapfortune_id=" + mapfortune_id +
				", systemitem_list='" + systemitem_list + '\'' +
				", item_list=" + item_list +
				", item_list_return='" + item_list_return + '\'' +
				", latest_maplevel=" + latest_maplevel +
				'}';
	}
}
