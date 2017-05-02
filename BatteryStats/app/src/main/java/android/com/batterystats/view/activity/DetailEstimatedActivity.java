package android.com.batterystats.view.activity;

import android.com.batterystats.R;
import android.com.batterystats.databinding.DetailEstimatedDataBinding;
import android.com.batterystats.model.Estimated;
import android.com.batterystats.model.StaticConfig;
import android.com.batterystats.viewmodel.DetailEstimatedModelView;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nguyenbinh on 01/05/2017.
 */

public class DetailEstimatedActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private DetailEstimatedDataBinding dataBinding;
    private Estimated estimatedData;
    private ArrayList<ApplicationInfo> listApplicationInfos;
    private DetailEstimatedModelView modelView;
    private ArrayList<PieEntry> entries;
    private String directory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.detail_estimated_activity);
        modelView = new DetailEstimatedModelView();
        dataBinding.setDeMV(modelView);
        Intent intent = getIntent();
        estimatedData = (Estimated) intent.getSerializableExtra("dataEstimate");
        String time = intent.getStringExtra("time");
        directory = intent.getStringExtra("directory");
        if (estimatedData == null || estimatedData.getListEntity().size() == 0) {
            Toast.makeText(this, getString(R.string.unknown), Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }
        modelView.setTimeEstimate(time);
        listApplicationInfos = new ArrayList<>();
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");


        dataBinding.piechart.setUsePercentValues(true);
        dataBinding.piechart.getDescription().setEnabled(false);
        dataBinding.piechart.setExtraOffsets(5, 10, 5, 5);

        dataBinding.piechart.setDragDecelerationFrictionCoef(0.95f);

        dataBinding.piechart.setCenterTextTypeface(mTfLight);
        dataBinding.piechart.setCenterText(generateCenterSpannableText());

        dataBinding.piechart.setDrawHoleEnabled(true);
        dataBinding.piechart.setHoleColor(Color.WHITE);

        dataBinding.piechart.setTransparentCircleColor(Color.WHITE);
        dataBinding.piechart.setTransparentCircleAlpha(110);

        dataBinding.piechart.setHoleRadius(58f);
        dataBinding.piechart.setTransparentCircleRadius(61f);

        dataBinding.piechart.setDrawCenterText(true);

        dataBinding.piechart.setRotationAngle(0);
        // enable rotation of the chart by touch
        dataBinding.piechart.setRotationEnabled(true);
        dataBinding.piechart.setHighlightPerTapEnabled(true);

        // dataBinding.piechart.setUnit(" €");
        // dataBinding.piechart.setDrawUnitsInChart(true);

        // add a selection listener
        dataBinding.piechart.setOnChartValueSelectedListener(this);

        setData(10);

        dataBinding.piechart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString(getString(R.string.name_chart));
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 7, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 7, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 7, s.length(), 0);
        return s;
    }

    private void setData(int count) {
        PackageManager packageManager = getPackageManager();
        entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            try {
                listApplicationInfos.add(packageManager.getApplicationInfo(estimatedData.getListEntity().get(i).name, PackageManager.GET_META_DATA));
            } catch (PackageManager.NameNotFoundException e) {
                listApplicationInfos.add(null);
                e.printStackTrace();
            }
            try {
                entries.add(new PieEntry(estimatedData.getListEntity().get(i).use,
                        (listApplicationInfos.get(i) == null) ? estimatedData.getListEntity().get(i).name : packageManager.getApplicationLabel(listApplicationInfos.get(i)).toString(),
                        null));
            } catch (Exception e) {
                entries.add(new PieEntry(estimatedData.getListEntity().get(i).use,
                        estimatedData.getListEntity().get(i).name,
                        null));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        dataBinding.piechart.setData(data);

        // undo all highlights
        dataBinding.piechart.highlightValues(null);

        dataBinding.piechart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        ApplicationInfo applicationInfo = listApplicationInfos.get(entries.indexOf(e));
        if (applicationInfo != null) {
            dataBinding.iconEntity.setImageDrawable(getPackageManager().getApplicationIcon(applicationInfo));
            dataBinding.setFileLog(directory + File.separator + applicationInfo.packageName);
        } else {
            if(StaticConfig.MAP_ICON.get(((PieEntry) e).getLabel().toLowerCase()) != null) {
                dataBinding.iconEntity.setImageDrawable(getDrawable(StaticConfig.MAP_ICON.get(((PieEntry) e).getLabel().toLowerCase())));
            }else {
                dataBinding.iconEntity.setImageDrawable(getDrawable(R.drawable.ic_android));
            }
            dataBinding.setFileLog(directory + File.separator + ((PieEntry) e).getLabel());
        }
        modelView.setEntityName(((PieEntry) e).getLabel());
        modelView.setUseEstimated(getString(R.string.use) + " :" + ((PieEntry) e).getValue() + " mAh");
        modelView.setShowChoose(true);
    }

    @Override
    public void onNothingSelected() {
        modelView.setShowChoose(false);
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
