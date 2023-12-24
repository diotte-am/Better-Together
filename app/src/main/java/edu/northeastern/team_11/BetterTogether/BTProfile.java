package edu.northeastern.team_11.BetterTogether;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

import edu.northeastern.team_11.BetterTogether.Tabs.BTUser;
import edu.northeastern.team_11.R;

public class BTProfile extends AppCompatActivity {
    ImageView proImage;

    BTUser currentUser, userView;
  
    Button editProfile;

    TextView textName, textBirthday, textAge, textLocation, textBio, userName, pronouns, streakCount;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_activity_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        proImage = (ImageView) findViewById(R.id.proPic);
        currentUser = (BTUser) getIntent().getSerializableExtra("currentUser");
        userView = (BTUser) getIntent().getSerializableExtra("userView");
        editProfile = (Button) findViewById(R.id.editProfileButton);

        if(userView.getId().equals(currentUser.getId())){
            editProfile.setVisibility(View.VISIBLE);
            updateData(currentUser);

        } else {
            editProfile.setVisibility(View.INVISIBLE);
            updateData(userView);
        }



    }

    public void updateData(BTUser user){
        Drawable d = getDrawable(R.drawable.animal3);
        proImage.setImageDrawable(d);
        proImage.setClipToOutline(true);
        textName = findViewById(R.id.proNameText);
        textBirthday = findViewById(R.id.proBirthdayText);
        textAge = findViewById(R.id.proAgeText);
        textLocation = findViewById(R.id.proLocationText);
        textBio = findViewById(R.id.proBioText);
        userName = findViewById(R.id.proNameHeading);
        pronouns = findViewById(R.id.proPronounHeading);
        streakCount = findViewById(R.id.proStreak);


        textName.setText(user.getFirstName() + " " + user.getLastName());
        textBirthday.setText(user.getBirthday());
        textAge.setText(user.calculateAge());
        textLocation.setText(user.getCity() + ", " + user.getCountry());
        textBio.setText(user.getBio());
        userName.setText(user.getUserName());
        pronouns.setText(user.getPronouns());
        textName.setText(user.getFirstName() + " " + user.getLastName());
        textBirthday.setText(user.getBirthday());
        textAge.setText(user.calculateAge());
        textLocation.setText(user.getCity() + ", " + user.getCountry());
        textBio.setText(user.getBio());
        userName.setText(user.getUserName());
        pronouns.setText(user.getPronouns());

        Integer medCount = user.getMedStreakCount();
        Integer affCount = user.getAffStreakCount();
        Integer yogaCount = user.getYogaStreakCount();
        Integer journCount = user.getJournStreakCount();

        Integer maxCount = Math.max(Math.max(medCount,affCount),Math.max(yogaCount,journCount));
        streakCount.setText("Current Max Streak: " + maxCount.toString());
    }


    public void editProfile(View view){
        Intent intent = new Intent(BTProfile.this, BTProfileEdit.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public void goHome(View view){
        Intent intent = new Intent(BTProfile.this, BTLandingPage.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }


}
