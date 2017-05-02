package android.com.batterystats.view.activity;

import android.com.batterystats.databinding.ViewLogDataBingding;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.com.batterystats.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ViewLogDetail extends AppCompatActivity {
    ViewLogDataBingding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.log_detail_activity);
        String fileLogName = getIntent().getStringExtra("fileLogName");
        File fileLog = new File(fileLogName);
        try {
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileLog)));
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                content.append(line).append("</br>");
                line = reader.readLine();
            }
            dataBinding.webViewLog.setInitialScale(300);
            dataBinding.webViewLog.getSettings().setBuiltInZoomControls(true);
            dataBinding.webViewLog.getSettings().setDisplayZoomControls(false);
            dataBinding.webViewLog.getSettings().setLoadWithOverviewMode(true);
            dataBinding.webViewLog.getSettings().setUseWideViewPort(true);
            dataBinding.webViewLog.loadData("<html><head></head><body><pre>"+content.toString()+"</pre></body></html>", "text/html", "utf-8");
        } catch (Exception e) {
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
