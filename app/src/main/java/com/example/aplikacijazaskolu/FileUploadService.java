package com.example.aplikacijazaskolu;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    private void uploadFileToServer(File file) {
// Create OkHttpClient instance
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yourserver.com/")  // Replace with your server's base URL
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

// Create FileUploadService instance
        FileUploadService service = retrofit.create(FileUploadService.class);

// Create request body
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("database", file.getName(), requestBody);

// Make the API call
        Call<ResponseBody> call = service.uploadFile(filePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle a successful upload
                    // ...
                } else {
                    // Handle an unsuccessful upload
                    // ...
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle the failure
                // ...
            }
        });
    }
}

