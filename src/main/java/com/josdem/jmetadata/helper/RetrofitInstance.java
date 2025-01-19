package com.josdem.jmetadata.helper;

import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

@Component
public class RetrofitInstance {

  public Retrofit getRetrofit() {
    return RetrofitHelper.getRetrofit();
  }
}
