package com.example.usbtin.ui.home;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.MenuItem;
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
import java.util.stream.IntStream;

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
    private EditText msgStep;

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
        msgStep = root.findViewById(R.id.msgStep);
        stopButton.setEnabled(false);

        runButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cleanTable();
                if (msgStep.getText().toString().equals("")){
                    msgStep.setText("1");
                }
                createRow(IdFrom.getText().toString(),IdTo.getText().toString(), Integer.parseInt(msgStep.getText().toString()));
            }
        });

        return root;
    }

    public void createRow(String IdFrom, String IdTo, int times) {
        runButton.setEnabled(false);
        stopButton.setEnabled(true);
        int first = Integer.parseInt(IdFrom,16);
        int second = Integer.parseInt(IdTo,16);

        for (int i = first; i <= second; i++) {
            currentId.setText(Integer.toHexString(i));
            for(int j=0; j<times;j++) {
                renderTable(i);
            }
        runButton.setEnabled(true);
        stopButton.setEnabled(false);
        }
    }

    public void renderTable(int number) {
        TableRow.LayoutParams trParam = new TableRow.LayoutParams();
        trParam.weight = 1;
        TableRow tableLine = new TableRow(getContext());

        TextView tableId = new TextView(getContext());
        tableId.setLayoutParams(trParam);
        tableId.setText(Integer.toHexString(number));
        tableId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(tableId);

        TextView payload = new TextView(getContext());
        payload.setText("Nessun Payload");
        payload.setLayoutParams(trParam);
        payload.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(payload);

        invioTable.addView(tableLine);
    }

    public void cleanTable() {
        while (invioTable.getChildCount() > 1)
            invioTable.removeView(invioTable.getChildAt(invioTable.getChildCount() - 1));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
