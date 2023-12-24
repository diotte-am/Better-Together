package edu.northeastern.team_11.BetterTogether;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

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

import edu.northeastern.team_11.BetterTogether.Tabs.BTUser;
import edu.northeastern.team_11.R;

public class BTActivitySelect extends AppCompatActivity {
    private static final String TAG = "BTActivitySelect";
    private AppCompatImageButton meditationBtn;
    private AppCompatImageButton affirmationBtn;
    private AppCompatImageButton yogaBtn;
    private AppCompatImageButton journalBtn;
    private BTUser currentUser;
    private DatabaseReference myDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    private FirebaseAuth mAuth;
    BTUser sessionUser;
    public BTActivitySelect() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_select_activity);

        myDatabase = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("/BT");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("/BT");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("/BT");
        mAuth = FirebaseAuth.getInstance();

        meditationBtn  = (AppCompatImageButton) findViewById(R.id.meditation_btn) ;
        affirmationBtn  = (AppCompatImageButton) findViewById(R.id.affirmation_btn) ;
        yogaBtn  = (AppCompatImageButton) findViewById(R.id.yoga_btn) ;
        journalBtn  = (AppCompatImageButton) findViewById(R.id.journal_btn) ;

        currentUser = (BTUser) getIntent().getSerializableExtra("currentUser");
        Log.d("ACTIVITY SELECT", "grabbed username" + currentUser.getUserName());

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    public void clickActivity(View anchorview){
        int id = anchorview.getId();
        String activity = "";

        if (id == R.id.meditation_btn) {
            Log.d(TAG, "clickActivity: " + "Meditate");
            activity = "Meditation";
            Intent intent = new Intent();
            setResult(0, intent);
            finish();
        } else if (id == R.id.affirmation_btn) {
            Log.d(TAG, "clickActivity: " + "Affirmation");
            activity = "Affirmation";
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
        } else if (id == R.id.yoga_btn) {
            Log.d(TAG, "clickActivity: Yoga");
            activity = "Yoga";
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        } else if (id == R.id.journal_btn) {
            Log.d(TAG, "clickActivity: journal");
            activity = "Journaling";
            Intent intent = new Intent();
            setResult(3, intent);
            finish();
        } else {
            Log.d(TAG, "clickActivity: No activity selected");
        }

    }


    // POP UP INFO
    public void meditation_popup(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView meditate_tv = (TextView) popupView.findViewById(R.id.tv);
        TextView Link1 = (TextView) popupView.findViewById(R.id.link1);
        TextView Link2 = (TextView) popupView.findViewById(R.id.link2);
        TextView Link3 = (TextView) popupView.findViewById(R.id.link3);

        Link1.setText(
                Html.fromHtml(
                "<a href=Positive Physchology>https://positivepsychology.com/meditation-exercises-activities/</a>"));
        Link1.setMovementMethod(LinkMovementMethod.getInstance());

        Link2.setText(
                Html.fromHtml(
                        "<a href=harvard.edu>https://www.health.harvard.edu/alternative-and-integrative-health/two-mindfulness-meditation-exercises-to-try</a> "));
        Link2.setMovementMethod(LinkMovementMethod.getInstance());

        Link3.setText(
                Html.fromHtml(
                        "<a href=ideas.ted.com>https://ideas.ted.com/cant-seem-to-meditate-7-joyful-activities-for-you-to-try-instead/</a> "));
        Link3.setMovementMethod(LinkMovementMethod.getInstance());


        meditate_tv.setText("Take 5 minutes to sit in a comfortable position, " +
                "close your eyes and focus on your breath. " +
                "Breathe in for 6 and out for 6. Repeat 10 times.\n\n" +
                "Outside references:\n"
        );


        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        anchorView.getLocationOnScreen(location);

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }
    public void affirmation_popup(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView affirm_tv = (TextView) popupView.findViewById(R.id.tv);
        TextView Link1 = (TextView) popupView.findViewById(R.id.link1);
        TextView Link2 = (TextView) popupView.findViewById(R.id.link2);
        TextView Link3 = (TextView) popupView.findViewById(R.id.link3);

        Link1.setText(
                Html.fromHtml(
                        "<a href=psychcentral.com>https://psychcentral.com/blog/self-affirmation-a-simple-exercise-that-actually-helps#10-affirmation-activities</a>"));
        Link1.setMovementMethod(LinkMovementMethod.getInstance());

        Link2.setText(
                Html.fromHtml(
                        "<a href=mindtools.com>https://www.mindtools.com/air49f4/using-affirmations</a> "));
        Link2.setMovementMethod(LinkMovementMethod.getInstance());

        Link3.setText(
                Html.fromHtml(
                        "<a href=abundancenolimits.com>https://www.abundancenolimits.com/positive-affirmation-activities-for-adults/</a> "));
        Link3.setMovementMethod(LinkMovementMethod.getInstance());

        affirm_tv.setText("Think of an area in your life that you would like to change. " +
                "Focus in on it and think of what you want to change. " +
                "Write it down in present tense. Speak them out loud.\n\n" +
                "Outside references:\n");

        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        anchorView.getLocationOnScreen(location);

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

    public void yoga_popup(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView yoga_tv = (TextView) popupView.findViewById(R.id.tv);
        TextView Link1 = (TextView) popupView.findViewById(R.id.link1);
        TextView Link2 = (TextView) popupView.findViewById(R.id.link2);
        TextView Link3 = (TextView) popupView.findViewById(R.id.link3);

        Link1.setText(
                Html.fromHtml(
                        "<a href=prevention.com>https://www.prevention.com/fitness/workouts/g30417941/best-yoga-stretches/</a>"));
        Link1.setMovementMethod(LinkMovementMethod.getInstance());

        Link2.setText(
                Html.fromHtml(
                        "<a href=yogabasics.com>https://www.yogabasics.com/connect/yoga-blog/morning-yoga-stretches-poses/</a> "));
        Link2.setMovementMethod(LinkMovementMethod.getInstance());

        Link3.setText(
                Html.fromHtml(
                        "<a href=nytimes.com>https://www.nytimes.com/guides/well/beginner-yoga</a> "));
        Link3.setMovementMethod(LinkMovementMethod.getInstance());

        yoga_tv.setText("Find a spacious area on the floor. " +
                "Sync your breath to 5 different stretches of your choosing. " +
                "Repeat 3 times. Finish with 1 minute of mindfulness.\n\n" +
                "Outside references:\n");

        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        anchorView.getLocationOnScreen(location);

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }
    public void journal_popup(View anchorView) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_window, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView yoga_tv = (TextView) popupView.findViewById(R.id.tv);
        TextView Link1 = (TextView) popupView.findViewById(R.id.link1);
        TextView Link2 = (TextView) popupView.findViewById(R.id.link2);
        TextView Link3 = (TextView) popupView.findViewById(R.id.link3);

        Link1.setText(
                Html.fromHtml(
                        "<a href=journey.cloud>https://journey.cloud/journal-prompts/</a>"));
        Link1.setMovementMethod(LinkMovementMethod.getInstance());

        Link2.setText(
                Html.fromHtml(
                        "<a href=witanddelight.com>https://witanddelight.com/2020/03/20-journaling-prompts-i-swear-by-to-get-you-out-of-your-head/</a> "));
        Link2.setMovementMethod(LinkMovementMethod.getInstance());

        Link3.setText(
                Html.fromHtml(
                        "<a href=psychcentral.com>https://psychcentral.com/blog/ready-set-journal-64-journaling-prompts-for-self-discovery</a> "));
        Link3.setMovementMethod(LinkMovementMethod.getInstance());

        yoga_tv.setText("Write down what comes to mind when you think of your day. " +
                "For every negative you think of try and find a positive. " +
                "Write down things you are grateful for and goals for tomorrow.\n\n" +
                "Outside references:\n");

        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        anchorView.getLocationOnScreen(location);

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }



    }

