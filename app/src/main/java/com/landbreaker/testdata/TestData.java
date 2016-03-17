package com.landbreaker.testdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.landbreaker.R;
import com.landbreaker.bean.MapData;
import com.landbreaker.bean.GameMap;
import com.landbreaker.config.Config;
import com.landbreaker.config.GameStage;
import com.landbreaker.file.ImgReader;
import com.landbreaker.item.GameItem_Dig;

public class TestData {

	public static List<RankData> getRankData_Area(Context context) {
		List<RankData> list = new ArrayList<RankData>();
		Bitmap[] bms = new Bitmap[] {
				BitmapFactory.decodeResource(context.getResources(), R.drawable.paihangtouxiang01),
				BitmapFactory.decodeResource(context.getResources(), R.drawable.paihangtouxiang02),
				BitmapFactory.decodeResource(context.getResources(), R.drawable.paihangtouxiang03) };
		int a = 999999;
		int b = 1;
		Random r = new Random();
		for (int i = 0; i < 20; i++) {
			RankData data = new RankData();
			data.icon = bms[i % 3];
			data.Rank = "No. " + (b + i);
			data.Point = "" + (a - i * 13 * r.nextInt(20));
			list.add(data);
		}
		return list;
	}

	public static List<AchievementData> getAchievementData(Context context) {
		List<AchievementData> list = new ArrayList<AchievementData>();

		Bitmap[] bms = new Bitmap[] { ImgReader.load("fames/128jiqiren.png", context),
				ImgReader.load("fames/128biandang.png", context), ImgReader.load("fames/128guandongzhu.png", context) };
		for (int i = 0; i < 9; i++) {
			AchievementData data = new AchievementData();
			data.icon = bms[i % 3];
			data.isComplete = (i % 2 == 0) ? true : false;
			data.name = "哈哈哈哈哈" + i;
			data.requirement = "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
			list.add(data);
		}

		return list;
	}

	public static List<MissionData> getMissionData(Context context) {
		List<MissionData> list = new ArrayList<MissionData>();
		MissionData data;

		data = new MissionData();
		data.mission_pic = ImgReader.load("set/yuangoulogo.png", context);
		data.mission_item = "道具x5";
		list.add(data);

		data = new MissionData();
		data.mission_pic = ImgReader.load("set/yuangoulogo.png", context);
		data.mission_item = "拉粑粑能量x5";
		list.add(data);

		return list;
	}

	public static List<AnnouncementData> getAnnouncementDataData(Context context) {
		List<AnnouncementData> list = new ArrayList<AnnouncementData>();
		AnnouncementData data;

		data = new AnnouncementData();
		data.img = ImgReader.load("set/yuangoulogo.png", context);
		data.title = "标题要大！要长---------";
		list.add(data);

		data = new AnnouncementData();
		data.img = ImgReader.load("set/yuangoulogo.png", context);
		data.title = "来来来";
		list.add(data);

		return list;
	}

	public static List<ShopItemData> getShopItemData(Context context) {
		List<ShopItemData> list = new ArrayList<ShopItemData>();
		float scaleSize = Config.scaleSize(context);
		ShopItemData data;
		data = new ShopItemData();
		initShopItemData(data, context, scaleSize, Config.ShopUILayout.item_scale_i, ShopItemData.NORMAL, 10,
				"shop/itembig_jiaochan.png", "感觉一挖就会断", "胶铲");
		list.add(data);

		data = new ShopItemData();
		initShopItemData(data, context, scaleSize, Config.ShopUILayout.item_scale_i, ShopItemData.RARE, 10,
				"shop/itembig_zsbx.png", "......", "彩虹石宝箱");
		list.add(data);

		data = new ShopItemData();
		initShopItemData(data, context, scaleSize, Config.ShopUILayout.item_scale_jz, ShopItemData.KHORIUM, 10,
				"shop/itembig_zuanshi5.png", "......", "钻石*5");
		list.add(data);

		data = new ShopItemData();
		initShopItemData(data, context, scaleSize, Config.ShopUILayout.item_scale_jz, ShopItemData.KHORIUM, 25,
				"shop/itembig_zuanshi15.png", "......", "钻石*15");
		list.add(data);

		data = new ShopItemData();
		initShopItemData(data, context, scaleSize, Config.ShopUILayout.item_scale_jz, ShopItemData.KHORIUM, 45,
				"shop/itembig_zuanshi50.png", "......", "钻石*50");
		list.add(data);

		data = new ShopItemData();
		initShopItemData(data, context, scaleSize, Config.ShopUILayout.item_scale_jz, ShopItemData.KHORIUM, 180,
				"shop/itembig_zuanshi100.png", "......", "钻石*100");
		list.add(data);

		return list;
	}

	private static void initShopItemData(ShopItemData data, Context context, float scaleSize, float zoomSize, int type,
			int value, String ic_path, String info, String name) {
		data.item_ic_path = ic_path;
		data.item_ic = ImgReader.loadSmaller(ic_path, context, zoomSize);
		data.item_info = info;
		data.item_name = name;
		data.item_value = value;
		data.type = type;
	}

	public static List<BagItemData> getBagItemData(Context context) {
		List<BagItemData> list = new ArrayList<BagItemData>();
		float scaleSize = Config.scaleSize(context);
		BagItemData data;

		data = new BagItemData();
		initBagItemData(data, context, scaleSize, Config.ItemUILayout.item_scale_normal, 3, 200,
				"item/itembig_jiqiren.png", "一個奇怪的機器人，這根天線難道可以控制它？", "天線機器人");
		list.add(data);

		data = new BagItemData();
		initBagItemData(data, context, scaleSize, Config.ItemUILayout.item_scale_small, 0, 80,
				"item/itembig_biandang.png", "還剩下很多炸雞！Luck~可是沒有筷子", "便當盒");
		list.add(data);

		data = new BagItemData();
		initBagItemData(data, context, scaleSize, Config.ItemUILayout.item_scale_normal, 4, 200,
				"item/itembig_xueshen.png", "我是學霸，誰與爭鋒", "住著考試之神的護身符");
		list.add(data);

		return list;
	}

	private static void initBagItemData(BagItemData data, Context context, float scaleSize, float zoomSize, int rank,
			int value, String ic_path, String info, String name) {
		data.item_ic_path = ic_path;
		data.item_ic = ImgReader.loadSmaller(ic_path, context, scaleSize * zoomSize);
		data.item_info = info;
		data.item_name = name;
		data.item_value = value;
		data.item_rank = rank;
	}

	public static List<ItemAtlasData> getItemAtlasData(Context context) {
		List<ItemAtlasData> list = new ArrayList<ItemAtlasData>();
		float scaleSize = Config.scaleSize(context);
		ItemAtlasData data;

		data = new ItemAtlasData();
		initItamAtlasData(data, context, scaleSize, Config.ItemUILayout.item_scale_normal, 3,
				"item/itembig_jiqiren.png", "一個奇怪的機器人，這根天線難道可以控制它？", "天線機器人", 0 + Config.Value.atlas_max * 3);
		list.add(data);

		data = new ItemAtlasData();
		initItamAtlasData(data, context, scaleSize, Config.ItemUILayout.item_scale_small, 0,
				"item/itembig_biandang.png", "還剩下很多炸雞！Luck~可是沒有筷子", "便當盒", 0);
		list.add(data);

		data = new ItemAtlasData();
		initItamAtlasData(data, context, scaleSize, Config.ItemUILayout.item_scale_normal, 4,
				"item/itembig_xueshen.png", "我是學霸，誰與爭鋒", "住著考試之神的護身符", 6 + Config.Value.atlas_max * 4);
		list.add(data);

		return list;
	}

	private static void initItamAtlasData(ItemAtlasData data, Context context, float scaleSize, float zoomSize,
			int rank, String ic_path, String info, String name, int id) {
		data.item_ic_path = ic_path;
		data.item_ic = ImgReader.loadSmaller(ic_path, context, scaleSize * zoomSize);
		data.item_info = info;
		data.item_name = name;
		data.item_rank = rank;
		data.item_id = id;
	}

	public static List<GameItem_Dig> getDigItemDataList(int key,int num) {
		List<GameItem_Dig> list = new ArrayList<GameItem_Dig>();
		GameItem_Dig item;
		switch (key) {
		case 7:
			for (int i = 0; i < num; i++) {
				item = new GameItem_Dig(GameStage.Item_State.PlasticShovel.DURABLE,
						GameStage.Item_State.PlasticShovel.POWER);
				list.add(item);
			}
			break;
		case 8:
			for (int i = 0; i < num; i++) {
				item = new GameItem_Dig(GameStage.Item_State.IronShovel.DURABLE, GameStage.Item_State.IronShovel.POWER);
				list.add(item);
			}
			break;
		case 9:
			for (int i = 0; i < num; i++) {
				item = new GameItem_Dig(GameStage.Item_State.Drill.DURABLE, GameStage.Item_State.Drill.POWER);
				list.add(item);
			}
			break;
		case 10:
			for (int i = 0; i < num; i++) {
				item = new GameItem_Dig(GameStage.Item_State.Piledriver.DURABLE, GameStage.Item_State.Piledriver.POWER);
				list.add(item);
			}
			break;
		default:
			list = null;
			break;
		}
		return list;
	}

	public static GameMap getMapData(int key) {
		GameMap mapData = new GameMap();
		mapData.map = new MapData();
		switch (key) {
		case 0:
			mapData.map.map_id = 1;
			break;
		case 1:
			mapData.map.map_id = 2;
			break;
		case 2:
			mapData.map.map_id = 3;
			break;
		default:
			mapData.map.map_id = 1;
			break;
		}
		mapData.map.item_list_return = "1:10002;2:10024;3:10052;4:10056;5:10071;6:10082;7:10086;8:1;9:10036;10:10090;11:10036;13:10031;14:10091;15:10032;16:1;17:10050;18:10041;19:10044;20:10097;21:10048;22:10042;23:10050;25:10041;26:10047;27:10096;28:10044;29:10042;30:10099;31:10096;32:10045;33:10045;34:10101;35:1;36:10049;37:10100;38:10041;39:10043;40:10044;41:10105;42:10108;43:10109;45:10111;46:10102;47:10104;48:10108;49:2;50:10111;51:1;52:10105;53:10105;54:10110;55:10107;57:1;58:10103;59:10108;60:10104;61:10110;62:10110;63:1;64:10103;65:10105;66:10106;67:1;68:1;69:10110;70:10104;71:1;72:10109;73:10102;75:1;76:10114;77:10115;79:10115;80:10118;82:10118;83:1;85:10113;87:10120;88:10115;89:10113;90:10117;91:10117;92:10119;93:10119;94:10112;95:1;96:10119;97:10113;98:10112;100:10120;101:10118;102:1;103:10117;104:10112;105:10113;107:10115;108:10114;110:10121;111:10119;112:10112;113:10119;114:10119;115:10119;116:10114;117:10112;119:10115;120:10116;124:2;131:1;132:1;135:1;147:1";

		return mapData;
	}
}
