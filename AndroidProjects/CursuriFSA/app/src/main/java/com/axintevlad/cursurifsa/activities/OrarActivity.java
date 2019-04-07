package com.axintevlad.cursurifsa.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;

public class OrarActivity extends NavDrawerActivity {

    TimetableView timetableView;
    Toolbar toolbar;
    private Toolbar mToolbar;
    ArrayList<Schedule> schedules = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orar);
        timetableView = findViewById(R.id.timetable);
        final FloatingActionButton fab = findViewById(R.id.fab_add);
        timetableView.setHeaderHighlight(2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrarActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_ora,null);
                final EditText nume = mView.findViewById(R.id.editText_nume_ora);
                final EditText locatie = mView.findViewById(R.id.editText_locatie);
                final EditText startTime = findViewById(R.id.editText_start_time);
                final EditText endTime = findViewById(R.id.editText_end_time);
                final  Button save = mView.findViewById(R.id.btnAdauga);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Schedule schedule = new Schedule();
                        schedule.setClassTitle(nume.getText().toString()); // sets subject
                        schedule.setClassPlace(locatie.getText().toString()); // sets place
                        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
                        schedule.setEndTime(new Time(13,0)); // sets the end of class time (hour,minute)
                        schedules.add(schedule);
                        timetableView.add(schedules);
                        Toast.makeText(OrarActivity.this,"AM ADAUGAT",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });


            }
        });

        timetableView.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                // ...
                Log.i("Pressed", "OnStickerSelected: ");
            }
        });


        Schedule schedule = new Schedule();
        schedule.setClassTitle("Data Structure"); // sets subject
        schedule.setClassPlace("IT-601"); // sets place
        schedule.setProfessorName("Won Kim"); // sets professor
        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,0)); // sets the end of class time (hour,minute)
        schedules.add(schedule);

      //  timetableView.add(schedules);


    }

    @Override
    protected int getNavigationItemID() {
        return 0;
    }

}
