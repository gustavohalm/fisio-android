package com.gustavohalm.fisio.Fragments;


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
import android.widget.Spinner;
import android.widget.Toast;

import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.BillsToPay;
import com.gustavohalm.fisio.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillsToPayFragment extends Fragment {
    private EditText editValue;
    private EditText editDescription;
    private EditText editYear;
    private Spinner spinMonth;
    private Spinner spinDay;
    private Button btnAdd;
    private SharedPreferences prefs;
    private ArrayAdapter<CharSequence> adapterDays;
    private ArrayAdapter<CharSequence> adapterMonths;
    private String token;
    private BillsToPay billToPay;
    private BillsToPay respondeBill;

    public BillsToPayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bills_to_pay, container, false);

        //region SETTING ATRIBUTES DATAS

        editValue = view.findViewById(R.id.editPayValue);
        editDescription = view.findViewById(R.id.editPayDescription);
        editYear = view.findViewById(R.id.editCashierYear);
        btnAdd = view.findViewById(R.id.btnPayAdd);
        spinDay = view.findViewById(R.id.spinBillsToPayDay);
        spinMonth = view.findViewById(R.id.spinCashierMonth);

        adapterDays = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinDay.setAdapter(adapterDays);
        adapterMonths= ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(adapterMonths);
        prefs = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefs.getString("the_token", "");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String year = editYear.getText().toString();
                String month = (String) spinMonth.getSelectedItem();
                String day = (String) spinDay.getSelectedItem();
                String description = editDescription.getText().toString();
                String value = editValue.getText().toString();

                if( Integer.valueOf(month) <= 9  )
                    month = "0"+month;
                if( Integer.valueOf(day) <= 9 )
                    day = "0"+day;
                String date = year + "-" + month + "-" + day;
                value = value.replace(",", ".");

                billToPay = new BillsToPay();
                billToPay.setDate(date);
                billToPay.setDescription(description);
                billToPay.setValue( Double.valueOf(value) );
                Log.v("debugM", "ok here");
                makeRequest(billToPay);
            }
        });


        //endregion


        return view;
    }

    public void makeRequest(BillsToPay bill)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<BillsToPay> callPay = service.createBillsToPay( "Token "+token, bill);

        callPay.enqueue(new Callback<BillsToPay>() {
            @Override
            public void onResponse(Call<BillsToPay> call, Response<BillsToPay> response) {
                if(response.isSuccessful())
                {
                    respondeBill = response.body();
                    Toast.makeText(getContext(), "Conta a Pagar adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Erro ao adicionar Conta a Pagar: " + response.code(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<BillsToPay> call, Throwable t) {
                Toast.makeText(getContext(), "Falha ao adicionar Conta a Pagar: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
