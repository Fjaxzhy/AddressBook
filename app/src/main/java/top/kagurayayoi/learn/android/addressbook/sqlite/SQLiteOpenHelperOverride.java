package top.kagurayayoi.learn.android.addressbook.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpenHelperOverride extends SQLiteOpenHelper {

   public SQLiteOpenHelperOverride(Context context){
       super(context, "addrlist.db", null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      String table_name = "AddrList";
      String primary_key = "id";
      db.execSQL(
              "create table " + table_name + "(" + primary_key + " integer primary key autoincrement,"
              + "name text,"
              + "number text)"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

   }
}
