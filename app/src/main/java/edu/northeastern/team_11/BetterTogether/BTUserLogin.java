package edu.northeastern.team_11.BetterTogether;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.northeastern.team_11.BetterTogether.Tabs.BTUser;
import edu.northeastern.team_11.R;

public class BTUserLogin extends AppCompatActivity {
    private static final String TAG = "BTUserLogin";

    private DatabaseReference myDatabase;
    private TextView userName;
    private TextView password;
    private Button loginBtn;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    BTUser sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        myDatabase = FirebaseDatabase.getInstance().getReference();

        userName = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.button2);
        registerBtn = findViewById(R.id.loginRegisterButton);

        loginBtn.setOnClickListener(v -> loginUser(userName.getText().toString().trim().toLowerCase()));
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    public void launchRegistration(View view){
        Intent intent = new Intent(BTUserLogin.this, BTUserRegistration.class);
        startActivity(intent);
    }

    private void loginUser(String username) {
        // Hash the username?
        if(username.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Field was empty, please enter username and password",
                    Toast.LENGTH_SHORT).show();}
        else if (!validateInput(username) || !validateInput(password.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();}
        else {
            myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("BT").child("users").hasChild(username)) {
                        if(snapshot.child("BT").child("users").child(username).child("info").child("password").getValue().toString().equals(password.getText().toString())) {
                            Intent intent = new Intent(BTUserLogin.this, BTLandingPage.class);
                            DataSnapshot user = snapshot.child("BT").child("users").child(username);
                            sessionUser = createReturningUser(user);
                            intent.putExtra("currentUser", sessionUser);
                            startActivity(intent);
                        } else {
                            String msg = "Wrong password!";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String msg = username + ": User not found";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

    private boolean validateInput(String username) {
        boolean valid = true;

        if (TextUtils.isEmpty(username)) {
            userName.setText("Required.");
            valid = false;
        }

        hideKeyboard();
        return valid;
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private BTUser createReturningUser(DataSnapshot user) {

        BTUser returningUser = user.child("info").getValue(BTUser.class);
        Log.w(TAG, "createReturningUser: " + returningUser.toString());
        return returningUser;
    }

}
