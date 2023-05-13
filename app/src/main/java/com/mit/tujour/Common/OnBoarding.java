package com.mit.tujour.Common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.mit.tujour.HelperClasses.SliderAdapter;
import com.mit.tujour.R;
import com.mit.tujour.User.UserDashboard;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;

    TextView[] dots;

    Button letsGetStarted;
    Button nextbtn;

    Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.let_get_started);
        nextbtn=findViewById(R.id.next_btn);

        //Call adapter
        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }
    public void skip(View view){
startActivity(new Intent(this, UserDashboard.class));
    }
    public void next(View view){
    viewPager.setCurrentItem(currentPos+1);
    }
    private void addDots(int position){
        dots = new  TextView[4];
        dotsLayout.removeAllViews();
        for (int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos=position;
            if (position == 0) {
                letsGetStarted.setVisibility(View.INVISIBLE);
                nextbtn.setVisibility(View.VISIBLE);
            } else if (position == 1) {
                letsGetStarted.setVisibility(View.INVISIBLE);
                nextbtn.setVisibility(View.VISIBLE);
            } else if (position == 2) {
                letsGetStarted.setVisibility(View.INVISIBLE);
               nextbtn.setVisibility(View.VISIBLE);
            } else {

                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.INVISIBLE);
            }

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}