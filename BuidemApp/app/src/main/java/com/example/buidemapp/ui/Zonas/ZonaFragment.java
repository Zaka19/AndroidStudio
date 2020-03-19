package com.example.buidemapp.ui.Zonas;

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

public class ZonaFragment extends Fragment {

    private ZonaViewModel zonaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        zonaViewModel =
                ViewModelProviders.of(this).get(ZonaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_zona, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        zonaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}