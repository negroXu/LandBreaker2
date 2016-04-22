package com.landbreaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

// 资料功能类别
public class Table_BASICITEM_BASICARCHIVEMENT {
	// 表格名称
	public static final String TABLE_NAME = "BASICITEM_BASICARCHIVEMENT";
	private Context contex;
	// 编号表格字段名称，固定不变
	public static final String KEY_ID = "basicitem_id";

	// 其它表格字段名称
	public static final String BASICARCH_ID_COLUMN = "basicarch_id";
	public static final String NUM_COLUMN = "num";


	// 使用上面宣告的变量建立表格的SQL指令 ('10001', '1', '便当盒', null, '便当盒', '0', '1', '5', '999999', '0')
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID
			+ " INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " + BASICARCH_ID_COLUMN + " INTEGER DEFAULT '1', "
			+ NUM_COLUMN + " INTEGER DEFAULT '1' " +")"; // " PRIMARY KEY ('id')," +"UNIQUE KEY 'id' ('id')," +"UNIQUE KEY 'NAME' ('NAME')"+

	// 数据库物件
	private SQLiteDatabase db;

	// 建构子，一般的应用都不需要修改
	public Table_BASICITEM_BASICARCHIVEMENT(Context context) {
		db = MyDBHelper.getDatabase(context);
		this.contex = context;
	}

	// 关闭数据库，一般的应用都不需要修改
	public void close() {
		db.close();
	}

	// 新增参数指定的物件
	public Item_BASICITEM_BASICARCHIVEMENT insert(Item_BASICITEM_BASICARCHIVEMENT item) {
		// 建立准备新增资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的新增资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		cv.put(KEY_ID, item.basicitem_id);
		cv.put(BASICARCH_ID_COLUMN, item.basicarch_id);
		cv.put(NUM_COLUMN, item.num);

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
	public boolean update(Item_BASICITEM_BASICARCHIVEMENT item) {
		// 建立准备修改资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的修改资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		// cv.put(KEY_ID, item.id);
		cv.put(KEY_ID, item.basicitem_id);
		cv.put(BASICARCH_ID_COLUMN, item.basicarch_id);
		cv.put(NUM_COLUMN, item.num);

		// 设定修改资料的条件为编号
		// 格式为“字段名称＝资料”
		String where = KEY_ID + "=" + item.basicitem_id;

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
	public List<Item_BASICITEM_BASICARCHIVEMENT> getAll() {
		List<Item_BASICITEM_BASICARCHIVEMENT> result = new ArrayList<Item_BASICITEM_BASICARCHIVEMENT>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}

		cursor.close();
		return result;
	}

	// 取得指定编号的资料物件
	public Item_BASICITEM_BASICARCHIVEMENT get(long id) {
		// 准备回传结果用的物件
		Item_BASICITEM_BASICARCHIVEMENT item = null;
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
	public Item_BASICITEM_BASICARCHIVEMENT getRecord(Cursor cursor) {
		// 准备回传结果用的物件
		Item_BASICITEM_BASICARCHIVEMENT result = new Item_BASICITEM_BASICARCHIVEMENT();

		result.basicitem_id = cursor.getLong(0);
		result.basicarch_id = cursor.getInt(1);
		result.num = cursor.getInt(2);

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

		String a = "INSERT INTO `BASICITEM_BASICARCHIVEMENT` VALUES ('10001', '3', '5'), ('10153', '1', '1'), ('10154', '1', '1'), ('10155', '1', '1'), ('10156', '1', '1'), ('10157', '1', '1'), ('10158', '1', '1'), ('10159', '1', '1'), ('10160', '1', '1'), ('10161', '1', '1'), ('10162', '1', '1'), ('10163', '1', '1'), ('10164', '1', '1'), ('10165', '1', '1');";
		db.execSQL(a);
	}

}