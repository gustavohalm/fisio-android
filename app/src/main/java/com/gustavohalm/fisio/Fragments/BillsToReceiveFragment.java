package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
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

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Adpaters.AdapterDays;
import com.gustavohalm.fisio.Adpaters.AdapterMonths;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.BillsToReceive;
import com.gustavohalm.fisio.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillsToReceiveFragment extends Fragment {
    private EditText editValue;
    private Spinner spinDay;
    private Spinner spinMonth;
    private EditText editYear;
    private Button btnPatient;
    private Button btnAdd;
    private String token;
    private int patientId;
    private String patientName;
    private SharedPreferences prefPatient;
    private SharedPreferences prefs;
    private ArrayAdapter<CharSequence> adapterDays;
    private ArrayAdapter<CharSequence> adapterMonths;
    private BillsToReceive responseBill    ;
    public BillsToReceiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //region SETTING ATRIBUTES DATA
        View view = inflater.inflate(R.layout.fragment_bills_to_receive, container, false);
        editValue = view.findViewById(R.id.ediReceiveValue);
        editYear = view.findViewById(R.id.editBillsToReceiveYear);
        btnPatient = view.findViewById(R.id.btnBillsToReceiveSelectPatient);
        btnAdd = view.findViewById(R.id.btnBillsToReceiveAdd);
        spinDay = view.findViewById(R.id.spinBillsToReceiveDay);
        spinMonth = view.findViewById(R.id.spinBillsToReceiveMonth);

        adapterDays = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDay.setAdapter(adapterDays);
        prefs = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefs.getString("the_token", "0");
        adapterMonths= ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(adapterMonths);
        prefPatient = getContext().getSharedPreferences("selected_item_patient", getContext().MODE_PRIVATE);
        patientId = prefPatient.getInt("patient_id_selected", 0 );
        patientName = prefPatient.getString("patient_name_selected", "");
        if  ( patientId != 0 )
            btnPatient.setText( patientName );

        //endregion


        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getContext().getSharedPreferences("patient_dialog", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("origin_page", "bills_to_receive");
                editor.apply();

                ((MainActivity)getActivity()).openDialogFragment();

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editValue.getText().toString();
                value        = value.replace(",", ",");
                patientId    = prefPatient.getInt("patient_id_selected", 0);
                patientName  = prefPatient.getString("patient_name_selected", "");
                String month = (String) spinMonth.getSelectedItem();
                String day   = (String) spinDay.getSelectedItem();
                String year  = editYear.getText().toString();
                String date  = year + "-" + month + "-" + day;
                BillsToReceive bill = new BillsToReceive();
                bill.setPatient( patientId );
                bill.setDate( date );
                bill.setValue( Double.valueOf(value) );
                makeRequest(bill);

            }
        });

        return view;
    }

    public void makeRequest(BillsToReceive bill)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create( Service.class );

        Call<BillsToReceive> callBill =  service.createBillsToReceive("Token "+token, bill);

        callBill.enqueue(new Callback<BillsToReceive>() {
            @Override
            public void onResponse(Call<BillsToReceive> call, Response<BillsToReceive> response) {

                if( response.isSuccessful() )
                {
                    responseBill = response.body();
                    Toast.makeText(getContext(), "Conta a receber adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getContext(), "Erro ao adicionar Conta a receber: " + response.code(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<BillsToReceive> call, Throwable t) {
                    Toast.makeText(getContext(), "Falha ao adicionar Conta a receber: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

}
