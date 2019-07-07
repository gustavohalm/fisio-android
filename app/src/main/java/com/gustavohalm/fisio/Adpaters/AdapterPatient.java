package com.gustavohalm.fisio.Adpaters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gustavohalm.fisio.Activities.MainActivity;
import com.gustavohalm.fisio.Fragments.BillsToPayFragment;
import com.gustavohalm.fisio.Fragments.BillsToReceiveFragment;
import com.gustavohalm.fisio.Fragments.NewAppointment;
import com.gustavohalm.fisio.Models.Patient;
import com.gustavohalm.fisio.R;

import java.util.List;

public class AdapterPatient extends RecyclerView.Adapter<AdapterPatient.MyViewHolder> {
    private List<Patient> patientList;
    private Context context;
    private String previous_page;
    public AdapterPatient(Context context,List<Patient> patientList, String previous_page) {
        this.patientList = patientList;
        this.context = context;
        this.previous_page = previous_page;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPatient.MyViewHolder viewHolder, final int i) {
        viewHolder.txtName.setText( patientList.get(i).getName());

        viewHolder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    SharedPreferences preferences = context.getSharedPreferences("selected_item_patient", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("patient_id_selected", patientList.get(i).getId() );
                    editor.putString("patient_name_selected", patientList.get(i).getName() );
                    editor.apply();
                    //AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    MainActivity  activity = (MainActivity) view.getContext();

                    SharedPreferences prefs = context.getSharedPreferences("patient_dialog", context.MODE_PRIVATE);
                    previous_page = prefs.getString("origin_page", "");
                    if(previous_page.equals("new_appointment"))
                    {
                        NewAppointment newAppointment = new NewAppointment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, newAppointment).addToBackStack(null).commit();
                    }
                    else if(previous_page.equals("bills_to_receive"))
                    {
                        BillsToReceiveFragment billsToReceiveFragment = new BillsToReceiveFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, billsToReceiveFragment).addToBackStack(null).commit();
                    }
                }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View viw = (LinearLayout) LayoutInflater.from(viewGroup.getContext() ).inflate(R.layout.list_patient, viewGroup, false);


       return new MyViewHolder(viw);
    }



    @Override
    public int getItemCount() {
        return patientList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtName = itemView.findViewById(R.id.listPatientName);
        }
    }
}
