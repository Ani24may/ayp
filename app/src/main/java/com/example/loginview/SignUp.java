package com.example.loginview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TextWatcher {
    private Spinner spinner;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    String MobilePattern = "[0-9]{10}";
    private String category;
    EditText fname;
    EditText lname;
    EditText pass;
    EditText email;
    EditText phone;
    EditText main_pass;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        fname=findViewById(R.id.FirstName);
        lname=findViewById(R.id.LastName);
        pass=findViewById(R.id.Password);
        main_pass=findViewById(R.id.main_pass);
        email=findViewById(R.id.Email);
        phone=findViewById(R.id.PhoneNo);
        signUp=findViewById(R.id.btn_signUp);
        fname.addTextChangedListener(this);
        lname.addTextChangedListener(this);
        pass.addTextChangedListener(this);
        main_pass.addTextChangedListener(this);
        email.addTextChangedListener(this);
        phone.addTextChangedListener(this);

        Log.d("ayp","onCreate activity");


        retrofit=InitializeRetrofit.getRetrofit();
        jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi.class);



        spinner=findViewById(R.id.spinner);
       ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cat=parent.getItemAtPosition(position).toString();
        if (cat.equals("Choose Member Category"))
        {

        }
        else
        {
            Toast.makeText(parent.getContext(),cat,Toast.LENGTH_SHORT).show();
            category=cat;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void SignUp(View view)
    {


        Registration registration=new Registration(fname.getText().toString(),lname.getText().toString(),pass.getText().toString(),
                email.getText().toString(),phone.getText().toString(),category);

        Call<Registration> call=jsonPlaceHolderApi.register(registration);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing Up. Please wait!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(SignUp.this,"SIGNUP SUCCESSFUL",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(SignUp.this,"SIGNUP FAILED. PLEASE CONTACT ADMINISTRATOR",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        signUp.setEnabled(!fname.getText().toString().isEmpty() &&
                !lname.getText().toString().isEmpty() &&
                !pass.getText().toString().isEmpty() &&
                !main_pass.getText().toString().isEmpty() &&
                !email.getText().toString().isEmpty() &&
                !phone.getText().toString().isEmpty());

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                {
                    email.setError("Please enter a valid email address");
                    signUp.setEnabled(false);
                }
        if(!main_pass.getText().toString().equals(pass.getText().toString()))
        {
            pass.setError("Password not matching");
            signUp.setEnabled(false);
        }
        if(!phone.getText().toString().matches(MobilePattern))
        {
            phone.setError("Enter 10 digit mobile number");
            signUp.setEnabled(false);
        }
    }

}
