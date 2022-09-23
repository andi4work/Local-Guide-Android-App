package com.maximoapps.in.localgudie_mapnavigation.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maximoapps.in.localgudie_mapnavigation.helpers.Constants;
import com.maximoapps.in.localgudie_mapnavigation.helpers.InternetDetector;
import com.maximoapps.localgudie_mapnavigation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ${ChandraMohanReddy} on 1/11/2017.
 */

public class SplashActivity extends Activity {
    private static final int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
    String TAG = "Device INFO";
    String ENCODED_JSON_STRING = "ENCODED_JSON_STRING";
    String base64EncodedJsonData, base64DecodedJsonData;
    String jsonStr;
    final Context context = SplashActivity.this;
    ImageView splashLogo, ivRefresh;
    InternetDetector internetDetector;
    Animation rotation;
    TextView greetings, tvInternet, tvRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        String device_language = Locale.getDefault().getLanguage();
        Constants.APP_LANGUAGE = device_language;
        tvInternet = (TextView) findViewById(R.id.tvInternetDetector);

        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRefresh.startAnimation(rotation);
                tvInternet.setText("Checking internet connection...");
                if (internetDetector.isConnectingToInternet() && internetDetector.isOnline()) {
                    startActivity(new Intent(getApplicationContext(), NearByActivity.class));
                    finish();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            ivRefresh.clearAnimation();
                            tvInternet.setText("Internet not available");
                        }
                    }, 1000);
                }
            }
        });
        splashLogo = (ImageView) findViewById(R.id.ivSplashLogo);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setFillAfter(true);
        splashLogo.startAnimation(rotation);
        greetings = (TextView) findViewById(R.id.tvGreetings);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetings.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greetings.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greetings.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greetings.setText("Hi...");
        }
        displayVersionName();

        // This solution will leak memory!  Don't use!!!
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Check();
            }
        }, 1500);

        internetDetector = new InternetDetector(getApplicationContext());

    }

    public void Check() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else if (internetDetector.isConnectingToInternet() && internetDetector.isConnectingToInternet()) {
            startActivity(new Intent(getApplicationContext(), NearByActivity.class));
        } else {
            splashLogo.clearAnimation();
            tvInternet.setVisibility(View.VISIBLE);
            ivRefresh.setVisibility(View.VISIBLE);
            tvRefresh.setVisibility(View.VISIBLE);

        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
                startActivity(new Intent(getApplicationContext(), NearByActivity.class));
            }

        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(getApplicationContext(), NearByActivity.class));
                } else {
                    finish();
                }
                return;

            }
        }
    }

    public void JsonConvertion() {
        JSONObject register = new JSONObject();
        try {
            register.put("imei", Constants.IMEI);
            register.put("imsi", Constants.IMSI);
            register.put("android_id", Constants.ANDROID_ID);
            register.put("device_make", Constants.DEVICE_MANUFACTURER);
            register.put("device_model", Constants.DEVICE_MODEL);
            register.put("os_version", Constants.OS_VERSION);
            register.put("network_type", Constants.NETWORK_TYPE);
            register.put("network_name", Constants.NETWORK_NAME);
            register.put("registered_email_id", Constants.REGISTERED_EMAIL_ID);
            register.put("creation_date", Constants.CREATION_DATE);
            register.put("device_width", Constants.DEVICE_WIDTH);
            register.put("sim_serial_number", Constants.SIM_SERIAL_NUMBER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonStr = register.toString();

    }

    private void displayVersionName() {
        String versionName = "";
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = "v " + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView tv = (TextView) findViewById(R.id.tvVersionName);
        tv.setText(versionName);
    }
}