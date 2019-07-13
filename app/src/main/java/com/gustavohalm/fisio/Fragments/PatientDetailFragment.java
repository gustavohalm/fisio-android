package com.gustavohalm.fisio.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gustavohalm.fisio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDetailFragment extends Fragment {


    public PatientDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_detail, container, false);



        return view;
    }

}
