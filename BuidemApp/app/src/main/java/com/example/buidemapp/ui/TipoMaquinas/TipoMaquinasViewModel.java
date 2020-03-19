package com.example.buidemapp.ui.TipoMaquinas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TipoMaquinasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TipoMaquinasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}