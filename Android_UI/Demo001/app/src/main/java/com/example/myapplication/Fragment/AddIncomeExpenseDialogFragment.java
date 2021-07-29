package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.Models.Category;
import com.example.myapplication.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

public class AddIncomeExpenseDialogFragment extends DialogFragment
        implements FragmentResultListener, DatePickerDialog.OnDateSetListener {

    private TextView textViewCreated, textViewCreatedValue;
    private Button buttonCancel, buttonSave;

    public AddIncomeExpenseDialogFragment(){}

    public static AddIncomeExpenseDialogFragment newInstance(Integer id, String name){
        AddIncomeExpenseDialogFragment fragment = new AddIncomeExpenseDialogFragment();
        Bundle arg = new Bundle();
        arg.putInt("id", id);
        arg.putString("name", name);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_add_income_expense_dialog_fragment,
                container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewCreatedValue = (TextView) view.findViewById(R.id.textViewCreatedValue);
        textViewCreated = (TextView) view.findViewById(R.id.textViewCreated);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancelCategoryName);
        buttonSave = (Button) view.findViewById(R.id.buttonSaveCategoryName);

//        String name = getArguments().getString("name", "");
//        Integer id = getArguments().getInt("id", -1);
        // neu id = -1: them moi
        // nguoc lai: cap nhat
//        editTextName.setText(name);

//        editTextName.requestFocus();
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CategoryDAO dao = new CategoryDAO(getContext());
//                String name = editTextName.getText().toString();
//                Category category = new Category();
//                category.setName(name);
//                category.setId(id);
//                if (id == -1){
//                    dao.insert(category);
//                } else {
//                    dao.update(category);
//                }
//
//                getParentFragmentManager().setFragmentResult("key", new Bundle());
//
//                onCancelClick();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });

        textViewCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getChildFragmentManager(), "datePicker");
            }
        });

    }

    private void onCancelClick(){
        dismiss();
    }

    @Override
    public void onFragmentResult(String requestKey, Bundle result) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        textViewCreatedValue.setText(dayOfMonth + "/" + monthOfYear + "/"+year + " ");
    }
}
