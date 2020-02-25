package uz.itmaker.naft.Interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static  final String BASE_URL = "http://naft.uz/api/v1/";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static  RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){

        OkHttpClient.Builder okhttpClientBuilder= new OkHttpClient.Builder();

        okhttpClientBuilder.readTimeout(60, TimeUnit.SECONDS);
        okhttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);

        GsonBuilder builder = new GsonBuilder().setLenient().
                excludeFieldsWithModifiers();
        Gson gson = builder.create();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static synchronized  RetrofitClient getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
