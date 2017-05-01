package android.com.batterystats.receiver;

import android.com.batterystats.model.StaticConfig;
import android.com.batterystats.viewmodel.ExecuteShellBatLog;
import android.com.batterystats.viewmodel.GetHtmlFromLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.asksven.android.common.RootShell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by nguyenbinh on 06/04/2017.
 */

public class AlarmGetBatLog extends BroadcastReceiver {
    public static String TAG = AlarmGetBatLog.class.getName();
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ExecuteShellBatLog.executeShellBatLog(true, mContext);
            }
        }).start();
    }
}

