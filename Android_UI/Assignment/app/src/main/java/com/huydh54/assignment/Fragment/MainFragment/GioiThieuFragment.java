package com.huydh54.assignment.Fragment.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.huydh54.assignment.Adapter.KeHoachAdapter;
import com.huydh54.assignment.Model.TuyChonKeHoach;
import com.huydh54.assignment.R;

import java.util.ArrayList;

public class GioiThieuFragment extends Fragment {

    ListView lvKeHoach;
    ArrayList<TuyChonKeHoach> dsKeHoach;
    KeHoachAdapter keHoachAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);

        lvKeHoach = view.findViewById(R.id.lvKeHoach);
        dsKeHoach = new ArrayList<>();
        dsKeHoach.add(new TuyChonKeHoach(R.drawable.ic_kehoach_money, "Ngân sách",
                "Một kế hoạch tài chính giúp bạn cân bằng được khoảng thu và khoảng chi của mình."));
        dsKeHoach.add(new TuyChonKeHoach(R.drawable.ic_kehoach_sukien, "Sự kiện",
                "Tạo một sự kiện trong ứng dụng để theo dõi việc chi tiêu trong một sự kiện có thực nào đó, ví dụ như đi du lịch cuối tuần."));
        dsKeHoach.add(new TuyChonKeHoach(R.drawable.ic_kehoach_giaodich, "Giao dịch định kỳ",
                "Tạo ra định kỳ các giao dịch sẽ được tự động thêm trong tương lai."));
        dsKeHoach.add(new TuyChonKeHoach(R.drawable.ic_kehoach_hoadon, "Hóa đơn",
                "Theo dõi hóa đơn của bạn như điện, thuê, thuê bao Internet..."));
        keHoachAdapter = new KeHoachAdapter(view.getContext(), dsKeHoach);
        lvKeHoach.setAdapter(keHoachAdapter);

        return view;
    }
}