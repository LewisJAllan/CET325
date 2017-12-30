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
            String description = "Dinosaur exhibition";
            ByteArrayOutputStream stream = insertpic("trex");
            byte[] bitmapdata = stream.toByteArray();
            insertArt("T-Rex", "Dinosaur", 30000000, description, 1, 0, bitmapdata, 0);
            //Two
            String description2 = "Theory of Evolution exhibition";
            ByteArrayOutputStream stream2 = insertpic("darwin");
            byte[] bitmapdata2= stream2.toByteArray();
            insertArt("Darwin Exhibit", "Charles Darwin", 1800, description2, 2, 0, bitmapdata2, 0);
            //Three
            String description3 = "History and present day valcanoes exhibition";
            ByteArrayOutputStream stream3 = insertpic("volcanoes");
            byte[] bitmapdata3 = stream3.toByteArray();
            insertArt("Volcanoes", "Geography", 2018, description3, 3, 0, bitmapdata3, 0);
            //Four
            String description4 = "Collection of marine invertibrates from around the world";
            ByteArrayOutputStream stream4 = insertpic("invertibrates");
            byte[] bitmapdata4 = stream4.toByteArray();
            insertArt("Marine Invertibrates", "Marine Life", 2018, description4, 4, 0, bitmapdata4, 0);
            //Five
            String description5 = "Models, full sized, of worlds largest mammals";
            ByteArrayOutputStream stream5 = insertpic("whale");
            byte[] bitmapdata5 = stream5.toByteArray();
            insertArt("Mammals (Blue Whale)", "Sir Richard Attenbrough", 2018, description5, 5, 0, bitmapdata5, 0);
            //Six
            String description6 = "Vast Collection of photgraphers images of nature";
            ByteArrayOutputStream stream6 = insertpic("imagesnature");
            byte[] bitmapdata6 = stream6.toByteArray();
            insertArt("Images of Nature", "Various", 2017, description6, 6, 0, bitmapdata6, 0);
            //Seven
            String description7 = "Exhibition of mammals, living and extinct.";
            ByteArrayOutputStream stream7 = insertpic("mammals");
            byte[] bitmapdata7 = stream7.toByteArray();
            insertArt("Mammals", "Sir Richard Attenbrough", 2018, description7, 7, 0, bitmapdata7, 0);
            //Eight
            String description8 = "Amphibians and reptiles both living and extinct.";
            ByteArrayOutputStream stream8 = insertpic("amphibians");
            byte[] bitmapdata8 = stream8.toByteArray();
            insertArt("Amphibians and Reptiles", "Sir Richard Attenbrough", 2018, description8, 8, 0, bitmapdata8, 0);
            //Nine
            String description9 = "Intricate detail of human biology through-out the ages.";
            ByteArrayOutputStream stream9 = insertpic("humanbiology");
            byte[] bitmapdata9 = stream9.toByteArray();
            insertArt("Human Biology", "Various", 2018, description9, 9, 0, bitmapdata9, 0);
            //Ten
            String description10 = "Every creepy crawly we have information about, or lack of!";
            ByteArrayOutputStream stream10 = insertpic("ants");
            byte[] bitmapdata10 = stream10.toByteArray();
            insertArt("Creepy Crawlies", "Bug's Life", 2018, description10, 10, 0, bitmapdata10, 0);
        }
        else{
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }

    }

    private ByteArrayOutputStream insertpic(String pic){
        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier(pic, "drawable", getPackageName()));
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, 600, 400, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream;
    }

    private void insertArt(String title,String author, int year, String description, int room, float rating, byte[] bitmapdata, int edit) {
        art = new Artwork(title, author, description, year, room, bitmapdata, rating, edit);
        //Toast.makeText(this,"object Artwork " + art.toString(),Toast.LENGTH_LONG).show();
        db.addArt(art);

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
