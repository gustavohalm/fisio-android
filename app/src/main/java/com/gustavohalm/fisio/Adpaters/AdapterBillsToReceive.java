package com.gustavohalm.fisio.Adpaters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gustavohalm.fisio.Models.BillsToReceive;
import com.gustavohalm.fisio.R;

import java.util.List;

public class AdapterBillsToReceive extends RecyclerView.Adapter<AdapterBillsToReceive.MyViewHolder> {
    private List<BillsToReceive> bills;
    private Context context;

    public AdapterBillsToReceive(List<BillsToReceive> bills, Context context) {
        this.bills = bills;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_bills, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
            holder.patient.setText(bills.get(i).getPatient());
            holder.patient.setText("R$ " + bills.get(i).getValue());

    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView patient;
        TextView value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.patient = itemView.findViewById(R.id.txtBillsDescription);
            this.value = itemView.findViewById(R.id.txtBillsValue);
        }
    }
}
