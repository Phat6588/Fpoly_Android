package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Adapter.CategoryItemAdapter;
import com.example.myapplication.DAO.CategoryDAO;
import com.example.myapplication.DAO.IncomeExpenseDAO;
import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.IncomeExpense;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

public class AddIncomeExpenseDialogFragment extends DialogFragment
        implements FragmentResultListener, DatePickerDialog.OnDateSetListener {

    private EditText editText_name, editText_description, editText_amount;
    private TextView textViewCreated, textViewCreatedValue;
    private Spinner spinnerCategory;
    private Button buttonCancelCategoryName, buttonSaveCategoryName;

    private List<Category> data;
    private CategoryItemAdapter adapter;

    private Integer id = -1;
    private Integer flag = Constants.INCOME;
    private Integer category_id = 0;
    private Date createdDate = new Date();

    public AddIncomeExpenseDialogFragment(){}

    public static AddIncomeExpenseDialogFragment newInstance(Integer id, Integer flag){
        AddIncomeExpenseDialogFragment fragment = new AddIncomeExpenseDialogFragment();
        Bundle arg = new Bundle();
        arg.putInt("id", id);
        arg.putInt("flag", flag);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_add_income_expense_dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewCreatedValue = (TextView) view.findViewById(R.id.textViewCreatedValue);
        textViewCreated = (TextView) view.findViewById(R.id.textViewCreated);
        buttonCancelCategoryName = (Button) view.findViewById(R.id.buttonCancelCategoryName);
        buttonSaveCategoryName = (Button) view.findViewById(R.id.buttonSaveCategoryName);
        editText_name = (EditText) view.findViewById(R.id.editText_name);
        editText_description = (EditText) view.findViewById(R.id.editText_description);
        editText_amount = (EditText) view.findViewById(R.id.editText_amount);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);

        // lay danh sach category cho vao spinner
        data = (new CategoryDAO(getContext())).get();
        adapter = new CategoryItemAdapter(getContext(), data);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setSelection(0);

        flag = getArguments().getInt("flag", Constants.INCOME);
        id = getArguments().getInt("id", -1);
        // neu id = -1: them moi
        // nguoc lai: cap nhat
//        editTextName.setText(name);

//        editTextName.requestFocus();
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        buttonSaveCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncomeExpenseDAO dao = new IncomeExpenseDAO(getContext());
                IncomeExpense model = new IncomeExpense();
                model.setName(editText_name.getText().toString());
                model.setDescription(editText_description.getText().toString());
                model.setAmount(Double.parseDouble(editText_amount.getText().toString()));
                model.setId(id);
                model.setFlag(flag);
                model.setCreatedDate(createdDate);
                model.setCategoryId(category_id);
                if (id == -1){
                    dao.insert(model);
                } else {
                    dao.update(model);
                }
                getParentFragmentManager().setFragmentResult("key", new Bundle());
                onCancelClick();
            }
        });

        buttonCancelCategoryName.setOnClickListener(new View.OnClickListener() {
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

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) adapterView.getItemAtPosition(i);
                category_id = category.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        createdDate = calendar.getTime();
        textViewCreatedValue.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/"+year + " ");
    }
}
