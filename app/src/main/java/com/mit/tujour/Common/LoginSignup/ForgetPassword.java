package com.mit.tujour.Common.LoginSignup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mit.tujour.R;
import com.mit.tujour.model.UserAttributes;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {


    ScrollView scrollView;
    TextInputLayout userName;
    RelativeLayout progressbar;
    Button forgetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        //userName = findViewById(R.id.forForgetPassUsername);
        userName = (TextInputLayout)findViewById(R.id.forForgetPassUsername) ;
        forgetButton = findViewById(R.id.forgetBtn);
        progressbar = findViewById(R.id.login_progress_bar);
    }

    public void CallNewForgetPass(View view) {
        if (!validateUser()){
            return;
        }
        //progressbar.setVisibility(View.VISIBLE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        String username = userName.getEditText().getText().toString();

        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);

        try {
            reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Map valueMap = (HashMap) task.getResult().getValue();

                    try {
                        if (task.isSuccessful()){
                            String _username = (String) valueMap.get("username");
                            if (username.equals(_username)){
                                userName.setError(null);
                                userName.setErrorEnabled(false);

                                String _fullName = (String) valueMap.get("fullName");
                                String _email = (String) valueMap.get("emailId");
                                Long _phoneNo = (Long) valueMap.get("phoneNo");
                                String _dob = (String) valueMap.get("dob");
                                String _password = (String) valueMap.get("password");
                                String _gender = (String) valueMap.get("gender");

                                Log.d("ForgetPassword1", "fullName = " + _fullName);
                                Log.d("ForgetPassword1", "username = " + _username);
                                Log.d("ForgetPassword1", "email = " + _email);
                                Log.d("ForgetPassword1", "password = " + _password);
                                Log.d("ForgetPassword1", "date = " + _dob);
                                Log.d("ForgetPassword1", "phoneNo = " + _phoneNo);
                                Log.d("ForgetPassword1", "gender = " + _gender);


                                intent.putExtra(UserAttributes.FULL_NAME, _fullName);
                                intent.putExtra(UserAttributes.EMAIL_ID, _email);
                                intent.putExtra(UserAttributes.USERNAME, _username);
                                intent.putExtra(UserAttributes.PASSWORD, _password);
                                intent.putExtra(UserAttributes.DOB, _dob);
                                intent.putExtra(UserAttributes.GENDER, _gender);
                                intent.putExtra(UserAttributes.PHONE_NO, _phoneNo);

                                startActivity(intent);


                                //progressbar.setVisibility(View.GONE);
                            } else {
                                //progressbar.setVisibility(View.GONE);
                                Toast.makeText(ForgetPassword.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            //progressbar.setVisibility(View.GONE);
                            Toast.makeText(ForgetPassword.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Log.e("ForgetPass", e.getMessage());
                        //progressbar.setVisibility(View.GONE);
                        Toast.makeText(ForgetPassword.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch (Exception e){
            Log.e("ForgetPass", e.getMessage());
            //progressbar.setVisibility(View.GONE);
        }
    }

    private boolean validateUser() {
        String val = userName.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            userName.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            userName.setError("Username is too large!");
            return false;
        /*} else if (!val.matches(checkspaces)) {
            username.setError("No White spaces are allowed!");
            return false;*/
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

}