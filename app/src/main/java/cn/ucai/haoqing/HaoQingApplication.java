package cn.ucai.haoqing;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class HaoQingApplication extends Application {
    public static Context applicationContext;
    private static HaoQingApplication instance;
    // login user name

    /**
     */

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;

    }

    public static HaoQingApplication getInstance() {
        return instance;
    }

    public static ArrayList<BluetoothDevice> list;

    public static ArrayList<BluetoothDevice> getList() {
        return list;
    }

    public static void setList(ArrayList<BluetoothDevice> list) {
        HaoQingApplication.list = list;
    }
}
