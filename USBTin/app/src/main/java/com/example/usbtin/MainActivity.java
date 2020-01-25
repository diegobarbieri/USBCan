package com.example.usbtin;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.DialogInterface;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtinException;

public class MainActivity extends AppCompatActivity {

    private static USBtin usbtin;
    private AppBarConfiguration mAppBarConfiguration;
    private ArrayList<CANMessage> canMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
	    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);  //PER IMPOSTAZIONI
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_impostazioni, R.id.nav_convertitore, R.id.nav_help,
                R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void convertClick(View view){
        Spinner mySpinner = (Spinner) findViewById(R.id.convertitore_selectFrom);
        String conversione = mySpinner.getSelectedItem().toString();
        if (conversione.equals("Decimale")) {
            EditText editText = (EditText) findViewById(R.id.convertitore_input);
            if (editText.getText().toString().matches((".*[a-zA-Z]+.*"))){
                alert("Errore di Input","Non hai inserito un numero valido, riprovare");
            }
            else{
                int interoDaConvertire = Integer.parseInt(editText.getText().toString());
                convertiDecimale(interoDaConvertire);
            }
        }
        else {
            EditText editText = (EditText) findViewById(R.id.convertitore_input);
            String hexDaConvertire = editText.getText().toString();
            convertiEsadecimale(hexDaConvertire);
        }
    }
    public void convertiDecimale(int number) {
        String hex = Integer.toHexString(number);
        TextView textView = (TextView)findViewById(R.id.convertitore_output);
        textView.setText(hex);
    }

    public void convertiEsadecimale(String hex) {
        int decimal=Integer.parseInt(hex,16);
        TextView textView = (TextView)findViewById(R.id.convertitore_output);
        textView.setText(Integer.toString(decimal));
    }

    public void onImpostazioni(MenuItem item){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void receiveMessage(String bus, String porta, String baudRate, String modalità) {
        try {
            usbtin = new USBtin();
            usbtin.connect(porta);

            if(modalità.equals("ACTIVE")) {
                usbtin.openCANChannel(Integer.parseInt(baudRate), USBtin.OpenMode.ACTIVE);
            }
            else {
                usbtin.addMessageListener(canMessage -> {createRow(canMessage);});
            }
        } catch (USBtinException ex) {

            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);

        }
    }

    public void Disconnect(MenuItem item) throws USBtinException {
        // close the CAN channel and close the connection
        usbtin.closeCANChannel();
        usbtin.disconnect();
    }

    public void createRow(de.fischl.usbtin.CANMessage message) {
        TableLayout tabLayout = findViewById(R.id.tableRicevi);

        TableRow.LayoutParams trParam = new TableRow.LayoutParams();
        trParam.weight = 1;
        TableRow tableLine = new TableRow(this);

        TextView Id = new TextView(this);
        Id.setLayoutParams(trParam);
        Id.setText(message.getId());
        Id.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(Id);

        TextView dlc = new TextView(this);
        dlc.setText(Integer.toString(message.hashCode()));
        dlc.setLayoutParams(trParam);
        dlc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(dlc);

        TextView Data = new TextView(this);
        Data.setText(message.getData().toString());
        Data.setLayoutParams(trParam);
        Data.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLine.addView(Data);

        tabLayout.addView(tableLine);
    }

    public void cleanTable(MenuItem item) {
        TableLayout tabLayoutRicevi = findViewById(R.id.tableRicevi);
        TableLayout tabLayoutInvio = findViewById(R.id.tableInvio);
        while (tabLayoutRicevi.getChildCount() > 1)
            tabLayoutRicevi.removeView(tabLayoutRicevi.getChildAt(tabLayoutRicevi.getChildCount() - 1));
        while (tabLayoutInvio.getChildCount() > 1)
            tabLayoutInvio.removeView(tabLayoutInvio.getChildAt(tabLayoutInvio.getChildCount() - 1));
    }

    public void alert(String title, String body) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(body);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
