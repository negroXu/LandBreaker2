package com.landbreaker.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.WindowManager;

import com.landbreaker.file.ImgReader;

import net.tsz.afinal.FinalBitmap;

import java.lang.reflect.Array;

/**
 * 游戏相关参数设置
 * 
 * @author kaiyu
 * 
 */
public class Config {

	public final static float Size_Width = 640;
	public final static float Size_Height = 1136;
	public static float Size_Scale = 1;

	/**
	 * 获得游戏界面根据屏幕大小的缩放系数
	 * 
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	public final static float scaleSize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Point size = new Point();
		wm.getDefaultDisplay().getSize(size);

		float sw = size.x / Size_Width;
		float sh = size.y / Size_Height;

		if (sw > sh)
			return sh;
		else
			return sw;
	}

	@SuppressLint("NewApi")
	public static Rect getWindowsRect(Context context) {
		Rect rect = new Rect();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getRectSize(rect);
		return rect;
	}

	public final static float default_PFS = 60;

	public final static long default_physicsTime = 20;

	/**
	 * 宝箱参数
	 */
	public final static int[] box_time = new int[]{0, 1 * 3600000, 2 * 3600000, 3 * 3600000};// 开启宝箱的时间
	public final static int[] box_price = new int[]{0, 18, 28, 48}; // 开启宝箱的费用(钻石)

	/**
	 * 游戏界面的组件位置
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class GameUILayout {

		public static Point torii = new Point(532, 990);// 鸟居

		public static Point luck = new Point(534, 1022);// 幸运签

		public static Point depth = new Point(606, 456);// 深度条

		public static int depth_height = 450;

		public static Point depth_Cursor = new Point(546, 236);// 深度指示器

		public static Point depth_Name = new Point(533, 236);// 深度名称

		public static Point tool_main = new Point(77, 996);// 工具_主

		public static Point[] tool_others = new Point[] {// 工具栏
		new Point(48, 867), new Point(135, 871), new Point(207, 922), new Point(227, 1007), new Point(189, 1086) };

		public static Point menu = new Point(77, 67);// 菜单按钮

		public static Point digEffect_jiedao = new Point(341, 758);// 挖掘效果_街道

		public static Point digItem_jiedao = new Point(395, 614);// 挖掘工具

		public static Point digEffect_center = new Point(347, 782);

		public static Point item_found_effect = new Point(328, 574);// 发现道具感叹号

		public static Point item_show = new Point(320, 520);

		public static int dig_touch_left = 35;
		public static int dig_touch_top = 316;
		public static int dig_touch_right = 606;
		public static int dig_touch_bottom = 825;

	}

	/**
	 * 系统菜单的组件位置
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class MenuUILayout {
		public static Point menu_bg = new Point(316, 488);// 菜单背景

		public static Point back = new Point(77, 67);// 返回按钮

		public static Point item_1 = new Point(135, 300);// 排行

		public static Point item_2 = new Point(316, 300);// 道具

		public static Point item_3 = new Point(494, 300);// 称号

		public static Point item_4 = new Point(135, 490);// 商店

		public static Point item_5 = new Point(316, 490);// 任务

		public static Point item_6 = new Point(494, 490);// new

		public static Point item_7 = new Point(135, 680);// 设置

		public static Point item_8 = new Point(316, 680);// 其他

		public static Point[] menu_item = new Point[] { item_1, item_2, item_3, item_4, item_5, item_6, item_7, item_8 };

	}

	/**
	 * logo/登录画面位置
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class LogUILayout {
		public static Point logo = new Point(320, 502);//

		public static Point login_bg = new Point(319, 512);// 背景框

		public static Point mail_ic = new Point(132, 289);// 邮箱图标

		public static Point pw_ic = new Point(132, 393);// 密码图标

		public static Point mail_text = new Point(368, 290);// 邮箱输入框

		public static Point pw_text = new Point(368, 394);// 密码输入框

		public static Point button_1 = new Point(319, 522);// 登录按钮/第一个

		public static Point button_2 = new Point(319, 629);// 注册按钮/第二个

		public static Point text = new Point(234, 745);// 快速登入/注册

		public static Point twitter = new Point(366, 746);// twitter图标

		public static Point facebook = new Point(455, 746);// facebook图标
	}

	/**
	 * 排行榜位置参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class RankingUILayout {
		public static Point type = new Point(531, 69);//

		public static Point myIco_size = new Point(250, 250);

		public static int myIco_top = 218 - PagerUILayout.page_top;

		public static int myName_top = myIco_top + myIco_size.y + 40;

		public static Point collection = new Point(80, 635 - PagerUILayout.page_top);

		public static Point list_ico_size = new Point(98, 98);

		public static int list_ico_left = 97;

		public static int list_text_left = 245;

	}

	/**
	 * 成就界面位置参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class AchievementUILayout {
		public static int icon_bg_left = 67;

		public static int icon_left = 72;

		public static Point icon_complete = new Point(114, 85);

		public static Point text_topLeft = new Point(230, 43);

		public static int item_dividerHeight = 40;

		public static int text_width = 200;

	}

	/**
	 * 任务界面参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class MissionUILayout {

		public static int list_img_width = 576;

		public static int list_img_height = 346;

		public static int list_text_marginTop = 45;

		public static int list_text_left = 52;
	}

	/**
	 * new界面参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class NewUILayout {
		public static int item_interval = 35;

		public static int img_defaultHeight = 505;

		public static int title_margin = 22;
	}

	/**
	 * 设置界面UI参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class SettingUILayout {

		public static Point bgm = new Point(214, 371);

		public static Point se = new Point(216, 514);

		public static Point bgm_switch = new Point(446, 371);

		public static Point se_switch = new Point(446, 514);

		public static Point language = new Point(320, 742);

	}

	/**
	 * 商店界面UI参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class ShopUILayout {

		public static Point total_money_bg = new Point(169, 27);

		public static Point total_money_ic = new Point(348 - 169, 63 - 27);

		public static Point total_diamond_bg = new Point(396, 27);

		public static Point total_diamond_ic = new Point(572 - 396, 64 - 27);

		public static Point total_diamond_plus = new Point(430 - 396, 64 - 27);

		public static int total_money_length = 134;

		public static int total_diamond_length = 86;

		public static int total_money_text_marginLeft = 11;

		public static int total_diamond_text_marginLeft = 60;

		public static Point info_bg = new Point(314, 981);

		public static int info_length = 483;

		public static Point info_name = new Point(80, 927);

		public static Point info_text = new Point(80, 970);

		public static Point item_leftTop = new Point(32, 129);

		public static Point item_money_bg_leftTop = new Point(2, 411 - 129);

		public static int item_offset_horizontal = 470 - 167;

		public static int item_offset_vertical = 815 - 445;

		public static Point item_value_ic = new Point(233, 33);

		public static Point item_value_ic_diamond = new Point(100,33);

		public static int item_value_length = 153;

		public static int item_value_marginLeft = 40;

		public static Point[] list_itemLeftTop = new Point[] { item_leftTop,
				new Point(item_leftTop.x + item_offset_horizontal, item_leftTop.y),
				new Point(item_leftTop.x, item_leftTop.y + item_offset_vertical),
				new Point(item_leftTop.x + item_offset_horizontal, item_leftTop.y + item_offset_vertical) };

		public static Point item_ic = new Point(167 - 32, 269 - 129);

		public static Point[] list_item_ic = new Point[] { item_ic,
				new Point(item_ic.x + item_offset_horizontal, item_ic.y),
				new Point(item_ic.x, item_ic.y + item_offset_vertical),
				new Point(item_ic.x + item_offset_horizontal, item_ic.y + item_offset_vertical) };

		public static float item_scale_i = (float) 0.48;
		public static float item_scale_jz = (float) 0.66;
	}

	/**
	 * 道具界面UI参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class ItemUILayout {
		public static Point item_leftTop = new Point(42, 0);

		public static int item_marginLeft = 140;

		public static int item_marginTop = 170;

		public static Point item_atlas = new Point(569, 1051);

		public static int item_max_width = 134;

		public static int item_num_width = 100;

		public static float item_scale_normal = (float) 0.25;
		public static float item_scale_small = (float) 0.20;

	}

	public static class ItemAtlasUILayout {
		public static Point rank = new Point(512, 66);
	}

	/**
	 * 道具详细页UI参数
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class ItemDetailUILayout {

		public static Point item_ic = new Point(319, 399);

		public static Point item_rank = new Point(552, 652);

		public static Point item_value_leftTop = new Point(395, 669);

		public static Point item_value_leftTop_diamond = new Point(395, 585);

		public static Point item_value_ic = new Point(573 - 395, 715 - 679);

		public static Point item_use = new Point(255, 794);

		public static Point item_exchange = new Point(505, 794);
	}

	/**
	 * 
	 * @author kaiyu
	 * 
	 */
	public static class PagerUILayout {
		public static Point left = new Point(24, 567);

		public static Point right = new Point(615, 567);

		public static int page_top = 145;

		public static int page_bottom = 1136;

		public static int page_top_item = 167;

		public static int page_bottom_item = 815;
	}

	// =============================================

	public static class Value {
		public static int curtain_Alpha_80 = (int) (255 * 0.8);// 幕布的透明度
		public static int curtain_Alpha_50 = (int) (255 * 0.5);// 幕布的透明度

		public static int animate_interval_default = 1000 / 12;

		public static boolean BGM_ON = true;

		public static boolean SE_ON = true;

		public static int bag_max = 6 * 4;

		public static int atlas_max = 6 * 4;

		public static ColorMatrixColorFilter ColoFilter_noItem = new ColorMatrixColorFilter(new float[] { 3, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 });

		public static ColorMatrixColorFilter ColoFilter_lock = new ColorMatrixColorFilter(new float[] { 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 });
		public static ColorMatrixColorFilter ColoFilter_normal = new ColorMatrixColorFilter(new float[] { 1, 0, 0, 0,
				0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 });
	}

	public static int getAndroidSDKVersion() {
		int version = -1;
		try {
			version = android.os.Build.VERSION.SDK_INT;
		} catch (NumberFormatException e) {
			Log.e("err", e.toString());
		}
		return version;
	}

	public static FinalBitmap finalBitmap(Context context){
		FinalBitmap finalBitmap = FinalBitmap.create(context);
		ImgReader mImgReader = new ImgReader(context);

		// 加载中
		finalBitmap.configLoadingImage(mImgReader.load("item/itembig_jiqiren.png"));
		// 加载失败
		finalBitmap.configLoadfailImage(mImgReader.load("item/itembig_jiqiren.png"));

		return finalBitmap;
	}
}
