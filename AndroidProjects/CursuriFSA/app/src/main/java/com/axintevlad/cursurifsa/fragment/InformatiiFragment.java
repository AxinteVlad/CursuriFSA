package com.axintevlad.cursurifsa.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.axintevlad.cursurifsa.R;

public class InformatiiFragment extends Fragment {
    int color;

    public InformatiiFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informatii, container, false);


//        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
//        frameLayout.setBackgroundColor(color);
//
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.informatii_recyclerview);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
//
//        DessertAdapter adapter = new DessertAdapter(getContext());
//        recyclerView.setAdapter(adapter);







        return view;
    }
}