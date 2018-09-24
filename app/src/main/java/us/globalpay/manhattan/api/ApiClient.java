package us.globalpay.manhattan.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import us.globalpay.manhattan.utils.StringsURL;

/**
 * Created by Josué Chávez on 20/09/2018.
 */
public class ApiClient
{
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(StringsURL.URL_BASE)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(47, TimeUnit.SECONDS)
            .writeTimeout(47, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .build();
}
