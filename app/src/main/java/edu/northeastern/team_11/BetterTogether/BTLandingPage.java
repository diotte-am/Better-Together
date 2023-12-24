package edu.northeastern.team_11.BetterTogether;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;

import java.util.Date;

import java.util.ArrayList;

import edu.northeastern.team_11.BetterTogether.Tabs.BTUser;
import edu.northeastern.team_11.BetterTogether.Tabs.HomeFragment;
import edu.northeastern.team_11.BetterTogether.Tabs.PostFragment;
import edu.northeastern.team_11.BetterTogether.Tabs.SettingsFragment;
import edu.northeastern.team_11.R;

public class BTLandingPage extends AppCompatActivity {
    TabLayout tabLayout;
    FrameLayout frameLayout;
    BTUser currentUser;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<String> activities;
    ArrayList<BTUser> usernames;
    int[] selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btlanding_page);
        setContentView(R.layout.activity_btlanding_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        tabLayout = findViewById(R.id.landing_page_tab);
        frameLayout = findViewById(R.id.landing_frame_layout);
        currentUser = (BTUser) getIntent().getSerializableExtra("currentUser");

        selection = new int[]{0};
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> selection[0] = result.getResultCode()
        );

        activities = new ArrayList<>(4);
        activities.add("Meditations");
        activities.add("Affirmations");
        activities.add("Yoga Practice");
        activities.add("Journaling");

        usernames = new ArrayList<>();

        createTabs();
    }

    private void createTabs() {
        TabLayout.Tab homeTab = tabLayout.newTab();
        homeTab.setIcon(R.drawable.bt_home);
        tabLayout.addTab(homeTab);

        TabLayout.Tab postTab = tabLayout.newTab();
        postTab.setIcon(R.drawable.bt_post);
        tabLayout.addTab(postTab);

        TabLayout.Tab settingsTab = tabLayout.newTab();
        settingsTab.setIcon(R.drawable.bt_user_settings);
        tabLayout.addTab(settingsTab);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landing_frame_layout, new HomeFragment(currentUser.getUserName()));
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("USERNAME", currentUser.getUserName());
                        fragment = new HomeFragment(currentUser.getUserName());
                        break;
                    case 1:
                        Intent i = new Intent(BTLandingPage.this, BTActivitySelect.class);
                        i.putExtra("currentUser", currentUser);
                        Log.d("currentUser", currentUser.toString());
                        activityResultLauncher.launch(i);
                        fragment = new PostFragment();

                        break;
                    case 2:
                        fragment = new SettingsFragment();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.landing_frame_layout, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }




        });
    }

    public void profile(View view){
        Intent intent = new Intent(BTLandingPage.this, BTProfile.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public void openProfile(View view, BTUser user){
        Intent intent = new Intent(BTLandingPage.this, BTProfile.class);
        intent.putExtra("userView", user);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public BTUser getUser(){
        return this.currentUser;
    }

    public void setUser(BTUser user){
        this.currentUser = user;
    }

    public void setUserList(ArrayList<BTUser> usernames){
        this.usernames = usernames;
    }

    public ArrayList<BTUser> getUserList() {return this.usernames;}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String activityResult = "";

        if (resultCode == 0) {
            activityResult = "Meditation";
        } else if (resultCode == 1) {
            activityResult = "Affirmation";
        }else if (resultCode == 2) {
            activityResult = "Yoga";
        } else {
            activityResult = "Journaling";
        }

        Bundle activityType = new Bundle();
        activityType.putString("activity", activityResult);
        activityType.putString("userName", currentUser.getUserName());

        Fragment newfragment = new PostFragment();
        newfragment.setArguments(activityType);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landing_frame_layout, newfragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}


