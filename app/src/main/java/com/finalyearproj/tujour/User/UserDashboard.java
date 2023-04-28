package com.finalyearproj.tujour.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.finalyearproj.tujour.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.finalyearproj.tujour.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.finalyearproj.tujour.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);

        featuredRecycler();

    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.indonetia_img,"Indonesia", "The island is so peaceful and the smiles are constant."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.thailand_img,"Thailand", "They say do what you love’ so I travelled to Thailand."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.japan_img,"Japan", "Happiness is going on a sushi date in Japan."));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }
}

