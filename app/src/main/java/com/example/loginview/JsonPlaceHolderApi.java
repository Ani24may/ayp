package com.example.loginview;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @POST("register")
    Call<Registration> register(@Body Registration registration);

    @GET("GetByPhone/{phone}")
    Call<Registration> CheckLogin(@Path("phone") String phone);

    @GET("GetRegId/{phone}")
    Call<Integer> getRegId(@Path("phone") String phone);

    @GET("GetUserInfo/{regid}")
    Call<UserInfo> getUserInfo(@Path("regid") int regid);

    @PUT("updateUserInfo/{regid}")
    Call<UserInfo> updateUserinfo(@Path("regid") String regid,@Body UserInfo userinfo);

    @PUT("updateFamilyInfo/{regid}")
    Call<FamilyInfo> updateFamilyInfo(@Path("regid") String regid,@Body FamilyInfo familyInfo);

    @GET("GetFamilyInfo/{regid}")
    Call<FamilyInfo> getFamilyInfo(@Path("regid") int regid);

    @Multipart
    @PUT("upload/{regid}")
    Call<ProfilePhoto> updateprofilePhoto(@Path("regid") String regid, @Part MultipartBody.Part file);

    @GET("downloadFile/{regid}")
    Call<ResponseBody> downloadPhoto(@Path("regid") String regid);

    @GET("GetAll")
    Call<List<Registration>> getAllRegistration();
}
