package com.example.buidemapp.ui.Maquinas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.buidemapp.Db_AppBuidem.Datasource;
import com.example.buidemapp.DialogCalendar;
import com.example.buidemapp.MainActivity;
import com.example.buidemapp.R;
import com.example.buidemapp.ui.Interface.ICrud;
import com.example.buidemapp.ui.Mapa.MapaFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;

public class MaquinaFragment extends Fragment implements ICrud {

    enum ORDER_BY {
        ALL, NAME_CLIENT, DATE, ADDRESS
    }

    private int ADD_MACHINE = 1;
    private int UPDATE_MACHINE = 2;

    private View root;

    private ImageView _Search;
    private EditText _TextToFilter;

    private ORDER_BY _order;

    //private Datasource _data;
    private Buidem_adapterM _adapter;

    private static String[] from = new String[]{Datasource.MAQUINAS_NOMBRECLIENTE, Datasource.MAQUINAS_NUMEROSERIE, Datasource.MAQUINAS_FECHA, Datasource.MAQUINAS_DIRECCION};
    private static int[] to = new int[]{R.id.edtNameM, R.id.edtNumeroSerie, R.id.edtFechaUlt, R.id.edtDireM};

    private FloatingActionButton _addMachine;
    private MaquinaViewModel maquinaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        maquinaViewModel =
                ViewModelProviders.of(this).get(MaquinaViewModel.class);
        root = inflater.inflate(R.layout.fragment_maquinas, container, false);

        _Search = root.findViewById(R.id._imageSearhMachine);
        _TextToFilter = root.findViewById(R.id.edtFilterMachine);

        _Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        _addMachine = root.findViewById(R.id.faAddMList);
        _addMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        LoadList();

        return root;
    }

    public void LoadList(){
        Cursor _allMachine = ((MainActivity)getActivity())._data.GetMaquinas();
        _order = ORDER_BY.ALL;
        _adapter = new Buidem_adapterM(getActivity(), R.layout.element_machine, _allMachine, from, to, 1, MaquinaFragment.this);

        if(_allMachine.getCount() <= 0){
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No hay ningun registro de maquinas.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }

        ListView lv = (ListView) root.findViewById(R.id._listMachine);
        lv.setAdapter(_adapter);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        Update(id);
                    }
                }
        );
    }

    public void Search(){
        ApplyCursorInList(((MainActivity)getActivity())._data.FilterMachinesToSerieCode(_TextToFilter.getText().toString()));
    }

    @Override
    public void Add() {
        Bundle bundle = new Bundle();
        bundle.putLong("CRUD",-1);

        Intent i = new Intent(getActivity(), CRUDMaquinas.class);
        i.putExtras(bundle);
        startActivityForResult(i,ADD_MACHINE);
    }

    @Override
    public void Update(long _id) {
        Bundle bundle = new Bundle();
        bundle.putLong("CRUD",_id);

        Intent i = new Intent(getActivity(), CRUDMaquinas.class);
        i.putExtras(bundle);
        startActivityForResult(i,UPDATE_MACHINE);
    }

    @Override
    public void Delete(final long _id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("¿Estas seguro quieres borrar esta maquina?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ((MainActivity)getActivity())._data.DeleteMaquinas(_id);
                    refresh();
                }
            });
            builder.setNegativeButton("No", null);

        builder.show();
    }

    @Override
    public void refresh() {
        _TextToFilter.setText("");

        switch(_order){
            case ALL:
                ApplyCursorInList(((MainActivity)getActivity())._data.GetMaquinas());
                break;
            case DATE:
                ApplyCursorInList(((MainActivity)getActivity())._data.OrderByDateLastRevision());
                break;
            case NAME_CLIENT:
                ApplyCursorInList(((MainActivity)getActivity())._data.OrderByClientName());
                break;
            case ADDRESS:
                ApplyCursorInList(((MainActivity)getActivity())._data.OrderByAddress());
                break;
        }
    }

    public void ApplyCursorInList(Cursor _cursor){
        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();

        ListView lv = (ListView) root.findViewById(R.id._listMachine);
        lv.setSelection(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MACHINE) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }

        if (requestCode == UPDATE_MACHINE) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_filter_machine, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nomClient:
                OrderToClientName();
                return true;
            case R.id.direccio:
                OrderToDireccion();
                return true;
            case R.id.Fecha:
                OrderToFecha();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void OrderToDireccion() {
        _order = ORDER_BY.ADDRESS;
        ApplyCursorInList(((MainActivity)getActivity())._data.OrderByAddress());
    }

    private void OrderToClientName() {
        _order = ORDER_BY.NAME_CLIENT;
        ApplyCursorInList(((MainActivity)getActivity())._data.OrderByClientName());
    }

    private void OrderToFecha() {
        _order = ORDER_BY.DATE;
        ApplyCursorInList(((MainActivity)getActivity())._data.OrderByDateLastRevision());
    }

    public void StartFragmentMap(Maquina _machine){
        ((MainActivity)getActivity()).ShowMap(_machine);
    }
}

class Buidem_adapterM extends android.widget.SimpleCursorAdapter {

    private MaquinaFragment _fragments;

    private ImageView _imgTel;
    private ImageView _imgEmail;
    private ImageView _imgDelete;
    private ImageView _imgMap;

    public Buidem_adapterM(Context context, int layout, Cursor c, String[] from, int[] to, int flags, MaquinaFragment fragment) {
        super(context, layout, c, from, to, flags);
        _fragments = (MaquinaFragment) fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = super.getView(position, convertView, parent);
        final Cursor Machine = (Cursor) getItem(position);

        TextView _edt = view.findViewById(R.id.edtFechaUlt);
        if(Machine.getString(Machine.getColumnIndexOrThrow(Datasource.MAQUINAS_FECHA)) != null) {
            try {
                _edt.setText(DialogCalendar.ChangeFormatDate(Machine.getString(Machine.getColumnIndexOrThrow(Datasource.MAQUINAS_FECHA)), "yyyy/MM/dd", "dd/MM/yyyy"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        _imgTel = (ImageView) view.findViewById(R.id.imgPhoneM);
        _imgEmail = (ImageView) view.findViewById(R.id.imgEmailM);

        if(Machine.getString(Machine.getColumnIndexOrThrow(Datasource.MAQUINAS_TELEFONO)).isEmpty()){
            _imgTel.setImageResource(R.drawable.ic_local_phone_black_24dp_no);
            _imgTel.setOnClickListener(null);
        }
        else{
            _imgTel.setImageResource(R.drawable.ic_local_phone_black_24dp);
            _imgTel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = (View) v.getParent();
                    ListView lv = (ListView) view.getParent();
                    int position = lv.getPositionForView(view);
                    Cursor _tel = (Cursor) getItem(position);

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + _tel.getString(_tel.getColumnIndexOrThrow(Datasource.MAQUINAS_TELEFONO))));
                    _fragments.startActivity(intent);
                }
            });
        }

        if(Machine.getString(Machine.getColumnIndexOrThrow(Datasource.MAQUINAS_EMAIL)).isEmpty()){
            _imgEmail.setImageResource(R.drawable.ic_email_black_24dp_no);
            _imgEmail.setOnClickListener(null);
        }
        else{
            _imgEmail.setImageResource(R.drawable.ic_email_black_24dp);
            _imgEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = (View) v.getParent();
                    ListView lv = (ListView) view.getParent();
                    int position = lv.getPositionForView(view);
                    Cursor _email = (Cursor) getItem(position);

                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                    String uriText = "mailto:" + Machine.getString(Machine.getColumnIndexOrThrow(Datasource.MAQUINAS_EMAIL))
                            + "?subject=" +
                            Uri.encode("Propera revisió màquina nº "
                                    + Machine.getString(Machine.getColumnIndexOrThrow(Datasource.MAQUINAS_NUMEROSERIE)));
                    sendIntent.setData(Uri.parse(uriText));
                    _fragments.startActivity(Intent.createChooser(sendIntent, "Enviar email"));
                }
            });
        }

        _imgMap = (ImageView) view.findViewById(R.id._imgMapMachine);

        _imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent();
                int position = lv.getPositionForView(view);
                Cursor Info = (Cursor) getItem(position);

                Maquina _machine = new Maquina(String.valueOf(Info.getInt(Info.getColumnIndexOrThrow(Datasource.TODASTABLAS_ID))),Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_NOMBRECLIENTE)),
                        Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_DIRECCION)),  Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_CODIGOPOSTAL)),
                        Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_POBLACION)),  Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_TELEFONO)),
                        Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_EMAIL)), Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_NUMEROSERIE)),
                        Info.getString(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_FECHA)), Info.getInt(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_TIPOMAQUINA)),
                        Info.getInt(Info.getColumnIndexOrThrow(Datasource.MAQUINAS_ZONA)));

                _fragments.StartFragmentMap(_machine);
            }
        });

        _imgDelete = (ImageView) view.findViewById(R.id.mgDeleteM) ;

        _imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long _id;

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent();
                int position = lv.getPositionForView(view);
                Cursor _zone = (Cursor) getItem(position);

                _id = _zone.getLong(_zone.getColumnIndexOrThrow(Datasource.TODASTABLAS_ID));

                _fragments.Delete(_id);
            }
        });

        return view;
    }
}

/*         final TextView textView = root.findViewById(R.id.text_dashboard);
        maquinaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        }); */