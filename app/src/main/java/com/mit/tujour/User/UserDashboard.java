package com.mit.tujour.User;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mit.tujour.Common.LoginSignup.Login;
import com.mit.tujour.Common.LoginSignup.RetailerStartUpScreen;
import com.mit.tujour.Common.LoginSignup.Signup;
import com.mit.tujour.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.mit.tujour.HelperClasses.HomeAdapter.CategoriesHelperclass;
import com.mit.tujour.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.mit.tujour.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.mit.tujour.HelperClasses.HomeAdapter.FeaturedViewInterface;
import com.mit.tujour.HelperClasses.HomeAdapter.MostViewAdapter;
import com.mit.tujour.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.mit.tujour.MainActivityWeather;
import com.mit.tujour.R;
import com.mit.tujour.model.Indonesia_package;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FeaturedViewInterface {

    //Variables

    private FeaturedViewInterface featuredViewInterface;
    RecyclerView featuredRecycler;
    RecyclerView categoriesRecyler;
    RecyclerView mostviewedRecycler;
    RecyclerView.Adapter adapter;

    private FirebaseAuth auth;
    GradientDrawable gradient1, gradient2, gradient3, gradient4, gradient5, gradient6, gradient7, gradient8;

    ImageView menuIcon;

    static final float END_SCALE = 0.7f;

    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView viewallforCategories;
    //ImageView plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        categoriesRecyler = findViewById(R.id.categories_recycler);
        mostviewedRecycler = findViewById(R.id.mostviewed_recycle);
        menuIcon = findViewById(R.id.menu_icon); // <-- After clicking here Navigation Drawer will open
        contentView = findViewById(R.id.content);

        auth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("notification");

        //RecyclerView func calls

        featuredRecycler();
        categoriesRecyler();
        mostviewedRecycler();

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        viewallforCategories=findViewById(R.id.viewall_catagories_userdashboard);
        viewallforCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opencat=new Intent(UserDashboard.this,AllCategories.class);
                startActivity(opencat);
            }
        });


        /*
        plus=findViewById(R.id.login_signup);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDashboard.this,RetailerStartUpScreen.class));
            }
        }); */


        navigationDrawer();
    }

    public void clickOnWeather(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivityWeather.class);
        startActivity(intent);
    }

    public void clickOnHotel(View view){
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void clickOnHospitals(View view){
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
    }
    public void clickOnMedical(View view){
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    

    //Navigation Drawer Functions
    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);  // <-- By default Home selected from Drawer

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        //Animation Drawer

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimaryLightPink));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);
                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    //if user press back switch instead of closing the whole app it will close only the Drawer
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(),AllCategories.class));
                break;

            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;

            case R.id.nav_logout:
                startActivity(new Intent(getApplicationContext(), Signup.class));
                break;
        }

        return true;  // <--True means, there is some navigation icon that needs to be selected
    }


    private void mostviewedRecycler() {

        gradient8 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbd2, 0xffd4cbd2});
        gradient7 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adcdf, 0xff7adcdf});
        gradient6 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c58f, 0xFFf7c58f});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7a5, 0xffb8d7a5});
        ArrayList<MostViewedHelperClass> MostViewedHelperClass = new ArrayList<>();
        //  categoriesHelperClasses.add(new CategoriesHelperclass(gradient1, R.drawable.school_image, "Education"));

        MostViewedHelperClass.add(new MostViewedHelperClass(gradient5, R.drawable.kolkata_image, "Kolkata", "Kolkata is a city of poetry, culture and history."));
        MostViewedHelperClass.add(new MostViewedHelperClass(gradient6, R.drawable.delhi_image, "Delhi", "Falling in love with the colorful culture of Delh"));
        MostViewedHelperClass.add(new MostViewedHelperClass(gradient8, R.drawable.kashmir_image, "Kashmir", "Lost in the beauty of Kashmir, found peace in my soul."));
        MostViewedHelperClass.add(new MostViewedHelperClass(gradient7, R.drawable.ladakh_image, "Ladakh", "Feeling small in the vastness of Leh Ladakh"));
        MostViewedHelperClass.add(new MostViewedHelperClass(gradient5, R.drawable.kanyakumari_image, "Kanyakumari", "Wherever you go, bring your own sunshine"));
        mostviewedRecycler.setHasFixedSize(true);
        adapter = new MostViewAdapter(MostViewedHelperClass);
        mostviewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mostviewedRecycler.setAdapter(adapter);
    }

    private void categoriesRecyler() {
        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});
        ArrayList<CategoriesHelperclass> categoriesHelperClasses = new ArrayList<>();
        //  categoriesHelperClasses.add(new CategoriesHelperclass(gradient1, R.drawable.school_image, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperclass(gradient2, R.drawable.hospital_image, "Hospital"));
        categoriesHelperClasses.add(new CategoriesHelperclass(gradient3, R.drawable.restaurant_image, "Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperclass(gradient4, R.drawable.shopping_image, "Shopping"));
        categoriesHelperClasses.add(new CategoriesHelperclass(gradient1, R.drawable.transport_image, "Transport"));
        categoriesHelperClasses.add(new CategoriesHelperclass(gradient5, R.drawable.medicalstore_image, "Medical Store"));
        categoriesRecyler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecyler.setAdapter(adapter);
    }

    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.indonetia_img, "Indonesia", "The island is so peaceful and the smiles are constant."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.thailand_img, "Thailand", "They say do what you love’ so I travelled to Thailand."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.japan_img, "Japan", "Happiness is going on a sushi date in Japan."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.hongkong_img, "Hong kong", "Hong Kong is really all that and dim sum."));
        adapter = new FeaturedAdapter(featuredLocations, this); //
        featuredRecycler.setAdapter(adapter);


        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

    public void callRetailerScreens(View view){
        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

    @Override
    public void onFeaturedItemClick(int position) {
        Intent intent = new Intent(this, Indonesia_package.class);
        startActivity(intent);
    }
}

