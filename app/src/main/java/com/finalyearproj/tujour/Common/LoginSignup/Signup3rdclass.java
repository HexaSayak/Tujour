package com.finalyearproj.tujour.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.util.Pair;
import android.widget.Button;
import android.widget.ScrollView;

import com.finalyearproj.tujour.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Signup3rdclass extends AppCompatActivity {


    ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup3rdclass);

        //Hooks
        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker =  findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);
    }

    public void callVerifyOTPScreen(View view){

        if (!validatePhoneNumber()) {
            return;
        }
        //Get all values passed from previous screens using intent
        String _fullName=getIntent().getStringExtra("fullname");
        String _email=getIntent().getStringExtra("email");
        String _username=getIntent().getStringExtra("username");
        String _password=getIntent().getStringExtra("password");
        String _date=getIntent().getStringExtra("date");
        String _gender=getIntent().getStringExtra("gender");
        String _userEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _phoneNo = countryCodePicker.getSelectedCountryCodeWithPlus() + _userEnteredPhoneNumber;

        Intent next3 = new Intent(getApplicationContext(), VerifyOTP.class);
        //Pass all fields to the next activity
        next3.putExtra("fullName", _fullName);
        next3.putExtra("email", _email);
        next3.putExtra("username", _username);
        next3.putExtra("password", _password);
        next3.putExtra("date", _date);
        next3.putExtra("gender", _gender);
        next3.putExtra("phoneNo", _phoneNo);
        next3.putExtra("whatToDO", "createNewUser"); // This is to identify that which action should OTP perform after verification.

        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup3rdclass.this, pairs);
            startActivity(next3, options.toBundle());
        } else {
            startActivity(next3);
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