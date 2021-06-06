package com.huydh54.fpolyapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huydh54.fpolyapp.Model.Chat;
import com.huydh54.fpolyapp.R;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
    Context context;
    ArrayList<Chat> dsChat = new ArrayList<Chat>();

    public ChatAdapter(Context context, ArrayList<Chat> dsChat) {
        this.context = context;
        this.dsChat = dsChat;
    }

    @Override
    public int getCount() {
        return dsChat.size();
    }

    @Override
    public Object getItem(int i) {
        return dsChat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatAdapter.ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ChatAdapter.ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.item_chat, null);
            viewHolder.anh = view.findViewById(R.id.imgAvatarChat);
            viewHolder.ten = view.findViewById(R.id.txtTenChat);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ChatAdapter.ViewHolder)view.getTag();
        }
        viewHolder.anh.setImageResource(dsChat.get(i).getAnh());
        viewHolder.ten.setText(dsChat.get(i).getTen());
        return view;
    }

    public class ViewHolder{
        ImageView anh;
        TextView ten;
    }
}
