package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycle);
        Button button = (Button) findViewById(R.id.button2);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        CustomAdapter adapter = new CustomAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
// Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);

        // grid
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvContacts.setLayoutManager(layoutManager);


//        RecyclerView.ItemDecoration itemDecoration = new
//                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        rvContacts.addItemDecoration(itemDecoration);

//        rvContacts.setItemAnimator(new SlideInUpAnimator());

        // chuyen ra giua man hinh HORIZONTAL
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(rvContacts);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Contact> list = Contact.createContactsList(20);
                contacts.addAll(list);
                adapter.notifyDataSetChanged();
//                rvContacts.scrollToPosition(contacts.size()-1);
            }
        });

        rvContacts.setOnFlingListener(new RecyclerView.OnFlingListener() {
            private static final int SWIPE_VELOCITY_THRESHOLD = 2000;
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                    if (velocityY < 0){
                        // down
                        contacts.remove(5);
//                        contacts.addAll(list);
                        adapter.notifyItemRemoved(5);
                    } else {
                        // up

                    }
                }
                return false;
            }
        });
    }
}