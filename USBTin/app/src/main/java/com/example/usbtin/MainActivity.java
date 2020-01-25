package com.example.usbtin;

import android.content.Intent;						  
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;							 
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.fischl.usbtin.USBtin;
import de.fischl.usbtin.USBtinException;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

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
            int interoDaConvertire = Integer.parseInt(editText.getText().toString());
            convertiDecimale(interoDaConvertire);
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
    public static void receiveMessage() {
        try {
            // create the instances
            USBtin usbtin = new USBtin();
            // connect to USBtin and open CAN channel with 10kBaud in Active-Mode
            usbtin.connect("COM3"); // Windows e.g. "COM3"
            usbtin.openCANChannel(50000, USBtin.OpenMode.ACTIVE);
            usbtin.addMessageListener(canmsg -> System.out.println(canmsg));
            System.in.read();

            // close the CAN channel and close the connection
            usbtin.closeCANChannel();
            usbtin.disconnect();

        } catch (USBtinException | IOException ex) {

            // Ohh.. something goes wrong while accessing/talking to USBtin
            System.err.println(ex);

        }
    }
	
	public void onImpostazioni(MenuItem item){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
