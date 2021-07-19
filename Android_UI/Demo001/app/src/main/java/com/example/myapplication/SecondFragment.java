package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    private TextView textViewFrg2;

    // chay 1st
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    // chay 2nd
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_second_fragment,
                container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textViewFrg2 = view.findViewById(R.id.textViewFrg2);
        String text = getArguments().getString("my_key", "");
        textViewFrg2.setText(text);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static SecondFragment newInstance(String text){
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("my_key", text);
        fragment.setArguments(args);
        return fragment;
    }
}
