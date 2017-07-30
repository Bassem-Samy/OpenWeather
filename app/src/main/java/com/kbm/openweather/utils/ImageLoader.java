package com.kbm.openweather.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Bassem on 7/29/2017.
 * Helper class that is used to load images
 * it's purpose is to make the code independent of the 3rd party library used to load image, like if we decide to use picasso instead of glide, w will just have to update the usage here and not in every occurrence
 */

public class ImageLoader {
    private static final String BASE_IMAGE_URL = "http://openweathermap.org/img/w/";
    private static final String DEFAULT_IMAGE_EXTENSION = ".png";

    public static void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext()).load(BASE_IMAGE_URL + imageUrl + DEFAULT_IMAGE_EXTENSION).asBitmap().into(imageView);
    }

    public static void loadImage(@DrawableRes int drawableId, ImageView imageView) {
        Glide.with(imageView.getContext()).load(drawableId).asBitmap().into(imageView);
    }

}
