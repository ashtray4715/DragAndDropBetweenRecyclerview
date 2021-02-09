package com.ashtray.quicksettings.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ashtray.quicksettings.entities.QSItem;
import com.ashtray.quicksettings.helpers.QSAvailableListAdapter;
import com.ashtray.quicksettings.helpers.QSCurrentListAdapter;
import com.ashtray.quicksettings.helpers.QSDragAndDropHandler;
import com.ashtray.quicksettings.R;
import com.ashtray.quicksettings.databinding.QsMainFragmentBinding;

import java.util.ArrayList;

public class QSMainFragment extends Fragment {

    private final String TAG = "[QSMainFragment]";

    private QsMainFragmentBinding binding;
    private QSViewModel viewModel;

    private QSCurrentListAdapter currentListAdapter;
    private QSAvailableListAdapter availableListAdapter;
    private QSDragAndDropHandler dragAndDropHandler;

    private boolean editModeStarted = false;

    private final QSDragAndDropHandler.CallBacks dragAndDropCallBack = new QSDragAndDropHandler.CallBacks() {
        @Override
        public void onStartDragging() {
            Log.d(TAG, "onStartDragging()");
        }

        @Override
        public void onStopDragging() {
            Log.d(TAG, "onStopDragging()");
            updateEditModeIfThereIsAnyChange();
        }
    };

    private final QSCurrentListAdapter.CallBacks currentListAdapterCallBack = item -> {
        Log.d(TAG, "onItemGetsDeleted: " + item);
        //we need to add deleted item to available list last position and
        //we need to update edit mode since we can have a change or vice-versa
        availableListAdapter.handleAddItemToTheLastPosition(item);
        updateEditModeIfThereIsAnyChange();
    };

    private void updateActionBar(String title, boolean showBackButton) {
        Activity activity = getActivity();
        if(activity != null) {
            activity.setTitle(title);
            ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
            if(actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(showBackButton);
                actionBar.setDisplayShowHomeEnabled(showBackButton);
            }else {
                Log.e(TAG, "updateActionBar: action bar is null");
            }
        } else {
            Log.e(TAG, "updateActionBar: activity is null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.qs_main_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(QSViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeVariablesAndComponents();
        drawFragmentForTheFirstTime();
        addHandlersAndListeners();
    }

    private void initializeVariablesAndComponents() {
        updateActionBar("Quick settings", true);

        currentListAdapter = new QSCurrentListAdapter(getContext(), viewModel.getUpdatedCurrentList());
        currentListAdapter.setCallBacks(currentListAdapterCallBack);
        availableListAdapter = new QSAvailableListAdapter(getContext(), viewModel.getUpdatedAvailableList());
        dragAndDropHandler = new QSDragAndDropHandler(currentListAdapter, availableListAdapter);
        dragAndDropHandler.setCallBacks(dragAndDropCallBack);
    }

    private void drawFragmentForTheFirstTime() {
        binding.changesApplyOrCancelView.setVisibility(View.GONE);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        binding.rvCurrentItems.setLayoutManager(layoutManager);
        binding.rvCurrentItems.setAdapter(currentListAdapter);
        binding.rvCurrentItems.setOnDragListener(dragAndDropHandler);

        LinearLayoutManager llManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        llManager.setReverseLayout(false);
        binding.rvAvailableItems.setLayoutManager(llManager);
        binding.rvAvailableItems.setAdapter(availableListAdapter);
        binding.rvAvailableItems.setOnDragListener(dragAndDropHandler);
    }

    private void addHandlersAndListeners() {
        binding.layoutApplyChanges.setOnClickListener(this::userWantToApplyChanges);
        binding.layoutCancelChanges.setOnClickListener(this::userWantToCancelChanges);
    }

    private void startEditMode() {
        if(editModeStarted) {
            return;
        }
        editModeStarted = true;
        updateActionBar("Edit quick settings", false);
        binding.changesApplyOrCancelView.setVisibility(View.VISIBLE);
    }

    private void endEditMode() {
        if(!editModeStarted) {
            return;
        }
        editModeStarted = false;
        updateActionBar("Quick settings", true);
        binding.changesApplyOrCancelView.setVisibility(View.GONE);
    }

    public void userWantToApplyChanges(View v) {
        Log.d(TAG, "userWantToApplyChanges()");
        // todo - need to complete this method
        endEditMode();
    }

    public void userWantToCancelChanges(View v) {
        Log.d(TAG, "userWantToCancelChanges()");
        // todo - need to complete this method
        endEditMode();
    }

    private void updateEditModeIfThereIsAnyChange() {
        if(isItemPositionChanged()) {
            startEditMode();
        } else {
            endEditMode();
        }
    }

    private boolean isItemPositionChanged() {
        ArrayList<QSItem> previousCurrentList = viewModel.getUpdatedCurrentList();  // get this from live data dot value
        ArrayList<QSItem> previousAvailableList = viewModel.getUpdatedAvailableList();  // get this from live data dot value
        ArrayList<QSItem> updatedCurrentList = currentListAdapter.getUpdatedItemList();
        ArrayList<QSItem> updatedAvailableList = availableListAdapter.getUpdatedItemList();

        if(previousAvailableList.size() != updatedAvailableList.size()) {
            return true; // available list size changed check
        } else if(previousCurrentList.size() != updatedCurrentList.size()) {
            return true; // current list size changed check (actually no need)
        } else { // we have to match the name if any reorder happens
            int currentListSize = updatedCurrentList.size();
            for (int i = 0; i < currentListSize; i++) {
                if (!previousCurrentList.get(i).name.equals(updatedCurrentList.get(i).name)) {
                    return true;
                }
            }
        }
        return false;
    }

}
