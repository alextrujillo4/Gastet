package com.ximeft29.gastetandroid.util;

import androidx.annotation.NonNull;
import com.ximeft29.gastetandroid.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class Database {
    private static FirebaseDatabase mDatabase;
    private static FirebaseUser firebaseUser;
    private static User user;
    private static String userFireId;


    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase.setPersistenceEnabled(true);
            downloadUser();
        }
        return mDatabase;
    }


    public static String getFireId() {
        if (user == null || userFireId == null|| mDatabase == null);
        mDatabase = FirebaseDatabase.getInstance();
        userFireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        try {
            downloadUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  userFireId;
    }
    public static User getUser() {
        if (user == null || firebaseUser == null|| mDatabase == null);
        mDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        try {
            downloadUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  user;
    }

    private static void downloadUser(){
        DatabaseReference ref = mDatabase.getReference("users/"+firebaseUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userD = dataSnapshot.getValue(User.class);
                user = userD;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}