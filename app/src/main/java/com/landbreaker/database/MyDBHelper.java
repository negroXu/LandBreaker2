package com.landbreaker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

	// 数据库名称
	public static final String DATABASE_NAME = "mydata.db";
	// 数据库版本，资料结构改变的时候要更改这个数字，通常是加一
	public static final int VERSION = 6;
	// 数据库物件，固定的字段变量
	private static SQLiteDatabase database;

	public MyDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Table_BASICITEM.CREATE_TABLE);
		db.execSQL(Table_BASICSYSTEMITEM.CREATE_TABLE);
		db.execSQL(Table_MAPFORTUNE.CREATE_TABLE);
		db.execSQL(Table_BASICMAP.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + Table_BASICITEM.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Table_BASICSYSTEMITEM.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Table_MAPFORTUNE.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Table_BASICMAP.TABLE_NAME);
		// 呼叫onCreate建立新版的表格
		onCreate(db);
	}

	// 需要数据库的元件呼叫这个方法，这个方法在一般的应用都不需要修改
	public static SQLiteDatabase getDatabase(Context context) {
		if (database == null || !database.isOpen()) { 
			database = new MyDBHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
		}

		return database;
	}
}
