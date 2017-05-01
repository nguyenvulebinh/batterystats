package android.com.batterystats.model;

import android.com.batterystats.R;
import android.os.Environment;

import java.util.HashMap;

/**
 * Created by nguyenbinh on 30/04/2017.
 */

public class StaticConfig {
    public static String DATA_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BatHisFile";
    public static String HTML_FILE_DEFAULT = "bathis.html";
    public static String STATS_INFO_FILE_DEFAULT = "info";
    public static String RAW_FILE_DEFAULT = "raw";
    public static String ESTIMATED_FILE_DEFAULT = "estimated";
    public static HashMap<String, Integer> MAP_ICON;
    static {
        MAP_ICON = new HashMap<>();
        MAP_ICON.put("screen", R.drawable.ic_screen);
        MAP_ICON.put("cell standby", R.drawable.ic_waiting);
        MAP_ICON.put("phone calls", R.drawable.ic_phone_call);
        MAP_ICON.put("wifi", R.drawable.ic_wifi);
        MAP_ICON.put("bluetooth", R.drawable.ic_bluetooth);
        MAP_ICON.put("system", R.drawable.ic_linux);
    }
}
