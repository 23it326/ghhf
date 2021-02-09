package com.plantix.demo.Service;

import com.plantix.demo.Beans.Example;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {



    String BASE_URL = "https://recipesapi.herokuapp.com/api/";

    @GET("{path}")
    Observable<Example> getRandomList(@Path("path") String path);
}

