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

    Artwork art;
    MySQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MySQLiteHelper(this);

        if(getContentResolver().query(ArtworkProvider.CONTENT_URI, null, null, null, null).getCount() == 0){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            //One
            insertpic("trex", "T-Rex", "Dinosaur", 30000000, "Dinosaur exhibition", 1, 0, 0);
            //Two
            insertpic("darwin", "Darwin Exhibit", "Charles Darwin", 1800, "Theory of Evolution exhibition", 2, 0, 0);
            //Three
            String description3 = "History and present day valcanoes exhibition";
            insertpic("volcanoes", "Volcanoes", "Geography", 2018, description3, 3, 0, 0);
            //Four
            String description4 = "Collection of marine invertibrates from around the world";
            insertpic("invertibrates", "Marine Invertibrates", "Marine Life", 2018, description4, 4, 0, 0);
            //Five
            String description5 = "Models, full sized, of worlds largest mammals";
            insertpic("whale", "Mammals (Blue Whale)", "Sir Richard Attenbrough", 2018, description5, 5, 0, 0);
            //Six
            String description6 = "Vast Collection of photgraphers images of nature";
            insertpic("imagesnature", "Images of Nature", "Various", 2017, description6, 6, 0, 0);
            //Seven
            String description7 = "Exhibition of mammals, living and extinct.";
            insertpic("mammals", "Mammals", "Sir Richard Attenbrough", 2018, description7, 7, 0, 0);
            //Eight
            String description8 = "Amphibians and reptiles both living and extinct.";
            insertpic("amphibians", "Amphibians and Reptiles", "Sir Richard Attenbrough", 2018, description8, 8, 0, 0);
            //Nine
            String description9 = "Intricate detail of human biology through-out the ages.";
            insertpic("humanbiology", "Human Biology", "Various", 2018, description9, 9, 0, 0);
            //Ten
            String description10 = "Every creepy crawly we have information about, or lack of!";
            insertpic("ants","Creepy Crawlies", "Bug's Life", 2018, description10, 10, 0, 0);
        }
        else{
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertpic(String pic, String title,String author, int year, String description, int room, float rating, int edit){
        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier(pic, "drawable", getPackageName()));
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, 600, 400, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        art = new Artwork(title, author, description, year, room, bitmapdata, rating, edit);
        db.addArt(art);

        Toast.makeText(this,"Created Artwork " + title,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        MenuItem item = menu.findItem(R.id.action_order);
        item.setVisible(false);
        MenuItem home = menu.findItem(R.id.action_home);
        home.setVisible(false);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
