package gianfranco.progettomonk;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Gianfranco on 05/04/2017.
 */

public  interface ApiInterface {
    @FormUrlEncoded
    @POST("api/Accounts/signUp")
    Call<ResponseBody> insertUser(@Field("email") String email, @Field("password")String password);


    @FormUrlEncoded
    @POST("api/Accounts/authenticate")
    Call<ResponseBody> getUser(@Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("api/QRCodes/add?")
    Call<ResponseBody> insertUrl(@Field("date") String date, @Field("qrCodeData") String qrCodeData, @Field("qrCodeType") String qrCodeType);


}
