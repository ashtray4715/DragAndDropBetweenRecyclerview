package com.ashtray.quicksettings;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.view.View;

public class QSLongClickHandler implements View.OnLongClickListener {

    private final String label;

    public QSLongClickHandler(String label) {
        this.label = label;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item clipItem = new ClipData.Item(label);
        ClipData clipData = new ClipData(label, new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, clipItem);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(clipData, shadowBuilder, v, 0);
        }else {
            v.startDrag(clipData, shadowBuilder, v, 0);
        }
        //v.setVisibility(View.INVISIBLE);
        return true;
    }
}