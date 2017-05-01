package android.com.batterystats.view.fragment;

import android.com.batterystats.R;
import android.com.batterystats.databinding.EstimatedPowerUseDataBinding;
import android.com.batterystats.viewmodel.EstimatedPowerUseModelView;
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

public class EstimatedPowerUseFragment extends Fragment {
    private EstimatedPowerUseModelView modelView;
    private EstimatedPowerUseDataBinding dataBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_estimated_power_use, container, false);
        modelView = new EstimatedPowerUseModelView(getActivity(), dataBinding);
        dataBinding.setEstiPUMV(modelView);
        return dataBinding.getRoot();
    }
}