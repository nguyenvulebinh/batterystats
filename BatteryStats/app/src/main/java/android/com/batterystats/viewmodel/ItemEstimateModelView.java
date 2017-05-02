package android.com.batterystats.viewmodel;

import android.com.batterystats.model.BatteryHistory;
import android.com.batterystats.model.Estimated;
import android.com.batterystats.view.activity.DetailEstimatedActivity;
import android.content.Intent;
import android.view.View;

/**
 * Created by nguyenbinh on 30/04/2017.
 */

public class ItemEstimateModelView {

    public void showChart(View view, Estimated estimated, String time, String directory){
        Intent intent = new Intent(view.getContext(), DetailEstimatedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("dataEstimate", estimated);
        intent.putExtra("time", time);
        intent.putExtra("directory", directory);
        view.getContext().startActivity(intent);
    }
}
