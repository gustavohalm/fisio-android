package com.gustavohalm.fisio.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.gustavohalm.fisio.Adpaters.AdapterBIllsToPay;
import com.gustavohalm.fisio.Adpaters.AdapterBillsToReceive;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.BillsToPay;
import com.gustavohalm.fisio.Models.BillsToReceive;
import com.gustavohalm.fisio.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashierFragment extends Fragment {

    private RecyclerView rcvPay;
    private RecyclerView rcvReceive;
    private Spinner spinMonth;
    private EditText editYear;
    private ImageButton btnSearch;
    private ArrayAdapter<CharSequence> adapterMonths;
    private List<BillsToPay> billsToPay;
    private List<BillsToReceive> billsToReceive;
    private String date, token;
    private SharedPreferences prefs;
    private AdapterBIllsToPay adapterBIllsToPay;
    private AdapterBillsToReceive adapterBillsToReceive;
    private Double totalPay;
    private Double totalReceive;
    private Double total;
    private TextView txtTotal;
    public CashierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cashier, container, false);
        spinMonth = view.findViewById(R.id.spinCashierMonth);
        editYear = view.findViewById(R.id.editCashierYear);
        btnSearch = view.findViewById(R.id.btnSearchCashier);
        rcvPay = view.findViewById(R.id.rcvCashierPay);
        rcvReceive = view.findViewById(R.id.rcvCashierReceive);
        txtTotal = view.findViewById(R.id.txtCashierTotal);

        adapterMonths= ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(adapterMonths);

        prefs = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefs.getString("the_token", "");
        totalPay = 0.0;
        totalReceive = 0.0;



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = editYear.getText().toString();
                String month =  (String) spinMonth.getSelectedItem();
                date = month +"-" + year;
                searchBillsToPay();
                searchBillsToReceive();
            }
        });

        return view;
    }

    public void searchBillsToPay()
    {
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<List<BillsToPay>> call = service.getBillsToPay("Token "  + token, date);

        call.enqueue(new Callback<List<BillsToPay>>() {
            @Override
            public void onResponse(Call<List<BillsToPay>> call, Response<List<BillsToPay>> response) {

                if (response.isSuccessful())
                {
                    billsToPay = response.body();
                    initializeBillstoPay();
                }
                else
                {
                    Toast.makeText(getContext(), "Erro ao recuperar Contas a pagar: " + response.code() , Toast.LENGTH_LONG ).show();
                }

            }

            @Override
            public void onFailure(Call<List<BillsToPay>> call, Throwable t) {


                Toast.makeText(getContext(), "Falha ao recuperar Contas a pagar: " + t.getMessage() , Toast.LENGTH_LONG ).show();

            }
        });

    }

    public void searchBillsToReceive()
    {
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<List<BillsToReceive>> call = service.getBillsToReceive("Token " + token, date);

        call.enqueue(new Callback<List<BillsToReceive>>() {
            @Override
            public void onResponse(Call<List<BillsToReceive>> call, Response<List<BillsToReceive>> response) {
                if(response.isSuccessful())
                {
                    billsToReceive = response.body();
                    initializeBillstoReceive();
                }
                else
                {
                    Toast.makeText(getContext(), "Erro ao recuperar Contas a receber: " + response.code() , Toast.LENGTH_LONG ).show();

                }
            }

            @Override
            public void onFailure(Call<List<BillsToReceive>> call, Throwable t) {

                Toast.makeText(getContext(), "Falha ao recuperar Contas a receber: " + t.getMessage() , Toast.LENGTH_LONG ).show();

            }
        });


    }


    public void initializeBillstoPay()
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        adapterBIllsToPay = new AdapterBIllsToPay(billsToPay, getContext());
        rcvPay.setHasFixedSize(true);
        rcvPay.setLayoutManager(manager);
        rcvPay.setAdapter(adapterBIllsToPay);

        for (int i =0; i <= billsToPay.size(); i++)
        {
            totalPay += billsToPay.get(i).getValue();
        }

    }

    public void initializeBillstoReceive()
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        adapterBillsToReceive = new AdapterBillsToReceive(billsToReceive, getContext());
        rcvReceive.setHasFixedSize(true);
        rcvReceive.setLayoutManager(manager);
        rcvReceive.setAdapter(adapterBillsToReceive);

        for (int i =0; i <= billsToReceive.size(); i++)
        {
            totalReceive += billsToReceive.get(i).getValue();
        }
        total = totalReceive - totalPay;
        txtTotal.setText("R$ " + total);
    }

}
