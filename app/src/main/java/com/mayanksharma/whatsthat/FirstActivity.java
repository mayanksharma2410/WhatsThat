package com.mayanksharma.whatsthat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {
    private Button back;
    private TextView Course;
    private TextView Sem;
    private TextView Year;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    String id;
    private String get_id;
    ListView listViewData;
    List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorage = storage.getReferenceFromUrl("gs://whatsthat-52cbb.appspot.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("docs");

        listViewData = (ListView)findViewById(R.id.listViewData);
        dataList = new ArrayList<>();

        //Sem = (TextView)findViewById(R.id.post_sem);
        //Course = (TextView)findViewById(R.id.post_course);
        //Year = (TextView)findViewById(R.id.post_year);
        back = (Button)findViewById(R.id.back1);

        get_id = mDatabase.push().getKey();

        //to go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "Thank You for using WHATS THIS?", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FirstActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                id = dataSnapshot.getKey();
                Data data = dataSnapshot.getValue(Data.class);

                Course.setText(data.getCourse());
                Sem.setText(data.getSem());
                Year.setText(data.getYear());
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
}
