package com.gustavohalm.fisio.Apis;

import com.gustavohalm.fisio.Models.Appointment;
import com.gustavohalm.fisio.Models.AppointmentNew;
import com.gustavohalm.fisio.Models.BillsToPay;
import com.gustavohalm.fisio.Models.BillsToReceive;
import com.gustavohalm.fisio.Models.Diagnostic;
import com.gustavohalm.fisio.Models.ImagePatient;
import com.gustavohalm.fisio.Models.Patient;
import com.gustavohalm.fisio.Models.Token;
import com.gustavohalm.fisio.Models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("image-patient/")
    Call<List<ImagePatient>> getPatientImages(@Header("Authorization") String token, @Query("patient") String patient_id);

    @Multipart
    @POST("image-patient/")
    Call<ResponseBody> uploadImagePatient(@Header("Authorization") String token, @Part("patient") RequestBody patient, @Part("description") RequestBody description, @Part MultipartBody.Part url);

    //endregion


    //region BillsToReceive ENDPOINTS

    @POST("billstoreceive/")
    Call<BillsToReceive> createBillsToReceive( @Header("Authorization") String token, @Body BillsToReceive bills );

    @GET("billstoreceive/")
    Call<List<BillsToReceive> > getBillsToReceive(@Header("Authorization") String token, @Query("date") String date);

    //endregion


    //region BillsToPay ENDPOINTS

    @POST("billstopay/")
    Call<BillsToPay> createBillsToPay(@Header("Authorization") String token, @Body BillsToPay bill);

    @GET("billstopay/")
    Call< List<BillsToPay> > getBillsToPay(@Header("Authorization") String token, @Query("date") String date);

    //endregion


    //region Appointments ENDPOINTS

    @GET("appointments/")
    Call< List<Appointment> > getAppointments(@Header("Authorization") String token, @Query("day") String date);

    @POST("appointment/")
    Call<AppointmentNew> createAppointment(@Header("Authorization") String token, @Body AppointmentNew appointment);

    @PUT("appointment/{id}/")
    Call<AppointmentNew> editAppointment(@Header("Authorization") String token,@Path("id") int id, @Body AppointmentNew appointment );

    //endregion


    //region Diagnostic ENDPOINTS

    @GET("diagnostic/")
    Call< List<Diagnostic> > getPatientDiagnostics(@Header("Authorization") String token, @Query("patient") int patientId );

    @POST("diagnostic/")
    Call<Diagnostic> createDiagnostic(@Header("Authorization") String token, @Body Diagnostic diagnostic );

    @Multipart
    @POST("image-diagnostic/")
    Call<ResponseBody> uploadImageDiagnostic(@Header("Authorization") String token, @Part("diagnostic") RequestBody patient, @Part("description") RequestBody description, @Part MultipartBody.Part url);


    //endregion

}
