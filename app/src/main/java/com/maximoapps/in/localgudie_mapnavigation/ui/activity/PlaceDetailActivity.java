package com.maximoapps.in.localgudie_mapnavigation.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maximoapps.in.localgudie_mapnavigation.helpers.Constants;
import com.maximoapps.in.localgudie_mapnavigation.helpers.DbHandler;
import com.maximoapps.in.localgudie_mapnavigation.helpers.GPSTracker;
import com.maximoapps.in.localgudie_mapnavigation.helpers.JSONParser;
import com.maximoapps.localgudie_mapnavigation.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView address, distance, contact, rating, review;
    ImageView call;
    String title, ratingS, photo, place_id, addressS, distanceS, contactS, currentLocationCoordinates, apiLocationCoordinates;
    //InterstitialAd mInterstitialAd;
    View mMapFragment;
    Double lat, lon, latCL, lonCL;
    Context mContext;
    ImageView backgroundImage;
    RatingBar ratingB;
    private GoogleMap mMap;
    ArrayList<String> reviewsArray = new ArrayList<String>();
    FloatingActionButton favourite, share;
    private SQLiteDatabase readableDatabase;
    public static final String DATABASE_NAME = "LocalGudie";
    public static final String FAVOURITE_TABLE_NAME = "FavouritePlaces";
    Boolean isFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_activity_detail);
        final DbHandler dbHandler = new DbHandler(PlaceDetailActivity.this);
        isFavourite = dbHandler.isFavouritePlaceOrNot(getIntent().getStringExtra("place_id"));

        favourite = (FloatingActionButton) findViewById(R.id.fabFavorite);
        if (isFavourite) {
            favourite.setImageResource(R.drawable.ic_favorite_selected_red);
        } else {
            favourite.setImageResource(R.drawable.ic_favorite_black);

        }


        share = (FloatingActionButton) findViewById(R.id.fabShare);
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavourite = dbHandler.isFavouritePlaceOrNot(getIntent().getStringExtra("place_id"));
                if (isFavourite == true) {
                    dbHandler.DeleteFavouritePlace(place_id);
                    favourite.setImageResource(R.drawable.ic_favorite_black);
                    Toast.makeText(getApplicationContext(), title + " removed from Favourite list", Toast.LENGTH_LONG).show();
                    return;
                } else if (title != null && isFavourite == false)
                    dbHandler.insertPlaceDetails(title, photo, ratingS, addressS, distanceS, contactS, apiLocationCoordinates, place_id);
                favourite.setImageResource(R.drawable.ic_favorite_selected_red);
                Toast.makeText(getApplicationContext(), title + " added to Favourite list", Toast.LENGTH_LONG).show();
                try {

                } catch (Exception e) {
                    favourite.setImageResource(R.drawable.ic_favorite_black);
                    System.out.println("Exemption error ::: " + e.toString());
                }

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Place Name: " + title + "\n Address: " + addressS + "\nhttp://maps.google.com/maps?q=" + title + " " + apiLocationCoordinates;
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check this out");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Check"));
            }
        });
        mMapFragment = (View) findViewById(R.id.map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        AdView adview = (AdView) findViewById(R.id.adViewTwo);
        final AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        address = (TextView) findViewById(R.id.tvAddress);
        distance = (TextView) findViewById(R.id.tvDistancee);
        contact = (TextView) findViewById(R.id.tvContact);
        review = (TextView) findViewById(R.id.tvReviews);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDetailActivity.this);
                builder.setTitle("A few reviews").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                ;

                ListView modeList = new ListView(PlaceDetailActivity.this);
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(PlaceDetailActivity.this, R.layout.review_list_item, R.id.tvReview, reviewsArray);
                modeList.setAdapter(modeAdapter);
                builder.setView(modeList);
                final Dialog dialog = builder.create();
                dialog.show();

            }
        });
        contact.setText("NA");
        call = (ImageView) findViewById(R.id.ivCall);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + contact.getText().toString()));
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rating = (TextView) findViewById(R.id.tvRating);
        ratingB = (RatingBar) findViewById(R.id.ratingbar);
        ratingB.setStepSize((float) 0.1);

        backgroundImage = (ImageView) findViewById(R.id.ivPLaceLocation);
        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsImageActivity.class);
                int[] screenLocation = new int[2];
                backgroundImage.getLocationOnScreen(screenLocation);

                //Pass the image title and url to DetailsImageActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", backgroundImage.getWidth()).
                        putExtra("height", backgroundImage.getHeight()).
                        putExtra("image", "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1400&key=" + Constants.API_KEY + "&photoreference=" + photo);
                //Start details activity
                startActivity(intent);
            }
        });
        title = getIntent().getStringExtra("title");
        place_id = getIntent().getStringExtra("place_id");
        new JsonTask().execute("https://maps.googleapis.com/maps/api/place/details/json?&key=" + Constants.API_KEY + "&placeid=" + place_id);
        // new JsonTask().execute("https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJxwkMVGOayzsROxCMjpscktA&key=AIzaSyABpsBAhnFtxKZzC4W-PjH2o7pABWywcC0");
        ratingS = getIntent().getStringExtra("rating");
        PlaceDetailActivity.this.setTitle(title);
        addressS = getIntent().getStringExtra("address");
        distanceS = getIntent().getStringExtra("distance");
        distance.setText(distanceS);
        photo = getIntent().getStringExtra("photo_reference");
        lat = getIntent().getDoubleExtra("api_location_lat", 12.9716);
        lon = getIntent().getDoubleExtra("api_location_long", 77.5946);
        GPSTracker latlon = new GPSTracker(getApplicationContext());
        Double lat1 = latlon.getLatitude();
        Double lon1 = latlon.getLongitude();
        latCL = getIntent().getDoubleExtra("current_location_lat", 12.9716);
        lonCL = getIntent().getDoubleExtra("current_location_long", 77.5946);
        makeURL(latCL, lonCL, lat, lon);
        currentLocationCoordinates = lat1 + "," + lon1;

        apiLocationCoordinates = lat + "," + lon;

        rating.setText(ratingS);
        if (ratingS != null) {
            ratingB.setRating(Float.parseFloat(ratingS));
        } else {
            ratingB.setRating(0);
        }
        if (photo != null) {
            Picasso.with(mContext).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&key=" + Constants.API_KEY + "&photoreference=" + photo).into(backgroundImage);
        } else {
            backgroundImage.setImageResource(R.drawable.no_preview);
        }
        address.setText(addressS);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + currentLocationCoordinates + "+&daddr=" + apiLocationCoordinates));
                startActivity(intent);
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
         /*       mInterstitialAd = new InterstitialAd(getApplicationContext());

                mInterstitialAd.setAdUnitId(getString(R.string.FULL_SCREEN_AD_UNIT_ID));

                AdRequest adRequest = new AdRequest.Builder()
                        .build();

                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);

                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case android.R.id.home:
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                finish(); // close this activity and return to preview activity (if there is any)


        }

        return super.onOptionsItemSelected(item);
    }

    //DRAW POLYLINE ON MAP >>>>>START
    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=" + Constants.API_KEY);
        new connectAsyncTask(urlString.toString()).execute();
        return urlString.toString();
    }

    public void drawPath(String result) {
        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            PolylineOptions options = new PolylineOptions().width(16).color(Color.BLUE).geodesic(true);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                options.add(point);
            }
            Polyline line = mMap.addPolyline(options);

        } catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public SQLiteDatabase getReadableDatabase() {

        return readableDatabase;
    }

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(PlaceDetailActivity.this);
            progressDialog.setMessage("Fetching route, Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.hide();
            if (result != null) {
                drawPath(result);
            }
        }

    }

   /* private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }*/

    //DRAW POLYLINE ON MAP >>>>>>END
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng google_api_lat_lon = new LatLng(lat, lon);
        LatLng current_lat_lon = new LatLng(latCL, lonCL);
        float zoomLevel = 16.0f;

        Marker markerAPILocation = mMap.addMarker(new MarkerOptions().position(google_api_lat_lon).title(title));
        markerAPILocation.showInfoWindow();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(google_api_lat_lon);
        builder.include(current_lat_lon);
        final LatLngBounds bounds = builder.build();

        // mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(google_api_lat_lon, zoomLevel));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            }
        });

    }


    private class JsonTask extends AsyncTask<String, String, String> {
        String phoneNumber;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(result);
                phoneNumber = jsonObj.getJSONObject("result").getString("international_phone_number");
                contactS = contact.getText().toString();
                contact.setText(phoneNumber);
                call.setVisibility(View.VISIBLE);

                JSONArray ja_data = jsonObj.getJSONObject("result").getJSONArray("reviews");
                int length = jsonObj.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObj1 = ja_data.getJSONObject(i);
                    reviewsArray.add(jsonObj1.getString("text"));
                    if (reviewsArray.size() <= 0) {
                        reviewsArray.add("REVIEWS NOT AVAILABLE");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                contact.setText("NA");
                call.setVisibility(View.GONE);
            }
        }

    }


}
