package com.hbeonlabs.newsmartguard;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2 extends AppCompatActivity {

    private RecyclerView mcardview;
    private DatabaseReference databaseReference;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<DataManager> list;
    Toolbar toolbar;

   public Main2(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SELECT YOUR HUB");



        mcardview=(RecyclerView)findViewById(R.id.myrecyclerview);
        //mcardview.setHasFixedSize(true);
        mcardview.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<DataManager>();

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren() )
                {
                    DataManager d =dataSnapshot1.getValue(DataManager.class);
                    list.add(d);
                }

                recyclerViewAdapter=new RecyclerViewAdapter(Main2.this,list);
                mcardview.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        list.get(position);
                        DataManager d=list.get(position);
                        String temp_title=d.getTitle().toString();
                        String temp_img=d.getImage().toString();
                        String temp_mob=d.getMob().toString();
                        String temp_status=d.getDevice_status().toString();
                        Intent intent=new Intent(Main2.this,HubHomeActivity.class);
                        intent.putExtra("Title",temp_title);
                        intent.putExtra("Image",temp_img);
                        intent.putExtra("Mobile No.",temp_mob);
                        intent.putExtra("Device Status",temp_status);
                        startActivity(intent);
                        finish();


                       // Toast.makeText(Main2.this, "Hub Clicked"+temp_title, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Main2.this, "Oopps..Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int res_id=item.getItemId();
       if(res_id==R.id.addicon)
       {
           Intent intent=new Intent(Main2.this,AddHubActivity.class);
           startActivity(intent);
           finish();
           Toast.makeText(getApplicationContext(), "Add a new Hub", Toast.LENGTH_SHORT).show();
       }
        return true;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
