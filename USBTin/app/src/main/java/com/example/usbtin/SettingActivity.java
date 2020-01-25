package com.example.usbtin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sSettingbus = prefs.getString("list_preference_bus","xxx");
        setA(sSettingbus);

        String sSettingCOM = prefs.getString("list_preference_com","xxx");
        setB(sSettingCOM);

        String sSettingbound = prefs.getString("list_preference_rate","xxx");
        setC(sSettingbound);

        String sSettingmode = prefs.getString("list_preference_mode","xxx");
        setD(sSettingmode);
    }

    public String getD() {
        return d;
    }
    public void setD(String d) {
        this.d = d;
    }
    public String getC() {
        return c;
    }
    public void setC(String c) {
        this.c = c;
    }
    public String getB() {
        return b;
    }
    public void setB(String b) {
        this.b = b;
    }
    public void setA(String a) {
        this.a = a;
    }
    public String getA() {
        return a;
    }
}



