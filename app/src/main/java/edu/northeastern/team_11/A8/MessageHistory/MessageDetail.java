package edu.northeastern.team_11.A8.MessageHistory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import edu.northeastern.team_11.R;

public class MessageDetail extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_message_detail);
        TextView receivedTotalV = findViewById(R.id.receivedTotal);
        TextView sentTotalV = findViewById(R.id.sentTotal);
        TextView receivedGoldV = findViewById(R.id.receivedGold);
        TextView sentGoldV = findViewById(R.id.sentGold);
        TextView receivedPlatinumV = findViewById(R.id.receivedPlatinum);
        TextView sentPlatinumV = findViewById(R.id.sentPlatinum);
        TextView sent = findViewById(R.id.messagesSent);
        TextView received = findViewById(R.id.messagesReceived);
        ImageView sticker = this.findViewById(R.id.stickerView);

        String stickerId = getIntent().getStringExtra("stickerId");
        String receivedTotal = getIntent().getStringExtra("receivedTotal");
        String sentTotal = getIntent().getStringExtra("sentTotal");
        String sentGold = getIntent().getStringExtra("sentGold");
        String receivedGold = getIntent().getStringExtra("receivedGold");
        String sentPlatinum = getIntent().getStringExtra("sentPlatinum");
        String receivedPlatinum = getIntent().getStringExtra("receivedPlatinum");

        receivedTotalV.setText(receivedTotal);
        sentTotalV.setText(sentTotal);
        receivedGoldV.setText(receivedGold);
        receivedPlatinumV.setText(receivedPlatinum);
        sentGoldV.setText(sentGold);
        sentPlatinumV.setText(sentPlatinum);
        if(receivedTotal == null){
            received.setText("");
        }
        if(sentTotal == null){
            sent.setText("");
        }

        if(stickerId != null){
            Picasso.get().load(Uri.parse(stickerId)).into(sticker);
        }

    }

    public void viewHistory(View view){
        Intent intent = new Intent(MessageDetail.this, MyFirebaseMessaging.class);
        String username = getIntent().getStringExtra("currentUser");
        intent.putExtra("currentUser", username);
        startActivity(intent);
    }


}
