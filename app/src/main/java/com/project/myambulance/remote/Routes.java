package com.project.myambulance.remote;

import com.project.myambulance.model.DataCovid;
import com.project.myambulance.model.History;
import com.project.myambulance.model.ResponseData;
import com.project.myambulance.model.ResponseList;
import com.project.myambulance.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Routes {

    @GET(RemoteEndpoint.ENDPOINT_GET_HISTORY)
    Call<ResponseList<History>> getHistory();

    @GET(RemoteEndpoint.ENDPOINT_GET_COVID)
    Call<DataCovid> getCovid();

    @FormUrlEncoded
    @POST(RemoteEndpoint.ENDPOINT_LOGIN)
    Call<ResponseData<User>> loginUser(@Field("username") String username,
                                       @Field("password") String password);
}
