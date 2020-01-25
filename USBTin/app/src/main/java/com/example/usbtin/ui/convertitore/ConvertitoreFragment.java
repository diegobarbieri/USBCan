package com.example.usbtin.ui.convertitore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usbtin.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ConvertitoreFragment extends Fragment {

    private ConvertitoreViewModel convertitoreViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        convertitoreViewModel =
                ViewModelProviders.of(this).get(ConvertitoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_convertitore, container, false);
        return root;
    }
}