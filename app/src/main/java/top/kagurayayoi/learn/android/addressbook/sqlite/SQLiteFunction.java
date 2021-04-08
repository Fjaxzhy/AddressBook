package top.kagurayayoi.learn.android.addressbook.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteFunction {

    private SQLiteDatabase db;

    public Cursor getDatabase(SQLiteOpenHelperOverride helper){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query("AddrList", new String[]{"id, name, number"},
                null, null, null, null ,null);
        return cursor;
    }

    public void insertData(String table_name, ContentValues values, SQLiteOpenHelperOverride helper){
        db = helper.getWritableDatabase();
        db.insert(table_name, null, values);
    }

    public void deleteData(String table_name, String id, SQLiteOpenHelperOverride helper){
        db = helper.getWritableDatabase();
        db.delete(table_name, "id = ?", new String[]{id});
    }

    public void updateData(String table_name, String id, ContentValues values, SQLiteOpenHelperOverride helper){
        db = helper.getWritableDatabase();
        db.update(table_name, values, "id = ?", new String[]{id});
    }

    public Cursor queryData(String table_name, String id , SQLiteOpenHelperOverride helper){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(table_name, new String[]{"id"}, "id = ?", new String[]{id},
                null, null, null);
        return cursor;
    }

    public void Close(){
        db.close();
    }
}

