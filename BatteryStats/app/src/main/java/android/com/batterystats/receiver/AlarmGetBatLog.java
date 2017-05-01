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
//                RootShell rootShell = RootShell.getInstance();
//                List<String> batHisContent = rootShell.run("dumpsys batterystats");
//                if (batHisContent.size() > 0) {
//                    int index = -1;
//                    for (int i = 0; i < batHisContent.size(); i++) {
//                        if (batHisContent.get(i).contains("RESET:TIME:")) {
//                            index = i;
//                            break;
//                        }
//                    }
//                    if (index != -1) {
//                        String[] listArg = batHisContent.get(index).split(" ");
//                        String time = listArg[listArg.length - 1];
//                        File directory = new File(StaticConfig.DATA_DIRECTORY + "/" + time);
//                        if (!directory.exists()) {
//                            directory.mkdirs();
//                        }
//                        GetHtmlFromLog.postServer(directory.getAbsolutePath(), batHisContent);
//                        writeStatsInfo(batHisContent, directory.getAbsolutePath());
//                        Log.e(TAG, batHisContent.get(1));
//                        rootShell.run("dumpsys batterystats --reset");
//                    } else {
//                        Log.e(TAG, "null");
//                    }
//                } else {
//                    Log.e(TAG, "null");
//                }
                ExecuteShellBatLog.executeShellBatLog(true, mContext);
            }
        }).start();
    }

//    private void writeStatsInfo(List<String> batHisContent, String directory) {
//        File fileInfo = new File(directory + File.separator + StaticConfig.STATS_INFO_FILE_DEFAULT);
//        File fileRaw = new File(directory + File.separator + StaticConfig.RAW_FILE_DEFAULT);
//        try {
//            String percent = batHisContent.get(0).substring(batHisContent.get(0).indexOf("(") + 1, batHisContent.get(0).indexOf("%") + 1);
//            FileWriter fileWriter = new FileWriter(fileInfo);
//            FileWriter rawFileWriter = new FileWriter(fileRaw);
//
//            fileWriter.write(percent);
//            for (String line : batHisContent) {
//                rawFileWriter.write(line + "\n");
//            }
//
//            fileWriter.flush();
//            rawFileWriter.flush();
//            fileWriter.close();
//            rawFileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

