package com.example.crudsqllite_zaka;

public class Work {

    private String _code;
    private String _description;
    private Double _pvp;
    private Double _stock;

    public Work(String _code, String _description, Double _pvp, Double _stock) {
        this._code = _code;
        this._description = _description;
        this._pvp = _pvp;
        this._stock = _stock;
    }

    public String get_code() {
        return _code;
    }

    public void set_code(String _code) {
        this._code = _code;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public Double get_pvp() {
        return _pvp;
    }

    public void set_pvp(Double _pvp) {
        this._pvp = _pvp;
    }

    public Double get_stock() {
        return _stock;
    }

    public void set_stock(Double _stock) {
        this._stock = _stock;
    }

    @Override
    public String toString() {
        return "Work{" +
                "_code='" + _code + '\'' +
                ", _description='" + _description + '\'' +
                ", _pvp=" + _pvp +
                ", _stock=" + _stock +
                '}';
    }
}
