package com.landbreaker.internet;

/**
 * Created by Administrator on 2015/12/17.
 */
public class URLS {

    public static String server = "http://www.yuangou.net:8080";

    // 注册
    public static String registerByAccount = server + "/LandBreaker/user/registerByAccount";

    // 登录
    public static String loginByAccount = server + "/LandBreaker/user/loginByAccount.do";

    // 换地图
    public static String newMap = server + "/LandBreaker/player/newMap.do";

    // 获取地图副本信息
    public static String getMapInfo = server + "/LandBreaker/system/getMapInfo.do";

    // 获取角色信息
    public static String getPlayerInfo = server + "/LandBreaker/player/getPlayerInfo.do";

    // 同步
    public static String sync = server + "/LandBreaker/player/sync.do";

    // 获取商城数据
    public static String getShop = server + "/LandBreaker/system/getShop.do";

    // 获取背包数据
    public static String getBagData = server + "/LandBreaker/player/getBagDetailByPlayerToken.do";
}
