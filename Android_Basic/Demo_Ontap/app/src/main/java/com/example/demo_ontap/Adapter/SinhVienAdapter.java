package com.example.demo_ontap.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo_ontap.Model.SinhVien;
import com.example.demo_ontap.R;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {

    private List<SinhVien> data;
    private Context context;

    public SinhVienAdapter(List<SinhVien> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SinhVien getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (_view == null){
            view = View.inflate(_viewGroup.getContext(),
                    R.layout.layout_sinh_vien_adapter, null);
        }

        TextView maSV = (TextView) view.findViewById(R.id.adapter_maSV);
        TextView hoTen = (TextView) view.findViewById(R.id.adapter_hoTen);
        TextView diemTrungBinh = (TextView) view.findViewById(R.id.adapter_diemTrungBinh);
        TextView xepLoai = (TextView) view.findViewById(R.id.adapter_xepLoai);

        SinhVien sv = getItem(_i);
        maSV.setText(sv.getMaSV());
        hoTen.setText(sv.getHoTen());
        diemTrungBinh.setText(String.valueOf(sv.getDiemTrungBinh()));
        xepLoai.setText(sv.getXepLoai());

        return view;
    }
}
