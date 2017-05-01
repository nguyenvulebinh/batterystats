package android.com.batterystats.viewmodel;

import android.com.batterystats.model.BatteryHistory;
import android.view.View;

/**
 * Created by nguyenbinh on 30/04/2017.
 */

public class ItemBatHisModelView {

    public void showHtml(View view, BatteryHistory batteryHistory){
        GetHtmlFromLog.showHtml(batteryHistory.getUrl(), view.getContext());
    }
}
