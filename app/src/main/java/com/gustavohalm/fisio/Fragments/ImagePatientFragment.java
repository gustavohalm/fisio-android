package com.gustavohalm.fisio.Fragments;


import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.ImagePatient;
import com.gustavohalm.fisio.R;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePatientFragment extends Fragment {
    private Button addImage;
    private Button btnUpload;
    private Button btnTakePhoto;
    private ImageView imageView;
    private String token;
    private int patient;
    private Bitmap imageBitmap;
    private SharedPreferences preferences;
    private SharedPreferences prefsToken;
    private Uri uri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ResponseBody imageResponse;
    public ImagePatientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_patient, container, false);
        btnTakePhoto = view.findViewById(R.id.btnImagePatientTakePhoto);
        btnUpload= view.findViewById(R.id.btnImagePatientUpload);
        addImage = view.findViewById(R.id.btnAddPatientImage);
        imageView = view.findViewById(R.id.imgPatientView);

        prefsToken = getContext().getSharedPreferences("token_fisio", getContext().MODE_PRIVATE);
        token = prefsToken.getString("the_token", "0");

        preferences = getContext().getSharedPreferences("patient_created", getContext().MODE_PRIVATE);
        patient = preferences.getInt("the_patient", 0);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                RequestBody patientRequest = RequestBody.create(MultipartBody.FORM, String.valueOf(patient));
                RequestBody descriptionRequest = RequestBody.create(MultipartBody.FORM, "uploaded image");

                File filesDir = getContext().getFilesDir();
                File imageFile = new File(filesDir, String.valueOf(rand.nextInt(10000))+ ".jpeg");

                OutputStream os;
                try {
                    os = new FileOutputStream(imageFile);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                MultipartBody.Part fileBody = MultipartBody.Part.createFormData("url", imageFile.getName(), requestFile);
                makeRequest(patientRequest, descriptionRequest, fileBody);
            }
        });

        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }
    }

    public void makeRequest(RequestBody patient,RequestBody description, MultipartBody.Part image)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/api/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);

        Call<ResponseBody>  call = service.uploadImagePatient("Token "+token, patient, description, image);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if ( response.isSuccessful() )
                {
                    imageResponse = response.body();
                    Toast.makeText(getContext(), "Imagem adicionada com sucesso", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getContext(), "Erro ao adicionar imagem: " + response.code(), Toast.LENGTH_SHORT).show();
                    String errorMessage = null;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.v("debugImage", errorMessage);

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Falha ao adicionar imagem: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}

