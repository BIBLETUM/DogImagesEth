package com.example.dogsimg;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("random")
    Single<DogImage> loadDogImage();
}
