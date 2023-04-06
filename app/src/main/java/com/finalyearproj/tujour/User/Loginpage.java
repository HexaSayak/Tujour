package com.finalyearproj.tujour.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.finalyearproj.tujour.R;

public class Loginpage extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("RohitBabu7872") && password.getText().toString().equals("7872")) {
                    Toast.makeText(Loginpage.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Loginpage.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}