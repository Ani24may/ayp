package com.example.loginview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileSection extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageButton ibPick;
    private CircleImageView civProfile;
    private Button btnConfirm;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit;
    private File file;
    private SharedPreferences sharedPreferences;
    private ProfilePhotoSessionMnanager profilePhotoSessionMnanager;
    private SharedPreferences profilePhotosharedPreferences;
    private Bitmap bitmap;
    private Uri resultUri;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_section);
        toolbar=findViewById(R.id.toolbarr);
        ibPick=findViewById(R.id.select_image);
        civProfile=findViewById(R.id.profile_image);
        btnConfirm=findViewById(R.id.confirm);
        setSupportActionBar(toolbar);
        ibPick.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        retrofit = InitializeRetrofit.getRetrofit();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        sharedPreferences=this.getSharedPreferences("LOGIN",0);
        profilePhotosharedPreferences=this.getSharedPreferences("PHOTO_INFO",0);
        profilePhotoSessionMnanager=new ProfilePhotoSessionMnanager(this);

        String internalpath=profilePhotosharedPreferences.getString("INTERNALPATH",null);
        String imagename=profilePhotosharedPreferences.getString("IMAGENAME",null);
        Toast.makeText(ProfileSection.this, internalpath+" /"+imagename, Toast.LENGTH_LONG).show();
        if(internalpath !=null && imagename!=null)
        {
            Toast.makeText(ProfileSection.this, internalpath+" /"+imagename, Toast.LENGTH_LONG).show();
            loadImageFromStorage(internalpath,imagename);
        }



        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new ProfileFragment(),"User's Details");
        viewPagerAdapter.AddFragment(new FamilyFragment(),"Family Details");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        if(v.equals(ibPick))
        {
            Dexter.withActivity(ProfileSection.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {

                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(ProfileSection.this);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if(response.isPermanentlyDenied())
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSection.this);
                                builder.setTitle("Permission Required")
                                        .setMessage("Permission to access your device storage is required to pick profile image. Please go to setting to enable permission to access storage .")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                intent.setData(Uri.fromParts("package",getPackageName(),null));
                                                startActivityForResult(intent,51);
                                            }
                                        }).setNegativeButton("Cancel",null).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();

        }
        if(v.equals(btnConfirm))
        {
            RequestBody requestBody =RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
            String id=String.valueOf(sharedPreferences.getInt("REGID",0));
            Call<ProfilePhoto> call=jsonPlaceHolderApi.updateprofilePhoto(id,body);

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Uploading Image. Please wait!");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDialog.show();


            call.enqueue(new Callback<ProfilePhoto>() {
                @Override
                public void onResponse(Call<ProfilePhoto> call, Response<ProfilePhoto> response) {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    ProfilePhoto pf=response.body();
                    save(String.valueOf(pf.getRegId()),pf.getPath());
                    Toast.makeText(ProfileSection.this,"Path is "+pf.getPath(),Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onFailure(Call<ProfilePhoto> call, Throwable t) {
                    //flag=false;
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(ProfileSection.this,"Unable to upload photo",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                civProfile.setImageURI(resultUri);
                file=new File(resultUri.getPath());
                //Toast.makeText(ProfileSection.this,"height is "+String.valueOf(civProfile.getHeight())+" Width is "+String.valueOf(civProfile.getWidth()),Toast.LENGTH_SHORT).show();
                btnConfirm.setVisibility(View.VISIBLE);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
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

    private void save(String fileName,String serverpath)
    {

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                String path = saveToInternalStorage(bitmap,fileName);
                profilePhotoSessionMnanager.CreateProfilePhotoSession(serverpath,fileName,path);
                Toast.makeText(ProfileSection.this, "image path is " + path, Toast.LENGTH_LONG).show();

            } catch (IOException ex) {
                Toast.makeText(ProfileSection.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

    }

    private void loadImageFromStorage(String path,String name)
    {

        try {
            File f=new File(path, name+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            civProfile.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
