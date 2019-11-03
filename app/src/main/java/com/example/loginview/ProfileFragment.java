package com.example.loginview;

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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {


    View view;
    private TextInputEditText membership;
    private TextInputEditText textCategory;
    private TextInputEditText textName;
    private TextInputEditText textFathersName;
    private TextInputEditText textGotra;
    private TextInputEditText textPhone;
    private TextInputEditText textPhone2;
    private TextInputEditText textLand1;
    private TextInputEditText textLand2;
    private TextInputEditText textEmail;
    private TextInputEditText textAdd1;
    private TextInputEditText textAdd2;
    private TextInputEditText textAdd3;
    private TextInputEditText textDistrict;
    private TextInputEditText textPin;
    private TextInputEditText textState;
    private TextInputEditText textBlood;
    private TextInputEditText textDob;
    private TextInputEditText textDom;
    private TextInputEditText textLocHome;
    private TextInputEditText textLocOffice;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private SessionManager sessionManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences UserInfoSharedPreferences;
    private UserInfoSessionManager userInfoSessionManager;

    public ProfileFragment()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.profile_fragment,container,false);
        userInfoSessionManager=new UserInfoSessionManager(this.getActivity());
        membership=view.findViewById(R.id.membership);
        textCategory=view.findViewById(R.id.textCategory);
        textName=view.findViewById(R.id.textName);
        textFathersName=view.findViewById(R.id.textFathersName);
        textGotra=view.findViewById(R.id.textGotra);

        textPhone=view.findViewById(R.id.textPhone);
        textPhone2=view.findViewById(R.id.textPhone2);
        textLand1=view.findViewById(R.id.textLand1);
        textLand2=view.findViewById(R.id.textLand2);
        textAdd3=view.findViewById(R.id.textAdd3);
        textAdd1=view.findViewById(R.id.textAdd1);
        textAdd2=view.findViewById(R.id.textAdd2);
        textDistrict=view.findViewById(R.id.textDistrict);
        textEmail=view.findViewById(R.id.textEmail);
        textPin=view.findViewById(R.id.textPin);
        textState=view.findViewById(R.id.textState);
        textBlood=view.findViewById(R.id.textBlood);
        textDob=view.findViewById(R.id.textDob);
        textDom=view.findViewById(R.id.textDom);
        textLocHome=view.findViewById(R.id.textLocHome);
        textLocOffice=view.findViewById(R.id.textLocOffice);

        sharedPreferences=this.getActivity().getSharedPreferences("LOGIN",0);
        UserInfoSharedPreferences=this.getActivity().getSharedPreferences("USER_INFO",0);

        textFathersName.setText(UserInfoSharedPreferences.getString("FATHERNAME",""));
        textGotra.setText(UserInfoSharedPreferences.getString("GOTRA",""));
        textPhone2.setText(UserInfoSharedPreferences.getString("MOBILENO2",""));
        textLand1.setText(UserInfoSharedPreferences.getString("LANDLINENO1",""));
        textLand2.setText(UserInfoSharedPreferences.getString("LANDLINENO2",""));
        textAdd1.setText(UserInfoSharedPreferences.getString("ADD1",""));
        textAdd2.setText(UserInfoSharedPreferences.getString("ADD2",""));
        textAdd3.setText(UserInfoSharedPreferences.getString("ADD3",""));
        textDistrict.setText(UserInfoSharedPreferences.getString("CITY",""));
        textPin.setText(UserInfoSharedPreferences.getString("PIN",""));
        textState.setText(UserInfoSharedPreferences.getString("STATE",""));
        textBlood.setText(UserInfoSharedPreferences.getString("BLOODGROUP",""));
        textDob.setText(UserInfoSharedPreferences.getString("DOB",""));
        textDom.setText(UserInfoSharedPreferences.getString("DOM",""));

        membership.setText(String.valueOf(sharedPreferences.getInt("REGID",0)));
        textCategory.setText(sharedPreferences.getString("CATEGORY",null));
        textName.setText(sharedPreferences.getString("NAME",null));
        textPhone.setText(sharedPreferences.getString("PHONE",null));
        textEmail.setText(sharedPreferences.getString("EMAIL",null));




        setHasOptionsMenu(true);
        retrofit = InitializeRetrofit.getRetrofit();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
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

                Toast.makeText(getContext(), "save selected", Toast.LENGTH_LONG).show();
                final UserInfo userInfo=new UserInfo(textFathersName.getText().toString(),
                        textGotra.getText().toString(),
                        textPhone2.getText().toString(),
                        textLand1.getText().toString(),
                        textLand2.getText().toString(),
                        textAdd1.getText().toString(),
                        textAdd2.getText().toString(),
                        textAdd3.getText().toString(),
                        textDistrict.getText().toString(),
                        textPin.getText().toString(),
                        textState.getText().toString(),
                        textBlood.getText().toString(),
                        textDob.getText().toString(),
                        textDom.getText().toString());
                Call<UserInfo> call=jsonPlaceHolderApi.updateUserinfo(String.valueOf(idd),userInfo);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                        createSession(userInfo);
                        Toast.makeText(getContext(), "updated succesfully", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
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

                    laodUser(idd);
                }

                return true;

        }
        return true;
    }

    public void laodUser(int id)
    {
        Call<UserInfo> call=jsonPlaceHolderApi.getUserInfo(id);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo ui=response.body();
                createSession(ui);
                Toast.makeText(getActivity(),"Data fetch succesfully , sample are: "+ui.getFatherName()+" "+ui.getState(),Toast.LENGTH_SHORT).show();

                textFathersName.setText(UserInfoSharedPreferences.getString("FATHERNAME",""));
                textGotra.setText(UserInfoSharedPreferences.getString("GOTRA",""));
                textPhone2.setText(UserInfoSharedPreferences.getString("MOBILENO2",""));
                textLand1.setText(UserInfoSharedPreferences.getString("LANDLINENO1",""));
                textLand2.setText(UserInfoSharedPreferences.getString("LANDLINENO2",""));
                textAdd3.setText(UserInfoSharedPreferences.getString("ADD1",""));
                textAdd2.setText(UserInfoSharedPreferences.getString("ADD2",""));
                textAdd3.setText(UserInfoSharedPreferences.getString("ADD3",""));
                textDistrict.setText(UserInfoSharedPreferences.getString("CITY",""));
                textPin.setText(UserInfoSharedPreferences.getString("PIN",""));
                textState.setText(UserInfoSharedPreferences.getString("STATE",""));
                textBlood.setText(UserInfoSharedPreferences.getString("BLOODGROUP",""));
                textDob.setText(UserInfoSharedPreferences.getString("DOB",""));
                textDom.setText(UserInfoSharedPreferences.getString("DOM",""));

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(getActivity(),"Did not refresh ",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void createSession(UserInfo ui)
    {
        userInfoSessionManager.CreateUserInfoSession(ui.getFatherName(),
                ui.getGotra(),
                ui.getMobileNo2(),
                ui.getLandLineNo1(),
                ui.getLandLineNo2(),
                ui.getAdd1(),
                ui.getAdd2(),
                ui.getAdd3(),
                ui.getCity(),
                ui.getPin(),
                ui.getState(),
                ui.getBloodGroup(),
                ui.getDob(),
                ui.getDom());
    }

    public void enableFields()
    {
        textFathersName.setEnabled(true);
        textGotra.setEnabled(true);
        textPhone2.setEnabled(true);
        textLand2.setEnabled(true);
        textAdd1.setEnabled(true);
        textAdd2.setEnabled(true);
        textAdd3.setEnabled(true);
        textDistrict.setEnabled(true);
        textPin.setEnabled(true);
        textState.setEnabled(true);
        textBlood.setEnabled(true);
        textDob.setEnabled(true);
        textDom.setEnabled(true);
        textLand1.setEnabled(true);
    }
}
