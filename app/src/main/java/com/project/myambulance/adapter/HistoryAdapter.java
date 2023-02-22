package com.project.myambulance.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.myambulance.databinding.ItemListHistoryUserBinding;
import com.project.myambulance.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<History> list = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<History> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListHistoryUserBinding itemListHistoryUserBinding = ItemListHistoryUserBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemListHistoryUserBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        History data = list.get(position);
        holder.itemListHistoryUserBinding.tvNama2.setText(data.getUsername());
        holder.itemListHistoryUserBinding.tvPlat2.setText(data.getNo_plat());
        holder.itemListHistoryUserBinding.tvAlamat2.setText(data.getAlamat());
        holder.itemListHistoryUserBinding.tvWaktu.setText(data.getWaktu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemListHistoryUserBinding itemListHistoryUserBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemListHistoryUserBinding = ItemListHistoryUserBinding.bind(itemView.getRootView());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
