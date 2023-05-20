package com.mit.tujour.Common.LoginSignup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mit.tujour.HelperClasses.DBService;
import com.mit.tujour.R;
import com.mit.tujour.model.TujourUser;
import com.mit.tujour.model.UserAttributes;

public class SetNewPassword extends AppCompatActivity {

    private String TUJOUR_USER_DB = "users";

    TextInputLayout newUpdatePass, newConfirmUpdatePass;

    RelativeLayout progressbar;
    String username;
    String password;
    FirebaseAuth mAuth;

    private TujourUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        mAuth = FirebaseAuth.getInstance();

        user = new TujourUser();
        user.setUsername(getIntent().getStringExtra(UserAttributes.USERNAME));
        user.setGender(getIntent().getStringExtra(UserAttributes.GENDER));
        user.setFullName(getIntent().getStringExtra(UserAttributes.FULL_NAME));
        user.setEmailId(getIntent().getStringExtra(UserAttributes.EMAIL_ID));
        user.setPhoneNo(getIntent().getLongExtra(UserAttributes.PHONE_NO, 0));
        user.setPassword(getIntent().getStringExtra(UserAttributes.PASSWORD));
        user.setDob(getIntent().getStringExtra(UserAttributes.DOB));

        Log.d("SetNewPass", "User found: " + user.toString());

        newUpdatePass = findViewById(R.id.newPasswordField);
        newConfirmUpdatePass = findViewById(R.id.newConfirmPasswordField);
        progressbar = findViewById(R.id.login_progress_bar);
    }

    public void sendNewUpdatePassToDB(View view) {
        if (!validateNewPass() | !validateConfirmNewPass()) {
            return;
        }
        //progressbar.setVisibility(View.VISIBLE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");

        String _newUpdatePass = newUpdatePass.getEditText().getText().toString().trim();
        String _newConfirmUpdatePass = newConfirmUpdatePass.getEditText().getText().toString().trim();

        if (_newUpdatePass.equals(_newConfirmUpdatePass)) {
            user.setPassword(_newUpdatePass);
            DBService dbService = new DBService();
            dbService.saveUser(user);
            Toast.makeText(SetNewPassword.this, "Password updated successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), ForgetPasswordSuccessMessage.class);
            startActivity(intent);
        } else {
            Toast.makeText(SetNewPassword.this, "Confirm Password doesn't match", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validateConfirmNewPass() {
        String val = newConfirmUpdatePass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            newConfirmUpdatePass.setError("Field can not be empty");
            return false;
        /*} else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;*/
        } else {
            newConfirmUpdatePass.setError(null);
            newConfirmUpdatePass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNewPass() {
        String val = newUpdatePass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            newUpdatePass.setError("Field can not be empty");
            return false;
        /*} else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 4 characters!");
            return false;*/
        } else {
            newUpdatePass.setError(null);
            newUpdatePass.setErrorEnabled(false);
            return true;
        }
    }
}