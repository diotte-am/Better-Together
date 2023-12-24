package edu.northeastern.team_11.A8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.team_11.A8.MessageHistory.MessageDetail;
import edu.northeastern.team_11.A8.MessageHistory.MyFirebaseMessaging;
import edu.northeastern.team_11.A8.MessageInteraction.SendFbMessage;
import edu.northeastern.team_11.R;

public class Landing extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickit);
    }

    public void viewHistory(View view){
        Intent intent = new Intent(Landing.this, MyFirebaseMessaging.class);
        String username = getIntent().getStringExtra("currentUser");
        intent.putExtra("currentUser", username);
        startActivity(intent);
    }

    public void sendSticker(View view){
        Intent intent = new Intent(Landing.this, SendFbMessage.class);
        String username = getIntent().getStringExtra("currentUser");
        intent.putExtra("currentUser", username);
        startActivity(intent);
    }

    public void viewNewSticker(View view){
        Intent intent = new Intent(Landing.this, MessageDetail.class);
        String username = getIntent().getStringExtra("currentUser");
        intent.putExtra("currentUser", username);
        startActivity(intent);
    }


}
