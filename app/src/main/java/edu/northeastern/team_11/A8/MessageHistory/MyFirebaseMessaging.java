package edu.northeastern.team_11.A8.MessageHistory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.northeastern.team_11.A8.Landing;
import edu.northeastern.team_11.A8.MessageInteraction.Sticker;
import edu.northeastern.team_11.A8.StickerCategories;
import edu.northeastern.team_11.R;

public class MyFirebaseMessaging extends AppCompatActivity {
    private static final String TAG = "FirebaseMessaging";

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FirebaseMessageAdapter adapter;
    List<FbMessage> messageReceivedList = new ArrayList<>();
    List<FbMessage> messageSentList = new ArrayList<>();
    DatabaseReference databaseReference;
    String currentUser;
    StickerCategories stickerCat = StickerCategories.FREE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_history);

        currentUser = getIntent().getStringExtra("currentUser");
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("/users/" + currentUser + "/messages");

        fetchMessageHistory();
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.fbMessageRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FirebaseMessageAdapter(messageReceivedList, messageSentList, this);
        recyclerView.setAdapter(adapter);
    }

    private void fetchMessageHistory() {
        DatabaseReference messageHistory = databaseReference.getRef();

        messageHistory.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageReceivedList.clear();
                Iterable<DataSnapshot> messagesReceived = snapshot.child("received").getChildren();
                messagesReceived.forEach(m -> {
                    // add categories here when they are available
                    String date = Objects.requireNonNull(m.child("date").getValue()).toString();
                    String sender = Objects.requireNonNull(m.child("sender").getValue()).toString();
                    String sticker = Objects.requireNonNull(m.child("sticker").getValue()).toString();
                    String category = Objects.requireNonNull(m.child("category").getValue()).toString();
                    LocalDate newDate = LocalDate.parse(date);
                    if(category.equals("gold")){
                        stickerCat = StickerCategories.GOLD;
                    } else if (category.equals("platinum")) {
                        stickerCat = StickerCategories.PLATINUM;
                    } else {
                        stickerCat = StickerCategories.FREE;
                    }

                    // add categories here when they are available
                    FbMessage newMessage = new FbMessage(newDate, sender, currentUser, new Sticker(sticker, stickerCat));
                    messageReceivedList.add(newMessage);
                });

                messageSentList.clear();
                Iterable<DataSnapshot> messagesSent = snapshot.child("sent").getChildren();
                messagesSent.forEach(m -> {
                    try {
                        String date = Objects.requireNonNull(m.child("date").getValue()).toString();
                        String recipient = Objects.requireNonNull(m.child("recipient").getValue()).toString();
                        String sticker = Objects.requireNonNull(m.child("sticker").getValue()).toString();
                        String category = Objects.requireNonNull(m.child("category").getValue().toString());
                        LocalDate newDate = LocalDate.parse(date);

                        if(category.equals("gold")){
                            stickerCat = StickerCategories.GOLD;
                        } else if (category.equals("platinum")) {
                            stickerCat = StickerCategories.PLATINUM;
                        } else {
                            stickerCat = StickerCategories.FREE;
                        }
                        FbMessage newMessage = new FbMessage(newDate, currentUser, recipient, new Sticker(sticker, stickerCat));
                        messageSentList.add(newMessage);
                    } catch (Exception e) {
                        Log.e(TAG, "onDataChange: " + e.getMessage());
                    }
                });

                adapter.notifyDataSetChanged();
                updateMessageTotals();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.getMessage());
            }
        });


    }

    private void updateMessageTotals() {
        TextView sentTotal = findViewById(R.id.sentTotal);
        TextView receivedTotal = findViewById(R.id.receivedTotal);
        TextView sentGold = findViewById(R.id.sentGold);
        TextView receivedGold = findViewById(R.id.receivedGold);
        TextView sentPlatinum = findViewById(R.id.sentPlatinum);
        TextView receivedPlatinum = findViewById(R.id.receivedPlatinum);

        sentPlatinum.setText("Platinum: " + getCount(messageSentList, "platinum"));
        receivedPlatinum.setText("Platinum " + getCount(messageReceivedList, "platinum"));

        sentGold.setText("Gold: " + getCount(messageSentList, "gold"));
        receivedGold.setText("Gold: "  + getCount(messageReceivedList, "gold"));

        sentTotal.setText("Total: " + messageSentList.size());
        receivedTotal.setText("Total: " + messageReceivedList.size());
    }

    public String getCount(List<FbMessage> List, String category){
        return String.valueOf(List.stream().filter(message -> message.getSticker().getCategory().equals(category)).count());
    }

    public void clickThumbnail(View view){
        Intent intent = new Intent(MyFirebaseMessaging.this, MessageDetail.class);

        String username = getIntent().getStringExtra("currentUser");
        intent.putExtra("currentUser", username);

        View path = view.findViewById(R.id.fbMessageImage);
        intent.putExtra("sentPlatinum", "Platinum: " + getCount(messageSentList, "platinum"));
        intent.putExtra("receivedPlatinum", "Platinum: " + getCount(messageReceivedList, "platinum"));
        intent.putExtra("sentGold", "Gold: " + getCount(messageSentList, "gold"));
        intent.putExtra("receivedGold", "Gold: " + getCount(messageReceivedList, "gold"));
        intent.putExtra("stickerId", path.getTag().toString());
        intent.putExtra("receivedTotal", "Total: " + messageReceivedList.size());
        intent.putExtra("sentTotal", "Total:" + messageSentList.size());

        startActivity(intent);
    }


}
