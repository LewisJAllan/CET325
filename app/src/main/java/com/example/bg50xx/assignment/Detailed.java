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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class Detailed extends AppCompatActivity implements View.OnClickListener{
    //initialize globally tools and variables for the activity
    private String ID;
    EditText editTitle;
    RatingBar ratingBar;
    EditText editAuthor;
    EditText editDescription;
    EditText editRoom;
    EditText editYear;
    ImageView pic;
    Button btnUpdate;
    Artwork art;
    MySQLiteHelper db;
    //getter and setter for passed parameter of previous activity
    public String getID(){
        return this.ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        //retrieved the passed data from previous activity
        Intent intent = getIntent();
        setID(intent.getStringExtra("id"));
        Log.d("Deets",getID());
        //open database connection to this activity
        db = new MySQLiteHelper(this);
        //give tools a variable name from layout
        ratingBar = (RatingBar) findViewById(R.id.ratingDetailed);
        editTitle = (EditText) findViewById(R.id.editDetTitle);
        editAuthor = (EditText) findViewById(R.id.editDetAuthor);
        editDescription = (EditText) findViewById(R.id.editDetDescription);
        editRoom = (EditText) findViewById(R.id.editDetRoom);
        editYear = (EditText) findViewById(R.id.editDetYear);
        btnUpdate = (Button) findViewById(R.id.btnDetUpdate);
        btnUpdate.setOnClickListener(this);
        pic = (ImageView) findViewById(R.id.imgDetailed);
        //create an Artwork object from database using the passed ID from previous activity
        art = db.getArtByTitle(getID());
        Log.d("Artwork", art.toString());
        //set the values of the layout based on the Artwork object values
        editTitle.setText(art.getTitle());
        editAuthor.setText(art.getAuthor());
        editYear.setText(String.valueOf(art.getYear()));
        editRoom.setText(String.valueOf(art.getRoom()));
        ratingBar.setRating(art.getRating());
        ByteArrayInputStream imageStream = new ByteArrayInputStream(art.getImage());
        Bitmap picture = BitmapFactory.decodeStream(imageStream);
        pic.setImageBitmap(picture);
        editDescription.setText(art.getDescription());
        //Check to see if this is a preloaded entry and a user created entry
        if(art.getEdit()==0){
            editTitle.setInputType(InputType.TYPE_NULL);
            editAuthor.setInputType(InputType.TYPE_NULL);
            editDescription.setInputType(InputType.TYPE_NULL);
            editYear.setInputType(InputType.TYPE_NULL);
            editRoom.setInputType(InputType.TYPE_NULL);
        }
    }

    @Override
    public void onClick(View view) {
        //create a database conenction to pass new information back
        MySQLiteHelper db1 = new MySQLiteHelper(this);
        //update the Artwork class
        art.setRating(ratingBar.getRating());
        art.setDescription(editDescription.getText().toString());
        art.setTitle(editTitle.getText().toString());
        art.setAuthor(editAuthor.getText().toString());
        art.setYear(Integer.parseInt(editYear.getText().toString()));
        art.setRoom(Integer.parseInt(editRoom.getText().toString()));
        //update the database and close connections
        db1.updateArt(art);
        db1.close();
        db.close();
        Toast.makeText(this, art.getTitle().toString() + " update",Toast.LENGTH_LONG).show();
        //confirm the update and move back to the List view activity
        Intent myIntent = new Intent(this.getApplication().getApplicationContext(), MasterView.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        MenuItem item = menu.findItem(R.id.action_order);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;

        switch (item.getItemId()){
            case R.id.action_ticket:
                db.close();
                myIntent = new Intent(this.getApplication().getApplicationContext(), Ticket.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_home:
                db.close();
                myIntent = new Intent(this.getApplication().getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_gallery:
                db.close();
                myIntent = new Intent(this.getApplication().getApplicationContext(), MasterView.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_map:
                myIntent = new Intent(this.getApplication().getApplicationContext(), MapsActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
