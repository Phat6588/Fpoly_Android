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

public class FirstFragment extends Fragment {

    public interface OnTextViewClickListener{
        public void onTextViewClick(String text);

    }

    private OnTextViewClickListener listener;
    private TextView textViewFrg1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTextViewClickListener){
            listener = (OnTextViewClickListener) context;
        } else {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_first_fragment,
                container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        textViewFrg1 = view.findViewById(R.id.textViewFrg1);
//        textViewFrg1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onTextViewClick("Hey hello!!!!");
//            }
//        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
