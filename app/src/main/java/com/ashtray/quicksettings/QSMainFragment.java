package com.ashtray.quicksettings;

import android.os.Bundle;
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

    private QsMainFragmentBinding binding;
    private QSViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.qs_main_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(QSViewModel.class);
        return binding.getRoot();
    }
}
