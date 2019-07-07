package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.gustavohalm.fisio.Adpaters.AdapterAppointment;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.Appointment;
import com.gustavohalm.fisio.R;

import java.security.acl.LastOwnerException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends Fragment {
    private Spinner spinDay;
    private Spinner spinMonth;
    private EditText editYear;
    private ImageButton btnSearch;
    private RecyclerView rcvAppointments;
    private ArrayAdapter<CharSequence> adapterDays;
    private ArrayAdapter<CharSequence> adapterMonths;
    private SharedPreferences preferences;
    private String token;
    private List<Appointment> appointmentList;
    private AdapterAppointment adapterAppointment;
    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        spinDay = view.findViewById(R.id.spinAppointmentDay);
        spinMonth = view.findViewById(R.id.spinAppointmentMonth);
        editYear = view.findViewById(R.id.editAppointmentYear);
        btnSearch = view.findViewById(R.id.btnSearchAppointment);
        rcvAppointments = view.findViewById(R.id.rcvAppointments);
        adapterDays = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDay.setAdapter(adapterDays);

        adapterMonths= ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(adapterMonths);

        preferences = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = preferences.getString("the_token", "0");


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("api_log", "clique foi");
                String day = (String) spinDay.getSelectedItem();
                String month = (String) spinMonth.getSelectedItem();
                String year = editYear.getText().toString();
                String date = year +"-"+month+"-"+day;
                makeRequest(date);

            }
        });

        return view;
    }

    public void makeRequest(String date)
    {
        Log.v("api_log", "metodo entrou");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call< List<Appointment> > call = service.getAppointments("Token " +token, date);

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if ( response.isSuccessful() )
                {
                    Log.v("api_log", "Loading is ok 1" );

                    appointmentList = response.body();
                    Log.v("api_log", "Loading is ok");
                    initializeRecycler();
                }
                else
                {
                    Toast.makeText(getContext(), "Erro ao carregar: " + response.code(), getContext().MODE_PRIVATE).show();

                    Log.v("api_log", "Error loading" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha ao carregar: " + t.getMessage(), getContext().MODE_PRIVATE).show();

                Log.v("api_log", "loading failed" + t.getMessage());
            }
        });

    }

    public void initializeRecycler()
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getActivity());
        rcvAppointments.setLayoutManager(manager);
        adapterAppointment = new AdapterAppointment(appointmentList);
        rcvAppointments.setHasFixedSize(true);
        rcvAppointments.setLayoutManager(manager);
        rcvAppointments.setAdapter(adapterAppointment);
    }
}
