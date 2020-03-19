package com.example.crudsqllite_zaka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ListMovement extends AppCompatActivity {

    private ListView _list;
    private GSA_Datasource _data;
    private GSA_adapter_movement _adapter;
    private String _code;
    private EditText dateI;
    private EditText dateF;
    private ImageView calendarI;
    private ImageView calendarF;
    //private Calendar c = Calendar.getInstance();
    private long id;

    private static String[] from = new String[]{GSA_Datasource.TABLE_COL2_2, GSA_Datasource.TABLE_COL3_2, GSA_Datasource.TABLE_COL4_2};
    private static int[] to = new int[]{R.id.tvDate, R.id.tvCan, R.id.tvType};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movement);

        androidx.appcompat.widget.Toolbar _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        _list = findViewById(R.id.listMovement);

        _code = this.getIntent().getExtras().getString("code");

        _toolbar.setTitle(_code);

        _data = new GSA_Datasource(this);

        final Cursor _c = _data.SearhCode(_code);
        _c.moveToFirst();

        id = _c.getLong(_c.getColumnIndexOrThrow(GSA_Datasource.TABLE_ID));

        _c.close();

        dateI = findViewById(R.id.edtDateI);
        dateF = findViewById(R.id.edtDateF);

        calendarI = findViewById(R.id.calendarI);
        calendarI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCalendar.Dialog(v.getContext(), dateI);
            }
        });

        calendarF = findViewById(R.id.calendarF);
        calendarF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCalendar.Dialog(v.getContext(), dateF);
            }
        });

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.buttonSearch);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Search();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

        FloatingActionButton btnC = (FloatingActionButton) findViewById(R.id.ButtonClean);
        btnC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Reset();
            }

        });

        load();
    }


    public void load(){
        Cursor _cursor = _data.ReadMovement(id);

        Log.d("@CodeSons", DatabaseUtils.dumpCursorToString(_cursor));

        _adapter = new GSA_adapter_movement(this, R.layout.elements_movement, _cursor, from, to, 1);

        _list.setAdapter(_adapter);
        _list.setVisibility(View.GONE);
    }

    public void Aply(Cursor _cursor){
        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();

        ListView lv = (ListView) findViewById(R.id.listMovement);
        lv.setSelection(0);
    }


    public void Search() throws ParseException {

        Cursor _cursor = null;

        String _dayI;
        String _dayF;

        if(!dateI.getText().toString().isEmpty() && !dateF.getText().toString().isEmpty()){
            _dayI = DialogCalendar.ChangeFormatDate(dateI.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");
            _dayF = DialogCalendar.ChangeFormatDate(dateF.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");

           _cursor = _data.DateIDateF(_dayI, _dayF, id);
           _list.setVisibility(View.VISIBLE);
        }
        else if(!dateI.getText().toString().isEmpty()){
            _dayI = DialogCalendar.ChangeFormatDate(dateI.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");
            _cursor = _data.DateI(_dayI, id);
            _list.setVisibility(View.VISIBLE);
        }
        else if(!dateF.getText().toString().isEmpty()){
            _dayF = DialogCalendar.ChangeFormatDate(dateF.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");
            _cursor = _data.DateF(_dayF, id);
            _list.setVisibility(View.VISIBLE);
        }
        else{
            _cursor = _data.ReadMovement(id);
            _list.setVisibility(View.VISIBLE);
        }

        if(_cursor != null) {
            Aply(_cursor);
        }
    }

    public void Reset(){
        dateI.setText("");
        dateF.setText("");
        _list.setVisibility(View.GONE);
    }

    public void cancel(){
        setResult(RESULT_CANCELED);
        finish();
    }

    /*public void Calendar(final EditText edt){
       int _year = c.get(Calendar.YEAR);
        int _month = c.get(Calendar.MONTH);
        int _day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int years, int months, int days) {

                if(months < 10 && days < 10){
                    dates = String.valueOf(years + "/" + "0" + (months + 1) + "/" + "0" + days);
                }
                else if(months < 10){
                    dates = String.valueOf(years + "/" + "0" + (months + 1) + "/" + days);
                }
                else if(days < 10){
                    dates = String.valueOf(years + "/" + (months + 1) + "/" + "0" + days);
                }
                else{
                    dates = String.valueOf(years + "/" + (months + 1) + "/" + days);
                }

                edt.setText(dates);
            }
        },_year,_month,_day);
        datePickerDialog.show();
    }*/

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_elements,menu);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        return true;
    }

    /*public static String ChangeFormatDate(String _dia, String FormatOrigin, String FormatFinal) throws ParseException {
        SimpleDateFormat parseador = new SimpleDateFormat(FormatOrigin);
        SimpleDateFormat formateador = new SimpleDateFormat(FormatFinal);
        Date date = parseador.parse(_dia);
        return formateador.format(date);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imgHome:
                cancel();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

class GSA_adapter_movement extends android.widget.SimpleCursorAdapter {

    public GSA_adapter_movement(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        final Cursor line = (Cursor) getItem(position);

        String dia = line.getString(line.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL2_2));

        TextView edt = view.findViewById(R.id.tvDate);
        try {
            edt.setText(DialogCalendar.ChangeFormatDate(dia,"yyyy/MM/dd","dd/MM/yyyy"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }
}
