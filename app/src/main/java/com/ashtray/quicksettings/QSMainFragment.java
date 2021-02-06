package com.ashtray.quicksettings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ashtray.quicksettings.databinding.QsMainFragmentBinding;

public class QSMainFragment extends Fragment {

    private final String TAG = "[QSMainFragment]";

    private QsMainFragmentBinding binding;
    private QSViewModel viewModel;

    private void updateActionBar(String title, boolean showBackButton) {
        getActivity().setTitle(title);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(showBackButton);
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
        updateActionBar("Edit Main", false);
        binding.changesApplyOrCancelView.setVisibility(View.GONE);
    }

    public void userWantToApplyChanges(View v) {
        Log.d(TAG, "userWantToApplyChanges()");
        // update list in view model
        endEditMode();
    }

    public void userWantToCancelChanges(View v) {
        Log.d(TAG, "userWantToCancelChanges()");
        // update current lists
        endEditMode();
    }
}
