package com.example.buidemapp.ui.Mapa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.buidemapp.R;

public class MapaFragment extends Fragment {

    private MapaViewModel mapaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapaViewModel =
                ViewModelProviders.of(this).get(MapaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mapa, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        mapaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}