package com.example.buidemapp.ui.Zonas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
import com.example.buidemapp.MainActivity;
import com.example.buidemapp.ui.Interface.ICrud;
import com.example.buidemapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ZonaFragment extends Fragment implements ICrud {

    private int ADD_ZONE = 1;
    private int UPDATE_ZONE = 2;

    private View root;

    private Datasource _data;
    private Buidem_adapterZ _adapter;

    private static String[] from = new String[]{Datasource.ZONAS_NOMBRE};
    private static int[] to = new int[]{R.id.tvNameZ};

    private FloatingActionButton _addZone;
    private ZonaViewModel zonaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        zonaViewModel = ViewModelProviders.of(this).get(ZonaViewModel.class);
        root = inflater.inflate(R.layout.fragment_zona, container, false);

        _data = new Datasource(getActivity());

        _addZone = root.findViewById(R.id.faAddZList);
        _addZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        LoadList();

        return root;
    }

    public void LoadList(){
        Cursor _allZones = _data.GetZonas();
        _adapter = new Buidem_adapterZ(getActivity(), R.layout.element_zone, _allZones, from, to, 1, ZonaFragment.this);

        if(_allZones.getCount() <= 0){
            Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No hay ningun registro de zonas.", Snackbar.LENGTH_SHORT);
            snackBar.show();
        }

        ListView lv = (ListView) root.findViewById(R.id._listZone);
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

    public void Add(){
        Bundle bundle = new Bundle();
        bundle.putLong("CRUD",-1);

        Intent i = new Intent(getActivity(), CRUDZonas.class);
        i.putExtras(bundle);
        startActivityForResult(i,ADD_ZONE);
    }

    public void Update(long _id){
        Bundle bundle = new Bundle();
        bundle.putLong("CRUD",_id);

        Intent i = new Intent(getActivity(), CRUDZonas.class);
        i.putExtras(bundle);
        startActivityForResult(i,UPDATE_ZONE);
    }

    public void Delete(final long _id) {
        Cursor _cursor = _data.SearchZoneInMachine(_id);

        if (_cursor.getCount() <= 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Estas seguro quieres borrar esta zona?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    _data.DeleteZonas(_id);
                    refresh();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Tienes una maquina enlazada a esta zona.");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            builder.show();
        }
        _cursor.close();
    }

    public void StartFragmentMap(final int id){
        ((MainActivity)getActivity()).ShowMapZone(((MainActivity)getActivity())._data.SearchMachinesByIdZone(id));
    }

    public void refresh() {
        Cursor _cursor = _data.GetZonas();
        _adapter.changeCursor(_cursor);
        _adapter.notifyDataSetChanged();

        ListView lv = (ListView) getActivity().findViewById(R.id._listZone);
        lv.setSelection(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ZONE) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }

        if (requestCode == UPDATE_ZONE) {
            if (resultCode == getActivity().RESULT_OK) {
                refresh();
            }
        }
    }
}

class Buidem_adapterZ extends android.widget.SimpleCursorAdapter {
    private ZonaFragment _fragments;

    private TextView _icon;
    private ImageView _imgDelete;
    private ImageView _imgGmap;

    public Buidem_adapterZ(Context context, int layout, Cursor c, String[] from, int[] to, int flags, ZonaFragment fragment) {
        super(context, layout, c, from, to, flags);
        _fragments = (ZonaFragment) fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = super.getView(position, convertView, parent);

        final Cursor zone = (Cursor) getItem(position);

        _icon = (TextView) view.findViewById(R.id._icon);
        _imgDelete = (ImageView) view.findViewById(R.id._imgDeleteZ) ;
        _imgGmap = (ImageView) view.findViewById(R.id._imgGmap) ;

        _icon.setText(zone.getString(zone.getColumnIndexOrThrow(Datasource.ZONAS_NOMBRE)).substring(0,1));

        if(zone.getString(zone.getColumnIndexOrThrow(Datasource.ZONAS_NOMBRE)).isEmpty()){
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0); ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            _imgGmap.setColorFilter(filter);
        }

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

        _imgGmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int _id;

                View view = (View) v.getParent();
                ListView lv = (ListView) view.getParent();
                int position = lv.getPositionForView(view);
                Cursor _zone = (Cursor) getItem(position);

                _id = _zone.getInt(_zone.getColumnIndexOrThrow(Datasource.TODASTABLAS_ID));

                _fragments.StartFragmentMap(_id);
            }
        });

        return view;
    }
}

/*zonaViewModel.getText().observe(this, new Observer<String>() {
    @Override
    public void onChanged(@Nullable String s) {
        textView.setText(s);
    }
});*/