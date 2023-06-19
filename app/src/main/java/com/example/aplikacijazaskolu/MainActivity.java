package com.example.aplikacijazaskolu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
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


import java.io.File;



public class MainActivity extends AppCompatActivity {
    public void upload() {
        FileUploader fileUploader = new FileUploader(getApplicationContext());
        fileUploader.uploadFileToServer();
    }
    private Spinner spinner;
    private Spinner spiner2;
    private Spinner spiner3;
    private Spinner spiner4;
    private EditText editText;
    private Button button;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        spinner = findViewById(R.id.spinner);
        spiner2 = findViewById(R.id.spinner2);
        spiner3 = findViewById(R.id.spinner3);
        spiner4 = findViewById(R.id.spinner4);
        button = findViewById(R.id.button);
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        File uceniciDatabase = new File("path/to/your/database.db");



        // Set up the OnItemSelectedListener for the first dropdown spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();

                if (selectedValue.equals("IB") || selectedValue.equals("IT")) {
                    spiner2.setEnabled(false);
                    spiner2.setSelection(getIndexOfItem(spiner2, "NI JEDAN"));
                } else {
                    spiner2.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected
            }
        });

        spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getItemAtPosition(position).toString();

                if (selectedValue.equals("DVOJEZIČNI")) {
                    spiner4.setEnabled(false);
                    spiner4.setSelection(getIndexOfItem2(spiner4, "DSD"));
                } else {
                    spiner4.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected
            }
        });

        button.setOnClickListener(view -> {
            createUser();
            upload();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.smjers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterNac = ArrayAdapter.createFromResource(this,
                R.array.smjersNac, android.R.layout.simple_spinner_item);
        adapterNac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(adapterNac);

        ArrayAdapter<CharSequence> adapterVjeronauk = ArrayAdapter.createFromResource(this,
                R.array.smjersVjeronauk, android.R.layout.simple_spinner_item);
        adapterVjeronauk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner3.setAdapter(adapterVjeronauk);

        ArrayAdapter<CharSequence> adapterDSD = ArrayAdapter.createFromResource(this,
                R.array.smjersDSD, android.R.layout.simple_spinner_item);
        adapterDSD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner4.setAdapter(adapterDSD);
    }

    private void createUser() {
        User user = fetchUserFromDatabase(1);

        if (user != null) {
            Toast.makeText(this, "User already exists: " + user.getName(), Toast.LENGTH_SHORT).show();
        } else {
            String name = editText.getText().toString();
            String smjer = spinner.getSelectedItem().toString();
            String nacSmjer = spiner2.getSelectedItem().toString();
            String vjeronauk = spiner3.getSelectedItem().toString();
            String dsd = spiner4.getSelectedItem().toString();

            user = new User(name, smjer, nacSmjer, vjeronauk, dsd);
            // Save the user to the database or perform other operations as needed
            long rowId = saveUserToDatabase(user);

            if (rowId != -1) {
                Toast.makeText(this, "User created successfully: " + user.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to create user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long saveUserToDatabase(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("smjer", user.getSmjer());
        values.put("nacSmjer", user.getNacSmjer());
        values.put("vjeronauk", user.getVjeronauk());
        values.put("dsd", user.getDsd());

        return database.insert("ucenici", null, values);
    }

    private User fetchUserFromDatabase(int objectId) {
        User user = null;
        Cursor cursor = null;

        try {
            String[] projection = {"name", "smjer", "nacSmjer", "vjeronauk", "dsd"};
            String selection = "name = ?";
            String[] selectionArgs = {String.valueOf(objectId)};

            cursor = database.query("ucenici", projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String smjer = cursor.getString(cursor.getColumnIndexOrThrow("smjer"));
                String nacSmjer = cursor.getString(cursor.getColumnIndexOrThrow("nacSmjer"));
                String vjeronauk = cursor.getString(cursor.getColumnIndexOrThrow("vjeronauk"));
                String dsd = cursor.getString(cursor.getColumnIndexOrThrow("dsd"));

                user = new User(name, smjer, nacSmjer, vjeronauk, dsd);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }

    private int getIndexOfItem(Spinner spinner, String item) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        return adapter.getPosition(item);
    }
    private int getIndexOfItem2(Spinner spiner2, String item) {
        ArrayAdapter<String> adapterNac = (ArrayAdapter<String>) spiner2.getAdapter();
        return adapterNac.getPosition(item);
    }

}
