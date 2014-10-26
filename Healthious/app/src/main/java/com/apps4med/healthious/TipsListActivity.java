package com.apps4med.healthious;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.apps4med.healthious.model.Tip;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TipsListActivity extends ListActivity {
    private ArrayList<Tip> tips = new ArrayList<Tip>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MyTask().execute();

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(TipsListActivity.this,
                    "", "Loading. Please wait...", true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpGet get = new
                        HttpGet("http://www.mobistuff.net/apps/apps4med/healthTips.json");

                HttpResponse rp = hc.execute(get);

                if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String result = EntityUtils.toString(rp.getEntity());
                    JSONObject root = new JSONObject(result);
                    JSONObject healthTips = root.getJSONObject("healthtips");

                    JSONArray food = healthTips.getJSONArray("food");
                    for (int i = 0; i < food.length(); i++) {
                        String s = food.getString(i);

                        Tip tip = new Tip();
                        tip.content = "food";
                        tip.author = s;
                        tips.add(tip);
                    }
                    JSONArray exercise = healthTips.getJSONArray("exercise");
                    for (int i = 0; i < exercise.length(); i++) {
                        String s = exercise.getString(i);

                        Tip tip = new Tip();
                        tip.content = "exercise";
                        tip.author = s;
                        tips.add(tip);
                    }

                    JSONArray sleep = healthTips.getJSONArray("sleep");
                    for (int i = 0; i < sleep.length(); i++) {
                        String s = sleep.getString(i);

                        Tip tip = new Tip();
                        tip.content = "sleep";
                        tip.author = s;
                        tips.add(tip);
                    }
                }
            } catch (Exception e) {
                Log.e("TipsListActivity", "Error loading JSON", e);
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            setListAdapter(new TweetListAdaptor(
                    TipsListActivity.this, R.layout.list_row, tips));
        }

    }

    private class TweetListAdaptor extends ArrayAdapter<Tip> {

        private ArrayList<Tip> tips;

        public TweetListAdaptor(Context context,

                                int textViewResourceId,
                                ArrayList<Tip> items) {
            super(context, textViewResourceId, items);
            this.tips = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_row, null);
            }
            Tip o = tips.get(position);

            TextView tt = (TextView) v.findViewById(R.id.toptext);
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
            tt.setText(o.content);
            bt.setText(o.author);

            return v;
        }
    }
}