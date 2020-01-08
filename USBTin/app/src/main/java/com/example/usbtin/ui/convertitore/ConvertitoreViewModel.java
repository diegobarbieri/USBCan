package com.example.usbtin.ui.convertitore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConvertitoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConvertitoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Convertitore Decimale to Hex");
    }

    public LiveData<String> getText() {
        return mText;
    }
}