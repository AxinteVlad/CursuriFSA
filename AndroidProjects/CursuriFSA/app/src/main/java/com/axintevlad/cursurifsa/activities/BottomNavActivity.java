package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.fragment.CursuriFragment;
import com.axintevlad.cursurifsa.fragment.ResurseFragment;
import com.axintevlad.cursurifsa.fragment.TemeFragment;

public class BottomNavActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private CursuriFragment cursuriFragment;
    private TemeFragment temeFragment;
    private ResurseFragment resurseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        Bundle bundle = new Bundle();
        bundle.putString("ID",id);


        bottomNavigationView = findViewById(R.id.bottomnav_main);
        frameLayout = findViewById(R.id.frame_main);

        cursuriFragment = new CursuriFragment();
        temeFragment = new TemeFragment();
        resurseFragment = new ResurseFragment();

        cursuriFragment.setArguments(bundle);
        temeFragment.setArguments(bundle);
        resurseFragment.setArguments(bundle);

        setFragment(cursuriFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.bottomNav_cursuri:{
                        setFragment(cursuriFragment);
                        return true;
                    }

                    case R.id.bottomNav_teme:{
                        setFragment(temeFragment);
                        return true;
                    }

                    case R.id.bottomNav_resurse:{
                        setFragment(resurseFragment);
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main,fragment);
        fragmentTransaction.commit();
    }
}
