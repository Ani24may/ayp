package com.example.loginview;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FamilyFragment extends Fragment {
    View view;
    private TextInputEditText spouce;
    private TextInputEditText membership;
    private TextInputEditText dobspouce;
    private TextInputEditText child1;
    private TextInputEditText dobchild1;
    private TextInputEditText child2;
    private TextInputEditText dobchild2;
    private TextInputEditText child3;
    private TextInputEditText dobchild3;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private SharedPreferences sharedPreferences;
    private SharedPreferences FamilySharedPreferences;
    private FamilyInfoSessionManager familyInfoSessionManager;



    public FamilyFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.family_fragment,container,false);
        familyInfoSessionManager =new FamilyInfoSessionManager(this.getActivity());
        spouce=view.findViewById(R.id.spouce);
        membership=view.findViewById(R.id.FamMembership);
        dobspouce=view.findViewById(R.id.dobSpouce);
        child1=view.findViewById(R.id.nameChild1);
        dobchild1=view.findViewById(R.id.dobChild1);
        child2=view.findViewById(R.id.nameChild2);
        dobchild2=view.findViewById(R.id.dobChild2);
        child3=view.findViewById(R.id.nameChild3);
        dobchild3=view.findViewById(R.id.dobChild3);
        sharedPreferences=this.getActivity().getSharedPreferences("LOGIN",0);
        FamilySharedPreferences=this.getActivity().getSharedPreferences("FAMILY_INFO",0);

        spouce.setText(FamilySharedPreferences.getString("SPOUCE",""));
        membership.setText(String.valueOf(sharedPreferences.getInt("REGID",0)));
        dobspouce.setText(FamilySharedPreferences.getString("DOBSPOUCE",""));
        child1.setText(FamilySharedPreferences.getString("CHILD1",""));
        dobchild1.setText(FamilySharedPreferences.getString("DOBCHILD1",""));
        child2.setText(FamilySharedPreferences.getString("CHILD2",""));
        dobchild2.setText(FamilySharedPreferences.getString("DOBCHILD2",""));
        child3.setText(FamilySharedPreferences.getString("CHILD3",""));
        dobchild3.setText(FamilySharedPreferences.getString("DOBCHILD3",""));



        setHasOptionsMenu(true);
        retrofit = InitializeRetrofit.getRetrofit();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        spouce=view.findViewById(R.id.spouce);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_edit,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd=sharedPreferences.getInt("REGID",0);
        switch(item.getItemId())
        {
            case R.id.edit:

                Toast.makeText(getActivity(), "Edit selected and tab user details in on", Toast.LENGTH_LONG).show();
               enableFields();
                return true;



            case R.id.save:

                Toast.makeText(getContext(), "save selected ", Toast.LENGTH_LONG).show();
                final FamilyInfo familyInfo=new FamilyInfo(spouce.getText().toString(),
                        dobspouce.getText().toString(),
                        child1.getText().toString(),
                        dobchild1.getText().toString(),
                        child2.getText().toString(),
                        dobchild2.getText().toString(),
                        child3.getText().toString(),
                        dobchild3.getText().toString());
                Call<FamilyInfo> call=jsonPlaceHolderApi.updateFamilyInfo(String.valueOf(idd),familyInfo);
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Saving your changes. Please wait!");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                // show it
                progressDialog.show();
                call.enqueue(new Callback<FamilyInfo>() {
                    @Override
                    public void onResponse(Call<FamilyInfo> call, Response<FamilyInfo> response) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        CreateSession(familyInfo);
                        Toast.makeText(getContext(), "updated succesfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<FamilyInfo> call, Throwable t) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(getContext(), "did not got update", Toast.LENGTH_LONG).show();
                    }
                });
                return true;

            case R.id.refresh:
                if(idd==0)
                {
                    Toast.makeText(getActivity(),"NO REGID FOUND",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadFamily(idd);
                }
                return true;

        }
        return true;
    }


    public void enableFields()
    {
        spouce.setEnabled(true);
        dobspouce.setEnabled(true);
        child1.setEnabled(true);
        dobchild1.setEnabled(true);
        child2.setEnabled(true);
        dobchild2.setEnabled(true);
        child3.setEnabled(true);
        dobchild3.setEnabled(true);
    }

    public void CreateSession(FamilyInfo fi)
    {
        familyInfoSessionManager.CreateFamilyInfoSession(fi.getSpouce(),
                fi.getDobSpouce(),
                fi.getChild1(),
                fi.getDobChild1(),
                fi.getChild2(),
                fi.getDobChild2(),
                fi.getChild3(),
                fi.getDobChild3());
    }


    public void loadFamily(int id)
    {
        Call<FamilyInfo> call=jsonPlaceHolderApi.getFamilyInfo(id);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Refreshing!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<FamilyInfo>() {
            @Override
            public void onResponse(Call<FamilyInfo> call, Response<FamilyInfo> response) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                FamilyInfo fi=response.body();
                Toast.makeText(getActivity(),"Data fetch succesfully , sample are: "+fi.getSpouce(),Toast.LENGTH_SHORT).show();
                CreateSession(fi);
                spouce.setText(FamilySharedPreferences.getString("SPOUCE",""));
                membership.setText(String.valueOf(sharedPreferences.getInt("REGID",0)));
                dobspouce.setText(FamilySharedPreferences.getString("DOBSPOUCE",""));
                child1.setText(FamilySharedPreferences.getString("CHILD1",""));
                dobchild1.setText(FamilySharedPreferences.getString("DOBCHILD1",""));
                child2.setText(FamilySharedPreferences.getString("CHILD2",""));
                dobchild2.setText(FamilySharedPreferences.getString("DOBCHILD2",""));
                child3.setText(FamilySharedPreferences.getString("CHILD3",""));
                dobchild3.setText(FamilySharedPreferences.getString("DOBCHILD3",""));

            }

            @Override
            public void onFailure(Call<FamilyInfo> call, Throwable t) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(getActivity(),"Did not refresh ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
