package com.example.loginview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit;
    SessionManager sessionManager;
    RotateLoading rotateLoading;


    public void login(View view)
    {
        final EditText username=findViewById(R.id.username_editTest);
        final EditText password=findViewById(R.id.password_editTest);

//        if(username.getText().toString().equals("anirudh") && password.getText().toString().equals("Welcome@1234"))
//        {
//            sessionManager.CreateSession(username.getText().toString(),password.getText().toString());
//            Toast.makeText(MainActivity.this,"Login sucessfull",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this,Login_form.class);
//            startActivity(intent);
//        }
        Call<Registration> call=jsonPlaceHolderApi.CheckLogin(username.getText().toString());
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        call.enqueue(new Callback<Registration>() {

            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
//                if(progressDialog.isShowing())
//                    progressDialog.dismiss();
                Registration rgr=response.body();
                Log.d("registration info",rgr.getFirstName()+" "+rgr.getLastName()+" "+rgr.getPassword()+" "+rgr.getPhoneNo());
                if(username.getText().toString().trim().equals(rgr.getPhoneNo()) && password.getText().toString().trim().equals(rgr.getPassword()))
                {
                    Toast.makeText(MainActivity.this,"Login sucessfull, RegID is "+rgr.getRegId(),Toast.LENGTH_SHORT).show();
                    sessionManager.CreateSession(username.getText().toString(),password.getText().toString(),rgr.getRegId(),
                            "Mr. "+rgr.getFirstName()+" "+rgr.getLastName(),rgr.getEmail(),rgr.getCategory());

                    Intent intent = new Intent(MainActivity.this,Login_form.class);
                    intent.putExtra("first_time",1);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"LOGIN FAILED.PROVIDE CORRECT USERNAME AND PASSWORD",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.d("inside enque__","");

                Toast.makeText(MainActivity.this,"Server Error. Please contact administrator",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void signUp(View view)
    {
        Intent intent=new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager=new SessionManager(this);
        if(sessionManager.isLogIn()==true)
        {
            Intent intent = new Intent(MainActivity.this,Login_form.class);
            intent.putExtra("first_time",0);
            startActivity(intent);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_main);
            retrofit = InitializeRetrofit.getRetrofit();
            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        }
        //sessionManager=new SessionManager(this);
    }
}
