package com.example.buidemapp;

import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerSearchPos {
    public static int SearchPos(ArrayList<String> _lista, String text){
        int pos = 0;
        boolean selected = false;

        for(int x = 0; x < _lista.size(); x++){
            if(text.equals(_lista.get(x)) && !selected){
                pos = x;
                selected = true;
            }
        }

        return pos;
    }
}
