package com.gustavohalm.fisio.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gustavohalm.fisio.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentDetailFragment extends Fragment {
    private SharedPreferences prefsDiagnostic;
    private SharedPreferences prefsToken;
    private Context context;
    private TextView txtHour;
    private TextView txtDate;
    private TextView txtPatient;
    private TextView txtStatus;
    private Spinner spinStatus;
    private Button btnChangeStatus;
    private Button btnDiagnostic;
    private String hour, date, patientName, status;
    private int patient_id, appointment_id;

    public AppointmentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_appointment_detail, container, false);
        context = getContext();
        txtHour = view.findViewById(R.id.txtAppointmentDetailHour);
        txtDate = view.findViewById(R.id.txtAppointmentDetailDate);
        txtPatient = view.findViewById(R.id.txtAppointmentPatientName);
        txtStatus = view.findViewById(R.id.txtAppointmentStatus);
        spinStatus = view.findViewById(R.id.spinAppointmentStatus);
        btnChangeStatus = view.findViewById(R.id.btnAppointmentChange);
        btnDiagnostic = view.findViewById(R.id.btnAppointmentDiagnostic);

        prefsDiagnostic = context.getSharedPreferences("patient_diagnostic", context.MODE_PRIVATE);

        


        return view;
    }

}
