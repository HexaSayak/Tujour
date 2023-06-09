package com.mit.tujour.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mit.tujour.R;
import com.mit.tujour.User.UserDashboard;

public class  SplashScreen extends AppCompatActivity {

    //Variables
    TextView txtTujour, txtSlogan;
    RelativeLayout relativeLayout;


    //Animation
    Animation txtAnimation, layoutAnimation;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //Hooks

        txtAnimation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.fall_down);
        layoutAnimation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.buttom_to_top);

        txtTujour = findViewById(R.id.txtTujour);
        txtSlogan = findViewById(R.id.txtSlogan);
        relativeLayout = findViewById(R.id.relMain);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtTujour.setVisibility(View.VISIBLE);
                        txtSlogan.setVisibility(View.VISIBLE);

                        txtTujour.setAnimation(txtAnimation);
                        txtSlogan.setAnimation(txtAnimation);
                    }
                },900);
            }
        },500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                if(isFirstTime){

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent = new Intent(SplashScreen.this, OnBoarding.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Intent intent = new Intent(SplashScreen.this, UserDashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        },5000);

    }
}