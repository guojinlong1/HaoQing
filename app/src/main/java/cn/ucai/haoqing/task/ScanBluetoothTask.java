package cn.ucai.haoqing.task;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.ucai.haoqing.HaoQingApplication;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class ScanBluetoothTask extends Activity{
    final static String TAG = ScanBluetoothTask.class.getName();

    private List<String> devices;


    private BluetoothAdapter mBluetoothAdapter;//蓝牙适配器
    private boolean mScanning;//是否扫描
    private Handler mHandler;//声明Handler

    private static final int REQUEST_ENABLE_BT = 1;//请求启动蓝牙（常量）,一定要写
    // 10秒后停止查找搜索.
    private static final long SCAN_PERIOD = 10000;//扫描周期

    ArrayList<BluetoothDevice> mLeDevices;


    public ScanBluetoothTask() {
        mHandler = new Handler();
        // 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        mLeDevices = HaoQingApplication.getList();
    }

    //扫描蓝牙设备
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    // Device scan callback.
    // 设备扫描回调
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(device.getName()!=null){
                        if(device.getName().contains("YUNMAI")){
                            Log.e(TAG,device.getName().toString());
                        }
                    }
                    addDevice(device);
                    sendStickyBroadcast(new Intent("yunmai"));

                }
            });
        }
    };

    public void addDevice(BluetoothDevice device) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device);
        }
    }
}
