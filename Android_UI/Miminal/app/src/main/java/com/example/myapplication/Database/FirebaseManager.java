package com.example.myapplication.Database;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    private FirebaseFirestore db;
    public FirebaseManager(){
        db = FirebaseFirestore.getInstance();
    }


}
