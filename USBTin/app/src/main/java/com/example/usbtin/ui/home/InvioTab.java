package com.example.usbtin.ui.home;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usbtin.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvioTab extends Fragment {
    private Button runButton;
    private Button stopButton;
    private EditText IdFrom;
    private EditText IdTo;
    private TextView currentId;
    private TableLayout invioTable;

    public InvioTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_invio_tab, container, false);
        runButton = root.findViewById(R.id.InviaButton);
        IdFrom = root.findViewById(R.id.IdFrom);
        IdTo = root.findViewById(R.id.IdTo);
        invioTable = root.findViewById(R.id.tableInvio);
        currentId = root.findViewById(R.id.currentId);
        stopButton = root.findViewById(R.id.stopButton);
        stopButton.setEnabled(false);

        runButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createRow(IdFrom.getText().toString(),IdTo.getText().toString());
            }
        });

        return root;
    }

    public void createRow(String IdFrom, String IdTo) {
        runButton.setEnabled(false);
        stopButton.setEnabled(true);
        int first = Integer.parseInt(IdFrom,16);
        int second = Integer.parseInt(IdTo,16);

        for (int i = first; i <= second; i++) {
            currentId.setText(String.valueOf(i));
            renderTable(i);
        }
        runButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    public void renderTable(int number) {
        TableRow.LayoutParams trParam = new TableRow.LayoutParams();
        trParam.weight = 1;
        TableRow tableLine = new TableRow(getContext());

        TextView tableId = new TextView(getContext());
        tableId.setLayoutParams(trParam);
        tableId.setText(String.valueOf(number));
        tableId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(tableId);

        TextView payload = new TextView(getContext());
        payload.setText("Nessun Payload");
        payload.setLayoutParams(trParam);
        payload.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(payload);

        invioTable.addView(tableLine);
    }
}
