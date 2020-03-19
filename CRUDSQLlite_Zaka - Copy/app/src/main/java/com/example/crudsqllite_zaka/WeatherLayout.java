package com.example.crudsqllite_zaka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class WeatherLayout extends AppCompatActivity {

    CoordinatorLayout _c;

    TextView tvCity;
    TextView tvWeather;
    TextView tvTemp;
    TextView tvTempMax;
    TextView tvTempMin;
    TextView tvFeelLike;
    TextView tvDetails;
    TextView tvHum;
    TextView tvWind;
    TextView tvVis;
    TextView tvPre;
    ImageView imgIcon;

    EditText edt;

    JSONObject ciudad = null;
    City _ciutat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        _c = findViewById(R.id.coord);
        _c.setBackground(getDrawable(R.drawable.gg));

        tvCity = findViewById(R.id.tvCity);
        tvWeather = findViewById(R.id.tvWeather);
        tvTemp = findViewById(R.id.tvTemp);
        tvTempMax = findViewById(R.id.tvTempMax);
        tvTempMin = findViewById(R.id.tvTempMin);
        tvFeelLike = findViewById(R.id.tvFeel);
        tvDetails = findViewById(R.id.tvDetails);
        tvHum = findViewById(R.id.tvHum);
        tvWind = findViewById(R.id.tvWind);
        tvVis = findViewById(R.id.tvAlgo);
        tvPre = findViewById(R.id.tvPres);

        imgIcon = findViewById(R.id.IconWeather);

        edt = findViewById(R.id.edtCiudad);

        visibility(View.GONE);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btnWea);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Search();
            }

        });
    }

    public void Search(){

        final ProgressDialog Dialog = new ProgressDialog(this);
        Dialog.setCancelable(false);
        Dialog.setCanceledOnTouchOutside(false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0,10000);

        client.get( Api.BaseApi + "?q=" + edt.getText() + Api.apiKey + Api.IdiomEs, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Descargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Dialog.setMessage("Procesando datos...");

                String str = new String(responseBody);

                try {
                    ciudad = new JSONObject(str);
                    _ciutat = new City(ciudad.getString("name"),
                            ciudad.getJSONObject("main").getDouble("temp"),
                            ciudad.getJSONObject("main").getDouble("temp_max"),
                            ciudad.getJSONObject("main").getDouble("temp_min"),
                            ciudad.getJSONObject("main").getDouble("feels_like"),
                            ciudad.getJSONArray("weather").getJSONObject(0).getString("icon"),
                            ciudad.getJSONArray("weather").getJSONObject(0).getString("description"),
                            ciudad.getJSONObject("main").getDouble("pressure"),
                            ciudad.getJSONObject("wind").getDouble("speed"),
                            ciudad.getJSONObject("main").getDouble("humidity"),
                            ciudad.getDouble("visibility")
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    InsertInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                visibility(View.VISIBLE);

                Dialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String str = new String(error.getMessage().toString());
                String valor = str;
                Dialog.hide();

                Snackbar _snack = Snackbar.make(findViewById(R.id.coord),valor,Snackbar.LENGTH_SHORT);
                _snack.show();
                return;
            }
        });
    }

    public void visibility(int _view){
        tvCity.setVisibility(_view);
        tvWeather.setVisibility(_view);
        tvTemp.setVisibility(_view);
        tvTempMax.setVisibility(_view);
        tvTempMin.setVisibility(_view);
        tvFeelLike.setVisibility(_view);
        tvDetails.setVisibility(_view);
        tvPre.setVisibility(_view);
        tvHum.setVisibility(_view);
        tvVis.setVisibility(_view);
        tvWind.setVisibility(_view);
        imgIcon.setVisibility(_view);
    }

    public void InsertInfo() throws IOException {
        tvCity.setText(_ciutat.get_nameCity());
        tvWeather.setText(_ciutat.getWeather());
        tvTemp.setText(String.valueOf((int)_ciutat.getTemp()) + "º");
        tvTempMax.setText(String.valueOf((int)_ciutat.getTempMax()) + "º");
        tvTempMin.setText(String.valueOf((int)_ciutat.getTempMin()) + "º");
        tvFeelLike.setText("Sensacion termica " + String.valueOf((int)_ciutat.getFeelLike()) + "º");
        tvPre.setText("Presion " + _ciutat.getPresion() + "mb");
        tvHum.setText("Humedad " + _ciutat.getHumedad() );
        tvVis.setText("Visiblidad " + _ciutat.getVisibilidad());
        tvWind.setText("Vel.Viento " + _ciutat.getVelocidad_viento() + "km/h");

        URL url = new URL(_ciutat.get_url());
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        imgIcon.setImageBitmap(bmp);

        edt.setText("");
    }
}
