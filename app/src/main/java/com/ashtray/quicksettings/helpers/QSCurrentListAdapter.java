package com.ashtray.quicksettings.helpers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashtray.quicksettings.entities.QSConstant;
import com.ashtray.quicksettings.entities.QSItem;
import com.ashtray.quicksettings.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;

public class QSCurrentListAdapter extends RecyclerView.Adapter<QSCurrentListAdapter.MyViewHolder> {

    private static final String TAG = "[QSCurrentListAdapter]";

    private final ArrayList<QSItem> items;
    private final Context context;

    public QSCurrentListAdapter(Context context, ArrayList<QSItem> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == QSConstant.QSItem_TYPE_HEADER) {
            View v = new View(context);
            return new HeaderViewHolder(v);
        } else if(viewType == QSConstant.QSItem_TYPE_DUMMY) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qs_current_item_dummy, parent, false);
            return new DummyViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.qs_current_item, parent, false);
            return new ItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.updateViewOnBind(context, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type;
    }

    public boolean handleMoveItem(RecyclerView.ViewHolder source, RecyclerView.ViewHolder destination) {
        if (source.getItemViewType() != destination.getItemViewType()) {
            return false;
        }
        int fromPosition = source.getAdapterPosition(), toPosition = destination.getAdapterPosition();
        Log.d(TAG, "onMove: "+ fromPosition + " -> " + toPosition);
        if(fromPosition < toPosition) {
            for(int i=fromPosition; i<toPosition; i++) {
                Collections.swap(items, i, i+1);
            }
        } else {
            for(int i=fromPosition; i>toPosition; i--) {
                Collections.swap(items, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        printTheNameOneByOne();
        return true;
    }

    public void handleAddDummyItem(int atPosition) {
        QSItem item = new QSItem();
        item.type = QSConstant.QSItem_TYPE_DUMMY;
        item.name = "D";
        if(items.size() > atPosition) {
            items.add(atPosition, item);
        } else {
            items.add(item);
        }
        notifyDataSetChanged();
    }

    public void handleAddItem(int draggableItemPosition, QSItem draggableItem) {
        if(items.size() > draggableItemPosition) {
            items.add(draggableItemPosition, draggableItem);
        } else {
            items.add(draggableItem);
        }
        notifyDataSetChanged();
    }

    public void handleRemoveItem(int atPosition) {
        if(items.size() > atPosition) {
            items.remove(atPosition);
            notifyDataSetChanged();
        }
    }

    public void handleReplaceItem(int position, QSItem item) {
        if(items.size() > position) {
            items.get(position).type = item.type;
            items.get(position).name = item.name;
            items.get(position).imageUrl = item.imageUrl;
            notifyItemChanged(position);
        }
    }

    public void printTheNameOneByOne() {
        StringBuilder sb = new StringBuilder();
        for(QSItem item: items) {
            sb.append(item.name).append(", ");
        }
        Log.d(TAG, "printTheNameOneByOne: " + sb.toString());
    }

    public QSItem getItemFromPosition(int position) {
        return items.get(position);
    }

    class ItemViewHolder extends MyViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.item_image);
            this.textView = itemView.findViewById(R.id.item_text);

            RelativeLayout relativeLayout = this.itemView.findViewById(R.id.qs_current_item_root_layout);
            relativeLayout.setOnLongClickListener(new QSLongClickHandler(QSConstant.QS_LABEL_CURRENT_ITEM));
        }

        public void updateViewOnBind(Context context, int position) {
            Glide.with(context)
                    .load(items.get(position).imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                    .into(this.imageView);
            this.textView.setText(String.valueOf(items.get(position).name.charAt(0)));
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

    static class HeaderViewHolder extends MyViewHolder {
        private final View itemView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        @Override
        public void updateViewOnBind(Context context, int position) {
            int width = 50, height = (position == 0) ? 90 : (position == 1) ? 8 : 91;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
            this.itemView.setLayoutParams(layoutParams);
            this.itemView.setBackgroundColor(Color.RED);
        }
    }

    static class DummyViewHolder extends MyViewHolder {

        public DummyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void updateViewOnBind(Context context, int position) {

        }
    }

    abstract static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void updateViewOnBind(Context context, int position);
    }
}
