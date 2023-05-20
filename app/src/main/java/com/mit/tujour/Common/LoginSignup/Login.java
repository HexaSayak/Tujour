package com.mit.tujour.Common.LoginSignup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mit.tujour.R;
import com.mit.tujour.User.UserDashboard;
import com.mit.tujour.model.TujourUser;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    //Variables
    TextInputLayout password, userName;
    RelativeLayout progressbar;

    TextView openForgetPass;
    Button loginButton;


    @SuppressLint("MissingInflatedId")
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
        openForgetPass = findViewById(R.id.openForgetPass);
    }

    //login the user in app!
    public void letTheUserLoggedIn(View view) {

        if(!isConnected(this)){
            showCustomDialogue();
        }
        Intent intent = new Intent(getApplicationContext(), UserDashboard.class);

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

        try {
            reference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Map valueMap = (HashMap) task.getResult().getValue();
                    //Log.d("Login", (String) valueMap.get("emailId")+" signed in successfully");

                    try {
                        if (task.isSuccessful()){
                            String _usename = (String) valueMap.get("username");
                            if (username.equals(_usename)){
                                String _password = (String) valueMap.get("password");
                                userName.setError(null);
                                userName.setErrorEnabled(false);

                                if (pass.equals(_password)) {
                                    password.setError(null);
                                    password.setErrorEnabled(false);

                                    String _fullname = (String) valueMap.get("fullName");
                                    String _email = (String) valueMap.get("emailId");
                                    Long _phoneNo = (Long) valueMap.get("phoneNo");
                                    String _dob = (String) valueMap.get("dob");
                                    progressbar.setVisibility(View.GONE);
                                    //Toast.makeText(Login.this, _fullname+"\n"+_email+"\n"+_phoneNo+"\n"+_dob, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Login.this, "Log in Successful!", Toast.LENGTH_SHORT).show();

                                    startActivity(intent);

                                }
                                else {
                                    progressbar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressbar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e){
            Log.e("Login", e.getMessage());
            progressbar.setVisibility(View.GONE);
        }

    }


    //Check Internet Connection
    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }
        else {
            return false;
        }
    }

    private void showCustomDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                        finish();
                    }
                });
        builder.show();


    }

    public void callForgetPassword(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));

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