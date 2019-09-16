package com.gustavohalm.fisio.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.AppointmentNew;
import com.gustavohalm.fisio.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private ImageButton btnChangeStatus;
    private Button btnDiagnostic;
    private String hour, date, patientName, status, value;
    private int patient_id, appointment_id;
    ArrayAdapter<CharSequence> adapterStatus;

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
        txtPatient = view.findViewById(R.id.txtAppointmentDetailName);
        txtStatus = view.findViewById(R.id.txtAppointmentStatus);
        spinStatus = view.findViewById(R.id.spinAppointmentStatus);
        btnChangeStatus = view.findViewById(R.id.btnAppointmentChange);
        btnDiagnostic = view.findViewById(R.id.btnAppointmentDiagnostic);

        prefsDiagnostic = context.getSharedPreferences("patient_diagnostic", context.MODE_PRIVATE);

        patient_id = prefsDiagnostic.getInt("patient", 0);
        appointment_id = prefsDiagnostic.getInt("appointment", 0);
        hour = prefsDiagnostic.getString("appointment_time","");
        date = prefsDiagnostic.getString("appointment_day", "");
        status = prefsDiagnostic.getString("appointment_status", "");
        value = prefsDiagnostic.getString("appointment_value", "");
        patientName = prefsDiagnostic.getString("patient_name", "");
        Log.v("debugMode", "name.:" +patientName);
        txtPatient.setText(  patientName);
        txtHour.setText( hour );
        String[] dateArr = date.split("-");
        String ano = dateArr[0];
        String mes = dateArr[1];
        String dia = dateArr[2];
        txtDate.setText( dia + "/" +mes+"/"+ano);
        txtStatus.setText( status );
        adapterStatus = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(adapterStatus);

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = (String) spinStatus.getSelectedItem();
                changeStatus(status);

              }
        });

        btnDiagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).openDiagnosticFragment();

            }
        });


        return view;
    }

    public void changeStatus(String newStatus)
    {
        prefsToken = context.getSharedPreferences("token_fisio", context.MODE_PRIVATE);
        String token = prefsToken.getString("the_token", "0");
        AppointmentNew appointment = new AppointmentNew();
        appointment.setDay(date);
        appointment.setPatient(patient_id);
        appointment.setId(appointment_id);
        appointment.setStatus(newStatus);
        appointment.setTime(hour);
        appointment.setValue(Double.valueOf(value));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<AppointmentNew> call = service.editAppointment("Token "+token, appointment_id, appointment);
        Log.v("debugMode", "error");
        call.enqueue(new Callback<AppointmentNew>() {
            @Override
            public void onResponse(Call<AppointmentNew> call, Response<AppointmentNew> response) {
                if ( response.isSuccessful() )
                {
                    Toast.makeText(context, "Status da Consulta alterado.", Toast.LENGTH_LONG).show();

                    txtStatus.setText(status);
                }
                else
                {
                    String errorMessage;
                    try {
                        errorMessage = response.errorBody().string();

                    }
                    catch (Exception e)
                    {
                        errorMessage =";";
                        e.printStackTrace();
                    }
                    Log.v("debugMode", errorMessage);
                    Toast.makeText(context, "Erro ao alterar Status da Consulta: " + errorMessage, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AppointmentNew> call, Throwable t) {
                Toast.makeText(context, "Falha ao alterar Status da Consulta: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("debugMode", "error faeil");
            }
        });

    }
}
