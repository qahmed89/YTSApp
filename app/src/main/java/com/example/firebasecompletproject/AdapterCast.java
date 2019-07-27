package com.example.firebasecompletproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebasecompletproject.model.details.Cast;
import com.example.firebasecompletproject.model.details.Movie;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCast extends RecyclerView.Adapter<AdapterCast.MyViewHolder> {
    private List<Movie> models;
    private List<Cast> castslist;
    private Context ctx;

    public AdapterCast(List<Movie> models, Context ctx) {
        this.models = models;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AdapterCast.MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
Movie model= models.get(i);
castslist = model.getCast();
        Glide.with(ctx).load(castslist.get(i).getUrlSmallImage()).into(myViewHolder.circleImageView);
        myViewHolder.textView.setText(castslist.get(i).getCharacterName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.item_cast_iv_pic);
            textView = itemView .findViewById(R.id.item_cast_tv_name);
        }
    }
}
