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
    private BatterystatsService mService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_battery_history, container, false);
        modelView = new BatteryHistoryModelView();
        dataBinding.setBatHisMV(modelView);


//        WebView webview = dataBinding.helpWebview;
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                dataBinding.progress.setProgress(progress);
//                if (progress == 100) {
//                    dataBinding.progress.setVisibility(View.GONE);
//
//                } else {
//                    dataBinding.progress.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        webview.setWebViewClient(new WebViewClient());
//        StringBuilder testHTML = new StringBuilder();
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(BatteryHistoryFragment.this.getContext().getAssets().open("test.html")));
//
//            // do reading, usually loop until end of file reading
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                testHTML.append(mLine).append("\n");
//            }
//        } catch (IOException e) {
//            //log the exception
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    //log the exception
//                }
//            }
//        }

//        webview.loadData(testHTML.toString(), "text/html", "UTF-8");

        postServer();
        return dataBinding.getRoot();
    }

    private void postServer() {
        mService = ApiUtils.getSOService();
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : MainActivity.batHisContent) {
            stringBuilder.append(str).append("\n");
        }
        mService.getParse(stringBuilder.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                StringBuilder testHTML = new StringBuilder();
//                BufferedReader reader = null;
//                try {
//                    reader = new BufferedReader(
//                            new InputStreamReader(BatteryHistoryFragment.this.getContext().getAssets().open("test.html")));
//
//                    // do reading, usually loop until end of file reading
//                    String mLine;
//                    while ((mLine = reader.readLine()) != null) {
//                        testHTML.append(mLine).append("\n");
//                    }
//                } catch (IOException e) {
//                    //log the exception
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            //log the exception
//                        }
//                    }
//                }

                try {
                    File htmlFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + "testhtml" + "/index.html");
                    FileWriter fileWriter = new FileWriter(htmlFile);
                    fileWriter.write(response.body());
                    fileWriter.flush();
                    fileWriter.close();
                    Uri uri2 = Uri.fromFile(htmlFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri2);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClassName("com.android.chrome", "com.google.android.apps.chrome.Main");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        try {
                            intent.setPackage(null);
                            startActivity(intent);
                        } catch (Exception ignored) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                webView.loadData(testHTML.toString(), "text/html", "UTF-8");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Error");
            }
        });

    }
}

interface BatterystatsService {
    @POST("/")
    Call<String> getParse(@Body String body);
}

class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

class ApiUtils {

    public static final String BASE_URL = "https://android-batterystats.appspot.com";

    public static BatterystatsService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(BatterystatsService.class);
    }
}