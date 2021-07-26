package com.example.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.Models.Category;
import com.example.myapplication.R;

import androidx.fragment.app.DialogFragment;

public class AddCategoryDialogFragment extends DialogFragment {

    public interface OnSaveClickListener{
        public void onSaveCategoryClick();
    }

    private OnSaveClickListener listener;

    private EditText editTextName;
    private Button buttonCancel, buttonSave;

    public AddCategoryDialogFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSaveClickListener){
            listener = (OnSaveClickListener) context;
        } else {
            throw new ClassCastException(context.toString());
        }
    }

    public static AddCategoryDialogFragment newInstance(String title){
        AddCategoryDialogFragment fragment = new AddCategoryDialogFragment();
        Bundle arg = new Bundle();
        arg.putString("title", title);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_add_category_dialog_fragment,
                container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = (EditText) view.findViewById(R.id.editText_category_name);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancelCategoryName);
        buttonSave = (Button) view.findViewById(R.id.buttonSaveCategoryName);

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        editTextName.requestFocus();
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                Category category = new Category();
                category.setName(name);

                CategoryDAO dao = new CategoryDAO(getContext());
                dao.insert(category);
                listener.onSaveCategoryClick();
                onCancelClick();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });


    }

    private void onCancelClick(){
        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
