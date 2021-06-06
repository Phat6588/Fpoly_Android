package com.huydh54.fpolyapp.Fragment.TrangMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.huydh54.fpolyapp.Adapter.ChatAdapter;
import com.huydh54.fpolyapp.Model.Chat;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class NhanTinFragment extends Fragment {

    ListView dsChat;
    ChatAdapter chatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nhan_tin, container, false);

        dsChat = view.findViewById(R.id.lv_Chat);

        ArrayList<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat(R.drawable.load_st1, "Đặng Hoàng Huy"));
        chatList.add(new Chat(R.drawable.load_st2, "Phan Hồng Quân"));
        chatList.add(new Chat(R.drawable.load_st3, "Phạm Nhật Nghĩa"));
        chatList.add(new Chat(R.drawable.load_st4, "Huỳnh Tấn Thành"));
        chatList.add(new Chat(R.drawable.load_st5, "Trần Phước Sanh"));
        chatList.add(new Chat(R.drawable.load_st6, "Phạm Thành Khương"));

        chatAdapter = new ChatAdapter(view.getContext(), chatList);
        dsChat.setAdapter(chatAdapter);

        return view;
    }
}