package com.demo.cblue.dagger;

import android.app.Application;
import com.demo.cblue.app.ApiConfiguration;
import com.demo.cblue.model.CBCollagesResponse;
import com.demo.cblue.retrofit.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kros on 8/25/15.
 */
@Module
public class NetworkModule {

    private final Application application;
    private final String endPoint;

    public NetworkModule(Application application, String endPoint) {
        this.application = application;
        this.endPoint = endPoint;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(RestAdapter adapter) {
        return adapter.create(ApiService.class);
    }

    @Provides
    @Singleton
    public ApiConfiguration provideApiConfiguration() {
        return new ApiConfiguration(endPoint);
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(ApiConfiguration apiConfiguration, OkClient client) {
        Gson gson = new GsonBuilder().registerTypeAdapter(CBCollagesResponse.class,
                new CBCollagesResponse.CBCollagesResponseDeserializer()).create();
        return new RestAdapter.Builder()
                .setEndpoint(apiConfiguration.baseDataApiUrlString())
                .setConverter(new GsonConverter(gson))
                .setLogLevel(LogLevel.FULL)
                .setClient(client)
                .build();
    }

    @Provides
    @Singleton
    public OkClient provideOkClient() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        return new OkClient(okHttpClient);
    }
}
