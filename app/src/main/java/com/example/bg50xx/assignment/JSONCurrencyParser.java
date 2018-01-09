package com.example.bg50xx.assignment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bg50xx on 14/12/2017.
 */

public class JSONCurrencyParser {
    //parse the data from the API and get the values to which can be saved to the
    //Currency class

    public static Currency getCurrency(String data) throws JSONException {
        Currency cur = new Currency();

        JSONObject Obj = new JSONObject(data);

        JSONObject mainObj = getObject("rates", Obj);
        cur.setRate(getFloat("EUR", mainObj));

        //Log.d(getCurrency(cur.toString()));
        return cur;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(tagName);
    }
    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }
}
