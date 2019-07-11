package com.hbeonlabs.newsmartguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReceiveSms extends BroadcastReceiver {

    private  static  final String TAG="ReceiveSms";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseRef = database.getReference().child(HubHomeActivity.title);

    public static String pstat;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle=intent.getExtras();
            SmsMessage[] msgs;
            String msg_from;
            if(bundle!=null) try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    msg_from = msgs[i].getOriginatingAddress();
                    String msgBody = msgs[i].getMessageBody();
                    String ph = HubHomeActivity.temp_ph.toString();

                    if (msg_from.equals(ph) ) {

                        if((msgBody.equals("Smart Guard is activated.")))
                        {
                            Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();

                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    pstat=dataSnapshot.child("device_status").getValue().toString();
                                    Log.d(TAG,"initial status: "+pstat);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Log.d(TAG, "yore out here "+pstat);

                            if(pstat.equals("false")) {
                                Log.d(TAG, "yore here ");
                                mDatabaseRef.child("device_status").setValue("true");
                                pstat="true";


                            }
                            else if(pstat.equals("true"))
                            {
                                mDatabaseRef.child("device_status").setValue("false");
                                pstat="false";
                                Log.d(TAG, "new status: "+pstat);
                            }
                        }



                        if((msgBody.equals("Smart Guard is deactivated."))) {
                            Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();

                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    pstat = dataSnapshot.child("device_status").getValue().toString();
                                    Log.d(TAG, "initial status: " + pstat);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Log.d(TAG, "yore out here " + pstat);

                            if (pstat.equals("false")) {
                                Log.d(TAG, "yore here ");
                                mDatabaseRef.child("device_status").setValue("true");
                                pstat = "true";


                            } else if (pstat.equals("true")) {
                                mDatabaseRef.child("device_status").setValue("false");
                                pstat = "false";
                                Log.d(TAG, "new status: " + pstat);
                            }
                        }


                        if((msgBody.equals("Siren on."))) {
                            Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();

                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    pstat = dataSnapshot.child("siren_status").getValue().toString();
                                    Log.d(TAG, "initial status: " + pstat);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Log.d(TAG, "yore out here " + pstat);

                            if (pstat.equals("false")) {
                                Log.d(TAG, "yore here ");
                                mDatabaseRef.child("siren_status").setValue("true");
                                pstat = "true";


                            }
                            else if (pstat.equals("true")) {
                                mDatabaseRef.child("siren_status").setValue("false");
                                pstat = "false";
                                Log.d(TAG, "new status: " + pstat);
                            }
                        }


                        if((msgBody.equals("Siren off."))) {
                            Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();

                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    pstat = dataSnapshot.child("siren_status").getValue().toString();
                                    Log.d(TAG, "initial status: " + pstat);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Log.d(TAG, "yore out here " + pstat);

                            if (pstat.equals("false")) {
                                Log.d(TAG, "yore here ");
                                mDatabaseRef.child("siren_status").setValue("true");
                                pstat = "true";


                            }
                            else if (pstat.equals("true")) {
                                mDatabaseRef.child("siren_status").setValue("false");
                                pstat = "false";
                                Log.d(TAG, "new status: " + pstat);
                            }
                        }

                        if((msgBody.equals("SG054 R"))) {
                            Toast.makeText(context, msgBody, Toast.LENGTH_LONG).show();
                            /*FirebaseDatabase db=FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference;
                            databaseReference=db.getReference();

                            DataManager data =new DataManager("https://sweetnaijamusics.com/wp-content/themes/SNM/images/default-398x200.jpg"
                                    ,AddHubActivity.mno,AddHubActivity.mname,"false","false");
                            databaseReference.child(AddHubActivity.mname).setValue(data);*/

                        }




                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}