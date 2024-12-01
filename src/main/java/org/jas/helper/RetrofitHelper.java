package org.jas.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static final String BASE_URL = "https://fruitypedia.josdem.io/";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
