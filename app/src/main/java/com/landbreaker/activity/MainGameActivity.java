package com.landbreaker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.Preference;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.landbreaker.application.AppData;
import com.landbreaker.base.GameScene._GameRequestData;
import com.landbreaker.bean.Feed;
import com.landbreaker.bean.MapData;
import com.landbreaker.bean.PlayerList;
import com.landbreaker.config.Config;
import com.landbreaker.config.GameStage;
import com.landbreaker.database.Item_BASICITEM;
import com.landbreaker.database.Item_BASICSYSTEMITEM;
import com.landbreaker.database.Table_BASICITEM;
import com.landbreaker.database.Table_BASICSYSTEMITEM;
import com.landbreaker.internet.InternetApi;
import com.landbreaker.item.GameItem_Dig;
import com.landbreaker.testdata.TestData;
import com.landbreaker.ui.UI_ToolsBar;
import com.landbreaker.view.GameView;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainGameActivity extends Activity implements _GameRequestData, Interface_MyThread {

    private GameView mGameView;
    private AppData appdata;
    private String token,last_guid;
    private int last_depth = 0;

    private ProgressDialog dialog;

    private static final int REQUEST_CODE_NEWMAP = 1;
    private static final int REQUEST_CODE_GETPLAYERINFO = 2;
    private static final int REQUEST_CODE_SYNC = 3;
    private static final int REQUEST_CODE_OLDMAP = 4;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        mGameView = new GameView(this, Config.default_PFS);
        appdata = (AppData) getApplication();
        token = appdata.userData.playerList[0].token;


        SharedPreferences sharedPreferences = getSharedPreferences("mapdata",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (token == null) {
            // 重新登陆
        }

        setContentView(mGameView);
        // 获取角色信息
        // requestGetPlayerInfo();

        // 读取上一次地图进度
        LoadData();

        if(last_guid != null && last_depth < GameStage.DEPTH_MAX){
            // 获取旧地图数据
            requestOldMap(last_guid);
        }else{
            // 获取新地图数据
            requestNewMap();
        }

        dialog = ProgressDialog.show(this,null,"正在加载",false,false);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        setContentView(mGameView);
        // 刷新体力
//        if(((AppData)getApplication()).userData.playerList != null){
//            mGameView.Scene.setToriiStamina(((AppData)getApplication()).userData.playerList[0].hp);
//        }
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        mGameView.Scene.closeMenu();
    }

    /**
     * 保存数据
     * @param last_depth
     * @param last_guid
     */
    public void SaveData(int last_depth,String last_guid ) {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("mapdata", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putInt("depth", last_depth);         //根据键值对添加数据
        edit.putString("guid", last_guid);
        edit.commit();
    }

    /**
     * 读取数据
     */
    public void LoadData() {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("mapdata", MODE_PRIVATE);
        last_depth = share.getInt("depth",0);
        last_guid = share.getString("guid",null);
    }
    /**
     * 申请切换地图
     */
    @Override
    public void changeMap() {
        // TODO Auto-generated method stub
        requestNewMap();
    }

    /**
     * 同步数据
     */
    @Override
    public void upLoadData(Feed[] feeds, UI_ToolsBar mUI_ToolsBar) {
        // TODO Auto-generated method stub
        //test
        String name = "";
        if (feeds[0].system_id > 10000) {
            // 物品
            Table_BASICITEM table = new Table_BASICITEM(this);
            Item_BASICITEM item = table.get(feeds[0].system_id);
            name = item.NAME;
        } else if(feeds[0].system_id < 10000 && feeds[0].system_id > 0){
            // 系统物品
            Table_BASICSYSTEMITEM table_basicsystemitem = new Table_BASICSYSTEMITEM(this);
            Item_BASICSYSTEMITEM item_basicsystemitem = table_basicsystemitem.get(feeds[0].system_id);
            name = item_basicsystemitem.NAME;
        } else if(feeds[0].system_id == -1){
            // 从systenitem_list中按照深度随机抽相应等级物品
            return;
        }

        try{
            JSONArray feedArrar = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("time",(System.currentTimeMillis() - 60000)+ "");//new Timestamp(System.currentTimeMillis())
            item.put("type","1");
            item.put("map_level",feeds[0].map_level + "");
            item.put("system_id",feeds[0].system_id + "");
            item.put("qty",feeds[0].qty + "");
            feedArrar.put(item);

            List<GameItem_Dig> list = mUI_ToolsBar.getCurrentItemList();
            GameItem_Dig gameItem_dig = list.get(0);

            //耗损2 并判断铲是都损坏
            if(!gameItem_dig.reductionDurable(2)){
                // 已损坏 移除
                mUI_ToolsBar.mItems[0].removeItem(gameItem_dig);

                item.put("map_equip", mUI_ToolsBar.mItems[0].id + "");
                // 修改铲数量全局值
                appdata.equipList.put(mUI_ToolsBar.mItems[0].id,appdata.equipList.get(mUI_ToolsBar.mItems[0].id) - 1);
            }

            Log.d("feedArrar",feedArrar.toString());

            // 执行同步
            InternetApi.sync(MainGameActivity.this,token,feedArrar.toString(),MainGameActivity.this,REQUEST_CODE_SYNC);

            Toast.makeText(this, "id:" + feeds[0].system_id + "\n depth:" + feeds[0].map_level + "\nname:" + name,
                    Toast.LENGTH_LONG).show();
        }catch (JSONException e){

        }

    }

    @Override
    public void Callback_MyThread(String result, int flag) {
        try{
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getBoolean("success")){
                Message msg = new Message();
                msg.what = flag;
                switch (flag){
                    case REQUEST_CODE_GETPLAYERINFO:
                        msg.obj = jsonObject.getString("data");
                        break;
                    case REQUEST_CODE_NEWMAP:
                    case REQUEST_CODE_OLDMAP:
                        msg.obj = jsonObject.getString("data");
                        break;
                    case REQUEST_CODE_SYNC:
                        msg.obj = jsonObject.getString("sync");
                        break;
                }
                mHandler.sendMessage(msg);
            }else{
                if(mGameView.Scene != null && flag == REQUEST_CODE_NEWMAP){
                    mGameView.Scene.changeMapFinish();
                }
                //失败提示 为处理
                Toast.makeText(MainGameActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
            }
        }catch(JSONException e){
        }
    }

    // ==Method===========================
    private android.os.Handler mHandler = new android.os.Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_CODE_NEWMAP:
                case REQUEST_CODE_OLDMAP:
                    MapData mapData = new Gson().fromJson((String)msg.obj,new TypeToken<MapData>(){}.getType());
                    setGameSence(mapData,msg.what);
                    last_guid = mapData.guid;
                    break;
                case REQUEST_CODE_GETPLAYERINFO:
                    for(int i = 0;i < ((AppData)getApplication()).userData.playerList.length; i++){
                        if(((AppData)getApplication()).userData.playerList[i].token.equals(token)){
                            ((AppData)getApplication()).userData.playerList[i] = new Gson().fromJson((String)msg.obj,new TypeToken<PlayerList>(){}.getType());
                            mGameView.Scene.setToriiStamina(((AppData)getApplication()).userData.playerList[i].hp);
                        }
                    }
                    break;
                case REQUEST_CODE_SYNC:
                    Log.d("sync","success");
                    break;
            }
        }
    };

    /**
     * 发起请求获取角色信息
     */
    private void requestGetPlayerInfo(){
        InternetApi.getPlayerInfo(this,token,MainGameActivity.this,REQUEST_CODE_GETPLAYERINFO);
    }

    /**
     * 发起请求地图数据
     * */
    private void requestNewMap(){
        InternetApi.newMap(this, token, MainGameActivity.this, REQUEST_CODE_NEWMAP);
        // 暂用
//        InternetApi.getMapInfo(this, "0000005", MainGameActivity.this, REQUEST_CODE_NEWMAP);
    }

    /**
     * 取旧地图数据
     * @param gui
     */
    private void requestOldMap(String gui){
        InternetApi.getMapInfo(this,gui,MainGameActivity.this,REQUEST_CODE_OLDMAP);
    }

    /**
     * 创建游戏场景
     */
    private void setGameSence(MapData map,int code) {
        if(dialog.isShowing()){
            dialog.dismiss();
        }

//        Random r = new Random();
//        MapData map_r = TestData.getMapData(r.nextInt(3)).map;//test

        mGameView.createGameScene(map,appdata.equipList);
        mGameView.Scene.setCallBack(this);
//        mGameView.Scene.changeMapStart();
//        mGameView.Scene.changeMapFinish();

        // 设置深度
        if(code == REQUEST_CODE_OLDMAP){
            mGameView.Scene.mUI_depth.down(last_depth);
        }

        // 获取用户详细信息
        requestGetPlayerInfo();

        //Toast.makeText(this, "" + map.map_id + "\n" + map.item_list_return, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "MainGame Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.landbreaker.activity/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 存储地图ID及深度
        SaveData(mGameView.Scene.mUI_depth.getDepth(),last_guid);
        Log.d("mUI_depth",mGameView.Scene.mUI_depth.getDepth() + "");
    }
}