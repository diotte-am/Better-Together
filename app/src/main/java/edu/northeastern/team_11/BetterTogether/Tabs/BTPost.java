package edu.northeastern.team_11.BetterTogether.Tabs;

import java.util.Random;

import edu.northeastern.team_11.R;

public class BTPost {
    private  String username;
    private  String date;
    private  String message;
    private  String activity;
    //private final int profile_picture;
    private int color;

    public BTPost(){
        // no argument constructor
    }
    public BTPost(String username, String activity, String date, String message/*,profile_picture*/) {
        this.username = username;
        this.activity = activity;
        this.date = date;
        this.message = message;
    //    this.profile_picture = profile_picture;
    }



    public String getDate() {
        return date;
    }
/*
    public int getProfile_picture() {
        return profile_picture;
    }
*/
    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getActivity() {
        return activity;
    }

    public void setColor(){
        int[] options = {R.color.post_item_color_1, R.color.post_item_color_2};
        Random random = new Random();
        int index = random.nextInt(options.length);
        this.color = options[index];
    }

    public int getColor(){return this.color;}
}
