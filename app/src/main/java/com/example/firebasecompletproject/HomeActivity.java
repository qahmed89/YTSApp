package com.example.firebasecompletproject;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasecompletproject.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Data> data_list;
    ArrayList<Data> data;
    NewAdapter myAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbars);
        toolbar.setTitle("Note Movie");


        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AllData").child(uId);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerid);
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(HomeActivity.this, R.anim.layout_animation_random_scale);
//
//        recyclerView.setLayoutAnimation(animation);
//        recyclerView.scheduleLayoutAnimation();


        recyclerView.scheduleLayoutAnimation();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        data = new ArrayList<Data>();

        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new NewAdapter(HomeActivity.this, data);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Data titles = dataSnapshot1.getValue(Data.class);
                    data.add(titles);
                    myAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutAnimation(animation);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView.setAdapter(myAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addDate();
            }
        });
    }

    public void addDate() {
        AlertDialog.Builder mydailog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.inputlayout, null);
        mydailog.setView(view);
        final AlertDialog dialog = mydailog.create();
        final TextInputEditText mTitle = view.findViewById(R.id.title_input);
        final TextInputEditText mDescription = view.findViewById(R.id.desc_input);
        final TextInputEditText mBudget = view.findViewById(R.id.budget_input);
        Button mSave = view.findViewById(R.id.save);


        recyclerView.setAdapter(myAdapter);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString().trim();
                String description = mDescription.getText().toString().trim();
                String budget = mBudget.getText().toString().trim();
                String mDate = DateFormat.getDateInstance().format(new Date());
                String id = databaseReference.push().getKey();
                Data data = new Data(id, title, description, budget, mDate);
                databaseReference.child(id).setValue(data);
                Toast.makeText(getApplicationContext(), "Date Has been inserted", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), databaseReference.toString() + "Date Has been inserted", Toast.LENGTH_SHORT).show();


            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmuinu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

