package com.example.buidemapp.ui.TipoMaquinas;

public class TipoDeMaquina {
    private int Id;
    private String Name;
    private String Color;

    public TipoDeMaquina() {
    }

    public TipoDeMaquina(int id, String name, String color) {
        Id = id;
        Name = name;
        Color = color;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
