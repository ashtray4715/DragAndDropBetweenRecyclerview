package com.ashtray.quicksettings;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class QSDragAndDropHandler implements View.OnDragListener {
    private static final String TAG = "[QSDragListener]";

    private static final int DRAG_ENTERED_INTO_NO_WHERE = -1;
    private static final int DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW = 1;
    private static final int DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW = 0;
    private static final int RECYCLER_VIEW_ID_NOT_FOUND = -1;
    private static final int DUMMY_ITEM_NOT_INSERTED = -1;

    private int currentItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;
    private int availableItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;

    private boolean dragStartedFromCurrentItemList = false;
    private boolean dragEnteredCallFirstTime = false;

    private int dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;

    private QSItem draggableItem;
    private int draggableItemPosition;

    /**
     * -1 = doesn't enter anywhere (DRAG_ENTERED_INTO_NO_WHERE)
     * 0 = enter into available list (DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW)
     * 1 = enter into current list (DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW)
     */
    private int dragEnteredIntoRecyclerView = 0;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        //Log.d(TAG, "onDrag: event -> " + event.getAction());
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
        currentItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;
        availableItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;
        dragEnteredCallFirstTime = true;
        dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_NO_WHERE;
        dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;

        if(event.getClipDescription().getLabel().toString().equals(QSConstant.QS_LABEL_CURRENT_ITEM)) {
            dragStartedFromCurrentItemList = true;
        } else if(event.getClipDescription().getLabel().toString().equals(QSConstant.QS_LABEL_AVAILABLE_ITEM)) {
            dragStartedFromCurrentItemList = false;
        }
        Log.d(TAG, "handleDragStarted: from " + (dragStartedFromCurrentItemList ? "Current list" : "Available list"));
    }

    private void handleDragEntered(View v, DragEvent event) {
        RecyclerView recyclerView = (RecyclerView) v;
        if(dragEnteredCallFirstTime) {
            dragEnteredCallFirstTime = false;
            if(dragStartedFromCurrentItemList) {
                currentItemRecyclerViewId = recyclerView.getId();
                dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW;
            } else {
                availableItemRecyclerViewId = recyclerView.getId();
                dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW;
            }
        } else {
            if(recyclerView.getId() == currentItemRecyclerViewId) {
                dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW;
            } else if(recyclerView.getId() == availableItemRecyclerViewId){
                dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW;
            } else if(currentItemRecyclerViewId == RECYCLER_VIEW_ID_NOT_FOUND) {
                currentItemRecyclerViewId = recyclerView.getId();
                dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW;
            } else {
                availableItemRecyclerViewId = recyclerView.getId();
                dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW;
            }
        }
        Log.d(TAG, "handleDragEntered: into " + (dragEnteredIntoRecyclerView == DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW ? "Available list" : "Current List"));
    }

    private void handleDragExited(View v, DragEvent event) {
        Log.d(TAG, "handleDragExited: into " + (dragEnteredIntoRecyclerView == DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW ? "Available list" : "Current List"));
        if(dragEnteredIntoRecyclerView == DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW) {
            if(dummyItemInsertedPosition != DUMMY_ITEM_NOT_INSERTED) {
                if(dragStartedFromCurrentItemList) {
                    RecyclerView recyclerView = (RecyclerView) v;
                    QSCurrentListAdapter adapter = (QSCurrentListAdapter) recyclerView.getAdapter();
                    adapter.handleRemoveItem(dummyItemInsertedPosition);
                    adapter.handleAddItem(draggableItemPosition);
                    dummyItemInsertedPosition = draggableItemPosition;
                } else {

                }
            }
        }
        dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_NO_WHERE;
    }

    private void handleDragDropped(View v, DragEvent event) {
        if(dragEnteredIntoRecyclerView != DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW) {
            return;
        }
        if(dummyItemInsertedPosition == DUMMY_ITEM_NOT_INSERTED) {
            return;
        }

        if(dragStartedFromCurrentItemList) {
            RecyclerView recyclerView = (RecyclerView) v;
            QSCurrentListAdapter adapter = (QSCurrentListAdapter) recyclerView.getAdapter();
            adapter.handleReplaceItem(dummyItemInsertedPosition, draggableItem);
            dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
        } else {

        }
    }

    private void handleDragEnded(View v, DragEvent event) {
        Log.d(TAG, "handleDragEnded: called");
        if(dummyItemInsertedPosition == DUMMY_ITEM_NOT_INSERTED) {
            return;
        }
        if(dragStartedFromCurrentItemList) {
            RecyclerView recyclerView = (RecyclerView) v;
            QSCurrentListAdapter adapter = (QSCurrentListAdapter) recyclerView.getAdapter();
            adapter.handleReplaceItem(dummyItemInsertedPosition, draggableItem);
            dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
        } else {

        }
    }

    private void handleDragLocation(View v, DragEvent event) {
        if(dragEnteredIntoRecyclerView != DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW) {
            return;
        }
        if(dragStartedFromCurrentItemList) {
            handleDragLocationCurrentToCurrent(v, event);
        } else {
            handleDragLocationAvailableToCurrent(v, event);
        }
    }

    private void handleDragLocationCurrentToCurrent(View v, DragEvent event) {
        RecyclerView recyclerView = (RecyclerView) v;
        View selectedView = (View) event.getLocalState();

        View onTopOfView = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if(onTopOfView == null) {
            return;
        }

        int currentPosition = recyclerView.getChildAdapterPosition(onTopOfView);
        if(currentPosition < 3 || dummyItemInsertedPosition == currentPosition) {
            // -1 for position invalid, (0,1,2) for header so less than 3 is invalid
            return;
        }

        if(dummyItemInsertedPosition == DUMMY_ITEM_NOT_INSERTED) {
            QSCurrentListAdapter adapter = (QSCurrentListAdapter) recyclerView.getAdapter();
            draggableItem = adapter.getItemFromPosition(currentPosition);
            draggableItemPosition = currentPosition;
            adapter.handleRemoveItem(currentPosition);
            adapter.handleAddItem(currentPosition);
            dummyItemInsertedPosition = currentPosition;
        } else if(dummyItemInsertedPosition != currentPosition) {
            QSCurrentListAdapter adapter = (QSCurrentListAdapter) recyclerView.getAdapter();
            adapter.handleRemoveItem(dummyItemInsertedPosition);
            adapter.handleAddItem(currentPosition);
            dummyItemInsertedPosition = currentPosition;
        }

    }

    private void handleDragLocationAvailableToCurrent(View v, DragEvent event) {

    }
}
