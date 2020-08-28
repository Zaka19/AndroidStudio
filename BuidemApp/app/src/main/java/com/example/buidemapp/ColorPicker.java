package com.example.buidemapp;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;

public class ColorPicker {

    public void ColorDialog(Context _context, final EditText _text, final TextView _color){
        ColorPickerDialog colorPickerDialog= ColorPickerDialog.createColorPickerDialog(_context,ColorPickerDialog.DARK_THEME);
        colorPickerDialog.setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
            @Override
            public void onColorPicked(int color, String hexVal) {
                _text.setText(hexVal);
                _text.setTextColor(Color.parseColor(hexVal));
                _color.setBackgroundColor(Color.parseColor(hexVal));
            }
        });
        colorPickerDialog.setHexaDecimalTextColor(Color.parseColor("#ffffff"));
        colorPickerDialog.show();
    }
}
