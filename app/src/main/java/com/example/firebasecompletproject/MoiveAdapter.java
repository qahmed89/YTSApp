package com.example.firebasecompletproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.firebasecompletproject.model.Data;
import com.example.firebasecompletproject.model.api.MoviesItem;
import com.example.firebasecompletproject.model.api.TorrentsItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MoiveAdapter extends RecyclerView.Adapter<MoiveAdapter.MyViewHolder> {
    private Context ctx;
    private List<MoviesItem> moviesItems;
    List<TorrentsItem> torrentsItemsList;


    public MoiveAdapter(Context ctx, List<MoviesItem> moviesItems) {
        this.ctx = ctx;
        this.moviesItems = moviesItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MoiveAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MoviesItem moviesItem = moviesItems.get(i);

        myViewHolder.name.setText(moviesItem.getTitle());

        myViewHolder.genre.setText(moviesItem.getGenres().get(0));
        myViewHolder.year.setText(""+moviesItem.getYear());
        Glide.with(ctx).load(moviesItem.getMediumCoverImage()).into(myViewHolder.imageView);
        final ArrayList<String> genress = (ArrayList<String>) moviesItem.getGenres();
        torrentsItemsList = moviesItem.getTorrents();
int io= moviesItem.getId();
        // final String count = torrentsItemsList.get(1).getUrl();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
         myViewHolder.linearLayout.setBackgroundColor(color);
         myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent detailsIntent=new Intent(ctx,DetailsActivity.class);
                 final String x = torrentsItemsList.get(0).getUrl();
                 final String count = torrentsItemsList.get(1).getUrl();
                 detailsIntent.putExtra("title",moviesItem.getTitle());
                 detailsIntent.putExtra("yt_trailer_code",moviesItem.getYtTrailerCode());
                 detailsIntent.putExtra("backgroundimg",moviesItem.getBackgroundImage());
                 detailsIntent.putExtra("movieids",moviesItem.getId());
                 detailsIntent.putExtra("year",moviesItem.getYear());
                 detailsIntent.putExtra("genres",genress);
                 detailsIntent.putExtra("rate",moviesItem.getRating());
                 detailsIntent.putExtra("poster",moviesItem.getLargeCoverImage());
                 detailsIntent.putExtra("720",x);
               detailsIntent.putExtra("1080",count);
                 detailsIntent.putExtra("synopsis",moviesItem.getSynopsis());

                 ctx.startActivity(detailsIntent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return moviesItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; TextView name,year,genre; RelativeLayout linearLayout;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_iv_poster);
            name = itemView.findViewById(R.id.item_tv_movie_name);
            year=itemView.findViewById(R.id.item_tv_movie_year);
            genre=itemView.findViewById(R.id.item_tv_movie_year);
            linearLayout =itemView.findViewById(R.id.item_relative_tintable);
            cardView=itemView.findViewById(R.id.item_card);

        }
    }
}
