package com.example.tablaquimica;

import java.io.Serializable;

enum ESTATS{
    LIQUID,SOLID,GAS;
}

enum NOMBREATOMIC{
    ALKALI_METAL,ALKALINE_EARTH_METAL,LANTHANOID,ACTINOID,TRANSITION_METAL,METAL,
    METALLOID,NON_METAL,HALOGEN,NOBLE_GAS,POST_TRANSITION_METAL;
}

public class cElement implements Serializable {

    private String Simbol;
    private String Nom;
    private int Numero;
    private String Massa_Atomica;
    private ESTATS _estat;
    private NOMBREATOMIC _nombreatomic;
    private String ElectronicNegativy;
    private String ElectronicAffinity;
    private String ConfiguracioElectronica;
    private int any;
    private Double _densitat;
    private String url;


    public String getSimbol() {
        return Simbol;
    }

    public void setSimbol(String simbol) {
        Simbol = simbol;
    }


    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }


    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }


    public String getMassa_Atomica() {
        return Massa_Atomica;
    }

    public void setMassa_Atomica(String massa_Atomica) {
        Massa_Atomica = massa_Atomica;
    }


    public ESTATS get_estat() {
        return _estat;
    }

    public void set_estat(ESTATS _estat) {
        this._estat = _estat;
    }


    public NOMBREATOMIC get_nombreatomic() {
        return _nombreatomic;
    }

    public void set_nombreatomic(NOMBREATOMIC _nombreatomic) {
        this._nombreatomic = _nombreatomic;
    }


    public String getConfiguracioElectronica() {
        return ConfiguracioElectronica;
    }

    public void setConfiguracioElectronica(String configuracioElectronica) {
        ConfiguracioElectronica = configuracioElectronica;
    }


    public Double get_densitat() {
        return _densitat;
    }

    public void set_densitat(Double _densitat) {
        this._densitat = _densitat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getElectronicNegativy() {
        return ElectronicNegativy;
    }

    public void setElectronicNegativy(String electronicNegativy) {
        ElectronicNegativy = electronicNegativy;
    }

    public String getElectronicAffinity() {
        return ElectronicAffinity;
    }

    public void setElectronicAffinity(String electronicAffinity) {
        ElectronicAffinity = electronicAffinity;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = any;
    }

    public cElement(String simbol, String nom, int numero, String massa_Atomica, int _estat, int _nombreatomic, String electronicNegativy, String electronicAffinity, String configuracioElectronica, Double densitat,int any) {
        Simbol = simbol;
        Nom = nom;
        Numero = numero;
        Massa_Atomica = massa_Atomica;
        this._estat = estats(_estat);
        this._nombreatomic = nombreatomics(_nombreatomic);
        ElectronicNegativy = electronicNegativy;
        ElectronicAffinity = electronicAffinity;
        ConfiguracioElectronica = configuracioElectronica;
        this._densitat = densitat;
        this.any = any;
        this.url = "https://en.wikipedia.org/wiki/"+nom;
    }

    public ESTATS estats(int num){
        ESTATS _estat = null;

        switch (num){
            case 0:
                _estat = ESTATS.LIQUID;
                break;
            case 1:
                _estat = ESTATS.SOLID;
                break;
            case 2:
                _estat = ESTATS.GAS;
                break;
        }

        return _estat;
    }

    public NOMBREATOMIC nombreatomics(int num){
        NOMBREATOMIC _nombreAtomic = null;

        switch (num){
            case 0:
                _nombreAtomic = NOMBREATOMIC.ALKALI_METAL;
                break;
            case 1:
                _nombreAtomic = NOMBREATOMIC.ALKALINE_EARTH_METAL;
                break;
            case 2:
                _nombreAtomic = NOMBREATOMIC.LANTHANOID;
                break;
            case 3:
                _nombreAtomic = NOMBREATOMIC.ACTINOID;
                break;
            case 4:
                _nombreAtomic = NOMBREATOMIC.TRANSITION_METAL;
                break;
            case 5:
                _nombreAtomic = NOMBREATOMIC.METAL;
                break;
            case 6:
                _nombreAtomic = NOMBREATOMIC.METALLOID;
                break;
            case 7:
                _nombreAtomic = NOMBREATOMIC.NON_METAL;
                break;
            case 8:
                _nombreAtomic = NOMBREATOMIC.HALOGEN;
                break;
            case 9:
                _nombreAtomic = NOMBREATOMIC.NOBLE_GAS;
                break;
            case 10:
                _nombreAtomic = NOMBREATOMIC.POST_TRANSITION_METAL;
                break;
        }

        return _nombreAtomic;
    }
}
