package com.ashtray.quicksettings;

import android.content.Context;
import android.util.Log;
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

public class QSCurrentListAdapter extends RecyclerView.Adapter<QSCurrentListAdapter.ViewHolder> {

    private static final String TAG = "[QSCurrentListAdapter]";

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
        holder.updateViewOnBind(context, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItemsList(ArrayList<QSItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.item_image);
            this.textView = itemView.findViewById(R.id.item_text);

            this.imageView.setOnClickListener(v -> deleteItem());
        }

        public void updateViewOnBind(Context context, int position) {
            Glide.with(context)
                    .load(items.get(position).imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                    .into(this.imageView);
            this.textView.setText(items.get(position).name);
        }

        public void deleteItem() {
            String name = textView.getText().toString();
            int position = -1;
            for(int i=0;i<items.size();i++) {
                if(items.get(i).name.equals(name)) {
                    position = i;
                    break;
                }
            }
            Log.d(TAG, "deleteItem: position = " + position);
            if(position != -1) {
                items.remove(position);
                notifyDataSetChanged();
            }
        }
    }
}
