package android.com.batterystats.viewmodel;

import android.com.batterystats.R;
import android.com.batterystats.databinding.EstimatedPowerUseDataBinding;
import android.com.batterystats.databinding.ItemBatHisFootDataBinding;
import android.com.batterystats.databinding.ItemEstimateDataBinding;
import android.com.batterystats.model.BatteryHistory;
import android.com.batterystats.model.Estimated;
import android.com.batterystats.model.StaticConfig;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nguyenbinh on 18/04/2017.
 */

public class EstimatedPowerUseModelView {
    private Context context;
    private EstimatedPowerUseDataBinding dataBinding;
    private EstimateRecycleViewAdapter adapter;

    public EstimatedPowerUseModelView(Context context, EstimatedPowerUseDataBinding dataBinding) {
        this.context = context;
        this.dataBinding = dataBinding;
        adapter = new EstimateRecycleViewAdapter(context);
        dataBinding.recycleListEstimated.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        dataBinding.recycleListEstimated.setLayoutManager(layoutManager);

    }
}

class ItemEstimateViewHolder extends RecyclerView.ViewHolder {
    private ItemEstimateDataBinding dataBinding;

    public ItemEstimateViewHolder(ItemEstimateDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


    public ItemEstimateDataBinding getDataBinding() {
        return dataBinding;
    }
}


class ItemEstimateFooterViewHolder extends RecyclerView.ViewHolder {
    private ItemBatHisFootDataBinding dataBinding;

    public ItemEstimateFooterViewHolder(ItemBatHisFootDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


    public ItemBatHisFootDataBinding getDataBinding() {
        return dataBinding;
    }
}

class EstimateRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BatteryHistory> listBatteryHistories;

    private Context context;
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 2;

    public EstimateRecycleViewAdapter(Context context) {
        this.listBatteryHistories = new ArrayList<>();
        this.context = context;
        getListBatteryHistory();
    }

    private void getListBatteryHistory() {
        File directory = new File(StaticConfig.DATA_DIRECTORY);
        if (directory.exists()) {
            for (File child : directory.listFiles()) {
                BatteryHistory batteryHistory = new BatteryHistory();
                batteryHistory.setTimeReset(child.getName());
                batteryHistory.setDescription(child.getName());
                batteryHistory.setUrl(StaticConfig.DATA_DIRECTORY + File.separator + child.getName() + File.separator + StaticConfig.HTML_FILE_DEFAULT);
                File childInfo = new File(child.getAbsolutePath() + File.separator + StaticConfig.ESTIMATED_FILE_DEFAULT);
                if (childInfo.exists()) {
                    Estimated estimated = new Estimated(context, childInfo);
                    batteryHistory.setEstimated(estimated);
                    listBatteryHistories.add(batteryHistory);
                }
            }
            if (listBatteryHistories.size() > 0) {
                Collections.sort(listBatteryHistories, new Comparator<BatteryHistory>() {
                    @Override
                    public int compare(BatteryHistory bat1, BatteryHistory bat2) {
                        return bat2.getRawTimeReset().compareTo(bat1.getRawTimeReset());
                    }
                });
                listBatteryHistories.get(0).setHead(true);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            ItemEstimateDataBinding hisDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_estimate, parent, false);
            ItemEstimateModelView modelView = new ItemEstimateModelView();
            hisDataBinding.setEtMV(modelView);
            return new ItemEstimateViewHolder(hisDataBinding);
        } else if (viewType == TYPE_FOOTER) {
            ItemBatHisFootDataBinding footDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_unknown, parent, false);
            return new ItemEstimateFooterViewHolder(footDataBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ItemEstimateDataBinding hisDataBinding = ((ItemEstimateViewHolder) holder).getDataBinding();
            hisDataBinding.setBhis(listBatteryHistories.get(position));
            hisDataBinding.setEstimate(listBatteryHistories.get(position).getEstimated());
            hisDataBinding.setDerectory(StaticConfig.DATA_DIRECTORY + File.separator + listBatteryHistories.get(position).getRawTimeReset());
            if(position == 0) {
                hisDataBinding.setTime(context.getString(R.string.time_estimate).replace("_time_start_", listBatteryHistories.get(position).getTimeReset()).replace("_time_end_", context.getString(R.string.now)));
            }else{
                hisDataBinding.setTime(context.getString(R.string.time_estimate).replace("_time_start_", listBatteryHistories.get(position).getTimeReset()).replace("_time_end_", listBatteryHistories.get(position - 1).getTimeReset()));
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public int getItemCount() {
        try {
            return listBatteryHistories.size() + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == listBatteryHistories.size()) return TYPE_FOOTER;
        else return TYPE_NORMAL;
    }
}