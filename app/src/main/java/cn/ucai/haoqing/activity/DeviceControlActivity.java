package cn.ucai.haoqing.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.ucai.haoqing.R;

public class DeviceControlActivity extends AppCompatActivity {
    final static String TAG = DeviceControlActivity.class.getName();

    private static int sex,height,age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        initView();
        initData();
    }

    private void initData() {
        sex = EditActivity.toSex();
        height = EditActivity.toHeight();
        age = EditActivity.toAge();
        Log.e(TAG,"sex:"+sex);
        Log.e(TAG,"age:"+age);
        Log.e(TAG,"height:"+height);
    }

    private void initView() {
    }
}
