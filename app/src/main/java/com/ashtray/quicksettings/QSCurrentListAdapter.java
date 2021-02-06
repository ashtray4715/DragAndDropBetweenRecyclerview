package com.ashtray.quicksettings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class QSCurrentListAdapter extends RecyclerView.Adapter<QSCurrentListAdapter.ViewHolder> {

    private ArrayList<QSItem> items;
    private final Context context;

    public QSCurrentListAdapter(Context context, ArrayList<QSItem> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qs_current_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position).imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItemsList(ArrayList<QSItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.item_image);
        }
    }
}
