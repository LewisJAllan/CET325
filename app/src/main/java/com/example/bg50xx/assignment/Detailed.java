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
        btnUpdate = (Button) findViewById(R.id.btnDetUpdate);
        btnUpdate.setOnClickListener(this);
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
        MySQLiteHelper db = new MySQLiteHelper(this);
        art.setRating(ratingBar.getRating());
        art.setDescription(editDescription.getText().toString());
        art.setTitle(editTitle.getText().toString());
        art.setAuthor(editAuthor.getText().toString());
        art.setYear(Integer.parseInt(editYear.getText().toString()));
        art.setRoom(Integer.parseInt(editRoom.getText().toString()));
        db.updateArt(art);
        Toast.makeText(this, art.getTitle().toString() + " update",Toast.LENGTH_LONG).show();
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
                myIntent = new Intent(this.getApplication().getApplicationContext(), Ticket.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_home:
                myIntent = new Intent(this.getApplication().getApplicationContext(), MainActivity.class);
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
