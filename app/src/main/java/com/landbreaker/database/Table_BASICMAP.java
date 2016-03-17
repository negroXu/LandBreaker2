package com.landbreaker.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Table_BASICMAP {
	public static final String TABLE_NAME = "BASICMAP";
	private Context contex;
	// 编号表格字段名称，固定不变
	public static final String KEY_ID = "id";

	// 其它表格字段名称
	public static final String STATUS_COLUMN = "STATUS";
	public static final String NAME_COLUMN = "NAME";
	public static final String PIC_URL_COLUMN = "pic_url";
	public static final String BASIC_GOLD_COLUMN = "basic_goldcoin";
	public static final String BASIC_MANEY_COLUMN = "basic_maney";
	public static final String LEVEL_COLUMN = "LEVEL";
	public static final String RATE_COLUMN = "rate";
	public static final String DESCRIPTION_COLUMN = "description";

	// 使用上面宣告的变量建立表格的SQL指令
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID
			+ " INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " + STATUS_COLUMN + " INTEGER DEFAULT '1', "
			+ NAME_COLUMN + " CHAR(100) DEFAULT NULL UNIQUE, " + PIC_URL_COLUMN + " CHAR(255) DEFAULT NULL, "
			+ BASIC_GOLD_COLUMN + " INTEGER NOT NULL, " + BASIC_MANEY_COLUMN + " INTEGER NOT NULL, " + LEVEL_COLUMN
			+ " INTEGER NOT NULL, " + RATE_COLUMN + " double NOT NULL, " + DESCRIPTION_COLUMN + " text " + ")";

	// 数据库物件
	private SQLiteDatabase db;

	// 建构子，一般的应用都不需要修改
	public Table_BASICMAP(Context context) {
		db = MyDBHelper.getDatabase(context);
		this.contex = context;
	}

	// 关闭数据库，一般的应用都不需要修改
	public void close() {
		db.close();
	}

	// 新增参数指定的物件
	public Item_BASICMAP insert(Item_BASICMAP item) {
		// 建立准备新增资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的新增资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		cv.put(KEY_ID, item.id);
		cv.put(STATUS_COLUMN, item.STATUS);
		cv.put(NAME_COLUMN, item.NAME);
		cv.put(PIC_URL_COLUMN, item.pic_url);
		cv.put(BASIC_GOLD_COLUMN, item.basic_goldcoin);
		cv.put(BASIC_MANEY_COLUMN, item.basic_maney);
		cv.put(LEVEL_COLUMN, item.LEVEL);
		cv.put(RATE_COLUMN, item.rate);
		cv.put(DESCRIPTION_COLUMN, item.description);

		// 新增一笔资料并取得编号
		// 第一个参数是表格名称
		// 第二个参数是没有指定字段值的默认值
		// 第三个参数是包装新增资料的ContentValues物件
		long id = db.insert(TABLE_NAME, null, cv);
		// 回传结果
		return item;
	}

	// 修改参数指定的物件
	public boolean update(Item_BASICMAP item) {
		// 建立准备修改资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的修改资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		// cv.put(KEY_ID, item.id);
		cv.put(KEY_ID, item.id);
		cv.put(STATUS_COLUMN, item.STATUS);
		cv.put(NAME_COLUMN, item.NAME);
		cv.put(PIC_URL_COLUMN, item.pic_url);
		cv.put(BASIC_GOLD_COLUMN, item.basic_goldcoin);
		cv.put(BASIC_MANEY_COLUMN, item.basic_maney);
		cv.put(LEVEL_COLUMN, item.LEVEL);
		cv.put(RATE_COLUMN, item.rate);
		cv.put(DESCRIPTION_COLUMN, item.description);

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

	// 读取所有记事资料
	public List<Item_BASICMAP> getAll() {
		List<Item_BASICMAP> result = new ArrayList<Item_BASICMAP>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}

		cursor.close();
		return result;
	}

	// 取得指定编号的资料物件
	public Item_BASICMAP get(long id) {
		// 准备回传结果用的物件
		Item_BASICMAP item = null;
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
	public Item_BASICMAP getRecord(Cursor cursor) {
		// 准备回传结果用的物件
		Item_BASICMAP result = new Item_BASICMAP();

		result.id = cursor.getInt(0);
		result.STATUS = cursor.getInt(1);
		result.NAME = cursor.getString(2);
		result.pic_url = cursor.getString(3);
		result.basic_goldcoin = cursor.getInt(4);
		result.basic_maney = cursor.getInt(5);
		result.LEVEL = cursor.getInt(6);
		result.rate = cursor.getDouble(7);
		result.description = cursor.getString(8);

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

		String a = "INSERT INTO `BASICMAP` VALUES ('1', '1', '街道', null, '100', '1', '1', '1', '街道'), ('2', '1', '公园', null, '100', '1', '1', '1', '公园');";
		db.execSQL(a);
	}
}
