package com.landbreaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// 资料功能类别
public class Table_GLOBALARCHIVEMENT_IN_PROGRESS {
	// 表格名称
	public static final String TABLE_NAME = "GLOBALARCHIVEMENT_IN_PROGRESS";
	private Context contex;
	// 编号表格字段名称，固定不变
	public static final String KEY_ID = "arch_id";

	// 其它表格字段名称
	public static final String PLAYER_ID_COLUMN = "player_id";
	public static final String STATUS_COLUMN = "STATUS";
	public static final String TYPE_COLUMN = "type";
	public static final String PROGRESS_COLUMN = "progress";
	public static final String START_TIME_COLUMN = "start_time";
	public static final String END_TIME_COLUMN = "end_time";

	// 使用上面宣告的变量建立表格的SQL指令
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID
			+ " INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " + PLAYER_ID_COLUMN + " INTEGER DEFAULT '1', "+ STATUS_COLUMN + " INTEGER DEFAULT '1', "
			+ TYPE_COLUMN + " INTEGER DEFAULT '1', " + PROGRESS_COLUMN + " text, " + START_TIME_COLUMN + " text, " + END_TIME_COLUMN + " text " + ")"; // " PRIMARY KEY ('id')," +"UNIQUE KEY 'id' ('id')," +"UNIQUE KEY 'NAME' ('NAME')"+

	// 数据库物件
	private SQLiteDatabase db;

	// 建构子，一般的应用都不需要修改
	public Table_GLOBALARCHIVEMENT_IN_PROGRESS(Context context) {
		db = MyDBHelper.getDatabase(context);
		this.contex = context;

	}

	// 关闭数据库，一般的应用都不需要修改
	public void close() {
		db.close();
	}

	// 新增参数指定的物件
	public Item_GLOBALARCHIVEMENT_IN_PROGRESS insert(Item_GLOBALARCHIVEMENT_IN_PROGRESS item) {
		// 建立准备新增资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的新增资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		cv.put(KEY_ID, item.arch_id);
		cv.put(PLAYER_ID_COLUMN, item.player_id);
		cv.put(STATUS_COLUMN, item.STATUS);
		cv.put(TYPE_COLUMN, item.type);
		cv.put(PROGRESS_COLUMN, item.progress);
		cv.put(START_TIME_COLUMN, item.start_time);
		cv.put(END_TIME_COLUMN, item.end_time);

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
	public boolean update(Item_GLOBALARCHIVEMENT_IN_PROGRESS item) {
		// 建立准备修改资料的ContentValues物件
		ContentValues cv = new ContentValues();

		// 加入ContentValues物件包装的修改资料
		// 第一个参数是字段名称， 第二个参数是字段的资料
		// cv.put(KEY_ID, item.id);
		cv.put(KEY_ID, item.arch_id);
		cv.put(PLAYER_ID_COLUMN, item.player_id);
		cv.put(STATUS_COLUMN, item.STATUS);
		cv.put(TYPE_COLUMN, item.type);
		cv.put(PROGRESS_COLUMN, item.progress);
		cv.put(START_TIME_COLUMN, item.start_time);
		cv.put(END_TIME_COLUMN, item.end_time);

		// 设定修改资料的条件为编号
		// 格式为“字段名称＝资料”
		String where = KEY_ID + "=" + item.arch_id;

		// 执行修改资料并回传修改的资料数量是否成功
		return db.update(TABLE_NAME, cv, where, null) > 0;
	}

	// 删除参数指定编号的资料
	public boolean delete(long id,int status) {
		// 设定条件为编号，格式为“字段名称=资料”
		String where = KEY_ID + "=" + id + " and " + STATUS_COLUMN + "=" + status;
		// 删除指定编号资料并回传删除是否成功
		return db.delete(TABLE_NAME, where, null) > 0;
	}

	// 删除表内所有数据
	public void deleteTable(){
		db.execSQL("delete from " + TABLE_NAME);
	}

	// 读取所有记事资料
	public List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> getAll() {
		List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> result = new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}

		cursor.close();
		return result;
	}

	// 取得指定编号的资料物件
	public Item_GLOBALARCHIVEMENT_IN_PROGRESS get(long id) {
		// 准备回传结果用的物件
		Item_GLOBALARCHIVEMENT_IN_PROGRESS item = null;
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

	// 取得指定编号的资料物件
	public List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> getSTATUS(int id) {
		// 准备回传结果用的物件
		List<Item_GLOBALARCHIVEMENT_IN_PROGRESS> result = new ArrayList<Item_GLOBALARCHIVEMENT_IN_PROGRESS>();
		// 使用编号为查询条件
		String where = STATUS_COLUMN + "=" + id;
		// 执行查询
		Cursor cursor = db.query(TABLE_NAME, null, where, null, null, null, null, null);

		while (cursor.moveToNext()) {
			result.add(getRecord(cursor));
		}

		// 关闭Cursor物件
		cursor.close();
		// 回传结果

		return result;
	}

	// 把Cursor目前的资料包装为物件
	public Item_GLOBALARCHIVEMENT_IN_PROGRESS getRecord(Cursor cursor) {
		// 准备回传结果用的物件
		Item_GLOBALARCHIVEMENT_IN_PROGRESS result = new Item_GLOBALARCHIVEMENT_IN_PROGRESS();

		result.arch_id = cursor.getInt(0);
		result.player_id = cursor.getInt(1);
		result.STATUS = cursor.getInt(2);
		result.type = cursor.getInt(3);
		result.progress = cursor.getString(4);
		result.start_time = cursor.getString(5);
		result.end_time = cursor.getString(6);

		Log.d("getRecord",result.progress);
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

		String a = "INSERT INTO `GLOBALARCHIVEMENT_IN_PROGRESS` VALUES ('1', '8', '1', '1', '10161:1', '2016-03-16 14:47:46', 'null' ), ('2', '8', '1', '2', '4:6', '2016-03-15 15:27:21', 'null' )";
		db.execSQL(a);
	}

}