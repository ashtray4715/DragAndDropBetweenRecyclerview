package com.ashtray.quicksettings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class QSAvailableListAdapter extends RecyclerView.Adapter<QSAvailableListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<QSItem> items;

    public QSAvailableListAdapter(Context context, ArrayList<QSItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.qs_available_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.updateViewOnBind(context, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.item_image);
            this.textView = itemView.findViewById(R.id.item_text);
        }

        public void updateViewOnBind(Context context, int position) {
            Glide.with(context)
                    .load(items.get(position).imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                    .into(this.imageView);
            this.textView.setText(items.get(position).name);
        }
    }
}
