package com.codehouse.vk;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface apiService {



    String API_TOKEN = "/rest/login";
    String API_OUT = "/rest/logout";
    String API_C = "/rest/abrir";
    String API_REGISTER = "/rest/register";
    String API_COMPARTIR = "/rest/llave/create";

    @Headers("Content-Type: application/json")
    @GET()
    Call<datosDispositivosObjeto> getdispositivoApi(@Url String url);

    @Headers("Content-Type: application/json")
    @GET()
    Call<datosLlavesObjeto> getllavesApi(@Url String url);

    @Headers("Content-Type: application/json")
    @GET()
    Call<datosRegistrosObjeto> getregistrosApi(@Url String url);

    @Headers("Content-Type: application/json")
    @POST(API_TOKEN)
    Call<sesionObjeto> request(@Body JsonObject a);

    @Headers("Content-Type: application/json")
    @POST(API_OUT)
    Call<String> logout(@Body JsonObject g);

    @Headers("Content-Type: application/json")
    @POST(API_C)
    Call<sesionObjeto> abrir(@Body JsonObject c);

    @Headers("Content-Type: application/json")
    @POST(API_REGISTER)
    Call<statusObjeto> registrarme(@Body JsonObject c);

    @Headers("Content-Type: application/json")
    @POST(API_COMPARTIR)
    Call<statusObjeto> compartir(@Body JsonObject c);
}
