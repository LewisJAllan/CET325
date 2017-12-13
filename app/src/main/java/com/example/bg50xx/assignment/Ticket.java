package com.example.bg50xx.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by bg50xx on 12/12/2017.
 */

public class Ticket extends AppCompatActivity {

    TextView txtAdult, txtStudent, txtChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        txtAdult = (TextView) findViewById(R.id.txtAdult);
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
            case R.id.action_home:
                myIntent = new Intent(this.getApplication().getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
//            case R.id.subaction_green:
//                rl.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
//                return true;
//            case R.id.action_user:
//                createUserDialog();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
