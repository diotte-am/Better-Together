package edu.northeastern.team_11;

import edu.northeastern.team_11.A6.DogSearch;
import edu.northeastern.team_11.A8.UserLogin;
import edu.northeastern.team_11.About.About;
import edu.northeastern.team_11.BetterTogether.BTLandingPage;
import edu.northeastern.team_11.BetterTogether.BTUserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dogSearch(View view) {
        Intent intent = new Intent(MainActivity.this, DogSearch.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(MainActivity.this, UserLogin.class);
        startActivity(intent);
    }

    public void about(View view){
        Intent intent = new Intent(MainActivity.this, About.class);
        startActivity(intent);
    }

    public void betterTogether(View view){
        Intent intent = new Intent(MainActivity.this, BTUserLogin.class);
//        Intent intent = new Intent(MainActivity.this, BTLandingPage.class);
        startActivity(intent);
    }
}