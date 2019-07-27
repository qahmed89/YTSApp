package com.example.firebasecompletproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.firebasecompletproject.model.Data;
import com.example.firebasecompletproject.model.RetrofitClient;
import com.example.firebasecompletproject.model.api.MoviesItem;
import com.example.firebasecompletproject.model.api.Yts;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MoiveActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MoiveAdapter ytsAdapter;
    RelativeLayout linearLayout;
    private int pages = 1;
    SwipeRefreshLayout swipeRefresh;
    List<MoviesItem> moviesItems;
    private LayoutAnimationController animation;
    ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moive);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.list);
        linearLayout = (RelativeLayout) findViewById(R.id.item_relative_tintable);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        moviesItems = new ArrayList<MoviesItem>();
        animation = AnimationUtils.loadLayoutAnimation(MoiveActivity.this, R.anim.layout_animation_random_scale);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataYts(pages);
                //        swipeRefresh.setProgressViewOffset(true,1,1);
                swipeRefresh.setRefreshing(false);

            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        ytsAdapter = new MoiveAdapter(MoiveActivity.this, moviesItems);
        recyclerView.setAdapter(ytsAdapter);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            //
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pages++;
                getDataYts(pages);


            }
        });


        getDataYts(pages);


    }

    private void getDataYts(final int page) {

        Call<Yts> call = RetrofitClient.getInstance().getDate_api().getDate(page);
        call.enqueue(new Callback<Yts>() {
            @Override
            public void onResponse(Call<Yts> call, Response<Yts> response) {
                if (response.isSuccessful() && response != null) {
                    moviesItems.addAll(response.body().getData().getMovies());


                    ytsAdapter.notifyItemRangeInserted(ytsAdapter.getItemCount(), moviesItems.size() - 1);


                } else {
                    Toast.makeText(MoiveActivity.this, "ddddd", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Yts> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
