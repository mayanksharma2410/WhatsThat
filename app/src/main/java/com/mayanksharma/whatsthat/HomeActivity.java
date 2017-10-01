package com.mayanksharma.whatsthat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class HomeActivity extends AppCompatActivity {
    private Button buttonScan;
    private IntentIntegrator qrScan;
    String post_qrValue;
    String post_course;
    String post_year;
    String post_sem;
    String post_image;
    String id;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    Data uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorage = storage.getReferenceFromUrl("gs://whatsthat-52cbb.appspot.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference("Docs");


        buttonScan = (Button) findViewById(R.id.buttonScan);

        //id = mDatabase.getKey();

        qrScan = new IntentIntegrator(this);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /*buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        }); */

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //id = dataSnapshot.getKey();
                uid = dataSnapshot.getValue(Data.class);
                //id = uid.getId();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            if(scanContent == null)
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else if (scanContent.equals(mDatabase.push().getKey())) {
                //Toast toast = Toast.makeText(getApplicationContext(), "1234", Toast.LENGTH_SHORT);
                //toast.show();
                Toast.makeText(this, uid.getCourse(), Toast.LENGTH_LONG).show();

                Intent intent1 = new Intent(HomeActivity.this, FirstActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(HomeActivity.this, "Sorry something went wrong", Toast.LENGTH_LONG).show();


              //  Intent intent2 = new Intent(HomeActivity.this, SecondActivity.class);
               // startActivity(intent2);
                //finish();
            }

        }
        else{
            //Toast toast = Toast.makeText(getApplicationContext(),
            // "No scan data received!", Toast.LENGTH_SHORT);
            //toast.show();
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
