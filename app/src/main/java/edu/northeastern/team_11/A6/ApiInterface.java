package edu.northeastern.team_11.A6;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("x-api-key: live_6lSYQdx2Y8zivviCjM0fXVtt6BMZFBwNFcWKd1MPkWCsfYr3ponbyMSRmNgwI9jo")
    @GET("breeds/search")
    Call<List<JsonObject>> searchDogsByBreedIds(@Query("q") String breedName);

    @Headers("x-api-key: live_6lSYQdx2Y8zivviCjM0fXVtt6BMZFBwNFcWKd1MPkWCsfYr3ponbyMSRmNgwI9jo")
    @GET("images/search?limit=10&size=small&has_breeds=true")
    Call<List<JsonObject>> getRandomDogs();

    @Headers("x-api-key: live_6lSYQdx2Y8zivviCjM0fXVtt6BMZFBwNFcWKd1MPkWCsfYr3ponbyMSRmNgwI9jo")
    @GET("images/{imageId}")
    Call<JsonObject> getBreedImage(@Path("imageId") String imageId);

}
