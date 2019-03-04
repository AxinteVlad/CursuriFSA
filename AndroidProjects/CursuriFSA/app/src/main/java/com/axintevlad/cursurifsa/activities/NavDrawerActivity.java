package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.axintevlad.cursurifsa.MainActivity;
import com.axintevlad.cursurifsa.R;


public abstract class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private LinearLayout content_view;
    private NavigationView navigation_view;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private String TAG = NavDrawerActivity.class.getSimpleName();

    //private String mFullName, mEmail;
    //private TextView mFullNameTextView, mEmailTextView;
    //private ImageView mProfileImageView;
    //SharedPrefManager sharedPrefManager;
    //Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_nav_drawer);

        content_view = findViewById(R.id.content_view);
        navigation_view = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_open, R.string.nav_clsed);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.e(TAG, "Eroare la getSupportActionBar ",e );

        }
        navigation_view.setNavigationItemSelectedListener(this);

        //Initializare nume,email,poza
        //View header = navigation_view.getHeaderView(0);
        //mFullNameTextView = header.findViewById(R.id.nav_header_name);
        //mEmailTextView = header.findViewById(R.id.nav_header_email);
        //mProfileImageView = header.findViewById(R.id.nav_header_photo);

        // create an object of sharedPreferenceManager and get stored user data

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the LinearLayout/FrameLayout content_view/view/stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (content_view != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, content_view, false);
            content_view.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (content_view != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            content_view.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (content_view != null) {
            content_view.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            mDrawerLayout.closeDrawer(GravityCompat.START, false);
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        mDrawerLayout.closeDrawer(GravityCompat.START);

        final int mItemId = menuItem.getItemId();
        if (mItemId == getNavigationItemID()) {
            return true;
        }
        onMenuItemClick(mItemId);
        return true;
    }

    //Every activity that extends NavigationDrawer must override this function in order to pass its menu id
    protected abstract int getNavigationItemID();

    public void onMenuItemClick(int item) {
        switch (item) {
            case R.id.nav_home:
                Log.d(TAG, "open Home Activity");
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.nav_cursuri:
                Log.d(TAG, "open Journey List Activity");
                Intent c = new Intent(this, MainActivity.class);
                startActivity(c);
                break;

            case R.id.nav_orar:
                Log.d(TAG, "open About Activity");
                Intent a = new Intent(this, MainActivity.class);
                startActivity(a);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() callback");
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause() callback");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy callback");
    }


}
