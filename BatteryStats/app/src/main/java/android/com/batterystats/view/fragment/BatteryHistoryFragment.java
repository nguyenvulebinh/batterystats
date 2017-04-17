package android.com.batterystats.view.fragment;

import android.com.batterystats.R;
import android.com.batterystats.databinding.BatteryHistoryDataBinding;
import android.com.batterystats.viewmodel.BatteryHistoryModelView;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nguyenbinh on 14/04/2017.
 */

public class BatteryHistoryFragment extends Fragment{
    private BatteryHistoryModelView modelView;
    private BatteryHistoryDataBinding dataBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_battery_history, container, false);
        modelView = new BatteryHistoryModelView();
        dataBinding.setBatHisMV(modelView);
        return dataBinding.getRoot();
    }
}
