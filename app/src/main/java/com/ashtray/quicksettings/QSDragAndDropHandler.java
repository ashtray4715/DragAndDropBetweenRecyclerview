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
    private static final int DRAGGABLE_ITEM_POSITION_INVALID = -1;

    private int currentItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;
    private int availableItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;

    private boolean dragStartedFromCurrentItemList = false;
    private boolean dragEnteredCallFirstTime = false;

    private int dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;

    private QSItem draggableItem;
    private int draggableItemPosition;

    private boolean dragCanStart = true;
    private boolean dragCanEnd = false;

    private final QSCurrentListAdapter currentListAdapter;
    private final QSAvailableListAdapter availableListAdapter;

    private CallBacks callBacks;

    public interface CallBacks {
        /**
         * This method will invoked when the dragging will be started
         */
        void onStartDragging();

        /**
         * This method will be invoked when the dragging will be stopped
         */
        void onStopDragging();
    }

    public QSDragAndDropHandler(QSCurrentListAdapter currentListAdapter, QSAvailableListAdapter availableListAdapter) {
        this.currentListAdapter = currentListAdapter;
        this.availableListAdapter = availableListAdapter;
    }

    public void setCallBacks(CallBacks callBacks) {
        this.callBacks = callBacks;
    }

    /**
     * -1 = doesn't enter anywhere (DRAG_ENTERED_INTO_NO_WHERE)
     * 0 = enter into available list (DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW)
     * 1 = enter into current list (DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW)
     */
    private int dragEnteredIntoRecyclerView = 0;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                handleDragStarted(event);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                handleDragEntered(v);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                handleDragExited();
                break;
            case DragEvent.ACTION_DROP:
                handleDragDropped();
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                handleDragEnded();
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                handleDragLocation(v, event);
            default:
                break;
        }
        return true;
    }

    private void handleDragStarted(DragEvent event) {
        if(!dragCanStart) {
            return;
        }
        dragCanStart = false;
        dragCanEnd = true;
        currentItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;
        availableItemRecyclerViewId = RECYCLER_VIEW_ID_NOT_FOUND;
        dragEnteredCallFirstTime = true;
        dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_NO_WHERE;
        dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
        draggableItemPosition = DRAGGABLE_ITEM_POSITION_INVALID;

        if(event.getClipDescription().getLabel().toString().equals(QSConstant.QS_LABEL_CURRENT_ITEM)) {
            dragStartedFromCurrentItemList = true;
        } else if(event.getClipDescription().getLabel().toString().equals(QSConstant.QS_LABEL_AVAILABLE_ITEM)) {
            dragStartedFromCurrentItemList = false;
        }
        Log.d(TAG, "handleDragStarted: from " + (dragStartedFromCurrentItemList ? "Current list" : "Available list"));

        if(this.callBacks != null) {
            this.callBacks.onStartDragging();
        }
    }

    private void handleDragEntered(View v) {
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

    private void handleDragExited() {
        Log.d(TAG, "handleDragExited: from " + (dragEnteredIntoRecyclerView == DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW ? "Available list" : "Current List"));
        if(dragEnteredIntoRecyclerView == DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW) {
            if(dummyItemInsertedPosition != DUMMY_ITEM_NOT_INSERTED) {
                if(dragStartedFromCurrentItemList) {
                    Log.d(TAG, "handleDragExited: removing dummy item [" + dummyItemInsertedPosition + "], adding new dummy at draggable position " + draggableItemPosition + "]");
                    currentListAdapter.handleRemoveItem(dummyItemInsertedPosition);
                    currentListAdapter.handleAddDummyItem(draggableItemPosition);
                    dummyItemInsertedPosition = draggableItemPosition;
                } else {
                    Log.d(TAG, "handleDragExited: removing dummy item [" + dummyItemInsertedPosition + "]");
                    currentListAdapter.handleRemoveItem(dummyItemInsertedPosition);
                    dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
                }
            }
        }
        dragEnteredIntoRecyclerView = DRAG_ENTERED_INTO_NO_WHERE;
    }

    private void handleDragDropped() {
        if(dragEnteredIntoRecyclerView != DRAG_ENTERED_INTO_CURRENT_ITEM_RECYCLE_VIEW) {
            return;
        }
        if(dummyItemInsertedPosition == DUMMY_ITEM_NOT_INSERTED) {
            return;
        }

        if(dragStartedFromCurrentItemList) {
            Log.d(TAG, "handleDragDropped: position = " + dummyItemInsertedPosition);
            currentListAdapter.handleReplaceItem(dummyItemInsertedPosition, draggableItem);
            dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
        } else {
            Log.d(TAG, "handleDragDropped: position = " + dummyItemInsertedPosition + ", removed = " + draggableItemPosition);
            currentListAdapter.handleReplaceItem(dummyItemInsertedPosition, draggableItem);
            dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
            availableListAdapter.handleRemoveItem(draggableItemPosition);
            draggableItemPosition = DRAGGABLE_ITEM_POSITION_INVALID;
        }
    }

    private void handleDragEnded() {
        if(!dragCanEnd) {
            return;
        }
        dragCanEnd = false;
        dragCanStart = true;
        if(dragStartedFromCurrentItemList) {
            if(dummyItemInsertedPosition != DUMMY_ITEM_NOT_INSERTED) {
                Log.d(TAG, "handleDragEnded: have dummy position at " + dummyItemInsertedPosition);
                currentListAdapter.handleReplaceItem(dummyItemInsertedPosition, draggableItem);
                dummyItemInsertedPosition = DUMMY_ITEM_NOT_INSERTED;
            }
        } else {
            if(draggableItemPosition != DRAGGABLE_ITEM_POSITION_INVALID) {
                Log.d(TAG, "handleDragEnded: have draggable item position at " + draggableItemPosition);
                availableListAdapter.handleReplaceItem(draggableItemPosition, draggableItem);
            }
        }

        if(this.callBacks != null) {
            this.callBacks.onStopDragging();
        }
    }

    private void handleDragLocation(View v, DragEvent event) {
        if(dragEnteredIntoRecyclerView == DRAG_ENTERED_INTO_AVAILABLE_ITEM_RECYCLE_VIEW) {
            if(draggableItemPosition == DRAGGABLE_ITEM_POSITION_INVALID) {
                if(!dragStartedFromCurrentItemList) {
                    RecyclerView recyclerView = (RecyclerView) v;
                    View onTopOfView = recyclerView.findChildViewUnder(event.getX(), event.getY());
                    if(onTopOfView == null) {
                        return;
                    }

                    int currentPosition = recyclerView.getChildAdapterPosition(onTopOfView);
                    if(currentPosition != -1) {
                        Log.d(TAG, "handleDragLocation: initializing draggable item from available list adapter [" + currentPosition + "]");
                        draggableItem = availableListAdapter.getItemFromPosition(currentPosition).getNewCopy();
                        draggableItemPosition = currentPosition;
                        availableListAdapter.handleReplaceWithDummyItem(draggableItemPosition);
                    }
                    return;
                }
            }
        }
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
            draggableItem = currentListAdapter.getItemFromPosition(currentPosition).getNewCopy();
            draggableItemPosition = currentPosition;
            Log.d(TAG, "handleDragLocationC2C: need to insert dummy [pos = " + currentPosition + ", dragPos = " + draggableItemPosition + "]");
            currentListAdapter.handleRemoveItem(currentPosition);
            currentListAdapter.handleAddDummyItem(currentPosition);
            dummyItemInsertedPosition = currentPosition;
            return;
        }

        //when dummy already inserted and
        //dummy not inserted in current position
        //we have a check above already
        Log.d(TAG, "handleDragLocationC2C: dummy item position update " + dummyItemInsertedPosition + " -> " + currentPosition);
        currentListAdapter.handleRemoveItem(dummyItemInsertedPosition);
        currentListAdapter.handleAddDummyItem(currentPosition);
        dummyItemInsertedPosition = currentPosition;
    }

    private void handleDragLocationAvailableToCurrent(View v, DragEvent event) {
        RecyclerView recyclerView = (RecyclerView) v;

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
            Log.d(TAG, "handleDragLocationA2C: need to insert dummy [pos = " + currentPosition + ", dragPos = " + draggableItemPosition + "]");
            currentListAdapter.handleAddDummyItem(currentPosition);
            dummyItemInsertedPosition = currentPosition;
        }

        //when dummy already inserted and
        //dummy not inserted in current position
        //we have a check above already
        Log.d(TAG, "handleDragLocationA2C: dummy item position update " + dummyItemInsertedPosition + " -> " + currentPosition);
        currentListAdapter.handleRemoveItem(dummyItemInsertedPosition);
        currentListAdapter.handleAddDummyItem(currentPosition);
        dummyItemInsertedPosition = currentPosition;
    }
}
