package com.example.bg50xx.assignment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DecimalFormat;

/**
 * Created by bg50xx on 12/12/2017.
 */

public class Ticket extends AppCompatActivity implements View.OnClickListener{

    TextView txtAdult, txtStudent, txtChild;
    double adult, child;
    String currentRate, alternateRate;
    DecimalFormat df;
    Button btnGBP,btnEuro;
    Currency cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        txtAdult = (TextView) findViewById(R.id.txtAdult);
        txtStudent = (TextView) findViewById(R.id.txtStudent);
        txtChild = (TextView) findViewById(R.id.txtChild);
        btnGBP = (Button) findViewById(R.id.btnGBP);
        btnGBP.setOnClickListener(this);
        btnEuro = (Button) findViewById(R.id.btnEuro);
        btnEuro.setOnClickListener(this);
        adult = 10.00;
        child = 5.00;
        GBP(adult,child);
        currentRate = "GBP";
        alternateRate = "EUR";
        df = new DecimalFormat("0.00");
        btnGBP.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    public void GBP(double adult, double child){
        df = new DecimalFormat("0.00");
        txtAdult.setText("£" + df.format(adult));
        txtStudent.setText("£" + df.format((adult*0.7)));
        txtChild.setText("£" + df.format(child));
    }

    public void EUR(float rate){
        df = new DecimalFormat("0.00");
        txtAdult.setText("€" + df.format((adult*rate)));
        txtStudent.setText("€" + df.format(((adult*rate)*0.7)));
        txtChild.setText("€" + df.format((child*rate)));
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        JSONCurrencyTask task = new JSONCurrencyTask();
        task.execute(new String[]{alternateRate});

        if (id == R.id.btnGBP) {
            GBP(adult, child);
            btnGBP.setVisibility(View.INVISIBLE);
            btnEuro.setVisibility((View.VISIBLE));
        }
        if (id == R.id.btnEuro) {
            EUR(cur.getRate());
            btnGBP.setVisibility(View.VISIBLE);
            btnEuro.setVisibility((View.INVISIBLE));
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
                    cur = JSONCurrencyParser.getCurrency(data, alternateRate, currentRate);
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
            if (cur!=null) {

                if (cur.getCurType()=="EUR"){
                    EUR(cur.getRate());
                }
            }
            else
            {
                GBP(adult, child);
            }
        }
    }
}
