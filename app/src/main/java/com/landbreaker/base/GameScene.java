package com.landbreaker.base;

import com.landbreaker.bean.Feed;
import com.landbreaker.config.Config;
import com.landbreaker.file.ImgReader;
import com.landbreaker.item.GameItem_Dig;
import com.landbreaker.ui.UI_ToolsBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import org.json.JSONException;

import java.util.List;

public class GameScene {

	public String Name;
	public Object Tag;
	protected Context mContext;
	protected _GameRequestData mInterface = null;
	protected float scaleSize;
	protected ImgReader mImgReader;
	protected boolean isInit = false;

	public GameScene(Context context) {
		mContext = context;
		scaleSize = Config.scaleSize(mContext);// 适应屏幕缩放
		mImgReader = new ImgReader(mContext);
	}

	public void draw(Canvas canvas) {
		if (!isInit) {
			canvas.drawColor(0xffffff);
			return;
		}
	}

	public void physics() {
		if (!isInit)
			return;
	}

	public boolean touchEvent(MotionEvent event) {
		return false;
	}

	protected void setSpirtePosition(BaseSprite bs, Point p) {
		bs.setPosition(p.x * scaleSize, p.y * scaleSize);
	}

	public void setCallBack(_GameRequestData _interface) {
		mInterface = _interface;
	}

	public interface _GameRequestData {

		void changeMap();

		void upLoadData(Feed[] feeds,UI_ToolsBar mUI_ToolsBar);
	}

	/**
	 * 切换地图
	 */
	protected void changeMap() {
		if (mInterface != null)
			mInterface.changeMap();
	}

	/**
	 * 同步数据
	 * 
	 * @param feeds
	 */
	protected void upLoadData(Feed[] feeds, UI_ToolsBar mUI_ToolsBar) {
		if (mInterface != null)
			mInterface.upLoadData(feeds,mUI_ToolsBar);
	}
}
