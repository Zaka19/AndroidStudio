package com.example.buidemapp.ui.Zonas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ZonaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ZonaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}