package com.maximoapps.in.localgudie_mapnavigation.ui.activity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.maximoapps.in.localgudie_mapnavigation.helpers.ConnectivityReceiver;
import com.maximoapps.in.localgudie_mapnavigation.ui.fragment.FragmentNearBy;
import com.maximoapps.localgudie_mapnavigation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ChandraMohanReddy} on 1/12/2017.
 */

public class NearByActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    LinearLayout footer;
    ImageView emergencyIV, eatenIV, commuteIV, frtIV, cargoIV, gatedCommIV;
    LinearLayout emergency, eaten, commute, frt, cargo, gatedComm;
    TextView emergencyTV, eatenTV, commuteTV, frtTV, cargoTV, gatedCommTV;
    protected DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        MobileAds.initialize(this, "ca-app-pub-2954547390793092~4241300765");
        title = new TextView(getApplicationContext());
        title.setText("<b> Nearby - </b>");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setTitle(Html.fromHtml(title.getText().toString() + "People"));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        emergencyIV = (ImageView) findViewById(R.id.ivEmergency);
        eatenIV = (ImageView) findViewById(R.id.ivEaten);
        commuteIV = (ImageView) findViewById(R.id.ivCommute);
        frtIV = (ImageView) findViewById(R.id.ivFrt);
        cargoIV = (ImageView) findViewById(R.id.ivCargo);
        gatedCommIV = (ImageView) findViewById(R.id.ivGatedComm);
        emergencyIV.setImageResource(R.drawable.emergency);
        eatenIV.setImageResource(R.drawable.eaten_unselected);
        commuteIV.setImageResource(R.drawable.commute_unselected);
        frtIV.setImageResource(R.drawable.versha_group_icon);
        cargoIV.setImageResource(R.drawable.cargo_unselected);
        gatedCommIV.setImageResource(R.drawable.gatepass_unselected);
        emergencyTV = (TextView) findViewById(R.id.tvEmergency);

        eatenTV = (TextView) findViewById(R.id.tvEaten);
        commuteTV = (TextView) findViewById(R.id.tvCommute);
        frtTV = (TextView) findViewById(R.id.tvFrt);
        frtTV.setText("VERSHA");
        cargoTV = (TextView) findViewById(R.id.tvCargo);
        gatedCommTV = (TextView) findViewById(R.id.tvGatedComm);

        emergencyTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        eatenTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        commuteTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        frtTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        cargoTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        gatedCommTV.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));


        footer = (LinearLayout) findViewById(R.id.footer);
        emergency = (LinearLayout) findViewById(R.id.llEmergency);
        eaten = (LinearLayout) findViewById(R.id.llEaten);
        commute = (LinearLayout) findViewById(R.id.llCommute);
        frt = (LinearLayout) findViewById(R.id.llFRT);
        cargo = (LinearLayout) findViewById(R.id.llCargo);
        gatedComm = (LinearLayout) findViewById(R.id.llGatedComm);
        emergency.setOnClickListener(this);
        eaten.setOnClickListener(this);
        commute.setOnClickListener(this);
        frt.setOnClickListener(this);
        cargo.setOnClickListener(this);
        gatedComm.setOnClickListener(this);

        viewPager.setAdapter(adapter);


        //TAB CUSTOM COLOR & FONT START  >>>>>
        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {

                TextView tabTextView = new TextView(this);
                tab.setCustomView(tabTextView);
                tabTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tab_unselected));
                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;

            }

        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                TextView text = (TextView) tab.getCustomView();
                String subTitle = text.getText().toString().trim();
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

                if (subTitle.equals("NEAR BY")) {
                    subTitle = "People";
                } else if (subTitle.equals("FRIENDS")) {
                    subTitle = "Friends";
                } else if (subTitle.equals("FAMILY")) {
                    subTitle = "Family";
                }
                toolbar.setTitle("Nearby - " + subTitle);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tab_unselected));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        //TAB CUSTOM COLOR & FONT END  <<<<<<
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentNearBy(), "NEAR BY");
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewPager.setAdapter(adapter);
                } else {
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}