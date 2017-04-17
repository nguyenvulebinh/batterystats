package android.com.batterystats.view.fragment;

import android.com.batterystats.R;
import android.com.batterystats.databinding.BatteryHistoryDataBinding;
import android.com.batterystats.databinding.DetailStatsDataBinding;
import android.com.batterystats.viewmodel.BatteryHistoryModelView;
import android.com.batterystats.viewmodel.DetailStatsModelView;
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

public class DetailStatsFragment  extends Fragment {
    private DetailStatsModelView modelView;
    private DetailStatsDataBinding dataBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_stats, container, false);
        modelView = new DetailStatsModelView();
        dataBinding.setDetailStatsMV(modelView);
        return dataBinding.getRoot();
    }
}