package com.example.crudsqllite_zaka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;

public class MovementsAll extends AppCompatActivity {

    private ListView _list;
    public TextView edt;
    private ImageView _calendar;
    GSA_Datasource _data;
    private GSA_Movement _adapter;

    private static String[] from = new String[]{GSA_Datasource.TABLE_COL1, GSA_Datasource.TABLE_COL2_2, GSA_Datasource.TABLE_COL3_2, GSA_Datasource.TABLE_COL4_2};
    private static int[] to = new int[]{R.id.tvCodeM, R.id.tvDateM, R.id.tvCanM, R.id.tvTypeM};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movements_all);

        _data = new GSA_Datasource(this);

        _list = findViewById(R.id.list_movement_all);

        edt = findViewById(R.id.edtDateM);

        _calendar = findViewById(R.id.calendarM);
        _calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCalendar.Dialog(v.getContext(),edt);
            }
        });

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btnSearchM);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    SearchMovements();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

        FloatingActionButton btnR = (FloatingActionButton) findViewById(R.id.btnResetM);
        btnR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ResetMovements();
            }

        });

        loadMovements();
    }

    public void loadMovements(){

        //Log.d("@CodeSons", DatabaseUtils.dumpCursorToString(_data.DateMovementsAll()));

        _adapter = new GSA_Movement(this, R.layout.elements_movements_all, _data.DateMovementsAll(), from, to, 1);

        _list.setAdapter(_adapter);

        _list.setVisibility(View.GONE);
    }

    public void SearchMovements() throws ParseException {
        Cursor _cursor = null;

        if(!edt.getText().toString().isEmpty()){
            String date = DialogCalendar.ChangeFormatDate(edt.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");
            _cursor = _data.DateMovements(date);
            _list.setVisibility(View.VISIBLE);
        }
        else{
            _cursor = _data.DateMovementsAll();
            _list.setVisibility(View.GONE);
        }

        Aply(_cursor);
    }

    public void ResetMovements(){
        edt.setText("");
        _list.setVisibility(View.GONE);
    }

    public void Aply(Cursor _cursor){
        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();
    }
}

class GSA_Movement extends android.widget.SimpleCursorAdapter {

    public GSA_Movement(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        final Cursor line = (Cursor) getItem(position);

        String dia = line.getString(line.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL2_2));

        TextView edt = view.findViewById(R.id.tvDateM);
        try {
            edt.setText(DialogCalendar.ChangeFormatDate(dia,"yyyy/MM/dd","dd/MM/yyyy"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }
}