package com.ashtray.quicksettings;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class QSDragAndDropHandler implements View.OnDragListener {
    private static final String TAG = "[QSDragListener]";

    private boolean dragStartFromCurrentItemList = false;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Log.d(TAG, "onDrag: event -> " + event.getAction());
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                handleDragStarted(v, event);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                handleDragEntered(v, event);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                handleDragExited(v, event);
                break;
            case DragEvent.ACTION_DROP:
                handleDragDropped(v, event);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                handleDragEnded(v, event);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                handleDragLocation(v, event);
            default:
                break;
        }
        return true;
    }

    private void handleDragStarted(View v, DragEvent event) {
        Log.d(TAG, "handleDragStarted: " + event.getX() + ", " + event.getY());

        if(event.getClipDescription().getLabel().toString().equals(QSConstant.QS_LABEL_CURRENT_ITEM)) {
            dragStartFromCurrentItemList = true;
        } else if(event.getClipDescription().getLabel().toString().equals(QSConstant.QS_LABEL_AVAILABLE_ITEM)) {
            dragStartFromCurrentItemList = false;
        }

        RecyclerView fromRecyclerView = (RecyclerView) v;
        View selectedView = (View) event.getLocalState();
        Log.d(TAG, "handleDragStarted: -> " + fromRecyclerView.getChildAdapterPosition(selectedView));
    }

    private void handleDragEntered(View v, DragEvent event) {
        Log.d(TAG, "handleDragEntered: " + event.getX() + ", " + event.getY());
    }

    private void handleDragExited(View v, DragEvent event) {
        Log.d(TAG, "handleDragExited: " + event.getX() + ", " + event.getY());
    }

    private void handleDragDropped(View v, DragEvent event) {
        Log.d(TAG, "handleDragDropped: " + event.getX() + ", " + event.getY());
    }

    private void handleDragEnded(View v, DragEvent event) {
        Log.d(TAG, "handleDragEnded: " + event.getX() + ", " + event.getY());
    }

    private void handleDragLocation(View v, DragEvent event) {
        Log.d(TAG, "handleDragLocation: " + event.getX() + ", " + event.getY());
    }
}
