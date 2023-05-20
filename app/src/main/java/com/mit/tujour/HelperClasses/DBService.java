package com.mit.tujour.HelperClasses;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mit.tujour.model.TujourUser;

public class DBService {

    public static final String  FIREBASE_RTDB_URL = "https://tujour-c14c7-default-rtdb.firebaseio.com";
    public static final String TUJOUR_USER_DB = "users";

    /*public TujourUser getUser(String username) {
       *//* DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                TujourUser user = new TujourUser();
                Map valueMap = (HashMap) task.getResult().getValue();
                //Log.d("Login", (String) valueMap.get("emailId")+" signed in successfully");

                try {
                    if (task.isSuccessful()) {
                        user.setUsername(valueMap.get(UserAttributes.USERNAME));
                        user.setGender(valueMap.get(UserAttributes.GENDER));
                        user.setFullName(valueMap.get(UserAttributes.FULL_NAME));
                        user.setEmailId(valueMap.get(UserAttributes.EMAIL_ID));
                        user.setPhoneNo(Long.valueOf((String) valueMap.get(UserAttributes.PHONE_NO)));
                        user.setPassword(valueMap.get(UserAttributes.PASSWORD));
                        user.setDob(valueMap.get(UserAttributes.DOB));

                        return  user;

                    } else {
                        Log.e("getUser", "Task unsuccessful");
                    }
                } catch (Exception e) {
                    Log.e("getUser", "No such user exist!");
                }
            }
        });*//*

    }*/

    public void saveUser(TujourUser user) {
       try {
           FirebaseDatabase rootNode = FirebaseDatabase.getInstance(FIREBASE_RTDB_URL);
           //now pointing/ Referencing to that Firebase Database Table
           DatabaseReference reference = rootNode.getReference().child(TUJOUR_USER_DB);
           reference.child(user.getUsername()).setValue(user);
       }catch (Exception e){
           Log.e("SetNewPassword", "Couldn't save user");
       }
    }
}
