package com.example.bg50xx.assignment;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getContentResolver().query(ArtworkProvider.CONTENT_URI, null, null, null, null).getCount() == 0){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            String title = "Dinosaur";
            String author = "Lewis";
            int year = 2018;
            String description = "Big Fuck off Dinosaur";
            int room = 1;
            float rating = 5;
            Drawable drawable = getResources().getDrawable(getResources()
                    .getIdentifier("nhm", "drawable", getPackageName()));
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            bitmap = Bitmap.createScaledBitmap(bitmap, 600, 400, false);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            insertArt(title, author, year, description, room, rating, bitmapdata, 0);
        }
        else{
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertArt(String title,String author, int year, String description, int room, float rating, byte[] bitmapdata, int edit) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_TITLE,title);
        values.put(MySQLiteHelper.KEY_AUTHOR,author);
        values.put(MySQLiteHelper.KEY_YEAR,year);
        values.put(MySQLiteHelper.KEY_DESCRIPTION,description);
        values.put(MySQLiteHelper.KEY_ROOM,room);
        values.put(MySQLiteHelper.KEY_RATING,rating);
        values.put(MySQLiteHelper.KEY_IMAGE,bitmapdata);
        values.put(MySQLiteHelper.KEY_EDIT, edit);
        getContentResolver().insert(ArtworkProvider.CONTENT_URI,values);
        Toast.makeText(this,"Created Artwork " + title,Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;

        switch (item.getItemId()){
            case R.id.action_ticket:
                myIntent = new Intent(this.getApplication().getApplicationContext(), Ticket.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_gallery:
            myIntent = new Intent(this.getApplication().getApplicationContext(), MasterView.class);
            startActivity(myIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
//            case R.id.action_user:
//                createUserDialog();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
