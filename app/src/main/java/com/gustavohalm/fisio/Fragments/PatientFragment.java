package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.ChangeFragment;
import com.gustavohalm.fisio.Models.ImagePatient;
import com.gustavohalm.fisio.Models.Patient;
import com.gustavohalm.fisio.R;

import java.util.Objects;

import javax.microedition.khronos.egl.EGLDisplay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment{
    //region ATRIBUTOS
    private TextView textView;
    private EditText editName;
    private EditText editCpf;
    private EditText editEmail;
    private EditText editRg;
    private EditText editCelphone;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editAddres1;
    private EditText editAddres2;
    private EditText editAddres3;
    private EditText editCity;
    private EditText editState;
    private Spinner spinDay;
    private Spinner spinMonth;
    private EditText editYear;
    private EditText editDescription;
    private Patient patient;
    private int agreement =0;
    private String token;
    private Button btn;
    private SharedPreferences prefs;
    ArrayAdapter<CharSequence> adapterDays;
    ArrayAdapter<CharSequence> adapterMonths;
    //endregion

    public PatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_patient, container, false);

        editName = view.findViewById(R.id.editPatientName);
        spinDay = view.findViewById( R.id.spinPatientDay);
        spinMonth = view.findViewById(R.id.spinPatientMonth);
        editYear = view.findViewById(R.id.editPatientYear);
        editEmail = view.findViewById(R.id.editPatientEmail);
        editCpf = view.findViewById(R.id.editPatientCpf);
        editAddres1  = view.findViewById(R.id.editPatientAddres1);
        editAddres2  = view.findViewById(R.id.editPatientAddres2);
        editAddres3  = view.findViewById(R.id.editPatientAddres3);
        editCity  = view.findViewById(R.id.editPatientCity);
        editCelphone = view.findViewById(R.id.editPatientCelphone);
        editEmail = view.findViewById(R.id.editPatientEmail);
        editHeight = view.findViewById(R.id.editPatientHeight);
        editWeight = view.findViewById(R.id.editPatientWeight);
        editRg = view.findViewById(R.id.editPatientRg);
        editDescription = view.findViewById(R.id.editPatientDescription);
        btn =  view.findViewById(R.id.btnPatientAdd);
        adapterDays = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDay.setAdapter(adapterDays);

        adapterMonths= ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(adapterMonths);


        prefs = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefs.getString("the_token", "0");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatient();
            }
        });

        return view;
    }

    public void addPatient()
    {

        String name =  editName.getText().toString();
        String year = editYear.getText().toString();
        String month = (String) spinMonth.getSelectedItem();
        String day  = (String) spinDay.getSelectedItem();
        String cpf  = editCpf.getText().toString();
        String rg = editRg.getText().toString();
        String celhone = editCelphone.getText().toString();
        String email = editEmail.getText().toString();
        String description = editDescription.getText().toString();
        String address_1 = editAddres1.getText().toString();
        String address_2 = editAddres2.getText().toString();
        String address_3 = editAddres3.getText().toString();
        String city = editCity.getText().toString();
        String state = "SP";
        String height =  editHeight.getText().toString().replace(",", ".") ;
        String weight =  editWeight.getText().toString().replace(",", ".") ;
        if ( Integer.valueOf(month) <= 9 )
            month = "0" + month;
        if( Integer.valueOf(day) <=9 )
            day = "0"+day;
        String data = year + "-" + month + "-" + day;
        patient = new Patient( name,height, weight,cpf, email, data, rg, address_1, address_2, address_3, city, state, 1, description , celhone );

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fisio.gustavohalm.com/api/v0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

       Service service = retrofit.create(Service.class);

        Call<Patient> call = service.createPatient("Token " + token, patient);
        call.enqueue(new Callback<Patient>() {
        @Override
        public void onResponse(Call<Patient> call, Response<Patient> response) {
            if (response.isSuccessful())
            {
                patient = response.body();
                Toast.makeText(getContext(), "Paciente cadastrado! Adicione alguma imagem. ", Toast.LENGTH_SHORT).show();

                ((MainActivity)getActivity()).openFragment();
            }
            else
            {
                Toast.makeText(getContext(), "Erro ao cadastrar o paciente: "  + response.code() , Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Patient> call, Throwable t) {


            Toast.makeText(getContext(), "Falha ao cadastrar o paciente: " + t.getMessage(), Toast.LENGTH_LONG).show();

        }
    });

    }

}
