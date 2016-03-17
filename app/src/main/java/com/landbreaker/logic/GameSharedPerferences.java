package com.landbreaker.logic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/12/11.
 */
public class GameSharedPerferences {

    /**
     * 保存BGM的设置
     * @param context
     * @param isOn
     */
    public static void saveBGMOnOff(Context context, boolean isOn) {
        SharedPreferences sp = context.getSharedPreferences("SETTING",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("BGM",isOn);
        editor.commit();
    }

    /**
     * 读取BGM的设置
     * @param context
     * @return
     */
    public static boolean loadBGMOnOff(Context context) {
        SharedPreferences sp = context.getSharedPreferences("SETTING", Context.MODE_PRIVATE);
        return sp.getBoolean("BGM",true);
    }

    /**
     * 保存SE
     * @param context
     * @param isOn
     */
    public static void saveSEOnOff(Context context, boolean isOn) {
        SharedPreferences sp = context.getSharedPreferences("SETTING",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("SE",isOn);
        editor.commit();
    }

    /**
     * 读取SE
     * @param context
     * @return
     */
    public static boolean loadSEOnOff(Context context) {
        SharedPreferences sp = context.getSharedPreferences("SETTING", Context.MODE_PRIVATE);
        return sp.getBoolean("SE",true);
    }


}
