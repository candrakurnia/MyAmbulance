package com.project.myambulance.remote;

import com.project.myambulance.model.DataCovid;
import com.project.myambulance.model.Driver;
import com.project.myambulance.model.History;
import com.project.myambulance.model.Lokasi;
import com.project.myambulance.model.ResponseData;
import com.project.myambulance.model.ResponseList;
import com.project.myambulance.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Routes {

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_GET_HISTORY)
    Call<ResponseList<History>> getHistory(@Field("no_ktp") String no_ktp);

    @GET(RemoteEndpoint.ENDPOINT_GET_COVID)
    Call<DataCovid> getCovid();

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_LOGIN)
    Call<ResponseData<User>> loginUser(@Field("username") String username,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_ORDER)
    Call<ResponseList<Lokasi>> order(@Field("no_ktp") String no_ktp,
                                     @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_REGISTER)
    Call<ResponseList<User>> register(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("no_telpon") String no_telpon,
                                      @Field("no_ktp") String no_ktp,
                                      @Field("no_kk") String no_kk);

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_DRIVER)
    Call<ResponseData<Driver>> driver(@Field("no_ktp") String no_ktp);

}
