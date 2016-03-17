package com.landbreaker.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class UserData {
	@SerializedName("id")
	public long id;
	@SerializedName("account")
	public String account;
	@SerializedName("password")
	public String password;
	@SerializedName("email")
	public String email;
	@SerializedName("mobile")
	public String mobile;
	@SerializedName("role")
	public int role;
	@SerializedName("name")
	public String name;
	@SerializedName("gender")
	public Object gender;
	@SerializedName("pic_url")
	public String pic_url;
	@SerializedName("description")
	public String description;
	@SerializedName("access_group")
	public Object access_group;
	@SerializedName("status")
	public int status;
	@SerializedName("token")
	public String token;
	@SerializedName("device")
	public int device;
	@SerializedName("push_token")
	public String push_token;
	@SerializedName("login_time")
	public long login_time;
	@SerializedName("create_time")
	public long create_time;
	@SerializedName("facebookInfo")
	public String facebookInfo;
	@SerializedName("twitterInfo")
	public String twitterInfo;
	@SerializedName("playerList")
	public PlayerList[] playerList = null;

	public String getaccount(){
		return this.account;
	}

	@Override
	public String toString() {
		return "UserData{" +
				"id=" + id +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", mobile='" + mobile + '\'' +
				", role=" + role +
				", name='" + name + '\'' +
				", gender=" + gender +
				", pic_url='" + pic_url + '\'' +
				", description='" + description + '\'' +
				", acess_group=" + access_group +
				", status=" + status +
				", token='" + token + '\'' +
				", device=" + device +
				", push_token='" + push_token + '\'' +
				", login_time=" + login_time +
				", create_time=" + create_time +
				", facebookInfo='" + facebookInfo + '\'' +
				", twitterInfo='" + twitterInfo + '\'' +
				", playerList=" + Arrays.toString(playerList) +
				'}';
	}
}
