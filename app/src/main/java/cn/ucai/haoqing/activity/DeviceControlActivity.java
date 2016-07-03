package cn.ucai.haoqing.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import cn.ucai.haoqing.R;

public class DeviceControlActivity extends AppCompatActivity {
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";//连接的设备名
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";//设备地址
    final static String TAG = DeviceControlActivity.class.getName();

    private static int sex,height,age;

    TextView tvStart;


    private BluetoothAdapter mBluetoothAdapter;//蓝牙适配器
    private boolean mScanning;  //是否扫描
    private Handler mHandler;   //什么Handler

    private static final int REQUEST_ENABLE_BT = 1;//请求启动蓝牙（常量）,一定要写
    // 10秒后停止查找搜索.
    private static final long SCAN_PERIOD = 10000;//扫描周期
    private ArrayList<BluetoothDevice> mLeDevices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setContentView(R.layout.activity_device_control);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        registerReciveer();
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceControlActivity.this,DeviceScanActivity.class));
            }
        });
    }

    private void initData() {
        //初始化 Bluetooth Adapter,通过 蓝牙管理器得到一个参考蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        sex = EditActivity.toSex();
        height = EditActivity.toHeight();
        age = EditActivity.toAge();
        mLeDevices = new ArrayList<BluetoothDevice>();
        Log.e(TAG,"age:"+age);
        Log.e(TAG,"height:"+height);
        Log.e(TAG,"sex:"+sex);

    }

    private void initView() {
        tvStart = (TextView) findViewById(R.id.device_type_value);
    }


    class scanBuletoothReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }


    scanBuletoothReceiver mReceiver;

    public void registerReciveer (){
        mReceiver = new scanBuletoothReceiver();
        IntentFilter filter = new IntentFilter("yunmai");
        registerReceiver(mReceiver,filter);
    }
}
