package com.example.bg50xx.assignment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by Lewis on 18/12/2017.
 */

public class MasterView extends AppCompatActivity {

    MySQLiteHelper db;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);

    }

    @Override
    public void onStart(){

        super.onStart();

        db = new MySQLiteHelper(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nhm);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();


        // add tracks
        db.addArtwork(new Artwork("Test", "Lewis", "Test", 1, 1992, bitMapData, 5));
        db.addArtwork(new Artwork("Test2", "Lewis", "Test2", 1, 2222, bitMapData, 3));

        // get all tracks
        List<Artwork> list = db.getAllArt();

        // get all tracks
        db.getAllArt();

    }
}
