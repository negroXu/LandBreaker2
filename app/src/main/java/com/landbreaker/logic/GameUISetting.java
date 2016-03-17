package com.landbreaker.logic;

import com.landbreaker.config.Config;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.EditText;

/**
 * 各类新旧api替换的设置
 * 
 * @author 开裕
 * 
 */
@SuppressLint("NewApi")
public class GameUISetting {

	public static void setViewCenterPosition(View view, Point center, float width, float height) {
		float scaleSize = Config.Size_Scale;
		float _width = width * scaleSize;
		float _height = height * scaleSize;
		view.setX(center.x * scaleSize - _width / 2);
		view.setY(center.y * scaleSize - _height / 2);
		if (view instanceof EditText) {
			((EditText) view).setMinWidth((int) _width);
			((EditText) view).setMaxWidth((int) _width);
			((EditText) view).setMinHeight((int) _height);
			((EditText) view).setMaxHeight((int) _height);
		}
	}

	public static void setViewX(View view, float x) {
		float scaleSize = Config.Size_Scale;
		view.setX(x * scaleSize);
	}

	public static void setViewY(View view, float y) {
		float scaleSize = Config.Size_Scale;
		view.setY(y * scaleSize);
	}

	public static void setViewBackGround(View view, Bitmap bg) {
		view.setBackground(new BitmapDrawable(null, bg));
	}

	public static void setViewLeftTop(View view, float x, float y) {
		float scaleSize = Config.Size_Scale;
		view.setX(x * scaleSize);
		view.setY(y * scaleSize);
	}

	public static void setViewLeftTop(View view, Point leftTop) {
		float scaleSize = Config.Size_Scale;
		view.setX(leftTop.x * scaleSize);
		view.setY(leftTop.y * scaleSize);
	}

	public static void setViewWidthHeight(View view, float width, float height) {
		view.getLayoutParams().width = (int) (width * Config.Size_Scale);
		view.getLayoutParams().height = (int) (height * Config.Size_Scale);

	}

	public static void setViewWidthHeight(View view, Bitmap bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		view.getLayoutParams().width = (int) (width * Config.Size_Scale);
		view.getLayoutParams().height = (int) (height * Config.Size_Scale);

	}
}
