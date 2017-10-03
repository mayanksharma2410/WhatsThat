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
    Data uid;
    private String getting_course, getting_sem, getting_year, get_id;
    private ListView mListView;
    List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dataList = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.listView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorage = storage.getReferenceFromUrl("gs://whatsthat-52cbb.appspot.com/");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("docs");

        Sem = (TextView)findViewById(R.id.post_sem);
        Course = (TextView)findViewById(R.id.post_course);
        Year = (TextView)findViewById(R.id.post_year);
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


        //adding a clicklistener on listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the upload
                Data data = dataList.get(i);

                //Opening the upload file in browser using the upload url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(data.getUrl()));
                startActivity(intent);
            }
        });

        //retrieving upload data from firebase database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Data data = postSnapshot.getValue(Data.class);
                    dataList.add(data);
                }

                String[] uploads = new String[dataList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = dataList.get(i).getUrl();
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Data data = new Data();
            data.setCourse(ds.child(get_id).getValue(Data.class).getCourse());
            data.setYear(ds.child(get_id).getValue(Data.class).getYear());
            data.setSem(ds.child(get_id).getValue(Data.class).getSem());

            ArrayList<String> array = new ArrayList<>();
            array.add(data.getCourse());
            array.add(data.getYear());
            array.add(data.getSem());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            mListView.setAdapter(adapter);
        }
    }

}
