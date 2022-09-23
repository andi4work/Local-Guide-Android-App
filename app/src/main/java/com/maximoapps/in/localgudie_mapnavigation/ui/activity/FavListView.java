package com.maximoapps.in.localgudie_mapnavigation.ui.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.etsy.android.grid.StaggeredGridView;
import com.google.android.gms.maps.model.LatLng;
import com.maximoapps.in.localgudie_mapnavigation.adapters.CustomGridAdapter;
import com.maximoapps.in.localgudie_mapnavigation.helpers.DbHandler;
import com.maximoapps.in.localgudie_mapnavigation.helpers.GPSTracker;
import com.maximoapps.in.localgudie_mapnavigation.model.GooglePlacesResponseResults;
import com.maximoapps.localgudie_mapnavigation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CM on 06-10-2020.
 */

public class FavListView extends AppCompatActivity {
    Intent intent;
    CustomGridAdapter adapter;
    StaggeredGridView grid;
    Toolbar mToolbar;
    ListView listView;
    String currentLocationCoordinates;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_list_view);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Favourite Places List");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        GPSTracker latlon = new GPSTracker(getApplicationContext());
        String lat = String.valueOf(latlon.getLatitude());
        String lon = String.valueOf(latlon.getLongitude());
        //  currentLocationCoordinates = "12.971599,77.594563";
        currentLocationCoordinates = lat + "," + lon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setTitle("List of favorite places");
            mToolbar.collapseActionView();
        }
        final DbHandler db = new DbHandler(this);
        final ArrayList<HashMap<String, String>> userList = db.GetPlaceDetails();
        listView = (ListView) findViewById(R.id.user_list);
        final ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.fav_list_row, new String[]{"name", "address"}, new int[]{R.id.tv_title, R.id.tv_address});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final DbHandler dbHandler = new DbHandler(FavListView.this);

                Toast.makeText(getApplicationContext(), userList.get(position).get("name"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PlaceDetailActivity.class);

                if (dbHandler.GetPlaceDetails().get(position).get("name") != null) {
                    intent.putExtra("title", dbHandler.GetPlaceDetails().get(position).get("name"));
                }
                if (dbHandler.GetPlaceDetails().get(position).get("place_id") != null) {
                    intent.putExtra("place_id", dbHandler.GetPlaceDetails().get(position).get("place_id"));
                }
                if (dbHandler.GetPlaceDetails().get(position).get("image") != null) {
                    intent.putExtra("photo_reference", dbHandler.GetPlaceDetails().get(position).get("image"));
                }
                if (dbHandler.GetPlaceDetails().get(position).get("address") != null) {
                    intent.putExtra("address", dbHandler.GetPlaceDetails().get(position).get("address"));
                }
                if (dbHandler.GetPlaceDetails().get(position).get("distance") != null) {
                    intent.putExtra("distance", dbHandler.GetPlaceDetails().get(position).get("distance"));
                }
                if (dbHandler.GetPlaceDetails().get(position).get("contact") != null) {
                    intent.putExtra("contact", dbHandler.GetPlaceDetails().get(position).get("contact"));
                }

                if (dbHandler.GetPlaceDetails().get(position).get("rating") != null)
                    intent.putExtra("rating", dbHandler.GetPlaceDetails().get(position).get("rating"));
                intent.putExtra("current_lat_lon", currentLocationCoordinates);

                String[] latlong = dbHandler.GetPlaceDetails().get(position).get("location").split(",");
                double lat = Double.parseDouble(latlong[0]);
                double lon = Double.parseDouble(latlong[1]);
                Location currentLocation = new Location("CurrentLocation");
                Location apiLocation = new Location("apiLocation");

                GPSTracker latlon = new GPSTracker(getApplicationContext());
                Double lat1 = latlon.getLatitude();
                Double lon1 = latlon.getLongitude();
                currentLocation.setLatitude(lat1);
                currentLocation.setLongitude(lon1);
                apiLocation.setLatitude(lat);
                apiLocation.setLongitude(lon);
                if (dbHandler.GetPlaceDetails().get(position).get("location") != null) {
                    intent.putExtra("api_location_lat", lat);
                    intent.putExtra("api_location_long", lon);
                    intent.putExtra("current_location_lat", lat1);
                    intent.putExtra("current_location_long", lon1);
                }
                float distanceD = apiLocation.distanceTo(currentLocation) / 1000;
                String s = String.valueOf(distanceD).substring(0, 4);
                if (s != null) {
                    intent.putExtra("distance", s + " Kms");
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final DbHandler db = new DbHandler(this);
        final ArrayList<HashMap<String, String>> userList = db.GetPlaceDetails();
        final ListAdapter adapter = new SimpleAdapter(this, userList, R.layout.fav_list_row, new String[]{"name", "address"}, new int[]{R.id.tv_title, R.id.tv_address});
        listView.setAdapter(adapter);
    }
}