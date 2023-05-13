package com.mit.tujour.Common.LoginSignup;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.mit.tujour.R;

public class Signup3rdclass extends AppCompatActivity {


    String fullName,username,email,password;
    String datePicker;

    RadioGroup radioGroup;
    RadioButton selectedGender;
    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup3rdclass);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker =  findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);

        fullName = getIntent().getStringExtra("fullName");
        username=getIntent().getStringExtra("username");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        datePicker=getIntent().getStringExtra("dob");
    }

    public void callVerifyOTPScreen(View view){

        if (!validatePhoneNumber()) {
            return;
        }
        //Get all values passed from previous screens using intent
        /*String _fullName=getIntent().getStringExtra("fullName");
        String _email=getIntent().getStringExtra("email");
        //String _username=username.getEditText().toString();
        String _username=getIntent().getStringExtra("username");
        String _password=getIntent().getStringExtra("password");
        String _date=getIntent().getStringExtra("date");
        String _gender=getIntent().getStringExtra("gender");*/
        String _userEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _userEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
        String fullName = getIntent().getStringExtra("fullName");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String dob = getIntent().getStringExtra("dob");
        String gender = getIntent().getStringExtra("gender");

        Log.d("Signup3", "fullName = " + fullName);
        Log.d("Signup3", "username = " + username);
        Log.d("Signup3", "email = " + email);
        Log.d("Signup3", "password = " + password);
        Log.d("Signup3", "dob = " + dob);
        Log.d("Signup3", "gender = " + gender);

        //Pass all fields to the next activity
        intent.putExtra("fullName", fullName);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("dob", dob);
        intent.putExtra("gender", gender);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDO", "createNewUser"); // This is to identify that which action should OTP perform after verification.

        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup3rdclass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }


    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}