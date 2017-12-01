package com.mayanksharma.whatsthat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mayanksharma.whatsthat.model.Course;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    private TextView tv_room;
    private ImageView qImage;
    private Button bNext;
    private String text2Qr;
    private String get_id;
    private Uri mQrCodeUri = null;
    List<Data> dataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ArrayList<Course> course = this.getIntent().getParcelableArrayListExtra("course");


        dataList = new ArrayList<>();
        tv_room = (TextView)findViewById(R.id.display_room);
        qImage = (ImageView)findViewById(R.id.display_image);
        bNext = (Button)findViewById(R.id.next_button);
        showData(course);

        //to go back
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this, "Thank You for using WHATS THIS?", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DisplayActivity.this, FinishActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showData(ArrayList<Course> courses) {
        final Course cour=courses.get(0);

        tv_room.setText(cour.getRoomNo());
        get_id = (cour.getId());

       /* progressbar.setVisibility(View.GONE);
        pdfView.setVisibility(View.VISIBLE);
        pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(cour.getUrl()+".pdf"));
                intent.putExtra("url",cour.getUrl());
                startActivity(intent);
            }
        });*/



    }


    @Override
    protected void onStart() {
        super.onStart();
        //For generating QR CODE

                text2Qr = get_id;
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qImage.setImageBitmap(bitmap);
                    qImage.setImageURI(mQrCodeUri);

                    //BitmapDrawable drawable = (BitmapDrawable) Image.getDrawable();
                    //Bitmap bitmap1 = drawable.getBitmap();
                    //storeImage(bitmap1);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

    }

}
