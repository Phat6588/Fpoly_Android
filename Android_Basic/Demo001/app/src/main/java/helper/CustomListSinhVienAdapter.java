package helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.demo001.R;

import java.util.List;

import model.SinhVien;

public class CustomListSinhVienAdapter extends BaseAdapter {

    private List<SinhVien> data;

    public CustomListSinhVienAdapter(List<SinhVien> _data) {
        data = _data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewSinhVien;
        if (view == null){
            viewSinhVien = View.inflate(viewGroup.getContext(),
                    R.layout.list_sinh_vien_layout, null);
        }
        else {
            viewSinhVien = view;
        }
        SinhVien sinhVien = (SinhVien) getItem(i);
        ((TextView)viewSinhVien.findViewById(R.id.textView_MaSinhVien))
                .setText(sinhVien.getMaSV());
        ((TextView)viewSinhVien.findViewById(R.id.textView_TenSinhVien))
                .setText(sinhVien.getHoTen());

        return viewSinhVien;
    }
}
