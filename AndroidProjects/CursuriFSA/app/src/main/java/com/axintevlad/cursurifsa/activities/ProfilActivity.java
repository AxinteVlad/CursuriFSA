package com.axintevlad.cursurifsa.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toolbar;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.ProfileViewPagerAdapter;
import com.axintevlad.cursurifsa.adapters.ViewPagerAdapter;
import com.axintevlad.cursurifsa.fragment.InformatiiFragment;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilActivity extends NavDrawerActivity {
    ViewPager mViewPager;
    Button btn_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        ProfileViewPagerAdapter mSectionsPagerAdapter = new ProfileViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        btn_email = findViewById(R.id.htab_header_email);
        btn_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());


    }

    @Override
    protected int getNavigationItemID() {
        return R.id.nav_profil;
    }
}
