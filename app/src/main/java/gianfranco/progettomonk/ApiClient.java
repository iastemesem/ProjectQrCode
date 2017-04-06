package gianfranco.progettomonk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gianfranco on 05/04/2017.
 */

public class ApiClient {
    private static  final String BASE_URL = "http://qrcode.webmonks.net:3200";
    private static final boolean DEBUG = true;
    private static Retrofit retrofit = null;

   /* public static Retrofit getRetrofit (){

        if (retrofit == null ){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }*/

    public static synchronized ApiInterface getInstance() {
        Retrofit clientServer = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return clientServer.create(ApiInterface.class);
    }
    public static synchronized ApiInterface getInstance( HashMap<String, String> headers) {
        Retrofit clientServer = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient(headers))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return clientServer.create(ApiInterface.class);
    }

    private static OkHttpClient getHttpClient() {
        return getHttpClient(null);
    }

    private static OkHttpClient getHttpClient(HashMap<String, String> headers) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // add headers
        builder.addInterceptor(getGenericHeader(headers));

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    private static Interceptor getGenericHeader(final HashMap<String, String> headers) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                if (headers != null) {
                    Iterator iterator = headers.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry pair = (Map.Entry) iterator.next();
                        requestBuilder.addHeader((String) pair.getKey(), (String) pair.getValue());
                        iterator.remove();
                    }
                }
                return chain.proceed(requestBuilder.build());
            }
        };
    }

}

