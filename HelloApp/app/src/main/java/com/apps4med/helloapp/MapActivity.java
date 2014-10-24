package com.apps4med.helloapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * https://code.google.com/apis/console/?noredirect#project:479916481218:access
 * http://developer.android.com/tools/publishing/app-signing.html
 * https://developers.google.com/maps/documentation/android/
 * https://developers.google.com/maps/documentation/android/start#getting_the_google_maps_android_api_v2
 * https://code.google.com/apis/console/
 * http://www.vogella.com/tutorials/AndroidGoogleMaps/article.html
 * https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=85:84:84:79:A3:DD:67:22:58:4F:B8:F5:63:EA:BD:F9:38:41:23:42%3Bcom.apps4med.helloapp
* */
public class MapActivity extends Activity {

    static final LatLng ATHENS = new LatLng(37.984275, 23.729413);
    static final LatLng PATRAS = new LatLng(38.246790, 21.734104);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode == ConnectionResult.SUCCESS) {
            Marker athens = map.addMarker(new MarkerOptions().position(ATHENS)
                    .title("Athens"));
            Marker patras = map.addMarker(new MarkerOptions()
                    .position(PATRAS)
                    .title("Patras")
                    .snippet("Patras is cool")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher)));

            //Move the camera instantly to athens with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ATHENS, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(6), 2000, null);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
