package com.example.firebasecompletproject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.firebasecompletproject.model.RetrofitClient;
import com.example.firebasecompletproject.model.api.MoviesItem;
import com.example.firebasecompletproject.model.api.Yts;
import com.example.firebasecompletproject.model.details.Movie;
import com.example.firebasecompletproject.model.details.YTSDetails;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    TextView years, rates, genras, synopsiss, q3d, q720, q1080;
    ImageView posters, toolbarimg;
    Toolbar toolbar;
    ViewPager viewPager;
    Adapter adapter;
    int id = 11;
    ArrayList<Movie> movies;
    ProgressWheel progressWheel;
    RecyclerView recyclerView;
    AdapterCast adapterCast ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        recyclerView = findViewById(R.id.detail_recycler_cast);
        progressWheel= findViewById(R.id.detail_progress_bar);
        years = findViewById(R.id.detail_tv_year);
        rates = findViewById(R.id.detail_tv_rate);
        genras = findViewById(R.id.detail_tv_genre);
        synopsiss = findViewById(R.id.detail_tv_overview);
        q3d = findViewById(R.id.detail_tv_res_3d);
        q720 = findViewById(R.id.detail_tv_res_720);
        q1080 = findViewById(R.id.detail_tv_res_1080);
        posters = findViewById(R.id.detail_iv_poster);
        toolbarimg = findViewById(R.id.detail_iv_toolbar_trailer_thumb);
        ImageView background = findViewById(R.id.detail_iv_background);
        viewPager = findViewById(R.id.detail_pager_screenshots);
        adapter = new Adapter(movies, DetailsActivity.this);
        movies = new ArrayList<Movie>();
        Toolbar toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent in = getIntent();
        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("genres");
        final String yt_trailer_codes = in.getStringExtra("yt_trailer_code");
        String title_tv = in.getStringExtra("title");
        String backgroundimgs = in.getStringExtra("backgroundimg");

        double rate = (double) in.getDoubleExtra("rate", 7.6);
        String image_tv = in.getStringExtra("poster");
        String synopsis_tv = in.getStringExtra("synopsis");
        final String movie720 = in.getStringExtra("720");
        final String movie1080 = in.getStringExtra("1080");
        final int movieid = (int) in.getIntExtra("movieids", 15);
        getDateScreenshot(id, true, true);

        int year_tv = (int) in.getIntExtra("year", 2018);
        rates.setText("" + rate);
        progressWheel.setVisibility(View.VISIBLE);
        Glide.with(this).load(image_tv).into(posters);
        Glide.with(this).load(getYouTubeImgUrl(yt_trailer_codes)).into(toolbarimg);
        Glide.with(this).load(backgroundimgs).into(background);
        progressWheel.setVisibility(View.INVISIBLE);
        synopsiss.setText(synopsis_tv);
        years.setText("" + year_tv);
        toolbar.setTitle(title_tv);
        setSupportActionBar(toolbar);
        genras.setText(myList.toString());
        q720.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = movie720;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        q1080.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urls = movie1080;
                Intent ix = new Intent(Intent.ACTION_VIEW);
                ix.setData(Uri.parse(urls));
                startActivity(ix);
            }
        });
        ScreenshotsAdapter screenshotsAdapter = new ScreenshotsAdapter();
        getcastdetails(15);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

                getDateScreenshot(movieid, true, true);
            }

            @Override
            public void onPageSelected(int i) {
                getDateScreenshot(movieid, true, true);

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                getDateScreenshot(movieid, true, true);

            }

        });

        toolbarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + yt_trailer_codes));
                startActivity(intent);


            }
        });

    }

    private void getcastdetails(int movieid) {
        Call<YTSDetails> call = RetrofitClient.getInstance().getDate_api2().getDates(movieid, true, true);
        call.enqueue(new Callback<YTSDetails>() {
            @Override
            public void onResponse(Call<YTSDetails> call, Response<YTSDetails> response) {
                movies.addAll(response.body().getData().getMovie());
                adapterCast = new AdapterCast(movies, DetailsActivity.this);recyclerView .setAdapter(adapterCast);}

            @Override
            public void onFailure(Call<YTSDetails> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(DetailsActivity.this, movies.toString()+"error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDateScreenshot(int movieid, boolean img, boolean cast) {
        Call<YTSDetails> call = RetrofitClient.getInstance().getDate_api2().getDates(movieid, true, true);
        call.enqueue(new Callback<YTSDetails>() {
            @Override
            public void onResponse(Call<YTSDetails> call, Response<YTSDetails> response) {

                if (response.isSuccessful() && response != null) {
                    movies.addAll(response.body().getData().getMovie());
                    ScreenshotsAdapter adapter = new ScreenshotsAdapter(movies.get(0).getMediumScreenshotImage1(),movies.get(0).getMediumScreenshotImage2(),movies.get(0).getMediumScreenshotImage3());
                    adapter.notifyAll();
                    Glide.with(DetailsActivity.this).load(movies.get(0).getMediumScreenshotImage1()).into(posters);
                    viewPager.setAdapter(adapter);
                    viewPager.setPadding(130, 0, 130, 0);
                }
            }

            @Override
            public void onFailure(Call<YTSDetails> call, Throwable t) {

            }
        });
    }

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static String getYouTubeImgUrl(String tubeId) {
        return "https://img.youtube.com/vi/" + tubeId + "/0.jpg";
    }

    public static String getYouTubeUrl(String tubeId) {
        return "https://www.youtube.com/watch?v=" + tubeId;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
