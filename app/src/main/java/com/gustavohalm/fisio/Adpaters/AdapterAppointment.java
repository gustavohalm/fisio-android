package com.gustavohalm.fisio.Adpaters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Fragments.AppointmentDetailFragment;
import com.gustavohalm.fisio.Fragments.DiagnosticFragment;
import com.gustavohalm.fisio.Models.Appointment;
import com.gustavohalm.fisio.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAppointment extends RecyclerView.Adapter<AdapterAppointment.MyViewHolder> {
    private List<Appointment> appointmentList;
    private List<Appointment> hoursList;
    private Context context;
    public AdapterAppointment(List<Appointment> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.hoursList = new ArrayList<>();
        this.context = context;
        for(int i =8; i<=17; i++) {
            if (i <= 9) {
                Appointment appointment = new Appointment();
                Appointment appointment2 = new Appointment();
                appointment.setTime("0" + i + ":00");
                appointment2.setTime("0" + i + ":30");
                hoursList.add(appointment);
                hoursList.add(appointment2);
            } else {
                Appointment appointment = new Appointment();
                Appointment appointment2 = new Appointment();
                appointment.setTime(i + ":00");
                appointment2.setTime(i + ":30");
                hoursList.add(appointment);
                hoursList.add(appointment2);

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
    public void onBindViewHolder(@NonNull AdapterAppointment.MyViewHolder myViewHolder, final int i) {
        final String hour = hoursList.get(i).getTime();

        final String hourtest = hour+":00";

        myViewHolder.txtHour.setText(hour);
        for (int j = 0; j < appointmentList.size(); j++) {
            if ( hourtest.equals( appointmentList.get(j).getTime())  ) {
                hoursList.get(i).setId( appointmentList.get(j).getId() );
                hoursList.get(i).setTime( appointmentList.get(j).getTime());
                hoursList.get(i).setDay( appointmentList.get(j).getDay());
                hoursList.get(i).setValue( appointmentList.get(j).getValue() );
                hoursList.get(i).setStatus( appointmentList.get(j).getStatus() );
                hoursList.get(i).setPatient( appointmentList.get(j).getPatient() );
                myViewHolder.txtPatient.setText(appointmentList.get(j).getPatient().getName());
                Log.v("api_log", appointmentList.get(j).getTime() + "deu");

            }Log.v("api_log", appointmentList.get(j).getTime());
            Log.v("api_log","hour-"+ hoursList.get(i));
        }

        myViewHolder.txtPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( hoursList.get(i).getPatient() != null  )
                {
                    SharedPreferences prefsDiagnostic = context.getSharedPreferences("patient_diagnostic", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefsDiagnostic.edit();
                    editor.putInt("patient", hoursList.get(i).getPatient().getId()  );
                    editor.putInt("appointment", hoursList.get(i).getId());
                    editor.putString("patient_name", hoursList.get(i).getPatient().getName());
                    editor.putString("appointment_time", hoursList.get(i).getTime());
                    editor.putString("appointment_day", hoursList.get(i).getDay());
                    editor.putString("appointment_status", hoursList.get(i).getStatus());
                    editor.putString("appointment_value",  String.valueOf( hoursList.get(i).getValue() ) );
                    Log.v("debugMode", "id>:" + hoursList.get(i).getId());
                    editor.apply();

                    MainActivity activity = (MainActivity) v.getContext();

                    AppointmentDetailFragment appointmentDetailFragment = new AppointmentDetailFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, appointmentDetailFragment).addToBackStack(null).commit();


                }

            }
        });

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
