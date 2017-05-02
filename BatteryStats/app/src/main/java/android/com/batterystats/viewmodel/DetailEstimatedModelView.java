package android.com.batterystats.viewmodel;

import android.com.batterystats.R;
import android.com.batterystats.view.activity.ViewLogDetail;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by nguyenbinh on 01/05/2017.
 */

public class DetailEstimatedModelView extends BaseObservable{
    @Bindable
    public boolean isShowChoose;
    @Bindable
    public String entityName;
    @Bindable
    public String useEstimated;
    @Bindable
    public String timeEstimate;

    public DetailEstimatedModelView(){
        isShowChoose = false;
        notifyPropertyChanged(BR.isShowChoose);
    }


    public void setShowChoose(boolean showChoose) {
        isShowChoose = showChoose;
        notifyPropertyChanged(BR.isShowChoose);
    }


    public void setEntityName(String entityName) {
        this.entityName = entityName;
        notifyPropertyChanged(BR.entityName);
    }

    
    public void setUseEstimated(String useEstimated) {
        this.useEstimated = useEstimated;
        notifyPropertyChanged(BR.useEstimated);
    }

    public void setTimeEstimate(String timeEstimate) {
        this.timeEstimate = timeEstimate;
        notifyPropertyChanged(BR.timeEstimate);
    }

    public void clickDetail(View view, String fileLogName){
        File fileLog = new File(fileLogName);
        if(fileLog.exists()){
            Intent intent = new Intent(view.getContext(), ViewLogDetail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fileLogName", fileLogName);
            view.getContext().startActivity(intent);
        }else {
            Toast.makeText(view.getContext(), view.getContext().getString(R.string.unknown) + " " + fileLogName.substring(fileLogName.lastIndexOf(File.separator) + 1), Toast.LENGTH_SHORT).show();
        }
    }
}
