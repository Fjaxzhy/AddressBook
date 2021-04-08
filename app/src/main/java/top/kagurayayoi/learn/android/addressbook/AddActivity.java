package top.kagurayayoi.learn.android.addressbook;

import android.content.ContentValues;
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

public class AddActivity extends AppCompatActivity{

    private Button add, cancel;
    private EditText Name, Number;
    private SQLiteOpenHelperOverride helper;
    private SQLiteFunction sql;
    private ContentValues values;
    private String name, number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new SQLiteOpenHelperOverride(this);
        sql = new SQLiteFunction();
        add = findViewById(R.id.button_add);
        cancel = findViewById(R.id.button_cancel);
        Name = findViewById(R.id.editText_Name);
        Number = findViewById(R.id.editText_PhoneNumber);

        add.setOnClickListener(new View.OnClickListener() {
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
                        sql.insertData("AddrList", values, helper);
                        toastShow("成功添加了一名联系人");
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
        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
