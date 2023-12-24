package edu.northeastern.team_11.A8;

import android.app.Activity;

public class User extends Activity {
    public String username;
    public String userDeviceID;
    public String token;

    public User(String username, String userDeviceID, String token) {
        this.username = username;
        this.userDeviceID = userDeviceID;
        this.token = token;
    }

    public User(){

    }

    public User(String username) {
        this.username = username;
    }
}
