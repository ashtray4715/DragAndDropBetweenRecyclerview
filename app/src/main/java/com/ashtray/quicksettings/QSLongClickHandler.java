package com.ashtray.quicksettings;

import android.content.ClipData;
import android.os.Build;
import android.view.View;

public class QSLongClickHandler implements View.OnLongClickListener {
    @Override
    public boolean onLongClick(View v) {
        ClipData clipData = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(clipData, shadowBuilder, v, 0);
        }else {
            v.startDrag(clipData, shadowBuilder, v, 0);
        }
        v.setVisibility(View.INVISIBLE);
        return true;
    }
}