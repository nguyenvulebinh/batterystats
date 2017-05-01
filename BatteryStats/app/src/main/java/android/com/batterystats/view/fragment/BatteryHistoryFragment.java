package android.com.batterystats.view.fragment;

import android.com.batterystats.R;
import android.com.batterystats.databinding.BatteryHistoryDataBinding;
import android.com.batterystats.view.activity.MainActivity;
import android.com.batterystats.viewmodel.BatteryHistoryModelView;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nguyenbinh on 14/04/2017.
 */

public class BatteryHistoryFragment extends Fragment {
    private BatteryHistoryModelView modelView;
    private BatteryHistoryDataBinding dataBinding;
    private String TAG = BatteryHistoryFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_battery_history, container, false);
        modelView = new BatteryHistoryModelView(getContext(), dataBinding);
        dataBinding.setBatHisMV(modelView);
        return dataBinding.getRoot();
    }
}