package com.inc.fsi.fnn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.inc.fsi.fnn.R;

public class ReceiveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView =  inflater.inflate(R.layout.fragment_receive, container, false);

        final TextView name = fragView.findViewById(R.id.name);
        final TextView status = fragView.findViewById(R.id.status);
        final ImageView face = fragView.findViewById(R.id.face);

        fragView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("Uchenna Chijioke");
                status.setText("Watchlisted");
                face.setImageResource(R.drawable.sample_avatar);
            }
        });

        return fragView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
