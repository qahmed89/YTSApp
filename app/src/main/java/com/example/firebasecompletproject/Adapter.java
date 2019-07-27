package com.example.firebasecompletproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebasecompletproject.model.details.Movie;
import com.example.firebasecompletproject.model.details.YTSDetails;

import java.util.List;

import retrofit2.Callback;

public class Adapter extends PagerAdapter {

    private List<Movie> models;
    private LayoutInflater layoutInflater;
    private Context context;


    public Adapter(List<Movie> models,Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.screenshot, container, false);

        ImageView imageView;


        imageView = view.findViewById(R.id.imagescreenshot);

        Glide.with(context).load(models.get(position).getMediumScreenshotImage1()).into(imageView);



        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
