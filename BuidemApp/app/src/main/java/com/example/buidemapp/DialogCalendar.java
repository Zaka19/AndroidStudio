package com.example.buidemapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogCalendar {
    private static Calendar c = Calendar.getInstance();
    static String dates;

    public static void Dialog(Context _contex, final TextView edt){
        int _year = c.get(Calendar.YEAR);
        int _month = c.get(Calendar.MONTH);
        int _day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(_contex, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int years, int months, int days) {

                if (months < 10 && days < 10) {
                    dates = "0" + days + "/" + "0" + (months + 1) + "/" + years;
                } else if (months < 10) {
                    dates = days + "/" + "0" + (months + 1) + "/" + years;
                } else if (days < 10) {
                    dates = "0" + days + "/" + (months + 1) + "/" + years;
                } else {
                    dates = days + "/" + (months + 1) + "/" + years;
                }

                edt.setText(dates);

            }
        },_year,_month,_day);
        datePickerDialog.show();
    }

    public static String ChangeFormatDate(String _dia, String FormatOrigin, String FormatFinal) throws ParseException {
        SimpleDateFormat parseador = new SimpleDateFormat(FormatOrigin);
        SimpleDateFormat formateador = new SimpleDateFormat(FormatFinal);
        Date date = parseador.parse(_dia);
        return formateador.format(date);
    }
}
