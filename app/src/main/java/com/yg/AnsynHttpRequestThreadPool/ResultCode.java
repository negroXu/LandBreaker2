package com.yg.AnsynHttpRequestThreadPool;

/**
 * Created by Negro on 16/1/26.
 */
public class ResultCode {
    // 系统返回码9XXXXX
    public static Integer SUCCESS = 0; // 成功
    public static Integer UNKNOWN_ERROR = -1;// 未知错误
    public static Integer REQUIREINFO_NOTENOUGH = 90001;// 请求的参数不全名错误
    public static Integer REQUIREINFO_ERROR = 90002;// 请求的参数错误
    public static Integer ILLEGAL_FOUND = 90003;// 检测到非法数据

    // 用户错误码10XXX
    public static Integer USERNAME_ERROR = 10001;// 用户名错误
    public static Integer PASSWORD_ERROR = 10002;// 密码错误
    public static Integer USERTOKEN_ERROR = 10003;// 用户token错误
    public static Integer USERTOKEN_EXPIRED = 10004;// 用户token过期
    public static Integer USERID_ERROR = 10005;// 用户id不存在
    public static Integer USERNAME_EXIST = 10006;// 用户名已被使用
    public static Integer USERSTATUS_ERROR = 10007;// 用户状态错误
    public static Integer REGISTERINFO_NOTENOUGH = 10008;// 注册信息不全
    public static Integer USERROLE_ERROR = 10009;// 用户角色错误
    public static Integer USERACCOUNT_ERROR = 10010;// 请求注册的帐号长度少于6位
    public static Integer USERPASSWORD_ERROR = 10011;// 请求注册的密码长度少于6位
    public static Integer USERMOBILE_ERROR = 10012;// 请求注册的手机号有误
    public static Integer USERGENDER_ERROR = 10013;// 请求注册的性别有误
    public static Integer USEREMAIL_ERROR = 10014;// 请求注册的邮箱地址有误
    public static Integer USEREMAIL_NULL = 10015;// 用户邮箱地址为空
    public static Integer USERDEVICE_NULL = 10016;// 用户机型为空
    public static Integer ACCOUNT_NOTEXIST = 10017;// 用户帐号不存在
    public static Integer USERACCOUNT_NULL = 10018;// 请求注册的帐号为空
    public static Integer USERPASSWORD_NULL = 10019;// 请求注册的密码为空
    public static Integer USERROLE_NULL = 10020;// 用户角色为空
    public static Integer USERDEVICE_ERROR = 10021;// 用户机型错误
    public static Integer USERPUSHTOKEN_NULL = 10022;// 用户推送标识为空
    public static Integer USERPUSHTOKEN_ERROR = 10023;// 用户推送标识错误
    public static Integer ACCOUNT_EXIST = 10024;// 用户帐号已存在

    // 用户twitter返回码101XX
    public static Integer TWITTERINFO_NOTENOUGH = 10101;// Twitter 信息不全
    public static Integer TWITTERINFO_ERROR = 10102;// Twitter 信息错误

    // 用户facebook返回码102XX
    public static Integer FACEBOOKINFO_NOTENOUGH = 10201;// Facebook 信息不全
    public static Integer FACEBOOKINFO_ERROR = 10202;// Facebook 信息错误

    // 用户返回码11XXX
    public static Integer USERNAME_NOTEXIST = 11001;// 用户名可以使用
    public static Integer USERNAME_ACCOUNT = 11002;// 帐号用户名
    public static Integer USERNAME_MOBILE = 11003;// 手机号用户名
    public static Integer USERNAME_EMAIL = 11004;// 邮箱用户名

    // 用户状态码12XXX
    public static Integer USERSTATUS_ACTIVE = 12001;// Active
    public static Integer USERSTATUS_PAUSE = 12002;// Pause
    public static Integer USERSTATUS_PENDING = 12003;// Pending
    public static Integer USERSTATUS_LOCKED = 12004;// Locked

    // 游戏角色错误码200XX
    public static Integer PLAYERID_NULL = 20001;// 游戏角色id为空
    public static Integer PLAYERID_ERROR = 20002;// 游戏角色id错误
    public static Integer PLAYERTOKEN_NULL = 20003;// 游戏角色token为空
    public static Integer PLAYERTOKEN_ERROR = 20004;// 游戏角色token错误
    public static Integer PLAYERHP_NOTENOUGH = 20005;// 游戏角色体力不足
    public static Integer PLAYER_KEY_NOTENOUGH = 20006;// 游戏钥匙不足
    public static Integer PLAYER_GOLDCOIN_NOTENOUGH = 20007;// 游戏金币不足
    public static Integer PLAYER_MONEY_NOTENOUGH = 20008;// 游戏钻石不足


    // 背包错误码201XX
    public static Integer PLAYERBAG_ERROR = 20101;// 游戏角色背包错误
    public static Integer PLAYERBAG_NOTENOUGH = 20102;// 游戏角色背包位置不足
    public static Integer PLAYERBAG_TYPE_ERROR = 20103;// 游戏角色背包类型错误
    public static Integer PLAYERBAG_OUTOFNUMBER = 20104;// 游戏角色背包超出背包格上限

    // 游戏物品错误码3XXXX
    public static Integer ITEMID_NULL = 30001;// 游戏物品id为空
    public static Integer ITEMID_ERROR = 30002;// 游戏物品id错误
    public static Integer ITEMID_DUPLICATE = 30003;// 游戏物品id重复
    public static Integer ITEM_KEY_ERROR = 30004;// 游戏钥匙不能直接使用

    // 游戏副本错误码4XXXX
    public static Integer MAPID_NULL = 40001;// 游戏副本id为空
    public static Integer MAPID_ERROR = 40002;// 游戏副本id错误
    public static Integer MAPID_DUPLICATE = 40003;// 游戏副本id重复

    // 同步feed错误码5XXXX
    public static Integer FEEDTIME_ERROR = 50001;// feed的时间参数有误
    public static Integer FEEDTYPE_ERROR = 50002;// feed的类型参数有误
    public static Integer FEEDMAPLEVEL_NULL = 50003;// feed的map_level 为空
    public static Integer FEEDMAPLEVEL_ERROR = 50004;// feed的map_level 错误
    public static Integer FEEDSYSTEMID_NULL = 50005;// feed的system_id 为空
    public static Integer FEEDSYSTEMID_ERROR = 50006;// feed的system_id 错误
    public static Integer FEEDQTY_NULL = 50007;// feed的qty 为空
    public static Integer FEEDQTY_ERROR = 50008;// feed的qty 错误
    public static Integer FEEDBY_NULL = 50009;// feed的by 为空
    public static Integer FEEDBY_ERROR = 50010;// feed的by 错误

    // 根据resultcode返回错误信息
    public static String getErrmsg(Integer resultCode) {
        String errmsg = "";
        switch (resultCode) {
            // 系统类
            case 0:
                errmsg = "success";
                break;
            case -1:
                errmsg = "unknown error";
                break;
            case 90001:
                errmsg = "require info not enough";
                break;
            case 90002:
                errmsg = "require info error";
                break;
            case 90003:
                errmsg = "illegal found, please re-login";
                break;

            // 用户类
            case 10001:
                errmsg = "invalid username";
                break;
            case 10002:
                errmsg = "wrong password";
                break;
            case 10003:
                errmsg = "invalid token";
                break;
            case 10004:
                errmsg = "expired token";
                break;
            case 10005:
                errmsg = "invalid user_id";
                break;
            case 10006:
                errmsg = "username exist";
                break;
            case 10007:
                errmsg = "user status error";
                break;
            case 10008:
                errmsg = "register info not enough";
                break;
            case 10009:
                errmsg = "user role error";
                break;
            case 10010:
                errmsg = "account length smaller than 6 chars";
                break;
            case 10011:
                errmsg = "password length smaller than 6 chars";
                break;
            case 10012:
                errmsg = "mobile error";
                break;
            case 10013:
                errmsg = "gender error";
                break;
            case 10014:
                errmsg = "email error";
                break;
            case 10015:
                errmsg = "email address is null";
                break;
            case 10016:
                errmsg = "user device is null";
                break;
            case 10017:
                errmsg = "user account not exist";
                break;
            case 10018:
                errmsg = "user account is null";
                break;
            case 10019:
                errmsg = "user password is null";
                break;
            case 10020:
                errmsg = "user role is null";
                break;
            case 10021:
                errmsg = "user device error";
                break;
            case 10022:
                errmsg = "user push_token is null";
                break;
            case 10023:
                errmsg = "user push_token error";
                break;
            case 10024:
                errmsg = "user account exist";
                break;

            // Twitter
            case 10101:
                errmsg = "Twitter info not enough";
                break;
            case 10102:
                errmsg = "Twitter info error";
                break;

            // Facebook
            case 10201:
                errmsg = "Facebook info not enough";
                break;
            case 10202:
                errmsg = "Facebook info error";
                break;

            case 11001:
                errmsg = "username not exist";
                break;
            case 11002:
                errmsg = "account exist";
                break;
            case 11003:
                errmsg = "mobile exist";
                break;
            case 11004:
                errmsg = "email exist";
                break;

            case 12001:
                errmsg = "active status";
                break;
            case 12002:
                errmsg = "pause status";
                break;
            case 12003:
                errmsg = "pending status";
                break;
            case 12004:
                errmsg = "locked status";
                break;

            // player
            case 20001:
                errmsg = "player id is null";
                break;
            case 20002:
                errmsg = "player id error";
                break;
            case 20003:
                errmsg = "player token is null";
                break;
            case 20004:
                errmsg = "player token error";
                break;
            case 20005:
                errmsg = "player HP not enough";
                break;
            case 20006:
                errmsg = "player key not enough";
                break;
            case 20007:
                errmsg = "player gold coin not enough";
                break;
            case 20008:
                errmsg = "player money not enough";
                break;

            // bag relate
            case 20101:
                errmsg = "player bag error";
                break;
            case 20102:
                errmsg = "player bag not enough";
                break;
            case 20103:
                errmsg = "player bag type error";
                break;
            case 20104:
                errmsg = "player bag out of bag limit";
                break;

            // game item
            case 30001:
                errmsg = "item id is null";
                break;
            case 30002:
                errmsg = "item id error";
                break;
            case 30003:
                errmsg = "item id duplicate";
                break;
            case 30004:
                errmsg = "key cannot directly use, please post box id to use the key";
                break;

            // game map
            case 40001:
                errmsg = "map id is null";
                break;
            case 40002:
                errmsg = "map id error";
                break;
            case 40003:
                errmsg = "map id duplicate";
                break;

            // feed
            case 50001:
                errmsg = "feed time error";
                break;
            case 50002:
                errmsg = "feed type error";
                break;
            case 50003:
                errmsg = "feed map_level null";
                break;
            case 50004:
                errmsg = "feed map_level error";
                break;
            case 50005:
                errmsg = "feed system_id null";
                break;
            case 50006:
                errmsg = "feed system_id error";
                break;
            case 50007:
                errmsg = "feed qty null";
                break;
            case 50008:
                errmsg = "feed qty error";
                break;
            case 50009:
                errmsg = "feed by null";
                break;
            case 50010:
                errmsg = "feed by error";
                break;
        }
        return errmsg;
    }
}
