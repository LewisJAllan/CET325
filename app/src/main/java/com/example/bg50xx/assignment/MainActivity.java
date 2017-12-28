package com.example.bg50xx.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getContentResolver().query(ArtworkProvider.CONTENT_URI, null, null, null, null).getCount() == 0){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }

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
