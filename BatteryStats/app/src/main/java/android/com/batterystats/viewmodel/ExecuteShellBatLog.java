package android.com.batterystats.viewmodel;

import android.com.batterystats.model.StaticConfig;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.asksven.android.common.RootShell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static android.R.attr.max;

/**
 * Created by nguyenbinh on 01/05/2017.
 */

public class ExecuteShellBatLog {
    private static String TAG = ExecuteShellBatLog.class.getName();

    public static void executeShellBatLog(boolean reset, Context context) {
        RootShell rootShell = RootShell.getInstance();
        List<String> batHisContent = rootShell.run("dumpsys batterystats");
        if (batHisContent.size() > 0) {
            int index = -1;
            for (int i = 0; i < batHisContent.size(); i++) {
                if (batHisContent.get(i).contains("RESET:TIME:")) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                String[] listArg = batHisContent.get(index).split(" ");
                String time = listArg[listArg.length - 1];
                File directory = new File(StaticConfig.DATA_DIRECTORY + "/" + time);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                GetHtmlFromLog.postServer(directory.getAbsolutePath(), batHisContent);
                writeStatsInfo(batHisContent, directory.getAbsolutePath(), index + 1, context.getPackageManager());
                Log.e(TAG, batHisContent.get(1));
                if (reset) {
                    rootShell.run("dumpsys batterystats --reset");
                }
            } else {
                Log.e(TAG, "null");
            }
        } else {
            Log.e(TAG, "null");
        }
    }

    private static void writeStatsInfo(List<String> batHisContent, String directory, int index, PackageManager packageManager) {
        File fileInfo = new File(directory + File.separator + StaticConfig.STATS_INFO_FILE_DEFAULT);
        File fileRaw = new File(directory + File.separator + StaticConfig.RAW_FILE_DEFAULT);
        File fileEstimated = new File(directory + File.separator + StaticConfig.ESTIMATED_FILE_DEFAULT);
        try {
            int start = -1, end = -1;
            String batCurrent = batHisContent.get(index).trim().split(" ")[2];
            try {
                start = Integer.parseInt(batCurrent);
            } catch (Exception ignored) {
            }
            while (!batHisContent.get(index).trim().equals("")) {
                index++;
            }
            index--;
            batCurrent = batHisContent.get(index).trim().split(" ")[2];
            try {
                end = Integer.parseInt(batCurrent);
            } catch (Exception ignored) {
            }
            String percent = "101%";
            if (start != -1 && end != -1) {
                percent = (start - end) + "%";
            }
            FileWriter fileWriter = new FileWriter(fileInfo, false);
            FileWriter fileWriterEstimate = new FileWriter(fileEstimated, false);
            FileWriter rawFileWriter = new FileWriter(fileRaw, false);

            fileWriter.write(percent);
            boolean isEstimate = false;
            for (String line : batHisContent) {
                rawFileWriter.write(line + "\n");
                if (line.contains("Estimated power use") && !isEstimate) {
                    isEstimate = true;
                    fileWriterEstimate.write(line + "\n");
                } else if (line.trim().equals("") && isEstimate) {
                    isEstimate = false;
                } else if (isEstimate) {
                    if (line.toLowerCase().contains("uid")) {
                        String[] arg = line.trim().split(" ");
                        String packageName = getPackageNameFromUID(arg[1], packageManager);
                        line = line.replace(arg[1], packageName);
                    }
                    fileWriterEstimate.write(line + "\n");
                }
            }

            fileWriter.flush();
            rawFileWriter.flush();
            fileWriterEstimate.flush();
            fileWriterEstimate.close();
            fileWriter.close();
            rawFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get package name from uid u0_axxx
     *
     * @param uid
     * @param packageManager
     * @return
     */
    public static String getPackageNameFromUID(String uid, PackageManager packageManager) {
        uid = uid.replace(":", "");
        try {
            int appid;
            if (uid.contains("u0") || uid.equals("0")) {
                appid = Integer.parseInt(uid.replace("u0_a", "").replace("u0a", "")) + 10000;
            } else {
                appid = Integer.parseInt(uid);
            }
            String[] packageNames = packageManager.getPackagesForUid(appid);
            if (packageNames != null && packageNames.length > 0) {
                Log.e(TAG, uid + " : " + packageNames[0]);
                return packageNames[0];
            } else {
                return uid;
            }
        } catch (Exception e) {
            return uid;
        }
    }
}
