package android.com.batterystats.viewmodel;

import android.com.batterystats.R;
import android.com.batterystats.databinding.BatteryHistoryDataBinding;
import android.com.batterystats.databinding.ItemBatHisDataBinding;
import android.com.batterystats.databinding.ItemBatHisFootDataBinding;
import android.com.batterystats.model.BatteryHistory;
import android.com.batterystats.model.StaticConfig;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nguyenbinh on 18/04/2017.
 */

public class BatteryHistoryModelView {
    private BatteryHistoryRecycleViewAdapter adapter;
    private BatteryHistoryDataBinding bhDataBinding;

    public BatteryHistoryModelView(Context context, BatteryHistoryDataBinding bhDataBinding) {
        this.bhDataBinding = bhDataBinding;
        adapter = new BatteryHistoryRecycleViewAdapter(context);
        bhDataBinding.recycleListBathis.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        bhDataBinding.recycleListBathis.setLayoutManager(layoutManager);
    }
}

class ItemBatteryHistoryViewHolder extends RecyclerView.ViewHolder {
    private ItemBatHisDataBinding dataBinding;

    public ItemBatteryHistoryViewHolder(ItemBatHisDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


    public ItemBatHisDataBinding getDataBinding() {
        return dataBinding;
    }
}


class ItemBatteryHistoryFooterViewHolder extends RecyclerView.ViewHolder {
    private ItemBatHisFootDataBinding dataBinding;

    public ItemBatteryHistoryFooterViewHolder(ItemBatHisFootDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


    public ItemBatHisFootDataBinding getDataBinding() {
        return dataBinding;
    }
}

class BatteryHistoryRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<BatteryHistory> listBatteryHistories;
    private Context context;
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 2;

    public BatteryHistoryRecycleViewAdapter(Context context) {
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
                File childInfo = new File(child.getAbsolutePath() + File.separator + StaticConfig.STATS_INFO_FILE_DEFAULT);
                if (childInfo.exists()) {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(childInfo)));
                        String batCurrent = reader.readLine();
                        if (batCurrent.equals("101%")) {
                            batteryHistory.setDescription(context.getString(R.string.unknown));
                        } else {
                            batteryHistory.setDescription(context.getString(R.string.use) + " " + batCurrent);
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                listBatteryHistories.add(batteryHistory);
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
            ItemBatHisDataBinding hisDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_bathis, parent, false);
            ItemBatHisModelView modelView = new ItemBatHisModelView();
            hisDataBinding.setBhMV(modelView);
            return new ItemBatteryHistoryViewHolder(hisDataBinding);
        } else if (viewType == TYPE_FOOTER) {
            ItemBatHisFootDataBinding footDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_unknown, parent, false);
            return new ItemBatteryHistoryFooterViewHolder(footDataBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ItemBatHisDataBinding hisDataBinding = ((ItemBatteryHistoryViewHolder) holder).getDataBinding();
            hisDataBinding.setBathis(listBatteryHistories.get(position));
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