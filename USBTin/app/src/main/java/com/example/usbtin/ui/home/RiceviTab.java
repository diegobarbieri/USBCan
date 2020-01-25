package com.example.usbtin.ui.home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usbtin.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiceviTab extends Fragment {
    private ListView receiverList;
    private ArrayList<String> listaElementi;
    private ArrayAdapter<String> adapter;

    public RiceviTab() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recevi_tab, container, false);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}