package android.com.batterystats.model;

import android.os.Environment;

/**
 * Created by nguyenbinh on 30/04/2017.
 */

public class StaticConfig {
    public static String DATA_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BatHisFile";
    public static String HTML_FILE_DEFAULT = "bathis.html";
    public static String STATS_INFO_FILE_DEFAULT = "info";
    public static String RAW_FILE_DEFAULT = "raw";
    public static String ESTIMATED_FILE_DEFAULT = "estimated";
}
