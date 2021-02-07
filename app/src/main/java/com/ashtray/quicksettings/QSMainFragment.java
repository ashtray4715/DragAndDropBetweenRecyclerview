package com.ashtray.quicksettings;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ashtray.quicksettings.databinding.QsMainFragmentBinding;

public class QSMainFragment extends Fragment {

    private final String TAG = "[QSMainFragment]";
    private static final int DRAG_DIRECTION = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;

    private QsMainFragmentBinding binding;
    private QSViewModel viewModel;

    private QSCurrentListAdapter currentListAdapter;
    private QSAvailableListAdapter availableListAdapter;

    private final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(DRAG_DIRECTION, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder svh, @NonNull RecyclerView.ViewHolder tvh) {
            return currentListAdapter.handleMoveItem(svh, tvh);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d(TAG, "onSwiped: direction -> " + direction);
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == QSConstant.QSItem_TYPE_HEADER ? 0 : makeMovementFlags(DRAG_DIRECTION, 0);
        }
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
        updateActionBar("Main", true);
    }

    private void drawFragmentForTheFirstTime() {
        currentListAdapter = new QSCurrentListAdapter(getContext(), viewModel.getUpdatedCurrentList());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        binding.rvCurrentItems.setLayoutManager(layoutManager);
        binding.rvCurrentItems.setAdapter(currentListAdapter);
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        //itemTouchHelper.attachToRecyclerView(binding.rvCurrentItems);
        binding.rvCurrentItems.setOnDragListener(new QSDragAndDropHandler());

        availableListAdapter = new QSAvailableListAdapter(getContext(), viewModel.getUpdatedAvailableList());
        LinearLayoutManager llManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvAvailableItems.setLayoutManager(llManager);
        binding.rvAvailableItems.setAdapter(availableListAdapter);
        binding.rvAvailableItems.setOnDragListener(new QSDragAndDropHandler());
    }

    private void addHandlersAndListeners() {
        binding.ivApplyChanges.setOnClickListener(this::userWantToApplyChanges);
        binding.tvApplyChanges.setOnClickListener(this::userWantToApplyChanges);
        binding.ivCancelChanges.setOnClickListener(this::userWantToCancelChanges);
        binding.tvCancelChanges.setOnClickListener(this::userWantToCancelChanges);
    }

    private void startEditMode() {
        updateActionBar("Edit Main", false);
        binding.changesApplyOrCancelView.setVisibility(View.VISIBLE);
    }

    private void endEditMode() {
        updateActionBar("Edit Main", true);
        binding.changesApplyOrCancelView.setVisibility(View.GONE);
    }

    public void userWantToApplyChanges(View v) {
        Log.d(TAG, "userWantToApplyChanges()");
        // update list in view model
        currentListAdapter.notifyDataSetChanged();
        currentListAdapter.printTheNameOneByOne();
        endEditMode();
    }

    public void userWantToCancelChanges(View v) {
        Log.d(TAG, "userWantToCancelChanges()");
        // update current lists
        endEditMode();
    }

}
