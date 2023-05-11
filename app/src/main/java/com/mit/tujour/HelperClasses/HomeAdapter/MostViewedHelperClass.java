package com.mit.tujour.HelperClasses.HomeAdapter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
public class MostViewedHelperClass {
    int image;
    String title, description;
    Drawable gradient;


    public MostViewedHelperClass(GradientDrawable gradient, int image, String title, String description) {

        this.image = image;
        this.title = title;
        this.gradient=gradient;
        this.description = description;
    }

    public int getImageView() {
        return image;
    }

    public String getTextView() {
        return title;
    }

    public String getDescription() {return description;}

    public Drawable getGradient() {
        return gradient;
    }

}


