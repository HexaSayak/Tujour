package com.mit.tujour.HelperClasses.HomeAdapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperclass {

    int image;
    String title ;
    Drawable gradient;
    public CategoriesHelperclass(GradientDrawable gradient, int image, String title) {
        this.image = image;
        this.title = title;
        this.gradient=gradient;
    }
    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }


    public Drawable getGradient() {
        return gradient;
    }
}
