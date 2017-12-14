package com.example.bg50xx.assignment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bg50xx on 14/12/2017.
 */

public class JSONCurrencyParser {

    public static Currency getCurrency(String data, String rate, String currency) throws JSONException {
        Currency cur = new Currency();

        if(rate.equals(currency)){
            cur.setCurType(rate);
            cur.setRate(1);
        }
        else{
            JSONObject Obj = new JSONObject(data);
            JSONObject RObj = Obj.getJSONObject("rates");
            String curRate = RObj.getString(rate);
            cur.setCurType(rate);
            cur.setRate(Float.parseFloat(String.valueOf(curRate)));
        }
        //Log.d(getCurrency(cur.toString()));
        return cur;
    }
}
