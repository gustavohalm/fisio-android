package com.gustavohalm.fisio.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.Diagnostic;
import com.gustavohalm.fisio.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosticFragment extends Fragment {
    private int patientId, appointmentId;
    private String patientName, token;
    private SharedPreferences prefsToken, prefsDiagnostic;
    private TextView txtPatientName;
    private EditText editAnamnese, editEvolution, editTrainning, editScale, editPhysicExam, editObservation;
    private Button btnDiagnostic;
    private Context context;
    private Diagnostic diagnostic,responseDiagnostic;
    public DiagnosticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagnostic, container, false);
        context = getContext();

        prefsDiagnostic = context.getSharedPreferences("patient_diagnostic", context.MODE_PRIVATE);
        patientId = prefsDiagnostic.getInt("patient", 0);
        appointmentId = prefsDiagnostic.getInt("appointment", 0);
        patientName = prefsDiagnostic.getString("patient_name", "");

        prefsToken = context.getSharedPreferences("token_fisio", context.MODE_PRIVATE);
        token = prefsToken.getString("the_token", "0");
        txtPatientName = view.findViewById(R.id.txtDiagnosticPatientName);
        editAnamnese = view.findViewById(R.id.editDiagnosticAnamnese);
        editEvolution = view.findViewById(R.id.editDiagnosticEvolution);
        editTrainning = view.findViewById(R.id.editDiagnosticTrainning);
        editScale = view.findViewById(R.id.editDiagnosticScale);
        editPhysicExam = view.findViewById(R.id.editDiagnosticPhysicExam);
        editObservation = view.findViewById(R.id.editDiagnosticObservation);
        btnDiagnostic = view.findViewById(R.id.btnDiagnosticEnd);
        txtPatientName.setText(patientName);


        btnDiagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRequest();

            }
        });


        return view;
    }


    public  void makeRequest()
    {
        String  anamnese, evolution, trainning, scale, physicExam, observation;

        anamnese = editAnamnese.getText().toString();
        evolution = editEvolution.getText().toString();
        trainning = editTrainning.getText().toString();
        scale = editScale.getText().toString();
        physicExam = editPhysicExam.getText().toString();
        observation = editObservation.getText().toString();

        diagnostic = new Diagnostic();
        diagnostic.setPatient(patientId);
        diagnostic.setAppointment(appointmentId);
        diagnostic.setAnamnese(anamnese);
        diagnostic.setObservation(observation);
        diagnostic.setTaining(trainning);
        diagnostic.setScale(scale);
        diagnostic.setPhysic_exam(physicExam);
        diagnostic.setEvolution(evolution);
        diagnostic.setName("diagnostic test");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<Diagnostic> call = service.createDiagnostic("Token " + token, diagnostic);

        call.enqueue(new Callback<Diagnostic>() {
            @Override
            public void onResponse(Call<Diagnostic> call, Response<Diagnostic> response) {

                if(response.isSuccessful())
                {
                    responseDiagnostic = response.body();
                    Toast.makeText(context, "Adicione imagens ao seu diagnostico", Toast.LENGTH_LONG).show();

                    ((MainActivity)getActivity()).openImageDiagnosticFragment();

                }
                else
                {
                    String errorMessage;
                    try
                    {

                        errorMessage = response.errorBody().string();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        errorMessage = "";
                    }

                    Toast.makeText(context, "Erro ao realizar Diagnostico", Toast.LENGTH_LONG).show();
                    Log.v("debugMode", "Erro: " + errorMessage);


                }


            }

            @Override
            public void onFailure(Call<Diagnostic> call, Throwable t) {

                Toast.makeText(context, "Falha ao realizar Diagnostico", Toast.LENGTH_LONG).show();
                Log.v("debugMode", "Falha: " + t.getMessage());

            }
        });


    }
}
