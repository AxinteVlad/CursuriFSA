package com.axintevlad.cursurifsa.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.AdminTabAdapter;
import com.axintevlad.cursurifsa.fragment.EleviTabFragment;
import com.axintevlad.cursurifsa.fragment.ProfesoriTabFragment;

public class AdminTabActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private AdminTabAdapter adminTabAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tab);
        adminTabAdapter = new AdminTabAdapter(getSupportFragmentManager());

        //tooldbar
        mToolbar = findViewById(R.id.toolbar_admintab);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Panou Admin");


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager_admintab);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablay_admintab);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        AdminTabAdapter adapter = new AdminTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new EleviTabFragment(), "ELEVI");
        adapter.addFragment(new ProfesoriTabFragment(), "STUDENTI");
        viewPager.setAdapter(adapter);
    }
}
