package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gustavohalm.fisio.Adpaters.AdapterPatient;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.Patient;
import com.gustavohalm.fisio.R;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogPatientFragment extends Fragment {

    private RecyclerView rcvPatients;
    private AdapterPatient adapterPatient;
    private List<Patient> listPatients ;
    private SharedPreferences preferencesAuth;

    private String previous_page;
    public DialogPatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_patient, container, false);

        rcvPatients = view.findViewById(R.id.rcvAppointmentPatient);
        recoverPatients();



        Log.v("here_is", "heres ok 2 ");
        return view;
    }

    public void recoverPatients()
    {
        preferencesAuth = getContext().getSharedPreferences( "token_fisio", getContext().MODE_PRIVATE );
        String user_token = preferencesAuth.getString("the_token", "0");
        Log.v("here_is", "Token " + user_token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);

        Call< List<Patient> > call = service.getPatients("Token " + user_token);
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if(response.isSuccessful())
                {
                    listPatients = response.body();
                    Log.v("here_is", "deu certo");
                    initalizeRecyler();
                }
                else
                {

                    Log.v("here_is", "deu erro" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Log.v("here_is", "deu falha" + t.getMessage());
            }
        });



        Log.v("here_is", "heres ok 3");


    }
    public void initalizeRecyler()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        adapterPatient = new AdapterPatient(this.getActivity(),listPatients, previous_page);
        rcvPatients.setHasFixedSize(true);
        rcvPatients.setLayoutManager(layoutManager);
        rcvPatients.setAdapter(adapterPatient);
    }
}
