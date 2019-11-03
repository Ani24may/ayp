package com.example.loginview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login_form extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    SessionManager sessionManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences loginSharedPreference;
    private ProfilePhotoSessionMnanager profilePhotoSessionMnanager;
    private View view;
    private ImageView iv;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit;
    private Bitmap bitmap;
    private TextView tv;
    private String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        profilePhotoSessionMnanager=new ProfilePhotoSessionMnanager(this);
        loginSharedPreference=this.getSharedPreferences("LOGIN",0);
        sharedPreferences=this.getSharedPreferences("PHOTO_INFO",0);
        sessionManager=new SessionManager(this);
        retrofit = InitializeRetrofit.getRetrofit();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        int value = getIntent().getExtras().getInt("first_time");

        if(value==1)
        {
            filename=String.valueOf(loginSharedPreference.getInt("REGID",0));
            Call<ResponseBody> call=jsonPlaceHolderApi.downloadPhoto(filename+".jpg");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(Login_form.this,"Request Fired",Toast.LENGTH_LONG).show();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            // display the image data in a ImageView or save it
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            String internalPath=saveToInternalStorage(bmp,filename);
                            profilePhotoSessionMnanager.CreateProfilePhotoSession("http://deviceIp:8888/downloadFile/"+filename+".jpg",filename,internalPath);
                            loadImageFromStorage(internalPath,filename);
//                            imageView.setImageBitmap(bmp);
                        } else {
                            // TODO
                        }
                    } else {
                        // TODO
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("throwable",t.toString());
                    Toast.makeText(Login_form.this,"unable to fetch",Toast.LENGTH_LONG).show();
                }
            });
        }
//        String internalpath=profilePhotosharedPreferences.getString("INTERNALPATH",null);
//        String imagename=profilePhotosharedPreferences.getString("IMAGENAME",null);

        //sessionManager.checkLogin();


        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        view=navigationView.inflateHeaderView(R.layout.nav_header);
        iv=view.findViewById(R.id.nav_image_view);
        tv=view.findViewById(R.id.username);
        tv.setText(String.valueOf(loginSharedPreference.getString("NAME","")));


        toolbar=findViewById(R.id.toolbar);
        tabLayout=findViewById(R.id.tab_layout);
        bottomNavigationView=findViewById(R.id.bottom_nav);

//        viewPager=findViewById(R.id.view_pager);
//        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
//        viewPagerAdapter.AddFragment(new ProfileFragment(),"Profile");
//        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);


        ActionBarDrawerToggle actionBarDrawerToggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EventsFragments()).commit();
            bottomNavigationView.setSelectedItemId(R.id.events);
            bottomNavigationView.getMenu().findItem(R.id.events).setChecked(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nv_profile:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
                Intent intent = new Intent(Login_form.this,ProfileSection.class);
                startActivity(intent);
                break;

            case R.id.members:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MembersFragment()).commit();
                break;

            case R.id.commitee:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CommiteeFragment()).commit();
                break;

            case R.id.gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GalleryFragment()).commit();
                break;

            case R.id.events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EventsFragments()).commit();
                break;

            case R.id.vivah:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
                Intent intent2 = new Intent(Login_form.this,WebViewActivity.class);
                startActivity(intent2);
                break;
        }
//        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {

            //super.onBackPressed();
            moveTaskToBack(true);
            //finish();
        }
    }

    private void loadImageFromStorage(String path,String name)
    {

        try {
            File f=new File(path, name+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            iv.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }


    private String saveToInternalStorage(Bitmap bitmapImage,String fileName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,fileName+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        String internalpath=sharedPreferences.getString("INTERNALPATH",null);
        String imagename=sharedPreferences.getString("IMAGENAME",null);
        Toast.makeText(Login_form.this, internalpath+" /"+imagename, Toast.LENGTH_LONG).show();
        if(internalpath !=null && imagename!=null)
        {
            Toast.makeText(Login_form.this, internalpath+" /"+imagename, Toast.LENGTH_LONG).show();
            loadImageFromStorage(internalpath,imagename);
        }
    }
}
