package com.example.firebasecompletproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasecompletproject.model.Data;

import java.util.ArrayList;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.MyViewHolder> {
    private Context ctx;
    private ArrayList<Data> data;

    public NewAdapter(Context ctx, ArrayList<Data> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Data datas = data.get(i);
        String titls=datas.getTitle();
        myViewHolder.title_Cd.setText(titls);
        myViewHolder.description_cd.setText(datas.getDescription());
        myViewHolder.buget_Cd.setText(datas.getBudget());
        myViewHolder.date_cd.setText(datas.getDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title_Cd,description_cd,buget_Cd  ;
     Button date_cd;

         public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title_Cd=itemView.findViewById(R.id.title);
                description_cd=itemView.findViewById(R.id.descriptation);
                buget_Cd=itemView.findViewById(R.id.budget);
                date_cd=itemView.findViewById(R.id.date);

            }
        }

    }
