package com.landbreaker.bean;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Feed {
    @SerializedName("time")
    public Timestamp time;
    // 1=sync; 2=buy; 3=sell
    @SerializedName("type")
    public int type;
    public final static int FEED_TYPE_SYNC = 1;
    public final static int FEED_TYPE_BUY = 2;
    public final static int FEED_TYPE_SELL = 3;
    @SerializedName("map_level")
    public int map_level;
    @SerializedName("map_equip")
    public int map_equip;
    @SerializedName("system_id")
    public int system_id;
    @SerializedName("bag_id")
    public int bag_id;// sell 的时候可用
    @SerializedName("qty")
    public int qty;

    @Override
    public String toString() {
        return "Feed{" +
                "time=" + time +
                ", type=" + type +
                ", map_level=" + map_level +
                ", map_equip=" + map_equip +
                ", system_id=" + system_id +
                ", bag_id=" + bag_id +
                ", qty=" + qty +
                '}';
    }
}
