package edu.northeastern.team_11.A8.MessageInteraction;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.team_11.A8.StickerCategories;
import edu.northeastern.team_11.R;

public class SendFbMessage extends AppCompatActivity {
    private static final String TAG = "SendFbMessage";

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    StickerAdapter adapter;
    List<Sticker> stickerList = new ArrayList<>();
    List<String> userList = new ArrayList<>();
    DatabaseReference databaseReference;
    String currentUser;
    String selectedSticker = "";
    EditText recipient;
    String stickerCategory = "free";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_send_fb_message);
        currentUser = getIntent().getStringExtra("currentUser");
        databaseReference = FirebaseDatabase.getInstance().getReference("/users");

        try {
            loadStickerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fetchUserList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, userList);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.recipientEditText);
        textView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("/users");
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.stickerRecyclerView);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new StickerAdapter(stickerList, this);
        recyclerView.setAdapter(adapter);

    }

    private void fetchUserList() {
        DatabaseReference allUsers = databaseReference.getRef();

        allUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                Iterable<DataSnapshot> users = snapshot.getChildren();
                users.forEach(u -> {
                    String username = u.child("username").getValue().toString();
                    userList.add(username);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.getMessage());
            }
        });
    }

    private void loadStickerList() throws IOException {
        try {
            AssetManager assetManager = getAssets();
            String[] images = assetManager.list("stickers/free");
            for (String image : images) {
                stickerList.add(new Sticker(image, StickerCategories.FREE));
            }
            String[] images1 = assetManager.list("stickers/gold");
            for (String s : images1) {
                stickerList.add(new Sticker(s, StickerCategories.GOLD));
            }
            String[] images2 = assetManager.list("stickers/platinum");
            for (String s : images2) {
                stickerList.add(new Sticker(s, StickerCategories.PLATINUM));
            }


        } catch (IOException e) {
            Log.e(TAG, "loadStickerList: " + e.getMessage());
        }
    }

    public void selectSticker(View view) {
        ImageView img = view.findViewById(R.id.stickerListImage);
        img.setBackgroundColor(Color.parseColor("#00fff2"));

        for (int i = 0; i < stickerList.size(); i++){
            if(stickerList.get(i).getPath() == img.getTag()) {
                stickerList.get(i).changeSelection();

                if(selectedSticker == img.getTag()){
                    selectedSticker = "";
                    adapter.notifyItemChanged(i);
                } else {
                    selectedSticker = img.getTag().toString();
                    stickerCategory = stickerList.get(i).getCategory();
                    adapter.notifyItemChanged(i);
                }

            } else {
                stickerList.get(i).deselect();
                adapter.notifyItemChanged(i);
            }
        }
    }

    public void sendSticker(View view) {
        String toUser = null;
        recipient = findViewById(R.id.recipientEditText);
        
        try {
            toUser = recipient.getText().toString();
            Log.d(TAG, "sendSticker: " + toUser);

            if(toUser.isBlank()){
                Toast tst = Toast.makeText(this, "Please enter a recipient!", Toast.LENGTH_SHORT);
                tst.show();
            } else if(selectedSticker.isBlank()){
                Toast tst = Toast.makeText(this, "Please select a sticker!", Toast.LENGTH_SHORT);
                tst.show();
            } else if(!userList.contains(toUser)){
                Toast tst = Toast.makeText(this, "Not a valid user", Toast.LENGTH_SHORT);
                tst.show();
            } else if(toUser.equals(currentUser)) {
                Toast.makeText(this, "Cannot send sticker to yourself!", Toast.LENGTH_SHORT).show();
            } else {
                writeNewMessage(LocalDate.now(), selectedSticker.substring(0, selectedSticker.length() - 4), currentUser, toUser);
                Toast.makeText(this, "Success! Message sent to " + toUser, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e(TAG, "sendSticker: " + e.getMessage());
        }
    }

    public void writeNewMessage(LocalDate date, String sticker, String sender, String recipient){
        DatabaseReference ref = databaseReference.child(sender).child("messages").child("sent").push();
        databaseReference.child(sender).child("messages").child("sent").child(ref.getKey()).child("sticker").setValue(sticker);
        databaseReference.child(sender).child("messages").child("sent").child(ref.getKey()).child("recipient").setValue(recipient);
        databaseReference.child(sender).child("messages").child("sent").child(ref.getKey()).child("date").setValue(date.toString());
        databaseReference.child(sender).child("messages").child("sent").child(ref.getKey()).child("category").setValue(stickerCategory);

        DatabaseReference ref1 = databaseReference.child(recipient).child("messages").child("received").push();
        databaseReference.child(recipient).child("messages").child("received").child(ref1.getKey()).child("sticker").setValue(sticker);
        databaseReference.child(recipient).child("messages").child("received").child(ref1.getKey()).child("sender").setValue(sender);
        databaseReference.child(recipient).child("messages").child("received").child(ref1.getKey()).child("date").setValue(date.toString());
        databaseReference.child(recipient).child("messages").child("received").child(ref1.getKey()).child("category").setValue(stickerCategory);
    }
}
