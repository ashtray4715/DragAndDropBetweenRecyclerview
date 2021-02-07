package com.ashtray.quicksettings;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

public class QSDragListener implements View.OnDragListener {
    private static final String TAG = "[QSDragListener]";

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

    }

    private void handleDragEntered(View v, DragEvent event) {

    }

    private void handleDragExited(View v, DragEvent event) {

    }

    private void handleDragDropped(View v, DragEvent event) {

    }

    private void handleDragEnded(View v, DragEvent event) {

    }

    private void handleDragLocation(View v, DragEvent event) {

    }
}
