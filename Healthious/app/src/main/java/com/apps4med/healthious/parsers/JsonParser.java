package com.apps4med.healthious.parsers;

import android.content.Context;
import android.util.Log;

import com.apps4med.healthious.model.HealthPrice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iskitsas on 26/10/2014.
 */
public class JsonParser {
    Context context;
    public JsonParser(Context context) {
        this.context = context;
    }
    public List<HealthPrice> getPrices(){

        List<HealthPrice> ret=new ArrayList<HealthPrice>();
        String filename="healthPrices.json";
        String lang=context.getResources().getConfiguration().locale.getISO3Language();

        String jsonContent=ParseUtility.readFileContentsFromAsset(context, filename);
        try {
            JSONObject root = new JSONObject(jsonContent);
            JSONArray jsonArray=root.getJSONArray("entries");
            JSONObject obj;
            int length=jsonArray.length();
            for(int i = 0;i<length;i++){
                obj=jsonArray.getJSONObject(i);

                HealthPrice price=new HealthPrice(
                        obj.getString("id"),
                        obj.getString("description"),
                        obj.getString("hospitalization"),
                        obj.getString("price")
                );
                ret.add(price);
            }
        } catch (JSONException e) {
            Log.e("LOG", "Error in parsing json");
        }


        return ret;
    }

    public List<HealthPrice> getPricesFromUrl(String url){
        List<HealthPrice> ret=new ArrayList<HealthPrice>();

        try {
            JSONObject root = getJSONFromUrl(url);
            JSONArray jsonArray=root.getJSONArray("entries");
            JSONObject obj;
            int length=jsonArray.length();
            for(int i = 0;i<length;i++){
                obj=jsonArray.getJSONObject(i);

                HealthPrice price=new HealthPrice(
                        obj.getString("id"),
                        obj.getString("description"),
                        obj.getString("hospitalization"),
                        obj.getString("price")
                );
                ret.add(price);
            }
        } catch (JSONException e) {
            Log.e("LOG", "Error in parsing json");
        }


        return ret;
    }

    public JSONObject getJSONFromUrl(String url) {
        InputStream is = null;
        JSONObject jObj = null;
        String json = "";
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // Depends on your web service
            //httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}
