package com.mit.tujour.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.mit.tujour.R;
import com.mit.tujour.model.TujourUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    //Variables
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password, userName;
    RelativeLayout progressbar;

    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        //hooks
        /*countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);*/
        userName = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);
        loginButton = findViewById(R.id.letTheUserLogIn);


    }

    //login the user in app!
    public void letTheUserLoggedIn(View view) {
        if (!validateUser() | !validatePassword()) {
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        //get data

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        //TODO use username instead of phoneNo
        //String username = "sanjeevg";
        String username = userName.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString().trim();
        TujourUser user;
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                 Map valueMap = (HashMap) task.getResult().getValue();
                 Log.d("Login", (String) valueMap.get("emailId")+" signed in successfully");
                 progressbar.setVisibility(View.GONE);
                 if (task.isSuccessful()){
                     String _password = (String) valueMap.get("password");
                     if (pass.equals(_password)) {
                         password.setError(null);
                         password.setErrorEnabled(false);

                         String _fullname = (String) valueMap.get("fullName");
                         String _email = (String) valueMap.get("emailId");
                         Long _phoneNo = (Long) valueMap.get("phoneNo");
                         String _dob = (String) valueMap.get("date");
                         progressbar.setVisibility(View.GONE);
                         Toast.makeText(Login.this, _fullname+"\n"+_email+"\n"+_phoneNo+"\n"+_dob, Toast.LENGTH_SHORT).show();
                     }
                     else {
                         Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                     }
                 }
            }
        });

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

    /*private boolean validatePhone() {
        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (_phoneNumber.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }

    }*/

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}