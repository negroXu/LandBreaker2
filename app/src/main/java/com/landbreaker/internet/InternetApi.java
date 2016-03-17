package com.landbreaker.internet;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.landbreaker.config.Config;
import com.yg.AnsynHttpRequestThreadPool.AnsynHttpRequest;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/17.
 */
public class InternetApi {

    /**
     * 用账号注册
     */
    public static void registerByAccount(Context context, String account, String password, Interface_MyThread _interface, int requestCode) {
        SimpleArrayMap<String, String> params = new SimpleArrayMap<String, String>();
        params.put("account", account);
        params.put("password", password);
        get(context, URLS.registerByAccount, params, _interface, requestCode, false, true);
    }

    /**
     * 用户登录
     */
    public static void loginByAccount(Context context,String account,String password,Interface_MyThread _interface,int requestCode)
    {
        SimpleArrayMap<String,String> params = new SimpleArrayMap<String,String>();
        params.put("account", account);
        params.put("password", password);
        get(context,URLS.loginByAccount,params,_interface,requestCode,false,true);
    }

    /**
     * 获取角色信息
     * */
    public static void getPlayerInfo(Context context, String token, Interface_MyThread _interface, int requestCode){
        SimpleArrayMap<String,String> params = new SimpleArrayMap<String, String>();
        params.put("token",token);
        get(context,URLS.getPlayerInfo,params,_interface,requestCode,false,false);
    }

    /**
     * 取新地图
     * @param context
     * @param token
     * @param _interface
     * @param requestCode
     */
    public static void newMap(Context context,String token,Interface_MyThread _interface, int requestCode){
        SimpleArrayMap<String,String> params = new SimpleArrayMap<String,String>();
        params.put("token",token);
        get(context,URLS.newMap,params,_interface,requestCode,false,false);

    };

    /**
     * 取地图
     * @param context
     * @param guid
     * @param _interface
     * @param requestCode
     */
    public static void getMapInfo(Context context,String guid,Interface_MyThread _interface, int requestCode){
        SimpleArrayMap<String,String> params = new SimpleArrayMap<String,String>();
        params.put("guid",guid);
        get(context,URLS.getMapInfo,params,_interface,requestCode,false,false);

    };

    /**
     * 获取商城数据
     * @param context
     * @param _interface
     * @param requestCode
     */
    public static void getShop(Context context,Interface_MyThread _interface, int requestCode){
        get(context,URLS.getShop,null,_interface,requestCode,false,false);
    }

    /**
     * 获取背包数据
     * @param context
     * @param token
     * @param _interface
     * @param requestCode
     */
    public static void getBagData(Context context,String token,Interface_MyThread _interface, int requestCode){
        SimpleArrayMap<String,String> params = new SimpleArrayMap<String,String>();
        params.put("token",token);
        get(context,URLS.getBagData,params,_interface,requestCode,false,false);
    }

    /**
     * 同步
     * @param context
     * @param token
     * @param feedStr
     * @param _interface
     * @param requestCode
     */
    public static void sync(final Context context, final String token, final String feedStr, final Interface_MyThread _interface, final int requestCode){
        Map<String,String> params = new HashMap<String, String>();
        params.put("feed",feedStr);
        //post(context,URLS.sync + "?token=" + token,params,_interface,requestCode,false,false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                postJson(context,URLS.sync + "?token=" + token,feedStr,_interface,requestCode);
            }
        }).start();
    }

    /**
     * Get请求
     * @param context
     * @param urls
     * @param params
     * @param _interface
     * @param requestCode
     * @param isCache
     * @param isDialog
     */
    private static void get(Context context, String urls, SimpleArrayMap<String, String> params, Interface_MyThread _interface, int requestCode, boolean isCache, boolean isDialog) {
        StringBuilder url = new StringBuilder(urls);
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                String key = params.keyAt(i);
                String values = params.get(key);
                if (i == 0) url.append("?");
                else url.append("&");
                url.append(key);
                url.append("=");
                url.append(values);
            }
        }
        AnsynHttpRequest.requestByGet(context, _interface, url.toString(), requestCode, isCache, isDialog);
    }

    /**
     * post 请求
     * @param context
     * @param url
     * @param params
     * @param _interface
     * @param requestCode
     * @param isCache
     * @param isDislog
     */
    private  static void post(Context context, String url, Map<String,String> params,Interface_MyThread _interface,int requestCode,boolean isCache,boolean isDislog){
        AnsynHttpRequest.requestByPost(context,_interface,url,params,requestCode,isCache,isDislog);
    }

    /**
     * post json格式
     * @param adress_Http
     * @param strJson
     * @return
     */
    public static String postJson(Context context,String adress_Http, String strJson,Interface_MyThread _interface,int requestCode) {

        String returnLine = "";
        try {

            System.out.println("**************开始http通讯**************");
            System.out.println("**************调用的接口地址为**************" + adress_Http);
            System.out.println("**************请求发送的数据为**************" + strJson);
            URL my_url = new URL(adress_Http);
            HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());

            byte[] content = strJson.getBytes("utf-8");

            out.write(content, 0, content.length);
            out.flush();
            out.close(); // flush and close

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            //StringBuilder builder = new StringBuilder();

            String line = "";

            System.out.println("Contents of post request start");

            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
                returnLine += line;
                System.out.println(line);
            }

            System.out.println("Contents of post request ends");

            reader.close();
            connection.disconnect();
            System.out.println("========返回的结果的为========" + returnLine);

            _interface.Callback_MyThread(returnLine,requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnLine;
    }
}
