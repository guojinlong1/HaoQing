package cn.ucai.haoqing.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cn.ucai.haoqing.R;

public class EditActivity extends AppCompatActivity {

    private ArrayList<BluetoothDevice> mLeDevices;

    private BluetoothAdapter mBluetoothAdapter;//蓝牙适配器
    private boolean mScanning;  //是否扫描
    private Handler mHandler;   //什么Handler

    private static final int REQUEST_ENABLE_BT = 1;//请求启动蓝牙（常量）,一定要写
    // 10秒后停止查找搜索.
    private static final long SCAN_PERIOD = 10000;//扫描周期


    final static String TAG = EditActivity.class.getName();


    private static EditText et_userName, et_height, et_age;
    private  RadioGroup rg_sex;

    private  String userName;
    private static int sex,height,age;
    private Button ivStart;


    public static ArrayList<String> list;

    public static ArrayList<String> getList() {
        return list;
    }

    public static void setList(ArrayList<String> list) {
        EditActivity.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setContentView(R.layout.activity_edit);
        initView();
        setListener();
        startBluetooth();


    }

    private void setListener() {
        registerReceiver();
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


   private void startBluetooth(){

       //检测当前手机是否支持蓝牙，如果不支持，退出
       if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
           //吐司出不支持蓝牙
           Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
           finish();
       }

       //初始化 Bluetooth Adapter,通过 蓝牙管理器得到一个参考蓝牙适配器
       final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
       mBluetoothAdapter = bluetoothManager.getAdapter();

       // 检查设备上是否支持蓝牙
       if (mBluetoothAdapter == null) {
           //吐司出（不支持蓝牙）
           Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
           finish();//如果不支持蓝牙就退出该程序
           return;
       }
       // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
       if (!mBluetoothAdapter.isEnabled()) {
           if (!mBluetoothAdapter.isEnabled()) {//
               Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
               startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
           }
       }
   }


    class BluetoothReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG,"____________________________OK");
        }
    }
    BluetoothReceiver mReceiver;

    public void registerReceiver(){
        mReceiver = new BluetoothReceiver();
        IntentFilter filter = new IntentFilter("OK");
        registerReceiver(mReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            unregisterReceiver(mReceiver);
        }
    }
}
