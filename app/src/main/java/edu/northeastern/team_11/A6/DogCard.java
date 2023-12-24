package edu.northeastern.team_11.A6;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class DogCard {
    private static final String TAG = "DogCard";

    private String name;
    private String id;
    private String height;
    private String weight;
    private String url;
    private String bredFor;
    private String breedGroup;
    private String lifeSpan;
    private String temperament;

    public DogCard(String name, String id, String height, String weight, String url, String lifeSpan, String temperament, String breedGroup, String bredFor) {
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.lifeSpan = lifeSpan;
        this.temperament = temperament;
        this.bredFor = bredFor;
        this.breedGroup = breedGroup;
        this.url = url;


    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getUrl() {
        return url;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setUrl(String url) {
        this.url = url;
        Log.d(TAG, "setUrl: " + this.url);
    }

}
