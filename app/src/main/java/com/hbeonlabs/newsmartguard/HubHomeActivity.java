package com.hbeonlabs.newsmartguard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HubHomeActivity extends AppCompatActivity {

    private  static  final String TAG="HubHomeActivity";
    public static String temp_ph,title;
    private DatabaseReference databaseReference;

    String dstatus; //device status
    String s_status; //sirenstatus
    String h_status; //hub status
    Toolbar toolbar;
    ImageView img;
    Context context;
    TextView status1,status2;

    public  Button btn_arm, btn_disarm,btn_ring,btn_silence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_home);

        //fetching variables from Main2
        Intent intent=getIntent();
         title=intent.getStringExtra("Title");
        String image=intent.getStringExtra("Image");
        final String mob=intent.getStringExtra("Mobile No.");
        String status=intent.getStringExtra("Device Status");

        temp_ph=new String(mob);//Variable for passing value to ReceiveSms

        //Toolbar setup
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //ImageView
        img=(ImageView)findViewById(R.id.imagehubhome);
        Picasso.with(context)
                .load(image)
                .into(img);

        //Buttons Intialization
        btn_arm=(Button)findViewById(R.id.buttonarm);
        btn_disarm=(Button)findViewById(R.id.buttondisarm);
        btn_ring=(Button)findViewById(R.id.buttonring);
        btn_silence=(Button)findViewById(R.id.buttonsilence);


        //Status Textview
        status1=(TextView) findViewById(R.id.sirenstatus);
        status2=(TextView)findViewById(R.id.devicestatus);


        //Button toggle
        databaseReference= FirebaseDatabase.getInstance().getReference().child(title);
        Log.d(TAG,"title: "+title);
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dstatus= dataSnapshot.child("device_status").getValue(String.class);
                s_status=dataSnapshot.child("siren_status").getValue(String.class);
                h_status=dataSnapshot.child("hub_status").getValue(String.class);
                Log.d("TAG", "hub:"+dstatus+"/"+s_status+"/"+h_status);

                if(dstatus.equals("true"))
                {
                    btn_disarm.setEnabled(true);
                    btn_disarm.setBackgroundColor(Color.parseColor("#63dbff"));
                    btn_arm.setEnabled(false);
                    btn_arm.setBackgroundColor(Color.parseColor("#bfbfbf"));
                    status2.setText("Status: Armed");
                }
                if (dstatus.equals("false"))
                {
                    btn_arm.setEnabled(true);
                    btn_arm.setBackgroundColor(Color.parseColor("#63dbff"));
                    btn_disarm.setEnabled(false);
                    btn_disarm.setBackgroundColor(Color.parseColor("#bfbfbf"));
                    status2.setText("Status: Disarmed");
                }

                if(s_status.equals("true")){
                    btn_ring.setEnabled(false);
                    btn_ring.setBackgroundColor(Color.parseColor("#bfbfbf"));
                    btn_silence.setEnabled(true);
                    btn_silence.setBackgroundColor(Color.parseColor("#63dbff"));
                    status1.setText("Siren ON");
                }
                if(s_status.equals("false"))
                {
                    btn_silence.setEnabled(false);
                    btn_silence.setBackgroundColor(Color.parseColor("#bfbfbf"));
                    btn_ring.setBackgroundColor(Color.parseColor("#63dbff"));
                    btn_ring.setEnabled(true);
                    status1.setText("Siren OFF");
                }
                if(h_status.equals("false"))
                {
                    btn_ring.setEnabled(false);
                    btn_silence.setEnabled(false);
                    btn_disarm.setEnabled(false);
                    btn_arm.setEnabled(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //Button OnclickListener
        btn_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!mob.equals("")) {
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(mob, null, "SG054 EN", null, null);
                            Toast.makeText(getApplicationContext(), "Request Sent Successfully !",
                                    Toast.LENGTH_SHORT).show();


                        }
                        catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS faild, please try again later!",
                                    Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }
                    }
            }
        });

        btn_disarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mob.equals("")) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mob, null, "SG054 DI", null, null);
                        Toast.makeText(getApplicationContext(), "Request Sent Successfully !",
                                Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, please try again later!",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }
            }
        });

        btn_ring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mob.equals("")) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mob, null, "SG054 SON", null, null);
                        Toast.makeText(getApplicationContext(), "Request Sent Successfully !",
                                Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, please try again later!",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }
            }
        });

        btn_silence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mob.equals("")) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mob, null, "SG054 SOFF", null, null);
                        Toast.makeText(getApplicationContext(), "Request Sent Successfully !",
                                Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, please try again later!",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }
                }
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_hub_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(HubHomeActivity.this,Main2.class);
            startActivity(intent);
            finish();
        }
        if(item.getItemId()==R.id.settingsicon)
        {
            Intent intent=new Intent(HubHomeActivity.this,Settings.class);
            startActivity(intent);
            finish();
            //Toast.makeText(HubHomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Main2.class));
        finish();
    }
}
