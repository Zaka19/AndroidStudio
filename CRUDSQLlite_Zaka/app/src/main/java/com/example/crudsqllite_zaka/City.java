package com.example.crudsqllite_zaka;

public class City {
   private String _nameCity;
   private double Temp;
   private double TempMax;
   private double TempMin;
   private double FeelLike;
   private String _url;
   private String weather;
   private int Presion;
   private int Velocidad_viento;
   private int Humedad;
    private int Visibilidad;

    public City(String _nameCity, double temp, double tempMax, double tempMin, double feelLike, String _url, String weather, double _presion, double vel, double hum, double visi) {
        this._nameCity = _nameCity;
        Temp = Convert(temp);
        TempMax = Convert(tempMax);
        TempMin = Convert(tempMin);
        FeelLike = Convert(feelLike);
        this._url = "https://openweathermap.org/img/w/" + _url + ".png";
        this.weather = weather;
        this.Presion = (int)_presion;
        this.Velocidad_viento = (int)vel;
        this.Humedad = (int)hum;
        this.Visibilidad = (int)visi;
    }

    public String get_nameCity() {
        return _nameCity;
    }

    public void set_nameCity(String _nameCity) {
        this._nameCity = _nameCity;
    }

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getTempMax() {
        return TempMax;
    }

    public void setTempMax(double tempMax) {
        TempMax = tempMax;
    }

    public double getTempMin() {
        return TempMin;
    }

    public void setTempMin(double tempMin) {
        TempMin = tempMin;
    }

    public double getFeelLike() {
        return FeelLike;
    }

    public void setFeelLike(double feelLike) {
        FeelLike = feelLike;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getPresion() {
        return Presion;
    }

    public void setPresion(int presion) {
        Presion = presion;
    }

    public int getVelocidad_viento() {
        return Velocidad_viento;
    }

    public void setVelocidad_viento(int velocidad_viento) {
        Velocidad_viento = velocidad_viento;
    }

    public int getHumedad() {
        return Humedad;
    }

    public void setHumedad(int humedad) {
        Humedad = humedad;
    }

    public int getVisibilidad() {
        return Visibilidad;
    }

    public void setVisibilidad(int visibilidad) {
        Visibilidad = visibilidad;
    }

    public double Convert(double x){
        return x - 273.15;
    }

    @Override
    public String toString() {
        return "City{" +
                "_nameCity='" + _nameCity + '\'' +
                ", Temp=" + Temp +
                ", TempMax=" + TempMax +
                ", TempMin=" + TempMin +
                ", FeelLike=" + FeelLike +
                ", _url='" + _url + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
