package edu.northeastern.team_11.BetterTogether.Tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.northeastern.team_11.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    private static final String TAG = "PostFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String username;
    private String activity;
    private Button postBtn;

    private EditText message;
    private int rating = 0;
    private ImageButton amazing_reaction, terrible_reaction,
            neutral_reaction, pleasant_reaction, bad_reaction;
    private DatabaseReference myDatabase;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;
    private FirebaseAuth mAuth;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Post.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2)  {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabase = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("/BT");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("/BT");
        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            activity = getArguments().getString("activity");
            username = getArguments().getString("userName");
            Log.d(TAG, "onCreateView activity : " + activity);
            Log.d(TAG,"onCreateView username " + username);
        }

    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        postBtn = (Button) view.findViewById(R.id.post_button);
        message = (EditText) view.findViewById(R.id.post_edit_text);
        amazing_reaction = (ImageButton) view.findViewById(R.id.amazing_reaction);
        pleasant_reaction = (ImageButton) view.findViewById(R.id.pleasant_reaction);
        neutral_reaction = (ImageButton) view.findViewById(R.id.neutral_reaction);
        bad_reaction = (ImageButton) view.findViewById(R.id.bad_reaction);
        terrible_reaction = (ImageButton) view.findViewById(R.id.terrible_reaction);

        amazing_reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating(5);
            }
        });

        pleasant_reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating(4);
            }
        });

        neutral_reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating(3);
            }
        });

        bad_reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating(2);
            }
        });

        terrible_reaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRating(1);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStreaks(username, activity);
                Fragment fragment = new HomeFragment(username);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.landing_frame_layout, fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
                sendPostToDB();

            }

        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private void saveRating(int rating){
        this.rating = rating;
    }

    private void checkStreaks(String username, String activity){
        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot user = snapshot.child("BT").child("users").child(username);
                Log.w(TAG, "User Found in DB : " + user.toString());
                findRecentStreaks(user, activity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void findRecentStreaks(DataSnapshot user, String activity) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);
        String yesterday = getYesterdayDate(dateFormat, date);
        String lastActivityDay = getLastDate(user, activity);
        Log.d("LAST ACTIVITY DAY ", lastActivityDay);

        // IF USER LOGGED IN FOR THE FIRST TIME
        if (lastActivityDay.equals("01-02-3900")) {
            Log.d("Checking Streaks ", "last day is null");
            changingStreakValue(user, activity, username);

        } else {

            //IF THE USER COMPLETED AN ACTIVITY ON THE SAME DAY
            if (lastActivityDay.equals(today)) {
                Log.d("Checking Streaks ", "last day is today");
                changingStreakValue(user, activity, username);


                //IF USER LOGGED IN ON CONSECUTIVE DAYS, INCREMENT BY ONE
            } else if (lastActivityDay.equals(yesterday)) {
                Log.d("Checking Streaks ", "last day is yesterday");
                changingStreakValue(user, activity, username);

                //IF THE LAST LOG IN DAY IS MORE THAN A DAY, RESET THE COUNTER
            } else {
                Log.d("Checking Streaks:", "last day is more than a day");
                resetDays(user, activity, username);
            }
        }
    }

    private String getLastDate(DataSnapshot user, String activity) {
        BTUser currentUser = createReturningUser(user);
        String lastDate = null;
        if (activity.equals("Meditation")){
            lastDate = currentUser.getLastMedActivityDate();
        }
        if (activity.equals("Affirmation")){
            lastDate = currentUser.getLastAffActivityDate();
        }
        if (activity.equals("Yoga")){
            lastDate = currentUser.getLastYogaActivityDate();
        }
        if (activity.equals("Journaling")){
            lastDate = currentUser.getLastJournActivityDate();
        }
        return lastDate;
    }

    private void changingStreakValue(DataSnapshot user, String activity, String username){
        BTUser currentUser = createReturningUser(user);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);
        Log.d(TAG, "Today's updated date " + today.toString());

        if (activity.equals("Meditation")) {
            Integer currentMedCount = currentUser.getMedStreakCount();
            Integer newStreak = currentMedCount + 1;
            currentUser.setMedStreakCount(newStreak);
            currentUser.setLastMedActivityDate(today);
        }
        if (activity.equals("Affirmation")) {
            Integer currentAffCount = currentUser.getAffStreakCount();
            Integer newStreak = currentAffCount + 1;
            currentUser.setAffStreakCount(newStreak);
            currentUser.setLastAffActivityDate(today);
        }
        if (activity.equals("Yoga")) {
            Integer currentYogaCount = currentUser.getYogaStreakCount();
            Integer newStreak = currentYogaCount + 1;
            currentUser.setYogaStreakCount(newStreak);
            currentUser.setLastYogaActivityDate(today);
        }
        if (activity.equals("Journaling")) {
            Integer currentJournCount = currentUser.getJournStreakCount();
            Integer newStreak = currentJournCount + 1;
            currentUser.setJournStreakCount(newStreak);
            currentUser.setLastJournActivityDate(today);
        }
        databaseReference1.child("users").child(username).child("info").setValue(currentUser);
        Log.d("changingStreakValue()", "Streak incremented");

    }

    private void resetDays(DataSnapshot user, String activity, String username) {
        BTUser returningUser = user.child("info").getValue(BTUser.class);
        Integer currentCount = 1;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);
        Log.d(TAG, "Today's updated date " + today.toString());

        if (activity.equals("Meditation")) {
            returningUser.setMedStreakCount(currentCount);
            returningUser.setLastMedActivityDate(today);
        }
        if (activity.equals("Affirmation")) {
            returningUser.setAffStreakCount(currentCount);
            returningUser.setLastAffActivityDate(today);
        }
        if (activity.equals("Yoga")) {
            returningUser.setYogaStreakCount(currentCount);
            returningUser.setLastYogaActivityDate(today);
        }
        if (activity.equals("Journaling")) {
            returningUser.setJournStreakCount(currentCount);
            returningUser.setLastJournActivityDate(today);
        }
        databaseReference2.child("users").child(username).child("info").setValue(returningUser);
        Log.d("resetDays()", "last day reset to zero");
    }

    private String getYesterdayDate(DateFormat simpleDateFormat, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return simpleDateFormat.format(calendar.getTime());
    }

    private BTUser createReturningUser(DataSnapshot user) {
        BTUser returningUser = user.child("info").getValue(BTUser.class);
        return returningUser;
    }

    private String currentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String today = dateFormat.format(date);
        return today;
    }

    private BTPost createPost(){
        BTPost post = new BTPost(this.username, this.activity, currentDate(), message.getText().toString());
        return post;
    }
    public void sendPostToDB(){

        DatabaseReference postReference = databaseReference1.child("users").child(username).child("posts");
        String postID = postReference.push().getKey();
        postReference.child(postID).setValue(createPost(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Log.e("TAG", "Error adding new post", error.toException());
                } else {
                    Log.d("TAG", "New post added successfully with key: " + postID);
                }
            }
        });

    }







}