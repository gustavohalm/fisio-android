package com.gustavohalm.fisio.Apis;

import com.gustavohalm.fisio.Models.Appointment;
import com.gustavohalm.fisio.Models.AppointmentNew;
import com.gustavohalm.fisio.Models.BillsToPay;
import com.gustavohalm.fisio.Models.BillsToReceive;
import com.gustavohalm.fisio.Models.Patient;
import com.gustavohalm.fisio.Models.Token;
import com.gustavohalm.fisio.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @POST("api-auth/")
    Call<Token> login(@Body User user);

    //region Patient ENDPOINTS

    @GET("patient/")
     Call< List <Patient> >  getPatients(@Header("Authorization") String token );

    @GET("patient/{id}")
     Call<Patient> getPatient(@Header("authorization") String token, @Path("id") int id);

    @POST("patient/")
    Call<Patient> createPatient(@Header("Authorization") String token, @Body Patient patient);

    //endregion


    //region BillsToReceive ENDPOINTS

    @POST("billstoreceive/")
    Call<BillsToReceive> createBillsToReceive( @Header("Authorization") String token, @Body BillsToReceive bills );

    @GET("billstoreceive/?date={date}")
    Call<List<BillsToReceive> > getBilssToReceive(@Header("Authorization") String token, @Path("date") String date);

    //endregion


    //region BillsToPay ENDPOINTS

    @POST("billstopay/")
    Call<BillsToPay> createBillsToPay(@Header("Authorization") String token, @Body BillsToPay bill);

    @GET("billstopay/?date={date}")
    Call< List<BillsToPay> > getBillsToPay(@Header("Authorization") String token, @Path("date") String date);

    //endregion


    //region Appointments ENDPOINTS

    @GET("appointments/")
    Call< List<Appointment> > getAppointments(@Header("Authorization") String token, @Query("day") String date);

    @POST("appointment/")
    Call<AppointmentNew> createAppointment(@Header("Authorization") String token, @Body AppointmentNew appointment);

    //endregion
}
