package com.huydh54.fpolyapp.Fragment.TrangMain;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.huydh54.fpolyapp.Adapter.KhoaHocAdapter;
import com.huydh54.fpolyapp.Adapter.SapXepKHAdapter;
import com.huydh54.fpolyapp.DAO.KhoaHocDAO;
import com.huydh54.fpolyapp.Model.KhoaHoc;
import com.huydh54.fpolyapp.Model.XDate;
import com.huydh54.fpolyapp.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HocTapFragment extends Fragment {

    GridView dsKhoaHoc;
    KhoaHocAdapter khoaHocAdapter;
    KhoaHocDAO khoaHocDAO;
    SearchView searchView;
    LinearLayout sapXepKH;
    ArrayList<KhoaHoc> khoaHocList;
    ArrayList<KhoaHoc> khoaHocListLoc;
    XDate xDate = new XDate();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoc_tap, container, false);

        dsKhoaHoc = (GridView) view.findViewById(R.id.gvKhoaHoc);
        searchView = view.findViewById(R.id.sv_Search);
        khoaHocDAO = new KhoaHocDAO(view.getContext());
        sapXepKH = view.findViewById(R.id.sapXepKH);

        khoaHocDAO.them("BUS1024", "load_kh1", "20/12/2020","Kinh tế");
        khoaHocDAO.them("ENT2125", "load_kh2", "15/11/2020","Tiếng Anh 2.1");
        khoaHocDAO.them("TOU2031", "load_kh3", "10/11/2020","Nghiệp vụ hướng dẫn 1");
        khoaHocDAO.them("HOS105", "load_kh4", "08/11/2020","An toàn & an ninh NHKS");
        khoaHocDAO.them("WEB2023", "load_kh5", "25/10/2020","Thiết kế Web");
        khoaHocDAO.them("VIE1016", "load_kh6", "15/10/2020","Chính trị");
        khoaHocDAO.them("PSY1011", "load_kh7", "10/10/2020","Tâm lý giao tiếp");
        khoaHocDAO.them("VIE1026", "load_kh8", "08/10/2020","Pháp luật");
        khoaHocDAO.them("COM107", "load_kh9", "05/10/2020","Tin học");

        khoaHocList = khoaHocDAO.doc();

        khoaHocAdapter = new KhoaHocAdapter(view.getContext(), khoaHocList);
        dsKhoaHoc.setAdapter(khoaHocAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                khoaHocAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                khoaHocAdapter.getFilter().filter(newText);
                return false;
            }
        });

        khoaHocListLoc = khoaHocList;

        sapXepKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_sapxepkhoahoc);

                ListView lvTuyChon = dialog.findViewById(R.id.lvDsTuyChonSX);
                ImageView thoat = dialog.findViewById(R.id.imgThoatSX);

                ArrayList<String> dsTuyChon = new ArrayList<>();

                SapXepKHAdapter sapXepKHAdapter = new SapXepKHAdapter(dialog.getContext(), dsTuyChon);
                lvTuyChon.setAdapter(sapXepKHAdapter);
                dsTuyChon.add("Khóa học mới nhất");
                dsTuyChon.add("Khóa học cũ nhất");
                dsTuyChon.add("A đến Z theo mã");
                dsTuyChon.add("Z đến A theo mã");
                dsTuyChon.add("A đến Z theo tên");
                dsTuyChon.add("Z đến A theo tên");
                dsTuyChon.add("Trở về mặc định");

                thoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                lvTuyChon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i == 0) khoaHocListLoc = moiNhat(khoaHocListLoc);
                        if(i == 1) khoaHocListLoc = cuNhat(khoaHocListLoc);
                        if(i == 2) khoaHocListLoc = orderByCodeAZ(khoaHocListLoc);
                        if(i == 3) khoaHocListLoc = orderByCodeZA(khoaHocListLoc);
                        if(i == 4) khoaHocListLoc = orderByNameAZ(khoaHocListLoc);
                        if(i == 5) khoaHocListLoc = orderByNameZA(khoaHocListLoc);
                        if(i == 6) khoaHocListLoc = khoaHocDAO.doc();
                        khoaHocAdapter = new KhoaHocAdapter(container.getContext(), khoaHocListLoc);
                        dsKhoaHoc.setAdapter(khoaHocAdapter);
                        dialog.cancel();
                        searchView.setQuery("", false);
                        Toast.makeText(view.getContext(), "Sắp xếp thành công!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    private ArrayList<KhoaHoc> orderByNameAZ(ArrayList<KhoaHoc> khoaHocList) {
        Comparator<KhoaHoc> com1 = new Comparator<KhoaHoc>() {

            public int compare(KhoaHoc o1, KhoaHoc o2) {
                return o1.getTen().compareTo(o2.getTen());
            }
        };
        Collections.sort(khoaHocList, com1);
        return khoaHocList;
    }

    private ArrayList<KhoaHoc> orderByNameZA(ArrayList<KhoaHoc> khoaHocList) {
        Comparator<KhoaHoc> com1 = new Comparator<KhoaHoc>() {

            public int compare(KhoaHoc o1, KhoaHoc o2) {
                return o2.getTen().compareTo(o1.getTen());
            }
        };
        Collections.sort(khoaHocList, com1);
        return khoaHocList;
    }

    private ArrayList<KhoaHoc> orderByCodeAZ(ArrayList<KhoaHoc> khoaHocList) {
        Comparator<KhoaHoc> com1 = new Comparator<KhoaHoc>() {

            public int compare(KhoaHoc o1, KhoaHoc o2) {
                return o1.getMa().compareTo(o2.getMa());
            }
        };
        Collections.sort(khoaHocList, com1);
        return khoaHocList;
    }

    private ArrayList<KhoaHoc> orderByCodeZA(ArrayList<KhoaHoc> khoaHocList) {
        Comparator<KhoaHoc> com1 = new Comparator<KhoaHoc>() {

            public int compare(KhoaHoc o1, KhoaHoc o2) {
                return o2.getMa().compareTo(o1.getMa());
            }
        };
        Collections.sort(khoaHocList, com1);
        return khoaHocList;
    }

    private ArrayList<KhoaHoc> cuNhat(ArrayList<KhoaHoc> khoaHocList) {
        ArrayList<KhoaHoc> locNgayList = khoaHocList;
        for(int i=0; i<locNgayList.size(); i++){
            try {
                Date ngayBatDau = xDate.dinhDangNgay(locNgayList.get(i).getThoiGian());
            } catch (ParseException e) {
                locNgayList.remove(i);
            }
        }
        Comparator<KhoaHoc> com1 = new Comparator<KhoaHoc>() {
            int i;
            public int compare(KhoaHoc o1, KhoaHoc o2) {
                try {
                    if (xDate.dinhDangNgay(o1.getThoiGian()).before(xDate.dinhDangNgay(o2.getThoiGian()))){
                        i = -1;
                    } else if (xDate.dinhDangNgay(o2.getThoiGian()).before(xDate.dinhDangNgay(o1.getThoiGian()))) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                } catch (ParseException e) {
                    i = 0;
                }
                return i;
            }
        };
        Collections.sort(locNgayList, com1);
        return locNgayList;
    }

    private ArrayList<KhoaHoc> moiNhat(ArrayList<KhoaHoc> khoaHocList) {
        ArrayList<KhoaHoc> locNgayList = khoaHocList;
        for(int i=0; i<locNgayList.size(); i++){
            try {
                Date ngayBatDau = xDate.dinhDangNgay(locNgayList.get(i).getThoiGian());
            } catch (ParseException e) {
                locNgayList.remove(i);
            }
        }
        Comparator<KhoaHoc> com1 = new Comparator<KhoaHoc>() {
            int i;
            public int compare(KhoaHoc o1, KhoaHoc o2) {
                try {
                    if (xDate.dinhDangNgay(o2.getThoiGian()).before(xDate.dinhDangNgay(o1.getThoiGian()))){
                        i = -1;
                    } else if (xDate.dinhDangNgay(o1.getThoiGian()).before(xDate.dinhDangNgay(o2.getThoiGian()))) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                } catch (ParseException e) {
                    i = 0;
                }
                return i;
            }
        };
        Collections.sort(locNgayList, com1);
        return locNgayList;
    }
}