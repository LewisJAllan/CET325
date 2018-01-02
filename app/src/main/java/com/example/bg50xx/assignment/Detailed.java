package com.example.bg50xx.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

/**
 * Created by Lewis on 02/01/2018.
 */

public class Detailed extends AppCompatActivity {

    private String ID;
    boolean editable;
    EditText editTitle;
    RatingBar ratingBar;
    EditText editAuthor;
    EditText editDescription;
    EditText editRoom;
    EditText editYear;
    ImageView pic;
    byte[] image;
    final ViewGroup nullParent = null;
    Cursor cursor;
    Artwork art;

    public String getID(){
        return this.ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

//    public float getRating(){
//        return this.rating;
//    }
//
//    public void setRating(float rating){
//        this.rating = rating;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();
        setID(intent.getStringExtra("id"));
        Log.d("Deets",getID());
        MySQLiteHelper db = new MySQLiteHelper(this);

        ratingBar = (RatingBar) findViewById(R.id.ratingDetailed);
        editTitle = (EditText) findViewById(R.id.editDetTitle);
        editAuthor = (EditText) findViewById(R.id.editDetAuthor);
        editDescription = (EditText) findViewById(R.id.editDetDescription);
        editRoom = (EditText) findViewById(R.id.editDetRoom);
        editYear = (EditText) findViewById(R.id.editDetYear);
        pic = (ImageView) findViewById(R.id.imgDetailed);
        art = db.getArt(Integer.parseInt(getID()));
        art.id = Integer.parseInt(getID());
        Log.d("Artwork", art.toString());

        editTitle.setText(art.getTitle());
        editAuthor.setText(art.getAuthor());
        editYear.setText(String.valueOf(art.getYear()));
        editRoom.setText(String.valueOf(art.getRoom()));
        ratingBar.setRating(art.getRating());
        ByteArrayInputStream imageStream = new ByteArrayInputStream(art.getImage());
        Bitmap picture = BitmapFactory.decodeStream(imageStream);
        pic.setImageBitmap(picture);
        editDescription.setText(art.getDescription());

        editable = (art.getEdit() > 0 ? true : false);
        if(!editable){
            editTitle.setInputType(InputType.TYPE_NULL);
            editAuthor.setInputType(InputType.TYPE_NULL);
            editDescription.setInputType(InputType.TYPE_NULL);
            editYear.setInputType(InputType.TYPE_NULL);
            editRoom.setInputType(InputType.TYPE_NULL);
        }
    }
}
