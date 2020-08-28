package com.example.buidemapp;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.buidemapp.Db_AppBuidem.Datasource;
import com.example.buidemapp.ui.Mapa.MapaFragment;
import com.example.buidemapp.ui.Maquinas.Maquina;
import com.example.buidemapp.ui.Maquinas.MaquinaFragment;
import com.example.buidemapp.ui.TipoMaquinas.TipoMaquinasFragment;
import com.example.buidemapp.ui.Zonas.ZonaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public Datasource _data;
    public BottomNavigationView Navigation;

    public static String _namePoblacio;
    private boolean SiEntraDesdeElIcono = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_maquinas, R.id.navigation_zonas, R.id.navigation_tiposmaquinas, R.id.navigation_mapa)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        loadFragment(new MaquinaFragment());

        Navigation = findViewById(R.id.nav_view);
        Navigation.setOnNavigationItemSelectedListener(this);

        _data = new Datasource(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_maquinas:
                this._namePoblacio = null;
                fragment = new MaquinaFragment();
                break;

            case R.id.navigation_zonas:
                this._namePoblacio = null;
                fragment = new ZonaFragment();
                break;

            case R.id.navigation_tiposmaquinas:
                this._namePoblacio = null;
                fragment = new TipoMaquinasFragment();
                break;

            case R.id.navigation_mapa:
                this._namePoblacio = !SiEntraDesdeElIcono ? null : this._namePoblacio;
                fragment = new MapaFragment();
                break;
        }

        this.SiEntraDesdeElIcono = false;
        return loadFragment(fragment);
    }

    public void ShowMap(Maquina _machine) {
        this.SiEntraDesdeElIcono = true;
        this._namePoblacio = _machine.get_poblacion();
        Navigation.setSelectedItemId(R.id.navigation_mapa);
        loadFragment(new MapaFragment(_machine));
    }

    public void ShowMapZone(ArrayList<Maquina> _machines) {
        this.SiEntraDesdeElIcono = true;
        if(_machines.size() > 0) {
            this._namePoblacio = _machines.get(0).get_poblacion();
        }
        Navigation.setSelectedItemId(R.id.navigation_mapa);
        loadFragment(new MapaFragment(_machines));
    }
}
