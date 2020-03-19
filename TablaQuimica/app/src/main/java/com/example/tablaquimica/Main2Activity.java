package com.example.tablaquimica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {
    private AdaptadorElement element;
    private ArrayList<cElement> Elements = new ArrayList<cElement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("Periodic Table");

        try {
            LoadJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MostrarLlista(Elements);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Game:
                /* Joc */
                Intent intent = new Intent(this, Game.class);
                intent.putExtra("elements",Elements);
                startActivity(intent);
                return true;
            case R.id.Tots:
                MostrarLlista(Elements);
                return true;
            case R.id.Liquids:
                MostrarLlista(newList(Elements, ESTATS.LIQUID));
                return true;
            case R.id.Gasos:
                MostrarLlista(newList(Elements, ESTATS.GAS));
                return true;
            case R.id.Solids:
                MostrarLlista(newList(Elements, ESTATS.SOLID));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadJSON() throws JSONException {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.periodictable);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }

        parseJSON(builder.toString());
    }

    private void parseJSON(String toString) throws JSONException {
        JSONArray array = new JSONArray(toString);

        for(int a = 0;a < array.length();a++){
            JSONObject object = (JSONObject) array.get(a);

            String name = object.getString("name");
            String symbol = object.getString("symbol");
            int num = object.getInt("atomicNumber");
            int year = object.getInt("yearDiscovered");
            String massaA = object.getString("atomicMass");
            String estat = object.getString("standardState");
            String AtomicNombre = object.getString("groupBlock");
            Double ElectronicNegativy = object.getDouble("electronegativity");
            int ElectronicAffinity = object.getInt("electronAffinity");
            String ConfiguracioElectronica = object.getString("electronicConfiguration");
            Double densitat = object.getDouble("density");

            Elements.add(new cElement(symbol,name,num,massaA,Integer.valueOf(estat),Integer.valueOf(AtomicNombre),String.valueOf(ElectronicNegativy),String.valueOf(ElectronicAffinity),ConfiguracioElectronica,densitat,year));
        }
    }

    public void MostrarLlista(ArrayList<cElement> elets) {
        element = new AdaptadorElement(this, elets);
        ListView lst = (ListView) findViewById(R.id.listBasic);
        lst.setAdapter(element);
    }

    public ArrayList<cElement> newList(ArrayList<cElement> elements, ESTATS estat) {
        ArrayList<cElement> newList = new ArrayList<cElement>();

        int x = 0;

        for(int a = 0;a < elements.size();a++){
            if(elements.get(a).get_estat() == estat){
                newList.add(elements.get(a));
            }
        }
        return newList;
    }
}

class AdaptadorElement extends ArrayAdapter<cElement> {

    private Context context;

    public AdaptadorElement(Context context, ArrayList<cElement> elements) {
        super(context, R.layout.elements, elements);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.elements, null);

        final cElement element = (cElement) getItem(position);

        TextView edt = (TextView) item.findViewById(R.id.edtNum);
        edt.setText(String.valueOf(element.getNumero()));
        edt.setTextColor(Color.parseColor("#3a5bb5"));


        /*TextView nom*/
        edt = (TextView) item.findViewById(R.id.edtNom);
        edt.setText(element.getNom());

        /*TextView simbol*/
        edt = (TextView) item.findViewById(R.id.edtSimbol);
        edt.setText(element.getSimbol());
        CambiarEstat(element,edt);

        /*TextView massa*/
        edt = (TextView) item.findViewById(R.id.edtMassa);
        edt.setText(String.valueOf(element.getMassa_Atomica()));

        CambiarNAtomic(element,item);

        item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                /* Nova Activity */
                Intent intent = new Intent(v.getContext(), InformacioElement.class);
                intent.putExtra("element",element);
                context.startActivity(intent);
            }
        });

        return(item);
    }

    public static void CambiarEstat(cElement element,TextView edt){
        switch (element.get_estat()) {
            case SOLID:
                edt.setTextColor(Color.parseColor("BLACK"));
                break;
            case LIQUID:
                edt.setTextColor(Color.parseColor("#0B0B61"));
                break;
            case GAS:
                edt.setTextColor(Color.parseColor("#246b1b"));
                break;
        }
    }

    public static void CambiarNAtomic(cElement element,View item){
        switch (element.get_nombreatomic()) {
            case POST_TRANSITION_METAL:
                item.setBackgroundColor(Color.parseColor("#547674"));
                break;
            case ALKALI_METAL:
                item.setBackgroundColor(Color.parseColor("#FA5858"));
                break;
            case ALKALINE_EARTH_METAL:
                item.setBackgroundColor(Color.parseColor("#F7BE81"));
                break;
            case LANTHANOID:
                item.setBackgroundColor(Color.parseColor("#F7819F"));
                break;
            case ACTINOID:
                item.setBackgroundColor(Color.parseColor("#FA58AC"));
                break;
            case TRANSITION_METAL:
                item.setBackgroundColor(Color.parseColor("#F5A9BC"));
                break;
            case METAL:
                item.setBackgroundColor(Color.parseColor("#A4A4A4"));
                break;
            case METALLOID:
                item.setBackgroundColor(Color.parseColor("#2e8b3c"));
                break;
            case NON_METAL:
                item.setBackgroundColor(Color.parseColor("#58FAAC"));
                break;
            case HALOGEN:
                item.setBackgroundColor(Color.parseColor("#F4FA58"));
                break;
            case NOBLE_GAS:
                item.setBackgroundColor(Color.parseColor("#81F7F3"));
                break;
        }
    }
}
