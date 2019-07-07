package com.gustavohalm.fisio.Adpaters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gustavohalm.fisio.Models.Appointment;
import com.gustavohalm.fisio.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAppointment extends RecyclerView.Adapter<AdapterAppointment.MyViewHolder> {
    private List<Appointment> appointmentList;
    private List<String> hoursList;

    public AdapterAppointment(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
        hoursList = new ArrayList<String>();
        for(int i =8; i<=17; i++) {
            if (i <= 9) {
                hoursList.add("0" + i + ":00");
                hoursList.add("0" + i + ":30");
            } else {
                hoursList.add(i + ":00");
                hoursList.add(i + ":30");

            }
            Log.v("api_log", "aqui"+i);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_appointment, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAppointment.MyViewHolder myViewHolder, int i) {
        String hour = hoursList.get(i);

        String hourtest = hour+":00";

        myViewHolder.txtHour.setText(hour);
        for (int j = 0; j < appointmentList.size(); j++) {
            if ( hourtest.equals( appointmentList.get(j).getTime())  ) {
                myViewHolder.txtPatient.setText(appointmentList.get(j).getPatient().getName());
                Log.v("api_log", appointmentList.get(j).getTime() + "deu");

            }Log.v("api_log", appointmentList.get(j).getTime());
            Log.v("api_log","hour-"+ hoursList.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return hoursList.size();
    }

    public    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtHour;
        TextView txtPatient;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                this.txtHour = itemView.findViewById(R.id.textHour);
                this.txtPatient = itemView.findViewById(R.id.txtAppointmentPatientName);
        }
    }
}
