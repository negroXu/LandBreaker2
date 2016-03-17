package com.landbreaker.application;

import android.app.Application;

import com.landbreaker.bean.UserData;
import com.landbreaker.testdata.BagData;
import com.landbreaker.testdata.ShopData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/12/21.
 */
public class AppData extends Application {

    public UserData userData = new UserData();
    public HashMap<Integer,Integer> equipList = new HashMap<Integer, Integer>();
    public List<ShopData> mListShopData = null;
    public List<BagData> mListBagData = null;

    @Override
    public void onCreate(){
        super.onCreate();
    }

//    public void setUserData(UserData userData){
//        this.userData = userData;
//    }
//
//    public UserData getUserData(){
//        return this.userData;
//    }
}
