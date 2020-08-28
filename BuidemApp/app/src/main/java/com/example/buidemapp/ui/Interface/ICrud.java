package com.example.buidemapp.ui.Interface;

public interface ICrud {
    void Add();

    void Update(long _id);

    void Delete(final long _id);

    void refresh();
}
