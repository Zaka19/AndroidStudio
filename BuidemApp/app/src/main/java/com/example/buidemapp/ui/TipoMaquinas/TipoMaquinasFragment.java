package com.example.buidemapp.ui.TipoMaquinas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.buidemapp.Db_AppBuidem.Datasource;
import com.example.buidemapp.ui.Interface.ICrud;
import com.example.buidemapp.MainActivity;
import com.example.buidemapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class TipoMaquinasFragment extends Fragment implements ICrud {

    private int ADD_TYPEMACHINE = 1;
    private int UPDATE_TYPEMACHINE = 2;

    private View root;

    //private Datasource _data;
    private Buidem_adapter _adapter;

    private static String[] from = new String[]{Datasource.TIPOMAQUINAS_NOMBRE};
    private static int[] to = new int[]{R.id.tvNameTM};

    private FloatingActionButton _addTypeMachine;
    private TipoMaquinasViewModel tipoMaquinasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tipoMaquinasViewModel =
                ViewModelProviders.of(this).get(TipoMaquinasViewModel.class);
        root = inflater.inflate(R.layout.fragment_tipomaquina, container, false);

        //_data = new Datasource(getActivity());

        _addTypeMachine = root.findViewById(R.id.faAddTMList);
        _addTypeMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        LoadList();

        return root;
    }

    private void LoadList() {
        Cursor _allTypeMachine= ((MainActivity)getActivity())._data.GetTipoMaquinas();
        _adapter = new Buidem_adapter(getActivity(), R.layout.element_typemahines, _allTypeMachine, from, to, 1, TipoMaquinasFragment.this);

        if(_allTypeMachine.getCount() <= 0){
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No hay ningun registro de tipo de maquinas.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }

        ListView lv = (ListView) root.findViewById(R.id._listTypeMachine);
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

    public void Add() {
        Bundle bundle = new Bundle();
        bundle.putLong("CRUD",-1);

        Intent i = new Intent(getActivity(), CRUDTipoMaquinas.class);
        i.putExtras(bundle);
        startActivityForResult(i,ADD_TYPEMACHINE);
    }

    public void Update(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong("CRUD",id);

        Intent i = new Intent(getActivity(), CRUDTipoMaquinas.class);
        i.putExtras(bundle);
        startActivityForResult(i,UPDATE_TYPEMACHINE);
    }

    public void Delete(final long _id) {
        Cursor _cursor = ((MainActivity)getActivity())._data.SearchTypeMachineInMachine(_id);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (_cursor.getCount() <= 0) {
            builder.setMessage("¿Estas seguro quieres borrar este tipo de maquina?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ((MainActivity)getActivity())._data.DeleteTipoMaquinas(_id);
                    refresh();
                }
            });
            builder.setNegativeButton("No", null);
        }
        else {
            builder.setMessage("Tienes una maquina enlazada a este tipo de maquina.");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
        }

        builder.show();
        _cursor.close();
    }

    public void refresh() {
        Cursor _cursor = ((MainActivity)getActivity())._data.GetTipoMaquinas();
        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();

        ListView lv = (ListView) getActivity().findViewById(R.id._listTypeMachine);
        lv.setSelection(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TYPEMACHINE) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }

        if (requestCode == UPDATE_TYPEMACHINE) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }
    }
}

class Buidem_adapter extends android.widget.SimpleCursorAdapter {
    private TipoMaquinasFragment _fragments;

    private ImageView _imgDelete;
    private TextView tvColor;

    public Buidem_adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags, TipoMaquinasFragment fragment) {
        super(context, layout, c, from, to, flags);
        _fragments = (TipoMaquinasFragment) fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = super.getView(position, convertView, parent);

        final Cursor typeMachine = (Cursor) getItem(position);

        _imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        tvColor = (TextView) view.findViewById(R.id.tvColorTM);

        tvColor.setBackgroundColor(Color.parseColor(typeMachine.getString(typeMachine.getColumnIndexOrThrow(Datasource.TIPOMAQUINAS_COLOR))));

        _imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long _id;

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent();
                int position = lv.getPositionForView(view);
                Cursor _typemachine = (Cursor) getItem(position);

                _id = _typemachine.getLong(_typemachine.getColumnIndexOrThrow(Datasource.TODASTABLAS_ID));
                _fragments.Delete(_id);
            }
        });

        return view;
    }
}

//ViewModel observer
        /*final TextView textView = root.findViewById(R.id.text_notifications);
          tipoMaquinasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/