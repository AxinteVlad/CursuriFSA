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

public class BottomNavActivity extends NavDrawerActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private CursuriFragment cursuriFragment;
    private TemeFragment temeFragment;
    private ResurseFragment resurseFragment;
    private String an,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        //receive an and id
        Intent intent = getIntent();
        an = intent.getStringExtra("an");
        id = intent.getStringExtra("ID");


        //send to fragment
        Bundle bundle = new Bundle();
        bundle.putString("an",an);
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


        //coming from saveActivity
        int i = intent.getIntExtra("NR_FRAGMENT",-1);
        switch (i){
            case 0: {
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
            }
            case 1:{
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
            case 2:{
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
            }
            default:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }


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

    @Override
    protected int getNavigationItemID() {
        return 0;
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main,fragment);
        fragmentTransaction.commit();
    }
}
