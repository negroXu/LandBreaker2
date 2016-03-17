package com.landbreaker.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * 本地图片读取器
 */
public class ImgReader {

	private Context mContext;

	public ImgReader(Context context) {
		mContext = context;
	}

	/**
	 * 读取根据scaleSize缩放的图片
	 * 
	 * @param fileName
	 * @return
	 */
	public  Bitmap load(String fileName) {
		return FileInput.loadBitmap(mContext, fileName);
	}

	public Bitmap loadSmaller(String fileName, float scale) {
		return FileInput.loadBitmapSmaller(mContext, fileName, scale);
	}

	public Bitmap[] loadGameBg(int id) {
		Bitmap[] bg = null;
		switch (id) {
		case 1:
			bg = new Bitmap[] { load("bg/jiedao.png"), load("bg/jiedao_f.png") };
			break;
		case 2:
			bg = new Bitmap[] { load("bg/gongyuan.png"), load("bg/gongyuan_f.png") };
			break;
		case 3:
			bg = new Bitmap[] { load("bg/houjie.png"), load("bg/houjie_f.png") };
			break;
		case 4:
			bg = new Bitmap[] { load("bg/haibian.png"), load("bg/haibian_f.png") };
			break;
		default:
			bg = new Bitmap[] { load("bg/jiedao.png"), load("bg/jiedao_f.png") };
			break;
		}
		return bg;
	}

	/**
	 * 读取体力图片
	 * 
	 * @return
	 */
	public Bitmap[] loadStamina() {
		// TODO Auto-generated method stub
		Bitmap[] list = new Bitmap[10];
		for (int i = 1; i < 10; i++)
			list[i - 1] = load("gameui/zhanbu0" + i + ".png");
		list[9] = load("gameui/zhanbu10.png");
		return list;
	}

	/**
	 * 读取挖掘效果
	 * 
	 * @return
	 */
	public Bitmap[] loadDigEffect() {
		Bitmap[] list = new Bitmap[3];

		list[0] = load("effect/dimianxiaoguolv1.png");
		list[1] = load("effect/dimianxiaoguolv2.png");
		list[2] = load("effect/dimianxiaoguolv3.png");

		return list;
	}

	public Bitmap[] loadDigEffect_front() {
		Bitmap[] list = new Bitmap[3];

		list[0] = load("effect/dimianxiaoguolv1_front.png");
		list[1] = load("effect/dimianxiaoguolv2_front.png");
		list[2] = load("effect/dimianxiaoguolv3_front.png");

		return list;
	}

	/**
	 * 读取工具图标
	 * 
	 * @return
	 */
	public Bitmap[] loadDigit() {
		Bitmap[] list = new Bitmap[6];

		for (int i = 0; i < list.length; i++) {
			list[i] = load("gameui/0" + (i + 1) + ".png");
		}

		return list;
	}

	/**
	 * 读取菜单组件未按下时的图片,数组最后一位为返回按键图片
	 * 
	 * @return
	 */
	public Bitmap[] loadMenuItemWithBack_up() {
		Bitmap[] list = new Bitmap[9];

		list[0] = load("menu/paihang_up.png");
		list[1] = load("menu/daoju_up.png");
		list[2] = load("menu/chenghao_up.png");
		list[3] = load("menu/shop_up.png");
		list[4] = load("menu/quwadi_up.png");
		list[5] = load("menu/renwu_up.png");
		list[6] = load("menu/new_up.png");
		list[7] = load("menu/sheding_up.png");
		list[8] = load("menu/back_up.png");

		return list;
	}

	/**
	 * 读取菜单组件按下时的图片,数组最后一位为返回按键图片
	 * 
	 * @return
	 */
	public Bitmap[] loadMenuItemWithBack_down() {
		Bitmap[] list = new Bitmap[9];

		list[0] = load("menu/paihang_down.png");
		list[1] = load("menu/daoju_down.png");
		list[2] = load("menu/chenghao_down.png");
		list[3] = load("menu/shop_down.png");
		list[4] = load("menu/quwadi_down.png");
		list[5] = load("menu/renwu_down.png");
		list[6] = load("menu/new_down.png");
		list[7] = load("menu/sheding_down.png");
		list[8] = load("menu/back_down.png");

		return list;
	}

	/**
	 * 读取菜单组件未按下时的图片
	 * 
	 * @return
	 */
	public Bitmap[] loadMenuItem_up() {
		Bitmap[] list = new Bitmap[8];

		list[0] = load("menu/paihang_up.png");
		list[1] = load("menu/daoju_up.png");
		list[2] = load("menu/chenghao_up.png");
		list[3] = load("menu/shop_up.png");
		list[4] = load("menu/quwadi_up.png");
		list[5] = load("menu/renwu_up.png");
		list[6] = load("menu/new_up.png");
		list[7] = load("menu/sheding_up.png");

		return list;
	}

	/**
	 * 读取菜单组件按下时的图片
	 * 
	 * @return
	 */
	public Bitmap[] loadMenuItem_down() {
		Bitmap[] list = new Bitmap[8];

		list[0] = load("menu/paihang_down.png");
		list[1] = load("menu/daoju_down.png");
		list[2] = load("menu/chenghao_down.png");
		list[3] = load("menu/shop_down.png");
		list[4] = load("menu/quwadi_down.png");
		list[5] = load("menu/renwu_down.png");
		list[6] = load("menu/new_down.png");
		list[7] = load("menu/sheding_down.png");

		return list;
	}

	public Bitmap[] loadItemRank() {
		Bitmap[] list = new Bitmap[5];
		list[0] = load("item/starlv_00.png");
		list[1] = load("item/starlv_01.png");
		list[2] = load("item/starlv_02.png");
		list[3] = load("item/starlv_03.png");
		list[4] = load("item/starlv_rainbow.png");
		return list;
	}

	public Bitmap[] loadAtlasRank() {
		Bitmap[] list = new Bitmap[5];
		list[0] = load("item/tujianfenlei_00.png");
		list[1] = load("item/tujianfenlei_01.png");
		list[2] = load("item/tujianfenlei_02.png");
		list[3] = load("item/tujianfenlei_03.png");
		list[4] = load("item/tujianfenlei_rb.png");
		return list;
	}

	public Bitmap[] loadAnimate(String path, int count) {
		Bitmap[] list = new Bitmap[count];
		String num = "000";
		for (int i = 0; i < count; i++) {
			if (i + 1 > 9)
				num = "00";
			list[i] = load("animation/" + path + "_" + num + (i + 1) + ".png");
		}
		return list;
	}

	public static Bitmap load(String fileName, Context context) {
		return FileInput.loadBitmap(context, fileName);
	}

	public static Bitmap loadSmaller(String fileName, Context context, float scale) {
		return FileInput.loadBitmapSmaller(context, fileName, scale);
	}

	public Bitmap loadNumBitmap(int num){
		if(num < 0 ){
			return null;
		}

		if(num > 99){
			num = 99;
		}

		int decade = num / 10;
		int unit = num % 10;

		Bitmap decadeBitmap = load("gameui/0" + decade + ".png");
		Bitmap unitBitmap = load("gameui/0" + unit + ".png");

		Bitmap bitmap = Bitmap.createBitmap(unitBitmap.getWidth() * 2,unitBitmap.getHeight(), Bitmap.Config.ARGB_8888);;

		Canvas canvas = new Canvas(bitmap);
		if(decade != 0){
			canvas.drawBitmap(decadeBitmap, new Matrix(), null);
		}

		canvas.drawBitmap(unitBitmap, unitBitmap.getWidth() - 15, 0, null);


		return bitmap;
	}

}
