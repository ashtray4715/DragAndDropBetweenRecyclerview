package com.ashtray.quicksettings.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashtray.quicksettings.entities.QSConstant;
import com.ashtray.quicksettings.entities.QSItem;
import com.ashtray.quicksettings.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class QSAvailableListAdapter extends RecyclerView.Adapter<QSAvailableListAdapter.BaseViewHolder> {

    private static final String TAG = "[QSAvailableAdapter]";

    private final Context context;
    private final ArrayList<QSItem> items;

    public QSAvailableListAdapter(Context context, ArrayList<QSItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == QSConstant.QSItem_TYPE_DUMMY) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.qs_available_item_dummy, parent, false);
            return new DummyViewHolder(v);
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.qs_available_item, parent, false);
            return new ItemViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
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

    public void handleReplaceWithDummyItem(int atPosition) {
        if(items.size() > atPosition) {
            items.get(atPosition).type = QSConstant.QSItem_TYPE_DUMMY;
            items.get(atPosition).name = QSConstant.QSItem_NAME_DUMMY;
            items.get(atPosition).imageUrl = QSConstant.QSItem_IMAGE_URL_DUMMY;
            notifyItemChanged(atPosition);
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

    public void handleRemoveItem(int position) {
        if(items.size() > position) {
            items.remove(position);
            notifyDataSetChanged();
        }
    }

    public void handleAddItemToTheLastPosition(QSItem item) {
        Log.d(TAG, "handleAddItemToTheLastPosition: "+ items.size() + ", " + item);
        items.add(item);
        notifyDataSetChanged();
    }

    public QSItem getItemFromPosition(int position) {
        return items.get(position);
    }

    public ArrayList<QSItem> getUpdatedItemList() {
        return items;
    }

    class ItemViewHolder extends BaseViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.item_image);
            this.textView = itemView.findViewById(R.id.item_text);

            //RelativeLayout rootLayout = itemView.findViewById(R.id.qs_available_item_root_layout);
            this.imageView.setOnLongClickListener(new QSLongClickHandler(QSConstant.QS_LABEL_AVAILABLE_ITEM));
        }

        public void updateViewOnBind(Context context, int position) {
            Glide.with(context)
                    .load(items.get(position).imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                    .into(this.imageView);
            this.textView.setText(items.get(position).name);
        }
    }

    static class DummyViewHolder extends BaseViewHolder {
        public DummyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void updateViewOnBind(Context context, int position) {

        }
    }

    abstract static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract public void updateViewOnBind(Context context, int position);
    }
}
