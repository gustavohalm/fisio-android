package com.gustavohalm.fisio.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.gustavohalm.fisio.Fragments.AgreementFragment;
import com.gustavohalm.fisio.Fragments.AppointmentDetailFragment;
import com.gustavohalm.fisio.Fragments.AppointmentFragment;
import com.gustavohalm.fisio.Fragments.BillsToPayFragment;
import com.gustavohalm.fisio.Fragments.BillsToReceiveFragment;
import com.gustavohalm.fisio.Fragments.CashierFragment;
import com.gustavohalm.fisio.Fragments.ContractsFragment;
import com.gustavohalm.fisio.Fragments.DiagnosticFragment;
import com.gustavohalm.fisio.Fragments.DialogPatientFragment;
import com.gustavohalm.fisio.Fragments.ImagePatientFragment;
import com.gustavohalm.fisio.Fragments.NewAppointment;
import com.gustavohalm.fisio.Fragments.PatientFragment;
import com.gustavohalm.fisio.R;

public class MainActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener  {
    private SharedPreferences preferences;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("token_fisio", MODE_PRIVATE);
        String token = preferences.getString("the_token", "0");
        if(token.equals("0"))
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        frameLayout = findViewById(R.id.FrameLayout);
        AppointmentFragment appointmentFragment = new AppointmentFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.FrameLayout, appointmentFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_patient) {
            PatientFragment patientFragment = new PatientFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace( R.id.FrameLayout, patientFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_appointment) {
            AppointmentFragment appointmentFragment = new AppointmentFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace( R.id.FrameLayout, appointmentFragment);
            fragmentTransaction.commit();


        } else if(id == R.id.nav_new_appointment){
            NewAppointment newAppointment = new NewAppointment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace( R.id.FrameLayout, newAppointment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_patient_detail) {
            AgreementFragment agreementFragment = new AgreementFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.FrameLayout, agreementFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_contracts) {
            ContractsFragment contractsFragment = new ContractsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.FrameLayout, contractsFragment );
            fragmentTransaction.commit();
        } else if (id == R.id.nav_cashier) {
            CashierFragment cashierFragment = new CashierFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.FrameLayout, cashierFragment );
            fragmentTransaction.commit();


        }else if (id == R.id.nav_bills_to_pay) {
            BillsToPayFragment billsToPayFragment = new BillsToPayFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.FrameLayout, billsToPayFragment);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_bills_to_receive) {
            BillsToReceiveFragment billsToReceiveFragment = new BillsToReceiveFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.FrameLayout, billsToReceiveFragment );
            fragmentTransaction.commit();

        } else if(id == R.id.nav_logout){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openFragment()
    {
        Log.v("taggy", "main foi");
        ImagePatientFragment imagePatientFragment = new ImagePatientFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, imagePatientFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openDialogFragment()
    {
        DialogPatientFragment dialogPatientFragment = new DialogPatientFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.FrameLayout, dialogPatientFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void openAppointmentDetailFragment()
    {
        AppointmentDetailFragment appointmentDetailFragment = new AppointmentDetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.FrameLayout,  appointmentDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void openDiagnosticFragment()
    {
        DiagnosticFragment diagnosticFragment = new DiagnosticFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.FrameLayout, diagnosticFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
