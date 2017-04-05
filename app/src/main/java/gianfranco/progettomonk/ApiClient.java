package gianfranco.progettomonk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gianfranco on 05/04/2017.
 */

public class ApiClient {

    private static  final String BASE_URL = "http://qrcode.webmonks.net:3200";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit (){

        if (retrofit == null ){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
