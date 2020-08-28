package com.example.buidemapp.ui.Weather;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buidemapp.Api;
import com.example.buidemapp.MainActivity;
import com.example.buidemapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class WeatherFragment extends Fragment {

    private int _temp;
    private int _tempMax;
    private int _tempMin;
    private String city;
    private String urlIcon;

    private JSONObject _weather = null;

    private View root;

    private TextView tvTemp;
    private TextView tvTempMax;
    private TextView tvTempMin;
    private TextView tvCity;

    private ImageView imgIcon;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_weather, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tvTemp = (TextView) root.findViewById(R.id.tvTempNormal);
        tvTempMax = (TextView) root.findViewById(R.id.tvTempmax);
        tvTempMin = (TextView) root.findViewById(R.id.tvTemMin);
        tvCity = (TextView) root.findViewById(R.id.tvNameCity);
        imgIcon = (ImageView) root.findViewById(R.id.imgIcon);

        city = MainActivity._namePoblacio;

        GetApi(city);

        return root;
    }

    private void GetApi(String city){
        final ProgressDialog Dialog = new ProgressDialog(getActivity());
        Dialog.setCancelable(false);
        Dialog.setCanceledOnTouchOutside(false);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0,10000);

        client.get(Api.BaseApi + "?q=" + city + Api.apiKey + Api.IdiomEs, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                Dialog.setMessage("Descargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                root.setVisibility(View.VISIBLE);

                String str = new String(responseBody);

                try {
                    _weather = new JSONObject(str);

                    _temp = (int) Math.round(_weather.getJSONObject("main").getDouble("temp") - 273.15);
                    _tempMax = (int) Math.round(_weather.getJSONObject("main").getDouble("temp_max") - 273.15);
                    _tempMin = (int) Math.round(_weather.getJSONObject("main").getDouble("temp_min") - 273.15);
                    urlIcon = "https://openweathermap.org/img/w/" + _weather.getJSONArray("weather").getJSONObject(0).getString("icon")  + ".png";

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    LoadData();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Dialog.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Dialog.hide();
            }
        });
    }

    private void LoadData() throws IOException {
        tvTemp.setText(String.valueOf(_temp) + "º");
        tvTempMax.setText(String.valueOf(_tempMax) + "º");
        tvTempMin.setText(String.valueOf(_tempMin) + "º");
        tvCity.setText(city);

        URL url = new URL(urlIcon);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        imgIcon.setImageBitmap(bmp);
    }
}
