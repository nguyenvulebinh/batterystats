package android.com.batterystats.viewmodel;

import android.com.batterystats.model.StaticConfig;
import android.com.batterystats.view.activity.MainActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nguyenbinh on 30/04/2017.
 */

public class GetHtmlFromLog {
    private static String TAG = GetHtmlFromLog.class.getName();

    /**
     * Get and save html from server when post log
     */
    public static void postServer(final String directory, List<String> batHisContent) {
        BatterystatsService mService = ApiUtils.getSOService();
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : batHisContent) {
            stringBuilder.append(str).append("\n");
        }
        mService.getParse(stringBuilder.toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response != null && response.body() != null) {
                    try {
                        File htmlFile = new File(directory + File.separator + StaticConfig.HTML_FILE_DEFAULT);
                        FileWriter fileWriter = new FileWriter(htmlFile);
                        fileWriter.write(response.body());
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "Error");
            }
        });
    }

    /**
     * Try Show html file in chrome
     *
     * @param filename
     * @param context
     */
    public static void showHtml(String filename, Context context) {
        File htmlFile = new File(filename);
        Uri uri2 = Uri.fromFile(htmlFile);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri2);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.android.chrome", "com.google.android.apps.chrome.Main");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                intent.setPackage(null);
                context.startActivity(intent);
            } catch (Exception ignored) {
            }
        }
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