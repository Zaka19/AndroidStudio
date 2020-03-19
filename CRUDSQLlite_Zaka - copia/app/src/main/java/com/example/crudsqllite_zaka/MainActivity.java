package com.example.crudsqllite_zaka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static int ACTIVITY_TASK_ADD = 1;
    private static int ACTIVITY_TASK_UPDATE = 2;
    private static int ACTIVITY_TASK_SHOW_MOV = 3;

    private TextView tv;
    private EditText edt;
    private EditText edtDate;
    private Calendar c = Calendar.getInstance();
    private String dates;
    private int _year = c.get(Calendar.YEAR);
    private int _month = c.get(Calendar.MONTH);
    private int _day = c.get(Calendar.DAY_OF_MONTH);

    private long Num_Stock;

    private GSA_Datasource _data;
    private GSA_adapter _adapter;
    Filters filter;

    private static String[] from = new String[]{GSA_Datasource.TABLE_COL1, GSA_Datasource.TABLE_COL2, GSA_Datasource.TABLE_COL4};
    private static int[] to = new int[]{R.id.tvCode, R.id.tvDesc, R.id.tvStock};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        edtDate = findViewById(R.id.edtmain);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.buttonadd);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                add();
            }
        });

        FloatingActionButton btnSearch = findViewById(R.id.buttonSeachDateMain);
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Date();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageView SearchMovementDate = findViewById(R.id.calendarmain);
        SearchMovementDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCalendar.Dialog(v.getContext(),edtDate);
            }
        });

        _data = new GSA_Datasource(this);

        loadData();
    }

    //Functions for ListView

    public void loadData(){

        Cursor _cursor = _data.ReadAll();
        filter = filter.ALL;
        /* while (_cursor.moveToNext()) {
                work = new Work(_cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL1)), _cursor.getString(_cursor.getColumnIndexOrThrow(_data.TABLE_COL2)), _cursor.getDouble(_cursor.getColumnIndexOrThrow(_data.TABLE_COL3)), _cursor.getDouble(_cursor.getColumnIndexOrThrow(_data.TABLE_COL4)));
                _works.add(work);
            }*/
        _adapter = new GSA_adapter(this, R.layout.elements, _cursor, from, to, 1);

            //_newadapter = new GSA_newadapter(this, _works, to);
            ListView lv = (ListView) findViewById(R.id.list);
            lv.setAdapter(_adapter);

            lv.setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View view,
                                                int position, long id) {
                            Log.d("Id",String.valueOf(id));
                            Update(id);
                        }
                    }
            );
        }

    public void refresh(){

        Cursor cursor = null;

        edtDate.setText("");

        switch (filter){
            case ALL:
                cursor = _data.ReadAll();
                break;
            case DESCENDENT:
                cursor = _data.ReadDescending();
                break;
            case NOHAVESTOCK:
                cursor = _data.ReadNoHaveStock();
                break;
            case HAVESTOCK:
                cursor = _data.ReadHaveStock();
                break;
        }
        Aply(cursor);
    }

    public void Aply(Cursor _cursor){
        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();

        ListView lv = (ListView) findViewById(R.id.list);
        lv.setSelection(0);
    }

    //CRUD

    public void add(){
        Bundle bundle = new Bundle();
        bundle.putLong("code",-1);

        Intent i = new Intent(this, addForm.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_TASK_ADD);
    }

    public void Update(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("code",id);

        Intent i = new Intent(this, addForm.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_TASK_UPDATE);
    }

    public void Delete(final String _code){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Estas seguro que deseas eliminar el registro?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                _data.Delete(_code);
                refresh();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public void ShowMovement(String code){
        Bundle bundle = new Bundle();
        bundle.putString("code",code);

        Intent i = new Intent(this, ListMovement.class );
        i.putExtras(bundle);
        startActivityForResult(i,ACTIVITY_TASK_SHOW_MOV);
    }

    //Functions for menu

    public void NoStock(){
        Cursor cursor = _data.ReadNoHaveStock();
        cursor.moveToFirst();
        filter = filter.NOHAVESTOCK;

        Aply(cursor);

        snackbar("Articulos sin estoc.");
    }

    public void Stock(){
        Cursor cursor = _data.ReadHaveStock();
        cursor.moveToFirst();
        filter = filter.HAVESTOCK;

        Aply(cursor);

        snackbar("Articulos con estoc.");
    }

    public void Asc(){
        Cursor cursor = _data.ReadAll();
        cursor.moveToFirst();
        filter = filter.ALL;

        Aply(cursor);

    }

    public void Desc(){
        Cursor cursor = _data.ReadDescending();
        cursor.moveToFirst();
        filter = filter.DESCENDENT;

        Aply(cursor);
    }

    public void Date() throws ParseException {
        Cursor _cursor = null;

        if(!edtDate.getText().toString().isEmpty()){
            String date = DialogCalendar.ChangeFormatDate(edtDate.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");
            _cursor = _data.DateMain(date);
            Log.d("@RE",DatabaseUtils.dumpCursorToString(_cursor));
        }
        else{
            _cursor = _data.ReadAll();
        }

        Aply(_cursor);
    }

    public void Movements(){
        Intent i = new Intent(this, MovementsAll.class );
        startActivityForResult(i,ACTIVITY_TASK_SHOW_MOV);
    }

    //Functions for stock

    public void ChangeStock(final int x, final String code) throws ParseException {
        _year = c.get(Calendar.YEAR);
        _month = c.get(Calendar.MONTH);
        _day = c.get(Calendar.DAY_OF_MONTH);

        AlertDialog _alert;
        _alert = new AlertDialog.Builder(this).create();

        final View customLayout = getLayoutInflater().inflate(R.layout.calendar, null);

        tv = customLayout.findViewById(R.id.tvCodeMovement);
        tv.setText( tv.getText().toString() + " " + code);

        edt = (EditText) customLayout.findViewById(R.id.cantidad);

        tv = (TextView) customLayout.findViewById(R.id.calendar);

        Log.d("data5", String.valueOf(_month));

        if (_month < 10 && _day < 10) {
            dates = "0" + _day + "/" + "0" + (_month + 1) + "/" + _year;
        } else if (_month < 10) {
            dates = _day + "/" + "0" + (_month + 1) + "/" + _year;
        } else if (_day < 10) {
            dates = "0" + _day + "/" + (_month + 1) + "/" + _year;
        } else {
            dates = _day + "/" + (_month + 1) + "/" + _year;
        }

        tv.setText(dates);

        customLayout.findViewById(R.id.calendardata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogCalendar.Dialog(v.getContext(), tv);
            }
        });

        _alert.setView(customLayout);

        _alert.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                try {
                    dates = DialogCalendar.ChangeFormatDate(tv.getText().toString(),"dd/MM/yyyy","yyyy/MM/dd");
                    Num_Stock = Long.valueOf(edt.getText().toString());
                    ExecuteComands(x, code, Num_Stock, dates);
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Has de poner un numero.", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        _alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        _alert.show();
    }

    /*public static String ChangeFormatDate(String _dia, String FormatOrigin, String FormatFinal) throws ParseException {
        SimpleDateFormat parseador = new SimpleDateFormat(FormatOrigin);
        SimpleDateFormat formateador = new SimpleDateFormat(FormatFinal);
        Date date = parseador.parse(_dia);
        return formateador.format(date);
    }*/

    /*public void DatePick(){
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

                _day = days;
                _month = months;
                _year = years;

                tv.setText(dates);
            }
        },_year,_month,_day);
        datePickerDialog.show();
    }*/

    public void ExecuteComands(int x, String code, long stock, String dates){
        long stockT;

        Cursor _cursor = _data.SearhCode(code);
        _cursor.moveToFirst();

        if(x == 1){
            _data.Add_2(code,dates,stock,"Entrada",_cursor.getLong(_cursor.getColumnIndexOrThrow(GSA_Datasource.TABLE_ID)));
            stockT = _cursor.getInt(_cursor.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL4)) + stock;
        } else{
            _data.Add_2(code,dates,stock,"Salida",_cursor.getLong(_cursor.getColumnIndexOrThrow(GSA_Datasource.TABLE_ID)));
            stockT = _cursor.getInt(_cursor.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL4)) - stock;
        }

        _data.Update(code,_cursor.getString(_cursor.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL2)),_cursor.getDouble(_cursor.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL3)),stockT);

        _cursor.close();

        refresh();
    }

    //Extras

    public void snackbar(String _message){
        Snackbar.make(findViewById(android.R.id.content), _message, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.NoHaveStock:
                NoStock();
                return true;
            case R.id.HaveStock:
                Stock();
                return true;
            case R.id.Ascendent:
                Asc();
                return true;
            case R.id.Descendent:
                Desc();
                return true;
            case R.id.Movements:
                Movements();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_TASK_ADD) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }

        if (requestCode == ACTIVITY_TASK_UPDATE) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }

        if (requestCode == ACTIVITY_TASK_SHOW_MOV) {
            if (resultCode == RESULT_OK) {
                refresh();
            }
        }
    }
}

class GSA_adapter extends android.widget.SimpleCursorAdapter {

    private static final String NoHaveStock = "#d78290";
    private static final String HaveStock = "#fefefe";
    private MainActivity _main;

    public GSA_adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        _main = (MainActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        final Cursor line = (Cursor) getItem(position);

        double stock = line.getDouble(
                line.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL4)
        );

        if (stock <= 0) {
            view.setBackgroundColor(Color.parseColor(NoHaveStock));
        }
        else if(stock > 0){
            view.setBackgroundColor(Color.parseColor(HaveStock));
        }

        ImageView _imgDelete = (ImageView) view.findViewById(R.id.deleteMain);
        _imgDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent().getParent();
                int position = lv.getPositionForView(view);
                Cursor linia = (Cursor) getItem(position);


                _main.Delete(linia.getString(linia.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL1)));
            }
        });

        ImageView _imPlus = (ImageView) view.findViewById(R.id.Mas);
        _imPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent().getParent();
                int position = lv.getPositionForView(view);
                Cursor linia1 = (Cursor) getItem(position);

                try {
                    _main.ChangeStock(1,linia1.getString(linia1.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        ImageView _imReset = (ImageView) view.findViewById(R.id.Menos);
        _imReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent().getParent();
                int position = lv.getPositionForView(view);
                Cursor linia2 = (Cursor) getItem(position);

                try {
                    _main.ChangeStock(-1,linia2.getString(linia2.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        ImageView _imStore = (ImageView) view.findViewById(R.id.Storie);
        _imStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent().getParent();
                int position = lv.getPositionForView(view);
                Cursor linia = (Cursor) getItem(position);

                _main.ShowMovement(linia.getString(linia.getColumnIndexOrThrow(GSA_Datasource.TABLE_COL1)));
            }
        });

        return view;
    }
}

/*class GSA_newadapter extends ArrayAdapter<Work>{

    private Context context;
    private String[] from;
    private int[] to;
    private TextView tv;

    public GSA_newadapter(Context context, ArrayList<Work> elements, int[] to) {
        super(context, R.layout.new_elements, elements);
        this.context = context;
        this.to = to;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.elements, null);

        int a = 0;

        final Work work = (Work) getItem(position);

        from = new String[]{work.get_code(),work.get_description(),String.valueOf(work.get_stock())};

        for(int x : to){
            tv = (TextView) item.findViewById(x);
            tv.setText(from[a]);
            a++;
        }

        tv = item.findViewById(R.id.tvCode);
        tv.setText(work.get_code());

        tv = item.findViewById(R.id.tvDescription);
        tv.setText(work.get_description());

        tv = item.findViewById(R.id.tvStock);
        tv.setText(String.valueOf(work.get_stock()));

        if(work.get_stock() <= 0){
            item.setBackgroundColor(Color.parseColor("#d78290"));
        }

        return(item);
    }
}
*/
