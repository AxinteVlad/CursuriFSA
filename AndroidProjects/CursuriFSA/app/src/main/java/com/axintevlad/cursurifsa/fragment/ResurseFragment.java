package com.axintevlad.cursurifsa.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axintevlad.cursurifsa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResurseFragment extends Fragment {


    public ResurseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resurse, container, false);
    }

}
