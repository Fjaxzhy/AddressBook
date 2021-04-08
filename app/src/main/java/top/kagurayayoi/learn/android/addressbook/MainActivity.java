package top.kagurayayoi.learn.android.addressbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import top.kagurayayoi.learn.android.addressbook.sqlite.SQLiteFunction;
import top.kagurayayoi.learn.android.addressbook.sqlite.SQLiteOpenHelperOverride;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private FloatingActionButton fab;
    private TextView textView;
    private ListView listView;
    private SQLiteOpenHelperOverride helper;
    private SQLiteFunction sql;
    private Cursor cursor;
    private int cursorCount;
    private ArrayList<String> addrArray;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sql = new SQLiteFunction();
        helper = new SQLiteOpenHelperOverride(MainActivity.this);
        fab = findViewById(R.id.fab);
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        addrArray = new ArrayList<>(100);


        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, addrArray);
        listView.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                upgrade_listView(helper);
                new Handler().postDelayed(this,1000);
            }
        },1000);

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0,0,0,"更改联系人信息");
                menu.add(0,1,1,"删除联系人信息");
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String id = String.valueOf(info.id);
        switch (item.getItemId()){
            case 0:
                intent = new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            case 1:
                sql.deleteData("AddrList", id, helper);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void upgrade_listView(SQLiteOpenHelperOverride helper){
        cursor = sql.getDatabase(helper);
        cursorCount = cursor.getCount();

        textView.setText("通讯录里有" + cursorCount + "个联系人");

        if (cursorCount == 0){
            return;
        }else {
            String str = "";
            int Count = 0;
            while (cursor.moveToNext()){
                if (Count == cursorCount){
                    return;
                }
                str += cursor.getInt(0) + ". ";
                str += cursor.getString(1) + " ";
                str += cursor.getString(2);
                addrArray.add(str);
                str = "";
                Count += 1;
                Log.e("getInt", String.valueOf(cursor.getInt(0)));
                Log.e("getString", cursor.getString(1));
                Log.e("getString", cursor.getString(2));
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sql.Close();
    }
}