package com.landbreaker.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/12/17.
 */
public class JsonData {


    @SerializedName("data")
    public String data;
    @SerializedName("resultcode")
    public int resultcode;
    @SerializedName("msg")
    public String msg;
    @SerializedName("success")
    public boolean success;

    public String getdata() { return data;}

    public int getresultcode() {
        return resultcode;
    }

    public String getmsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "data=" + data.toString() +
                ", resultcode=" + resultcode +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }
}