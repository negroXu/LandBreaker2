package com.landbreaker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.landbreaker.R;
import com.landbreaker.application.AppData;
import com.landbreaker.base.GameActivity;
import com.landbreaker.bean.JsonData;
import com.landbreaker.bean.UserData;
import com.landbreaker.config.Config;
import com.landbreaker.database.Table_BASICSYSTEMITEM;
import com.landbreaker.file.ToastUtils;
import com.landbreaker.internet.InternetApi;
import com.landbreaker.internet.JsonUtils;
import com.landbreaker.listener.GameButtonTouchListener;
import com.landbreaker.logic.GameUISetting;
import com.yg.AnsynHttpRequestThreadPool.Interface_MyThread;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends GameActivity implements Interface_MyThread {

    private View view = null;// 黑色幕布
    private ImageView mIv_bgk = null;// 登录背景框
    private ImageView mIv_mail = null;// 邮箱图标
    private ImageView mIv_pw = null;// 密码图标

    private EditText mEt_mail = null;// 账号输入框
    private EditText mEt_pw = null;// 密码输入框

    private ImageView mIv_bt1 = null;// 第一个按键/登录/注册
    private ImageView mIv_bt2 = null;// 第二个按键/注册
    private ImageView mIv_text = null;// 提示文本
    private ImageView mIv_twitter = null;// 推特登录按钮
    private ImageView mIv_facebook = null;// 脸书登录按钮

    private Bitmap bm_bgk = null;
    private Bitmap bm_mail = null;
    private Bitmap bm_pw = null;
    private Bitmap bm_input = null;// 输入栏
    private Bitmap[] bms_login = null;
    private Bitmap[] bms_register = null;
    private Bitmap bm_text1 = null;
    private Bitmap bm_text2 = null;
    private Bitmap[] bms_twitter = null;
    private Bitmap[] bms_facebook = null;
    private Bitmap[] bms_backspace = null;

    private final static int LOGIN_BUTTON = 1;
    private final static int REGISTER_BUTTON = 2;
    private final static int TWITTER_BUTTON = 3;
    private final static int FACEBOOK_BUTTON = 4;
    private final static int BACKSPACE_BUTTON = 5;
    private boolean isLoginPage = true;

    private ItemOnTouchListener _loginListener = null;
    private ItemOnTouchListener _registerListener = null;
    private ItemOnTouchListener _twitterListener = null;
    private ItemOnTouchListener _facebookListener = null;
    private ItemOnTouchListener _backSpaceListener = null;

    private static final int REQUEST_CODE_REGISTER_BY_ACCOUNT = 1;
    private static final int REQUEST_CODE_LOGIN_BY_ACCOUNT = 2;

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        hasMenu = false;
        super.init();
        addViewInCenterLayerFromXML(R.layout.activity_login, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mIv_bgk = (ImageView) findViewById(R.id.iv_login_bgk);
        mIv_mail = (ImageView) findViewById(R.id.iv_login_mail);
        mIv_pw = (ImageView) findViewById(R.id.iv_login_pw);
        mIv_bt1 = (ImageView) findViewById(R.id.iv_login_bt1);
        mIv_bt2 = (ImageView) findViewById(R.id.iv_login_bt2);
        mIv_text = (ImageView) findViewById(R.id.iv_login_text);
        mIv_twitter = (ImageView) findViewById(R.id.iv_login_twitter);
        mIv_facebook = (ImageView) findViewById(R.id.iv_login_facebook);

        view = findViewById(R.id.view_login);

        mEt_mail = (EditText) findViewById(R.id.et_login_mail);
        mEt_pw = (EditText) findViewById(R.id.et_login_pw);
    }

    @Override
    protected void loadImage() {
        // TODO Auto-generated method stub
        super.loadImage();
        bm_bgk = mImgReader.load("login/denglubeijingkuang.png");
        bm_mail = mImgReader.load("login/mail.png");
        bm_pw = mImgReader.load("login/pw.png");
        bm_input = mImgReader.load("login/shurukuang.png");
        bms_login = new Bitmap[]{mImgReader.load("login/denglu_up.png"), mImgReader.load("login/denglu_down.png")};
        bms_register = new Bitmap[]{mImgReader.load("login/zhuce_up.png"), mImgReader.load("login/zhuce_down.png")};
        bm_text1 = mImgReader.load("login/kuaisudengru.png");
        bm_text2 = mImgReader.load("login/kuaisuzhuce.png");
        bms_twitter = new Bitmap[]{mImgReader.load("login/twi_up.png"), mImgReader.load("login/twi_down.png")};
        bms_facebook = new Bitmap[]{mImgReader.load("login/fb_up.png"), mImgReader.load("login/fb_down.png")};

        bms_backspace = new Bitmap[]{mImgReader.load("login/fanhui_up.png"), mImgReader.load("login/fanhui_down.png")};
    }

    @Override
    protected void setItem() {
        // TODO Auto-generated method stub
        // super.setItem();
        mGbg_bg.setDrawOption(bm_bg, Config.getWindowsRect(this), -1, -1);

        _loginListener = new ItemOnTouchListener(bms_login);
        _registerListener = new ItemOnTouchListener(bms_register);
        _twitterListener = new ItemOnTouchListener(bms_twitter);
        _facebookListener = new ItemOnTouchListener(bms_facebook);
        _backSpaceListener = new ItemOnTouchListener(bms_backspace);

        setImageView(mIv_bgk, Config.LogUILayout.login_bg, bm_bgk, null, null);
        setImageView(mIv_mail, Config.LogUILayout.mail_ic, bm_mail, null, null);
        setImageView(mIv_pw, Config.LogUILayout.pw_ic, bm_pw, null, null);
        setImageView(mIv_bt1, Config.LogUILayout.button_1, bms_login[0], LOGIN_BUTTON, _loginListener);
        setImageView(mIv_bt2, Config.LogUILayout.button_2, bms_register[0], REGISTER_BUTTON, _registerListener);
        setImageView(mIv_text, Config.LogUILayout.text, bm_text1, null, null);
        setImageView(mIv_twitter, Config.LogUILayout.twitter, bms_twitter[0], TWITTER_BUTTON, _twitterListener);
        setImageView(mIv_facebook, Config.LogUILayout.facebook, bms_facebook[0], FACEBOOK_BUTTON, _facebookListener);
        setImageView(mIv_menu, Config.MenuUILayout.back, bms_backspace[0], BACKSPACE_BUTTON, _backSpaceListener);

        mIv_menu.setVisibility(View.INVISIBLE);

        setLayout(mEt_mail, Config.LogUILayout.mail_text, bm_input);
        setLayout(mEt_pw, Config.LogUILayout.pw_text, bm_input);
        GameUISetting.setViewBackGround(mEt_mail, bm_input);
        GameUISetting.setViewBackGround(mEt_pw, bm_input);
    }

    // 设置控件layout
    private void setLayout(View view, Point center, Bitmap bm) {
        GameUISetting.setViewCenterPosition(view, center, bm.getWidth(), bm.getHeight());
    }

    @Override
    protected void _touchEventAction(int key) {
        // TODO Auto-generated method stub
        super._touchEventAction(key);
        switch (key) {
            case LOGIN_BUTTON:
                login();
                break;
            case REGISTER_BUTTON:
                if (isLoginPage)
                    registPage();
                else {
                    regist();
                }
                break;
            case TWITTER_BUTTON:
                loginPage_twi();
                break;
            case FACEBOOK_BUTTON:
                loginPage_fb();
                break;
            case BACKSPACE_BUTTON:
                if (!isLoginPage)
                    loginPage();
                break;
            default:
                break;
        }
    }

    // 登录界面
    private void loginPage() {
        isLoginPage = true;

        mIv_menu.setVisibility(View.INVISIBLE);
        mEt_mail.setHint(R.string.login_hint_mail);
        mEt_mail.setText(null);
        mEt_pw.setText(null);
        setButtonState(mIv_mail, View.VISIBLE, bm_mail, null, null);
        setButtonState(mIv_bt1, View.VISIBLE, bms_login[0], LOGIN_BUTTON, _loginListener);
        setButtonState(mIv_bt2, View.VISIBLE, bms_register[0], REGISTER_BUTTON, _registerListener);
        setButtonState(mIv_text, View.VISIBLE, bm_text1, null, null);
        setButtonState(mIv_twitter, View.VISIBLE, bms_twitter[0], TWITTER_BUTTON, _twitterListener);
        setButtonState(mIv_facebook, View.VISIBLE, bms_facebook[0], FACEBOOK_BUTTON, _facebookListener);
        view.setBackgroundResource(R.color.bg_transparent_default);
    }

    // 注册页面
    private void registPage() {
        // TODO Auto-generated method stub
        isLoginPage = false;

        mIv_menu.setVisibility(View.VISIBLE);
        mEt_mail.setHint(R.string.login_hint_mail);
        mEt_mail.setText(null);
        mEt_pw.setText(null);
        setButtonState(mIv_mail, View.VISIBLE, bm_mail, null, null);
        setButtonState(mIv_bt1, View.VISIBLE, bms_register[0], REGISTER_BUTTON, _registerListener);
        setButtonState(mIv_bt2, View.INVISIBLE, null, null, null);
        setButtonState(mIv_text, View.VISIBLE, bm_text2, null, null);
        setButtonState(mIv_twitter, View.VISIBLE, bms_twitter[0], TWITTER_BUTTON, _twitterListener);
        setButtonState(mIv_facebook, View.VISIBLE, bms_facebook[0], FACEBOOK_BUTTON, _facebookListener);
        view.setBackgroundResource(R.color.bg_transparent_low);
    }

    // 推特登录界面
    private void loginPage_twi() {
        isLoginPage = false;

        mIv_menu.setVisibility(View.VISIBLE);
        mEt_mail.setHint(R.string.login_hint_twitter);
        mEt_mail.setText(null);
        mEt_pw.setText(null);
        setButtonState(mIv_mail, View.VISIBLE, bms_twitter[0], null, null);
        setButtonState(mIv_bt1, View.VISIBLE, bms_login[0], LOGIN_BUTTON, _loginListener);
        setButtonState(mIv_bt2, View.INVISIBLE, null, null, null);
        setButtonState(mIv_text, View.INVISIBLE, null, null, null);
        setButtonState(mIv_twitter, View.INVISIBLE, null, null, null);
        setButtonState(mIv_facebook, View.INVISIBLE, null, null, null);
        view.setBackgroundResource(R.color.bg_transparent_low);
    }

    // 推特登录界面
    private void loginPage_fb() {
        isLoginPage = false;

        mIv_menu.setVisibility(View.VISIBLE);
        mEt_mail.setHint(R.string.login_hint_facebook);
        mEt_mail.setText(null);
        mEt_pw.setText(null);
        setButtonState(mIv_mail, View.VISIBLE, bms_facebook[0], null, null);
        setButtonState(mIv_bt1, View.VISIBLE, bms_login[0], LOGIN_BUTTON, _loginListener);
        setButtonState(mIv_bt2, View.INVISIBLE, null, null, null);
        setButtonState(mIv_text, View.INVISIBLE, null, null, null);
        setButtonState(mIv_twitter, View.INVISIBLE, null, null, null);
        setButtonState(mIv_facebook, View.INVISIBLE, null, null, null);
        view.setBackgroundResource(R.color.bg_transparent_low);
    }

    /**
     * 设置按键的状态
     */
    private void setButtonState(ImageView view, int visibility, Bitmap show, Object tag,
                                GameButtonTouchListener listener) {
        view.setVisibility(visibility);
        if (show != null)
            view.setImageBitmap(show);
        if (tag != null)
            view.setTag(tag);
        if (listener != null)
            view.setOnTouchListener(listener);
    }

    // 登录
    private void login() {
        // TODO Auto-generated method stub
        String account = mEt_mail.getText().toString();
        String password = mEt_pw.getText().toString();
        account = "6890847@qq.com";
        password = "123456789";
        if (checkInput(account, password)) {
            InternetApi.loginByAccount(LoginActivity.this, account, password, LoginActivity.this, REQUEST_CODE_LOGIN_BY_ACCOUNT);
        }
    }

    // 注册
    private void regist() {
        // TODO Auto-generated method stub
        /*Intent intent = new Intent(LoginActivity.this, MainGameActivity.class);
        startActivity(intent);
        finish();*/
        String account = mEt_mail.getText().toString();
        String password = mEt_pw.getText().toString();
        if (checkInput(account, password)) {
            InternetApi.registerByAccount(LoginActivity.this, account, password, LoginActivity.this, REQUEST_CODE_REGISTER_BY_ACCOUNT);
        }
    }

    // 隐藏输入键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                mEt_mail.clearFocus();
                mEt_pw.clearFocus();
                return true;
            }
        }
        return false;
    }

    //================================Method=====================================

    @Override
    public void Callback_MyThread(String result, int flag) {
        // TODO Auto-generated method stub
        Message msg = new Message();
        msg.what = flag;
        msg.obj = result;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_CODE_REGISTER_BY_ACCOUNT:
                    resultRegisterByAccount((String) msg.obj);
                    break;
                case REQUEST_CODE_LOGIN_BY_ACCOUNT:
                    try {
                        resultLoginByAccount((String) msg.obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    /**
     * 注册请求结果
     */
    private void resultRegisterByAccount(String json) {
        JsonData data = JsonUtils.getData(json, JsonData.class);
        if (data != null && data.isSuccess()) {
            ToastUtils.showMessage(LoginActivity.this,R.string.login_register_success);
            loginPage();
        }else{
            ToastUtils.showMessage(LoginActivity.this,data.getmsg());
        }
    }

    /**
     * TODO 登录结果
     */
    private void resultLoginByAccount(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        if (jsonObject != null && jsonObject.getBoolean("success")) {
            UserData userData = new Gson().fromJson(jsonObject.getString("data"),new TypeToken<UserData>(){}.getType());
            ((AppData)getApplication()).userData = userData;
            ((AppData)getApplication()).equipList = userData.playerList[0].getequipList();

            Intent intent = new Intent(LoginActivity.this, MainGameActivity.class);
            startActivity(intent);
            finish();
        }
        ToastUtils.showMessage(LoginActivity.this,jsonObject.getString("msg"));
    }

    /**
     * 检查输入的账号密码
     */
    private boolean checkInput(String account, String password) {
        if (account.length() < 6) {
            ToastUtils.showMessage(LoginActivity.this, R.string.login_account_less);
            return false;
        }
        if (password.length() < 6) {
            ToastUtils.showMessage(LoginActivity.this,R.string.login_password_less);
            return false;
        }
        return true;
    }

}




