package com.gustavohalm.fisio.Adpaters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gustavohalm.fisio.Models.BillsToPay;
import com.gustavohalm.fisio.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterBIllsToPay extends RecyclerView.Adapter<AdapterBIllsToPay.MyViewHolder> {
    private List<BillsToPay> listBills;
    private Context context;
    public AdapterBIllsToPay(List<BillsToPay> listBills, Context context) {
        this.listBills = listBills;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_bills, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String valor =  "R$ " +  listBills.get(i).getValue();
        valor = valor.replace(".", ",");
        myViewHolder.description.setText( listBills.get(i).getDescription() );
        myViewHolder.value.setText(valor );
    }

    @Override
    public int getItemCount() {
        return listBills.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView value;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = itemView.findViewById(R.id.txtBillsDescription);
            this.value = itemView.findViewById(R.id.txtBillsValue);
        }
    }
}
