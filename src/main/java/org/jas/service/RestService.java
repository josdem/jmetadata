package org.jas.service;

import org.jas.model.Category;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface RestService {
    @GET("categories/{language}")
    Call<List<Category>> findByI18n(@Path("language") String language);
}
