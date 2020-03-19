package com.example.buidemapp.ui.TipoMaquinas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.buidemapp.R;

public class TipoMaquinasFragment extends Fragment {

    private TipoMaquinasViewModel tipoMaquinasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tipoMaquinasViewModel =
                ViewModelProviders.of(this).get(TipoMaquinasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tipomaquina, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        tipoMaquinasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}