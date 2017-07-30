package com.kbm.openweather.ui.forecast.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kbm.openweather.R;
import com.kbm.openweather.models.ForecastItem;
import com.kbm.openweather.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bassem on 7/30/2017.
 */

public class ForecastItemAdapter extends RecyclerView.Adapter<ForecastItemAdapter.ViewHolder> {
    private List<ForecastItem> mDataset;

    public ForecastItemAdapter(List<ForecastItem> items) {
        this.mDataset = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_day_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ForecastItem item = mDataset.get(position);
        holder.degreeTextView.setText(item.getMainWeatherInfo().getTemp());
        if (item.getWeatherItems() != null && item.getWeatherItems().size() > 0) {
            ImageLoader.loadImage(item.getWeatherItems().get(0).getIcon(), holder.iconImageView);
            holder.descriptionTextView.setText(item.getWeatherItems().get(0).getDescription());
        } else {
            ImageLoader.loadImage(R.drawable.placeholder, holder.iconImageView);
            holder.descriptionTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_icon)
        ImageView iconImageView;
        @BindView(R.id.txt_description)
        TextView descriptionTextView;
        @BindView(R.id.txt_degree)
        TextView degreeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
