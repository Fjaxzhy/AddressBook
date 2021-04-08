package top.kagurayayoi.learn.android.addressbook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.kagurayayoi.learn.android.addressbook.sqlite.SQLiteFunction;
import top.kagurayayoi.learn.android.addressbook.sqlite.SQLiteOpenHelperOverride;

public class UpdateActivity extends AppCompatActivity {

    private SQLiteOpenHelperOverride helper;
    private SQLiteFunction sql;
    private String id;
    private Intent intent;
    private EditText Name, Number;
    private Button update, cancel;
    private ContentValues values;
    private String name, number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        helper = new SQLiteOpenHelperOverride(this);
        sql = new SQLiteFunction();
        intent = getIntent();
        id = intent.getStringExtra("id");
        Name = findViewById(R.id.editText_Name_Update);
        Number = findViewById(R.id.editText_PhoneNumber_Update);
        update = findViewById(R.id.button_Update);
        cancel = findViewById(R.id.button_Cancel2);

        Cursor cursor = sql.queryData("AddrList", id, helper);
        Name.setText(cursor.getString(1));
        Number.setText(cursor.getString(2));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText() != null || Number.getText() != null) {
                    name = Name.getText().toString().trim();
                    number = Number.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        toastShow("联系人名称不能为空");
                    } else if (TextUtils.isEmpty(number)) {
                        toastShow("联系人号码不能为空");
                    } else {
                        values = new ContentValues();
                        values.put("name", name);
                        values.put("number", number);
                        sql.updateData("AddrList", id, values, helper);
                        toastShow("成功更新了一名联系人");
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void toastShow(String message){
        Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
