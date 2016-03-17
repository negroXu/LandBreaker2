package com.landbreaker.bean;

import com.google.gson.annotations.SerializedName;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import java.util.Arrays;
import java.util.HashMap;

public class PlayerList {

	@SerializedName("id")
	public long id;
	@SerializedName("user_id")
	public long user_id;
	@SerializedName("status")
	public int status;
	@SerializedName("name")
	public String name;
	@SerializedName("pic_url")
	public String pic_url;
	@SerializedName("level")
	public int level;
	@SerializedName("experience")
	public long experience;
	@SerializedName("goldcoin")
	public long goldcoin;
	@SerializedName("money")
	public long money;
	@SerializedName("equip")
	public String equip;
	@SerializedName("buff")
	public Object buff;
	@SerializedName("title")
	public Object title;
	@SerializedName("archievement_point")
	public long archievement_point;
	@SerializedName("hp")
	public int hp;
	@SerializedName("hp_time")
	public long hp_time;
	@SerializedName("globalmap_id")
	public long globalmap_id;
	@SerializedName("token")
	public String token;
	@SerializedName("login_time")
	public long login_time;
	@SerializedName("create_time")
	public long create_time;
	@SerializedName("bagList")
	public Bag[] bagList;

	private HashMap<Integer, Integer> myList = null;

	public HashMap<Integer, Integer> getequipList() {
		if (myList == null) {
			myList = new HashMap<Integer, Integer>();
			String[] strs = this.equip.split(";");
			for (String str : strs) {
				String[] part = str.split(":");
				myList.put(Integer.parseInt(part[0]), Integer.parseInt(part[1]));
			}
		}
		return myList;
	}

	@Override
	public String toString() {
		return "PlayerList{" +
				"id=" + id +
				", user_id=" + user_id +
				", status=" + status +
				", name='" + name + '\'' +
				", pic_url='" + pic_url + '\'' +
				", level=" + level +
				", experience=" + experience +
				", goldcoin=" + goldcoin +
				", money=" + money +
				", equip='" + equip + '\'' +
				", buff=" + buff +
				", title=" + title +
				", archievement_point=" + archievement_point +
				", hp=" + hp +
				", hp_time=" + hp_time +
				", globalmap_id=" + globalmap_id +
				", token='" + token + '\'' +
				", login_time=" + login_time +
				", create_time=" + create_time +
				", bagList=" + Arrays.toString(bagList) +
				"}";
	}
}
