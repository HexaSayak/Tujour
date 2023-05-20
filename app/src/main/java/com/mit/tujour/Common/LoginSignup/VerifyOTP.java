package com.mit.tujour.Common.LoginSignup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mit.tujour.HelperClasses.DBService;
import com.mit.tujour.R;
import com.mit.tujour.User.UserDashboard;
import com.mit.tujour.model.TujourUser;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    private String TUJOUR_HOTEL_DB = "hotel";
    private String TUJOUR_BUS_DB = "bus";

    PinView pinFromUser;
    String codeBySystem;
    TextView otpDescriptionText;
    FirebaseAuth mAuth;
    String fullName;
    String phoneNo;
    String email;

    String username;
    String password;
    String date;
    String gender;
    String whatToDO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        mAuth = FirebaseAuth.getInstance();

        //Hooks
        pinFromUser = findViewById(R.id.pin_view);
        otpDescriptionText = findViewById(R.id.otp_description_text);

        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("dob");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDO = getIntent().getStringExtra("whatToDO");
        otpDescriptionText.setText("Enter One Time Password Sent to "+phoneNo);
        sendVerificationCodeToUser(phoneNo);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void goToHomeFromOTP(){
        Log.v("VerifyOTP", "send OTP clicked");
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }
                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Log.e("Send OTP", e.getMessage(), e);
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            storeNewUsersData();
                            //Toast.makeText(VerifyOTP.this, "Verification Completed", Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeNewUsersData() {

       /* FirebaseDatabase rootNode = FirebaseDatabase.getInstance(DBService.FIREBASE_RTDB_URL);
        //now pointing/ Referencing to that Firebase Database Table
        DatabaseReference reference = rootNode.getReference().child(DBService.TUJOUR_USER_DB);
        Map<String, TujourUser> userCollection = new HashMap<>();*/
        TujourUser user = new TujourUser();
       // user.setUuid(UUID.randomUUID().toString());
        user.setFullName(fullName);
        user.setEmailId(email);
        user.setPhoneNo(Long.valueOf(phoneNo));
        user.setUsername(username);
        user.setDob(date);
        user.setGender(gender);
        user.setPassword(password);
        Log.d("VerifyOTP", "storing user record to db" + user.toString());

        DBService dbService = new DBService();
        dbService.saveUser(user);

    }

    //Jump back to UserDashboard after OTP verification!
    public void callNextScreenFromOTP(View view){
        String code = pinFromUser.getText().toString();
        if (!code.isEmpty()){
            verifyCode(code);
        }

        storeNewUsersData();
        Intent homeIntent = new Intent(getApplicationContext(), UserDashboard.class);
        startActivity(homeIntent);
    }
}