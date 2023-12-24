package edu.northeastern.team_11.A8;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.northeastern.team_11.R;

public class UserLogin extends AppCompatActivity {

    // A Firebase reference represents a particular location in your Database
    // and can be used for reading or writing data to that Database location.
    private DatabaseReference myDataBase;
    private TextView userNameSignUp;
    private TextView userNameLogin;
    private Button registerBtn;
    private Button logInBtn;
    private Activity context;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set up the database reference
        myDataBase = FirebaseDatabase.getInstance().getReference();

        //set up all the UI view elements
        userNameSignUp = findViewById(R.id.editTextTextPersonName);
        registerBtn = findViewById(R.id.button_register);
        userNameLogin = findViewById(R.id.loginTV);
        logInBtn = findViewById(R.id.login_button);

        // Set on Click Listener buttons
        registerBtn.setOnClickListener(this::registerUserAccount);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if username exists in database open main activity and send username to it
                login(userNameLogin.getText().toString());
            }
        });
        context = this;
        mAuth = FirebaseAuth.getInstance();

        System.out.println("onCreate Successful");
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public void registerUserAccount(View view) {
        String userName;
        userName = userNameSignUp.getText().toString();
        //get the current device id
        @SuppressLint({"DeviceIds", "HardwareIds"}) String android_id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //validate for the user name input
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(context.getApplicationContext(), "Please enter a valid user name",  Toast.LENGTH_LONG).show();

            return;
        }
        String msg1 = "Username successfully created!";
        Toast signUpSuccess = Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_LONG);
        signUpSuccess.show();

        writeNewUser(userName);

        // notify app that new data is available to sync
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("RegisterUser", "onCreate Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    System.out.println("token from add for " + userName +  ":" + token);
                    User user = new User(userName, android_id, token);
                    myDataBase.child("users").child(android_id).setValue(user);
                });
    }

    private void updateUI(FirebaseUser user) {

    }

    public void login(String username) {
        myDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                if (snapshot.child("users").hasChild(username)) {
                    //Intent intent = new Intent(UserLogin.this, MainActivity.class);
                    Intent intent = new Intent(UserLogin.this, Landing.class);
                    intent.putExtra("currentUser", username);
                    //startActivity(intent);
                    startActivity(intent);
                }
                else {
                    String msg = username + ": User not found";
                    Toast loginFail = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    loginFail.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void writeNewUser(String username) {
        User user = new User(username);
        myDataBase.child("users").child(username).setValue(user);
    }
}
