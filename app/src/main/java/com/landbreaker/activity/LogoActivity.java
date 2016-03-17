package com.landbreaker.activity;

import java.util.List;

import com.landbreaker.R;
import com.landbreaker.config.Config;
import com.landbreaker.database.Item_BASICMAP;
import com.landbreaker.database.Table_BASICITEM;
import com.landbreaker.database.Table_BASICMAP;
import com.landbreaker.database.Table_BASICSYSTEMITEM;
import com.landbreaker.database.Table_MAPFORTUNE;
import com.landbreaker.file.ImgReader;
import com.landbreaker.logic.GameUISetting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class LogoActivity extends Activity {
    private ImgReader mImgReader = null;
    private ImageView mLogo = null;
    private ImageView mBackground = null;
    private Bitmap bm_logo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        init();
    }

    private void init() {
        Config.Size_Scale = Config.scaleSize(this);
        mImgReader = new ImgReader(this);
        mLogo = (ImageView) findViewById(R.id.iv_logo);
        bm_logo = mImgReader.load("logo/gamelogo.png");
        mLogo.setImageBitmap(bm_logo);
        GameUISetting.setViewWidthHeight(mLogo, bm_logo);
        GameUISetting.setViewCenterPosition(mLogo, Config.LogUILayout.logo, bm_logo.getWidth(), bm_logo.getHeight());

        mBackground = (ImageView) findViewById(R.id.iv_background);
        mBackground.setImageBitmap(mImgReader.load("logo/logo_bg.jpg"));

        initDataBase();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
//                    Intent intent = new Intent(LogoActivity.this, ItemActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

	/*
     * @Override public boolean onTouchEvent(MotionEvent event) { // TODO
	 * Auto-generated method stub switch (event.getAction()) { case
	 * MotionEvent.ACTION_DOWN:
	 * 
	 * Intent intent = new Intent(LogoActivity.this, MainActivity.class);
	 * startActivity(intent); finish(); break;
	 * 
	 * default: break; } return super.onTouchEvent(event); }
	 */

    /**
     * 检查设置数据库
     */
    private void initDataBase() {
        // TODO Auto-generated method stub
        Table_BASICITEM basicItem = new Table_BASICITEM(getApplicationContext());
        if (basicItem.getCount() == 0) {
            basicItem.sample();
        }else{
//            basicItem.deleteTable();
//            basicItem.sample();
        }
        basicItem.close();
        basicItem = null;

        Table_BASICSYSTEMITEM basicItem1 = new Table_BASICSYSTEMITEM(getApplicationContext());
        if(basicItem1.getCount() == 0){
            basicItem1.sample();
            Log.d("Table_BASICSYSTEMITEM","success");
        }

        Table_MAPFORTUNE basicItem2 = new Table_MAPFORTUNE(getApplicationContext());
        if (basicItem2.getCount() == 0) {
            basicItem2.sample();
        }else{
//            basicItem2.deleteTable();
//            basicItem2.sample();
        }
        basicItem2.close();
        basicItem2 = null;

        Table_BASICMAP basicItem3 = new Table_BASICMAP(getApplicationContext());
        if (basicItem3.getCount() == 0) {
            basicItem3.sample();
        }
        basicItem3.close();
        basicItem3 = null;

    }

}
