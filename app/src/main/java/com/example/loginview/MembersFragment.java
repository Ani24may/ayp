package com.example.loginview;

import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MembersFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;

    private List<Registration> registrationList;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.members_fragment,container,false);
        retrofit = InitializeRetrofit.getRetrofit();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        recyclerView=view.findViewById(R.id.recycleview);

        Call<List<Registration>> call=jsonPlaceHolderApi.getAllRegistration();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<List<Registration>>() {
            @Override
            public void onResponse(Call<List<Registration>> call, Response<List<Registration>> response) {
                Toast.makeText(getContext(),"Request Fired",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                List<Registration> re=response.body();
                MemberRecyclerViewAdapter memberRecyclerViewAdapter=new MemberRecyclerViewAdapter(re,getContext());
                recyclerView.setAdapter(memberRecyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Registration>> call, Throwable t) {
                Toast.makeText(getContext(),"unable to fetch",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
