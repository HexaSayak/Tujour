package com.mit.tujour.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.mit.tujour.R;
import com.mit.tujour.User.UserDashboard;
import com.mit.tujour.model.TujourUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    //Variables
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;

    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_login);

        //hooks
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);
        loginButton = findViewById(R.id.letTheUserLogIn);


    }

    //login the user in app!
    public void letTheUserLoggedIn(View view) {
        if (!validatePhone() | !validatePassword()) {
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        //get data
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        //final String _completePhoneNumber = "+"+countryCodePicker.getFullNumber()+_phoneNumber;
        String _completePhoneNumber = countryCodePicker.getSelectedCountryCodeWithPlus() + _phoneNumber;

        Log.d("completePhoneNumber", "complete phone number store" + _completePhoneNumber);
        //Database
        /*FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://tujour-c14c7-default-rtdb.firebaseio.com/");
        DatabaseReference reference = rootNode.getReference("Users");*/
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        //TODO use username instead of phoneNo
        String username = "sanjeevg";
        TujourUser user;
        reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                 Map valueMap = (HashMap) task.getResult().getValue();
                 Log.d("Login", (String) valueMap.get("emailId")+" signed in successfully");
            }
        });

        //Query checkUserDatabase = reference.orderByChild("phoneNo").equalTo(_completePhoneNumber);
        Query checkUserDatabase = reference.child("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);
        //Query checkUserDatabase = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                /*if (datasnapshot.exists()){
                    phoneNumber.setError(null);
                    String passwordFromDB = datasnapshot.child(String.valueOf(phoneNumber)).child("password").getValue(String.class);

                    if (passwordFromDB.equals(_password)){
                        phoneNumber.setError(null);
                        Intent intent = new Intent(Login.this, UserDashboard.class);
                        startActivity(intent);
                    } else {
                        password.setError("Invalid Credentials");
                        password.requestFocus();
                    }
                } else {
                    phoneNumber.setError("User does not exist");
                    phoneNumber.requestFocus();
                }*/
                if (datasnapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    //here requesting to DB that, This phone is exist in DB or not ?
                    String systemPassword = datasnapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    Log.d("systemPass_store", "System pass store from DB" + systemPassword);
                    if (systemPassword.equals(_password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullname = datasnapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = datasnapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = datasnapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _dob = datasnapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                        Toast.makeText(Login.this, _fullname+"\n"+_email+"\n"+_phoneNo+"\n"+_dob, Toast.LENGTH_SHORT).show();
                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validatePhone() {
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

    }

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