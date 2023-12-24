package edu.northeastern.team_11.A6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.team_11.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomDogActivity extends AppCompatActivity {
    private static final String TAG = "DogActivity";

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    DogRecyclerAdapter adapter;
    List<DogCard> dogCardList = new ArrayList<>();
    WebView dogWalk;
    TextView fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        createRecyclerView();
        dogWalk = findViewById(R.id.dogWalk);
        fetch = findViewById(R.id.fetching);

        Intent intent = getIntent();
        String searchType = intent.getStringExtra("SearchType");

        if (searchType.equalsIgnoreCase("Query")){
            String breed = intent.getStringExtra("Parameter");
            fetchByBreed(breed);
        } else {
            fetchDogs();
        }

    }

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.dogCardRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DogRecyclerAdapter(dogCardList, this);
        recyclerView.setAdapter(adapter);

    }

    private void fetchDogs() {
        dogWalk.loadUrl("file:///android_asset/dogwalkSm.gif");
        dogWalk.getSettings().setLoadWithOverviewMode(true);
        dogWalk.getSettings().setUseWideViewPort(true);

        RetrofitClient.getRetrofitClient()
                .getRandomDogs()
                .enqueue(new Callback<List<JsonObject>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<JsonObject>> call, @NonNull Response<List<JsonObject>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            response.body().stream().forEach(obj -> {

                                String url = obj.get("url").toString().trim().replace("\"", "");
                                String name = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("name").toString().trim().replace("\"", "");
                                String id = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("id").toString().trim().replace("\"", "");

                                String bredFor;
                                try {
                                    bredFor = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("bred_for").toString().trim().replace("\"", "");
                                } catch(NullPointerException e) {
                                    bredFor = "N/A";
                                    e.printStackTrace();
                                }

                                String breedGroup;
                                try {
                                    breedGroup = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("breed_group").toString().trim().replace("\"", "");
                                } catch(NullPointerException e) {
                                    breedGroup = "N/A";
                                    e.printStackTrace();
                                }

                                String lifeSpan = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("life_span").toString().trim().replace("\"", "");
                                String temperament = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("temperament").toString().trim().replace("\"", "");
                                String weight = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("weight").getAsJsonObject().get("imperial").toString().trim().replace("\"", "");
                                String height = obj.getAsJsonArray("breeds").get(0).getAsJsonObject().get("height").getAsJsonObject().get("imperial").toString().trim().replace("\"", "");

                                dogCardList.add(new DogCard(name, id, height, weight, url, lifeSpan, temperament, breedGroup, bredFor));
                            });

                            adapter.notifyDataSetChanged();
                            dogWalk.setVisibility(View.GONE);
                            fetch.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<JsonObject>> call, @NonNull Throwable t) {
                        Toast.makeText(RandomDogActivity.this, "FAIL: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void fetchByBreed(String breed){
        dogWalk.loadUrl("file:///android_asset/dogwalkSm.gif");
        dogWalk.getSettings().setLoadWithOverviewMode(true);
        dogWalk.getSettings().setUseWideViewPort(true);

        RetrofitClient.getRetrofitClient()
                .searchDogsByBreedIds(breed)
                .enqueue(new Callback<List<JsonObject>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<JsonObject>> call, @NonNull Response<List<JsonObject>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            response.body().stream().forEach(obj -> {
                                String url = "file:///android_asset/blank.png";

                                String bredFor;
                                try {
                                    bredFor = obj.get("bred_for").toString().trim().replace("\"", "");
                                } catch(NullPointerException e) {
                                    bredFor = "";
                                    e.printStackTrace();
                                }

                                String breedGroup;
                                try {
                                    breedGroup = obj.get("breed_group").toString().trim().replace("\"", "");
                                } catch(NullPointerException e) {
                                    breedGroup = "";
                                    e.printStackTrace();
                                }
                                String temperament;
                                try {
                                    temperament = obj.get("temperament").toString().trim().replace("\"", "");
                                } catch(NullPointerException e) {
                                    temperament = "";
                                }

                                String name = obj.get("name").toString().trim().replace("\"", "");
                                String id = obj.get("id").toString().trim().replace("\"", "");
                                String lifeSpan = obj.get("life_span").toString().trim().replace("\"", "");
                                String weight = obj.get("weight").getAsJsonObject().get("imperial").toString().trim().replace("\"", "");
                                String height = obj.get("height").getAsJsonObject().get("imperial").toString().trim().replace("\"", "");

                                dogCardList.add(new DogCard(name, id, height, weight, url, lifeSpan, temperament, breedGroup, bredFor));
                                int position = dogCardList.size() - 1;

                                try {

                                    String imageId = obj.get("reference_image_id").toString().trim().replace("\"", "");
                                    fetchUrl(imageId, position);
                                } catch (NullPointerException e) {
                                    dogCardList.get(position).setUrl("file:///android_asset/pictureUnavailable.jpg");
                                }
                            });

                            adapter.notifyDataSetChanged();
                            dogWalk.setVisibility(View.GONE);
                            fetch.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<JsonObject>> call, @NonNull Throwable t) {
                        Toast.makeText(RandomDogActivity.this, "FAIL: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void fetchUrl(String imageId, int position) {
        final String[] url = new String[1];

        if (imageId.equalsIgnoreCase("null")) {
            dogCardList.get(position).setUrl("file:///android_asset/pictureUnavailable.jpg");
        } else {
            RetrofitClient.getRetrofitClient()
                    .getBreedImage(imageId)
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                url[0] = response.body().get("url").toString().trim().replace("\"", "");
                                dogCardList.get(position).setUrl(url[0]);
                                adapter.notifyItemChanged(position);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                            Log.e(TAG, "onFailure: ",t);
                        }
                    });
        }
    }


}