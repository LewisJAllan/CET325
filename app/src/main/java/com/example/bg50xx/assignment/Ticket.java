package com.example.bg50xx.assignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by bg50xx on 12/12/2017.
 */

public class Ticket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }
}
