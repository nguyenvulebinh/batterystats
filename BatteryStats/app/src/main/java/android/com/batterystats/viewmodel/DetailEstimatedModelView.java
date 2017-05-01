package android.com.batterystats.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

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
}
