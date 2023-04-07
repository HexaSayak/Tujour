package com.finalyearproj.tujour.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.finalyearproj.tujour.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.finalyearproj.tujour.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.finalyearproj.tujour.R;
import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);
        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
//        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
//        categoriesRecycler = findViewById(R.id.categories_recycler);
        //Functions will be executed automatically when this activity will be created
        featuredRecycler();
//        mostViewedRecycler();
//        categoriesRecycler();
    }



  private void featuredRecycler() {
      featuredRecycler.setHasFixedSize(true);
      featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

      ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
      featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald, "Mcdonald's", "asbkd asudhlasn saudnas jasdjasl hisajdl asjdlnas"));
      featuredLocations.add(new FeaturedHelperClass(R.drawable.city1, "Edenrobe", "asbkd asudhlasn saudnas jasdjasl hisajdl asjdlnas"));
      featuredLocations.add(new FeaturedHelperClass(R.drawable.city2, "Walmart", "asbkd asudhlasn saudnas jasdjasl hisajdl asjdlnas"));
      adapter = new FeaturedAdapter(featuredLocations);
      featuredRecycler.setAdapter(adapter);

      GradientDrawable gradient1=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xffeff400, 0xffaff600} );
   }
}