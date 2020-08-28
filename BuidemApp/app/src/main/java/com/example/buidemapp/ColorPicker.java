package com.example.buidemapp;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

public class ColorPicker {

    public void ColorDialog(Context _context, final EditText _text){
        ColorPickerDialog colorPickerDialog= ColorPickerDialog.createColorPickerDialog(_context,ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                _text.setHint(hexVal);
                _text.setBackgroundColor(Color.parseColor(hexVal));
            }
        });
        colorPickerDialog.setHexaDecimalTextColor(Color.parseColor("#ffffff"));
        colorPickerDialog.show();
    }
}
