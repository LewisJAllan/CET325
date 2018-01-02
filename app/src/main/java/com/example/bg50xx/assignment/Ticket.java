package com.example.bg50xx.assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DecimalFormat;

/**
 * Created by bg50xx on 12/12/2017.
 */

public class Ticket extends AppCompatActivity implements View.OnClickListener{

    TextView txtAdult, txtStudent, txtChild;
    double adult, child, discount;
    DecimalFormat df;
    Button btnGBP,btnEuro, btnPrice;
    Currency cur;
    SharedPreferences myPreferences;
    SharedPreferences.Editor editor;
    EditText editAdult, editChild, editPass, editDiscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        txtAdult = (TextView) findViewById(R.id.txtAdult);
        txtStudent = (TextView) findViewById(R.id.txtStudent);
        txtChild = (TextView) findViewById(R.id.txtChild);
        editAdult = (EditText) findViewById(R.id.editAdult);
        editChild = (EditText) findViewById(R.id.editChild);
        editPass = (EditText) findViewById(R.id.editPass);
        editDiscount = (EditText) findViewById(R.id.editDiscount);
        btnGBP = (Button) findViewById(R.id.btnGBP);
        btnGBP.setOnClickListener(this);
        btnEuro = (Button) findViewById(R.id.btnEuro);
        btnEuro.setOnClickListener(this);
        btnPrice = (Button) findViewById(R.id.btnPrice);
        btnPrice.setOnClickListener(this);
        adult = 10.00;
        child = 5.00;
        discount = 0.3;

        JSONCurrencyTask task = new JSONCurrencyTask();
        task.execute(new String[]{""});
        Thread thread = new Thread();
        try {
            thread.run();
            thread.sleep(1500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("pref", String.valueOf(cur.getRate()));
        loadPreferences();

    }

    public void loadPreferences(){
        myPreferences = getPreferences(Context.MODE_PRIVATE);
        String Pref = myPreferences.getString("Currency", "");
        Log.d("pref", Pref);

        if(Pref.equals("GBP")){
            GBP(adult, child);
        }
        else if(Pref.equals("EUR")){
            EUR(cur.getRate());
        }
        else {
            GBP(adult, child);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        MenuItem item = menu.findItem(R.id.action_order);
        item.setVisible(false);
        MenuItem ticket = menu.findItem(R.id.action_ticket);
        ticket.setVisible(false);
        return true;
    }

    public void GBP(double adult, double child){
        df = new DecimalFormat("'£'0.00");
        txtAdult.setText(df.format(adult));
        txtStudent.setText(df.format((adult*discount)));
        txtChild.setText(df.format(child));
        btnGBP.setVisibility(View.INVISIBLE);
        btnEuro.setVisibility((View.VISIBLE));
    }

    public void EUR(float rate){
        df = new DecimalFormat("'€'0.00");
        txtAdult.setText(df.format((adult*rate)));
        txtStudent.setText(df.format(((adult*rate)*discount)));
        txtChild.setText(df.format((child*rate)));
        btnGBP.setVisibility(View.VISIBLE);
        btnEuro.setVisibility((View.INVISIBLE));
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
            case R.id.action_gallery:
                myIntent = new Intent(this.getApplication().getApplicationContext(), MasterView.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnGBP) {
            GBP(adult, child);
            editor = myPreferences.edit();
            editor.putString("Currency", "GBP");
            editor.commit();
        }
        if (id == R.id.btnEuro) {
            EUR(cur.getRate());
            editor = myPreferences.edit();
            editor.putString("Currency", "EUR");
            editor.commit();
        }
        if (id == R.id.btnPrice) {
            if(editPass.getText().toString().equals("admin")){
                adult = Double.parseDouble(editAdult.getText().toString());
                child = Double.parseDouble(editChild.getText().toString());
                discount = (100-(Double.parseDouble(editDiscount.getText().toString())))/100;
                GBP(adult,child);
            }
            else{
                Toast.makeText(getApplicationContext(),"Incorrect password" + editPass.getText().toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private class JSONCurrencyTask extends AsyncTask<String, Void, Currency> {

        @Override
        protected Currency doInBackground(String... params) {
            Log.d("data", params[0]);
            cur = new Currency();
            String data = ((new CurrencyHttpClient()).getCurrencyData());
            if (data != null) {
                try {
                    cur = JSONCurrencyParser.getCurrency(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return cur;
            }
            else return null;
        }

        @Override
        protected void onPostExecute(Currency cur) {
            super.onPostExecute(cur);
            if (cur ==null) {
                Toast.makeText(getApplicationContext(),"Unable to retrieve data",Toast.LENGTH_LONG).show();
            }
        }
    }
}
