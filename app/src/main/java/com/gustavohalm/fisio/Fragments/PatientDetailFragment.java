package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDetailFragment extends Fragment {
    private Button selectPatient;
    private TextView txtName, txtPhone, txtEmail, txtBorn;
    private RecyclerView rcvDiagnostics;
    private  SharedPreferences prefs, preferences;
    private String token;
    private int patient_id;

    public PatientDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_detail, container, false);
        selectPatient = view.findViewById(R.id.btnDetailPatient);
        txtName = view.findViewById(R.id.txtPatientDetailName);
        txtPhone = view.findViewById(R.id.txtPatientDetailPhone);
        txtEmail = view.findViewById(R.id.txtPatientDetailEmail);
        txtBorn = view.findViewById(R.id.txtPatientDetailBorn);

        prefs = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefs.getString("the_token", "0");
        preferences = getContext().getSharedPreferences("selected_item_patient", getContext().MODE_PRIVATE);
        patient_id = preferences.getInt("patient_id_selected", 0 );
        String patient_name = preferences.getString("patient_name_selected", "Paciente");

        if ( patient_id != 0 )
        {
            selectPatient.setText(patient_name);
            //Recuperar dados e diagnosticos

        }
        selectPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getContext().getSharedPreferences("patient_dialog", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editorPrefs = prefs.edit();
                editorPrefs.putString("origin_page", "new_appointment");
                editorPrefs.apply();
                ((MainActivity)getActivity()).openDialogFragment();
            }
        });



        return view;
    }

}
