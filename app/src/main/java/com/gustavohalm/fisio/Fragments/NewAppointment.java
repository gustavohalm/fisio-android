package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.Appointment;
import com.gustavohalm.fisio.Models.AppointmentNew;
import com.gustavohalm.fisio.Models.Patient;
import com.gustavohalm.fisio.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAppointment extends Fragment {

    private Spinner spnDay;
    private Spinner spnMonth;
    private EditText editYear;
    private EditText editTime;
    private EditText editValue;
    private Button schedule;
    private Button selectPatient;
    ArrayAdapter<CharSequence> adapterDays;
    ArrayAdapter<CharSequence> adapterMonths;
    private AppointmentNew appointmentResponse;
    private Patient patient;
    private String date;
    private String time;
    private String token;
    private String value;
    private String status;
    private SharedPreferences preferences;
    private SharedPreferences prefs;
    private int patient_id;
    public NewAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_appointment, container, false);
        spnDay = view.findViewById(R.id.spinNewAppointmentDay);
        spnMonth = view.findViewById(R.id.spinNewAppointmentMonth);
        editYear = view.findViewById(R.id.editNewAppointmentYear);
        editTime = view.findViewById(R.id.editNewAppointmentTime);
        editValue = view.findViewById(R.id.editNewAppointmentValue);
        schedule = view.findViewById(R.id.btnNewAppointment);
        selectPatient = view.findViewById(R.id.btnNewAppointmentSelectPatient);
        adapterDays = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDay.setAdapter(adapterDays);

        adapterMonths= ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(adapterMonths);
        prefs = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefs.getString("the_token", "0");
        preferences = getContext().getSharedPreferences("selected_item_patient", getContext().MODE_PRIVATE);
        patient_id = preferences.getInt("patient_id_selected", 0 );
        String patient_name = preferences.getString("patient_name_selected", "Paciente");
        if ( patient_id != 0 )
            selectPatient.setText( patient_name);

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

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = editYear.getText().toString();
                String day = (String) spnDay.getSelectedItem();
                String month = (String) spnMonth.getSelectedItem();
                value = editValue.getText().toString();
                if ( Integer.valueOf(month) <= 9 )
                    month = "0" + month;
                if( Integer.valueOf(day) <=9 )
                    day = "0"+day;
                date = year + "-" + month + "-" + day;
                time = editTime.getText().toString();
                makeRequest();



            }
        });


        return  view;
    }

    public void makeRequest()
    {
        status = "Pendente";
        AppointmentNew appointment = new AppointmentNew();
        appointment.setDay(date);
        appointment.setPatient(patient_id);
        appointment.setTime(time);
        appointment.setStatus(status);
        appointment.setValue(Double.valueOf(value));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<AppointmentNew>  call = service.createAppointment("Token " +token, appointment);

        call.enqueue(new Callback<AppointmentNew>() {
            @Override
            public void onResponse(Call<AppointmentNew> call, Response<AppointmentNew> response) {
                if ( response.isSuccessful() )
                {
                    appointmentResponse = response.body();
                    Toast.makeText(getContext(), "Agendado com sucesso.", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getContext(), "Erro ao realizer agendamento: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AppointmentNew> call, Throwable t) {
                Toast.makeText(getContext(), "Falha ao realizer agendamento: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
}
