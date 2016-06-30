package cn.ucai.haoqing.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import cn.ucai.haoqing.R;

public class EditActivity extends AppCompatActivity {

    final static String TAG = EditActivity.class.getName();


    private static EditText et_userName, et_height, et_age;
    private  RadioGroup rg_sex;

    private  String userName;
    private static int sex,height,age;
    private Button ivStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        setListener();
    }

    private void setListener() {
        setSexChangedListener();
        setOnLoginListener();
    }

    private void setOnLoginListener() {
        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_userName.getText().toString().trim().length()==0){
                    et_userName.setError("请输入名字");
                    return;
                }
                if(et_height.getText().toString().length()==0 ){
                    et_height.setError("请输入身高");
                    return;
                }
                if(et_age.getText().toString().length()==0){
                    et_age.setError("请输入年龄");
                }

                startActivity(new Intent(EditActivity.this,DeviceControlActivity.class));
                userName = et_userName.getText().toString().trim();
                Log.e(TAG,"sex:"+sex);
                Log.e(TAG,"age:"+age);
                Log.e(TAG,"height:"+height);
                finish();
            }
        });
    }

    private void setSexChangedListener() {
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_man:
                        sex = 1 ;
                        break;
                    case R.id.rb_woman:
                        sex = 0;
                        break;
                }
            }
        });
    }


    private void initView() {
        et_userName = (EditText) findViewById(R.id.et_name);
        et_height = (EditText) findViewById(R.id.et_height);
        et_age = (EditText) findViewById(R.id.et_age);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        ivStart = (Button) findViewById(R.id.btn_start);
    }

    public static int toAge(){
        if(et_age.getText().toString().trim().length()!=0){
            age = Integer.parseInt(et_age.getText().toString().trim());
        }
        return age;
    }

    public static int toHeight(){
        if(et_height.getText().toString().trim().length()!=0){
            height = Integer.parseInt(et_height.getText().toString().trim());
        }
        return height;
    }
    public static int toSex(){
        return sex;
    }
}
