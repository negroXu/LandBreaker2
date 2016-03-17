package com.landbreaker.database;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

// 资料功能类别
public class Table_BASICSYSTEMITEM {
	// 表格名称
	public static final String TABLE_NAME = "BASICSYSTEMITEM";
	private Context contex;
	// 编号表格字段名称，固定不变
	public static final String KEY_ID = "id";

	// 其它表格字段名称
	public static final String TYPE_COLUMN = "type";
	public static final String STATUS_COLUMN = "status";
	public static final String NAME_COLUMN = "NAME";
	public static final String PIC_URL_COLUMN = "pic_url";
	public static final String DESCRIPTION_COLUMN = "description";
	public static final String LEVEL_COLUMN = "LEVEL";
	public static final String RATE_COLUMN = "rate";
	public static final String SELL_PRICE_COLUMN = "sell_price";
	public static final String BUY_PRICE_COLUMN = "buy_price";
	public static final String BUY_MONEY_COLUMN = "buy_money";

	// 使用上面宣告的变量建立表格的SQL指令 ('10001', '1', '便当盒', null, '便当盒', '0', '1', '5', '999999', '0')
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID
			+ " INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " + TYPE_COLUMN + " INTEGER NOT NULL, "+STATUS_COLUMN + " INTEGER DEFAULT '1', "
			+ NAME_COLUMN + " CHAR(100) DEFAULT NULL UNIQUE, " + PIC_URL_COLUMN + " CHAR(255) DEFAULT NULL, "
			 + LEVEL_COLUMN + " INTEGER NOT NULL, " + RATE_COLUMN
			+ " DOUBLE NOT NULL, " + SELL_PRICE_COLUMN + " INTEGER DEFAULT '0', " + BUY_PRICE_COLUMN
			+ " INTEGER DEFAULT '999999'," + BUY_MONEY_COLUMN + " INTEGER DEFAULT '999999'," + DESCRIPTION_COLUMN + " TEXT" +")"; // " PRIMARY KEY ('id')," +"UNIQUE KEY 'id' ('id')," +"UNIQUE KEY 'NAME' ('NAME')"+

	// 数据库物件
	private SQLiteDatabase db;

	// 建构子，一般的应用都不需要修改
	public Table_BASICSYSTEMITEM(Context context) {
		db = MyDBHelper.getDatabase(context);
		this.contex = context;
	}

	// 关闭数据库，一般的应用都不需要修改
	public void close() {
		db.close();
	}

	// 新增参数指定的物件
	public Item_BASICSYSTEMITEM insert(Item_BASICSYSTEMITEM item) {
		// 建立准备新增资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的新增资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		cv.put(KEY_ID, item.id);
		cv.put(TYPE_COLUMN, item.type);
		cv.put(STATUS_COLUMN, item.status);
		cv.put(NAME_COLUMN, item.NAME);
		cv.put(PIC_URL_COLUMN, item.pic_url);
		cv.put(DESCRIPTION_COLUMN, item.description);
		cv.put(LEVEL_COLUMN, item.LEVEL);
		cv.put(RATE_COLUMN, item.rate);
		cv.put(SELL_PRICE_COLUMN, item.sell_price);
		cv.put(BUY_PRICE_COLUMN, item.buy_price);
		cv.put(BUY_MONEY_COLUMN, item.buy_money);

		// 新增一笔资料并取得编号
		// 第一个参数是表格名称
		// 第二个参数是没有指定字段值的默认值
		// 第三个参数是包装新增资料的ContentValues物件
		long id = db.insert(TABLE_NAME, null, cv);
		//Toast.makeText(contex, "" + id, Toast.LENGTH_SHORT).show();
		// 回传结果
		return item;
	}

	// 修改参数指定的物件
	public boolean update(Item_BASICSYSTEMITEM item) {
		// 建立准备修改资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的修改资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		// cv.put(KEY_ID, item.id);
		cv.put(KEY_ID, item.id);
		cv.put(TYPE_COLUMN, item.type);
		cv.put(STATUS_COLUMN, item.status);
		cv.put(NAME_COLUMN, item.NAME);
		cv.put(PIC_URL_COLUMN, item.pic_url);
		cv.put(DESCRIPTION_COLUMN, item.description);
		cv.put(LEVEL_COLUMN, item.LEVEL);
		cv.put(RATE_COLUMN, item.rate);
		cv.put(SELL_PRICE_COLUMN, item.sell_price);
		cv.put(BUY_PRICE_COLUMN, item.buy_price);
		cv.put(BUY_MONEY_COLUMN, item.buy_money);

		// 设定修改资料的条件为编号
		// 格式为“字段名称＝资料”
		String where = KEY_ID + "=" + item.id;

		// 执行修改资料并回传修改的资料数量是否成功
		return db.update(TABLE_NAME, cv, where, null) > 0;
	}

	// 删除参数指定编号的资料
	public boolean delete(long id) {
		// 设定条件为编号，格式为“字段名称=资料”
		String where = KEY_ID + "=" + id;
		// 删除指定编号资料并回传删除是否成功
		return db.delete(TABLE_NAME, where, null) > 0;
	}

	// 删除表内所有数据
	public void deleteTable(){
		db.execSQL("delete from " + TABLE_NAME);
	}

	// 读取所有记事资料
	public List<Item_BASICSYSTEMITEM> getAll() {
		List<Item_BASICSYSTEMITEM> result = new ArrayList<Item_BASICSYSTEMITEM>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}

		cursor.close();
		return result;
	}

	// 取得指定编号的资料物件
	public Item_BASICSYSTEMITEM get(long id) {
		// 准备回传结果用的物件
		Item_BASICSYSTEMITEM item = null;
		// 使用编号为查询条件
		String where = KEY_ID + "=" + id;
		// 执行查询
		Cursor result = db.query(TABLE_NAME, null, where, null, null, null, null, null);

		// 如果有查询结果
		if (result.moveToFirst()) {
			// 读取包装一笔资料的物件
			item = getRecord(result);
		}

		// 关闭Cursor物件
		result.close();
		// 回传结果
		return item;
	}

	// 把Cursor目前的资料包装为物件
	public Item_BASICSYSTEMITEM getRecord(Cursor cursor) {
		// 准备回传结果用的物件
		Item_BASICSYSTEMITEM result = new Item_BASICSYSTEMITEM();

		result.id = cursor.getLong(0);
		result.type = cursor.getInt(1);
		result.status = cursor.getInt(2);
		result.NAME = cursor.getString(3);
		result.pic_url = cursor.getString(4);
		result.description = cursor.getString(5);
		result.LEVEL = cursor.getInt(6);
		result.rate = cursor.getDouble(7);
		result.sell_price = cursor.getInt(8);
		result.buy_price = cursor.getInt(9);
		result.buy_money = cursor.getInt(10);


		// 回传结果
		return result;
	}

	// 取得资料数量
	public int getCount() {
		int result = 0;
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

		if (cursor.moveToNext()) {
			result = cursor.getInt(0);
		}

		return result;
	}

	// 建立范例资料
	public void sample() {

		String a = "INSERT INTO `BASICSYSTEMITEM` VALUES ('1', '0', '3', '木宝箱', '/LandBreaker/sitem/muxiang.png', '1', '1', '999999', '999999', '5', '可以开出宝物的木质宝箱'), ('2', '0', '3', '铁宝箱', '/LandBreaker/sitem/tiexiang.png', '1', '2', '999999', '999999', '5', '可以开出宝物的铁质宝箱'), ('3', '0', '3', '钻石宝箱', '/LandBreaker/sitem/zuanshixiang.png', '1', '3', '999999', '999999', '5', '可以开出宝物的钻石宝箱'), ('4', '1', '3', '金币', null, '0.5', '1', '999999', '999999', '0', '金币'), ('5', '2', '3', '钻石', null, '0.01', '1', '999999', '999999', '0', '钻石'), ('6', '3', '3', '体力', null, '0.1', '1', '999999', '999999', '0', '体力'), ('7', '4', '1', '胶铲', '/LandBreaker/tool/84jiaochan.png', '0.1', '1', '880', '8', '5', '挖掘的道具，比较脆弱'), ('8', '4', '1', '金铲', '/LandBreaker/tool/84jinshuchan.png', '0.09', '2', '1280', '12', '5', '挖掘的道具，稍微好一点'), ('9', '4', '1', '手摇钻', '/LandBreaker/tool/84shouzuan.png', '0.08', '3', '1880', '18', '5', '挖掘的道具，可以挖得快一点'), ('10', '4', '1', '电钻', '/LandBreaker/tool/84dianzuan.png', '0.05', '4', '2880', '28', '5', '挖掘的道具，电动的哦'), ('11', '4', '1', '鱼竿', '/LandBreaker/tool/84diaoyugan.png', '0.1', '1', '880', '8', '5', '钓鱼的道具，比较脆弱'), ('12', '4', '1', '鱼叉', '/LandBreaker/tool/84yucha.png', '0.09', '2', '1280', '12', '5', '钓鱼的道具，稍微好一点'), ('13', '5', '1', '木钥匙', '/LandBreaker/sitem/mukey.png', '0.1', '1', '8800', '18', '5', '可以秒开木质宝箱的钥匙'), ('14', '5', '1', '铁钥匙', '/LandBreaker/sitem/tiekey.png', '0.05', '2', '12800', '28', '5', '可以秒开铁质宝箱的钥匙'), ('15', '5', '1', '钻石钥匙', '/LandBreaker/sitem/zuanshikey.png', '0.01', '3', '28800', '48', '5', '可以秒开钻石宝箱的钥匙'), ('16', '5', '1', '体力药水', '/LandBreaker/sitem/tiliyaoshui.png', '0.05', '2', '28800', '48', '5', '可以补充5点体力的神奇药水'), ('17', '5', '1', '幸运药水', '/LandBreaker/sitem/xingyuyaoshui.png', '0.05', '2', '12800', '28', '5', '可以稍微提高下运气的神奇药水'), ('18', '5', '3', '100硬币', '/LandBreaker/sitem/100yen.png', '0.05', '1', '999999', '999999', '100', '可以出售给商人的古代硬币'), ('19', '5', '3', '10000纸币', '/LandBreaker/sitem/10000yen.png', '0.01', '2', '999999', '999999', '10000', '可以出售给商人的纸币'), ('20', '5', '2', '解锁背包', '/LandBreaker/sitem/jiesuodaojuge.png', '0', '5', '18800', '98', '5', '可以开启五格背包格的神奇解锁器'), ('21', '5', '2', '几个电钻', '/LandBreaker/sitem/jigedianzuan.png', '0', '5', '18800', '98', '5', '能买到多少个就要看老板娘的心情咯，不过起码也会有3个的啦'), ('22', '5', '2', '一叠胶铲', '/LandBreaker/sitem/yidiejiaochan.png', '0', '5', '4800', '28', '5', '能买到多少个就要看老板娘的心情咯，不过起码也会有3个的啦'), ('23', '5', '2', '一筐手钻', '/LandBreaker/sitem/yikuangshouzuan.png', '0', '5', '12800', '68', '5', '能买到多少个就要看老板娘的心情咯，不过起码也会有3个的啦'), ('24', '5', '2', '一捆金铲', '/LandBreaker/sitem/yikunjinchan.png', '0', '5', '8800', '48', '5', '能买到多少个就要看老板娘的心情咯，不过起码也会有3个的啦'), ('25', '5', '2', '一排鱼竿', '/LandBreaker/sitem/yipaiyugan.png', '0', '5', '4800', '28', '5', '能买到多少个就要看老板娘的心情咯，不过起码也会有3个的啦'), ('26', '5', '2', '一组鱼叉', '/LandBreaker/sitem/yizuyucha.png', '0', '5', '12800', '68', '5', '能买到多少个就要看老板娘的心情咯，不过起码也会有3个的啦');";
		db.execSQL(a);
	}

}