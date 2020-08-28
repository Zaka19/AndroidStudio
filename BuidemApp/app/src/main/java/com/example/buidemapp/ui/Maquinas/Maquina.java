package com.example.buidemapp.ui.Maquinas;

public class Maquina {
    private String Id;
    private String _nombreCliente;
    private String _direccion;
    private String _codigoPostal;
    private String _poblacion;
    private String _telefono;
    private String _email;
    private String _numeroSerie;
    private String _fecha;
    private int _tipoMaquina;
    private int _zonaMaquina;

    public Maquina(){

    }

    public Maquina(String id, String _nombreCliente, String _direccion, String _codigoPostal, String _poblacion, String _telefono, String _email, String _numeroSerie, String _fecha, int _tipoMaquina, int _zonaMaquina) {
        Id = id;
        this._nombreCliente = _nombreCliente;
        this._direccion = _direccion;
        this._codigoPostal = _codigoPostal;
        this._poblacion = _poblacion;
        this._telefono = _telefono;
        this._email = _email;
        this._numeroSerie = _numeroSerie;
        this._fecha = _fecha;
        this._tipoMaquina = _tipoMaquina;
        this._zonaMaquina = _zonaMaquina;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String get_nombreCliente() {
        return _nombreCliente;
    }

    public void set_nombreCliente(String _nombreCliente) {
        this._nombreCliente = _nombreCliente;
    }

    public String get_direccion() {
        return _direccion;
    }

    public void set_direccion(String _direccion) {
        this._direccion = _direccion;
    }

    public String get_codigoPostal() {
        return _codigoPostal;
    }

    public void set_codigoPostal(String _codigoPostal) {
        this._codigoPostal = _codigoPostal;
    }

    public String get_poblacion() {
        return _poblacion;
    }

    public void set_poblacion(String _poblacion) {
        this._poblacion = _poblacion;
    }

    public String get_telefono() {
        return _telefono;
    }

    public void set_telefono(String _telefono) {
        this._telefono = _telefono;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_numeroSerie() {
        return _numeroSerie;
    }

    public void set_numeroSerie(String _numeroSerie) {
        this._numeroSerie = _numeroSerie;
    }

    public String get_fecha() {
        return _fecha;
    }

    public void set_fecha(String _fecha) {
        this._fecha = _fecha;
    }

    public int get_tipoMaquina() {
        return _tipoMaquina;
    }

    public void set_tipoMaquina(int _tipoMaquina) {
        this._tipoMaquina = _tipoMaquina;
    }

    public int get_zonaMaquina() {
        return _zonaMaquina;
    }

    public void set_zonaMaquina(int _zonaMaquina) {
        this._zonaMaquina = _zonaMaquina;
    }

    @Override
    public String toString() {
        return "MaquinaViewModel{" +
                "_nombreCliente='" + _nombreCliente + '\'' +
                ", _direccion='" + _direccion + '\'' +
                ", _codigoPostal='" + _codigoPostal + '\'' +
                ", _poblacion='" + _poblacion + '\'' +
                ", _telefono='" + _telefono + '\'' +
                ", _email='" + _email + '\'' +
                ", _numeroSerie='" + _numeroSerie + '\'' +
                ", _fecha='" + _fecha + '\'' +
                ", _tipoMaquina='" + _tipoMaquina + '\'' +
                ", _zonaMaquina='" + _zonaMaquina + '\'' +
                '}';
    }
}
