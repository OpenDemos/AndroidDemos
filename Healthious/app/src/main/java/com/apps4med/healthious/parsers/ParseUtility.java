package com.apps4med.healthious.parsers;

import android.content.Context;
import android.util.Log;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: skitsas
 * Date: 5/18/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseUtility {

    public static String readFileContentsFromAsset(Context context,String filename){
        String response;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            response = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.e("LOG","Error reading file from asset: IO exception");
            return null;
        }
        return response;
    }

    public static String readFileContentsFromRaw(Context context,int resourceId){
        String response;
        try {
            InputStream is = context.getResources().openRawResource(resourceId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            response = new String(buffer);
        } catch (IOException e) {
            Log.e("LOG","Error reading file from raw: IO exception");
            return null;
        }
        return response;
    }
}
