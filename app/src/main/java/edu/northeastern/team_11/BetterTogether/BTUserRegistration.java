package edu.northeastern.team_11.BetterTogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.northeastern.team_11.BetterTogether.Tabs.BTUser;
import edu.northeastern.team_11.R;

public class BTUserRegistration extends AppCompatActivity {

    TextView errorMessage;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String userId;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        errorMessage = (TextView) findViewById(R.id.editError);
        errorMessage.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("/BT");
        user = mAuth.getCurrentUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        if (user != null) {
            user.reload();
        }
    }

    public void registerUser(View view){

        errorMessage.setVisibility(View.INVISIBLE);
        TextView pronounView = (TextView) findViewById(R.id.regPronouns);
        String pronounText = pronounView.getText().toString();
        pronounView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView emailView = (TextView) findViewById(R.id.regEmail);
        String emailText = emailView.getText().toString();
        emailView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView firstNameView = (TextView) findViewById(R.id.regFirstName);
        String firstName =firstNameView.getText().toString();
        firstNameView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView lastNameView = (TextView) findViewById(R.id.regLastName);
        String lastName = lastNameView.getText().toString();
        lastNameView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView passwordView = (TextView) findViewById(R.id.regPassword);
        String password = passwordView.getText().toString();
        passwordView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView confirmPasswordView = (TextView) findViewById(R.id.regConfirmPassword);
        String confirmPassword = confirmPasswordView.getText().toString();
        confirmPasswordView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView usernameView = (TextView) findViewById(R.id.regUsername);
        String username = usernameView.getText().toString();
        usernameView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView birthDayView = (TextView) findViewById(R.id.regBirthdayDay);
        String birthDay = birthDayView.getText().toString();
        birthDayView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView birthMonthView = (TextView) findViewById(R.id.regBirthdayMonth);
        String birthMonth = birthMonthView.getText().toString();
        birthMonthView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView birthYearView = (TextView) findViewById(R.id.regBirthdayYear);
        String birthYear = birthYearView.getText().toString();
        birthYearView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView cityView = (TextView) findViewById(R.id.regCity);
        String city = cityView.getText().toString();
        cityView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView countryView = (TextView) findViewById(R.id.regCountry);
        String country = countryView.getText().toString();
        countryView.setBackgroundResource(R.drawable.rounded_registration_5);

        TextView bioView = (TextView) findViewById(R.id.regBio);
        String bio = bioView.getText().toString();
        bioView.setBackgroundResource(R.drawable.rounded_registration_5);

        String wordCount = ((TextView) findViewById(R.id.regWordCount)).getText().toString();

        Boolean formError = false;
        String errors = "";

        if(emailText.length() == 0){
            emailView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "email field is empty\n";
        } if (firstName.length() == 0) {
            firstNameView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "first name field is empty\n";
        } if(lastName.length() == 0) {
            lastNameView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "last name field is empty\n";
        } if(password.length() == 0) {
            passwordView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "password field is empty\n";
        } if(confirmPassword.length() == 0) {
            confirmPasswordView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "password confirmation field is empty\n";
        } if (!confirmPassword.equals(password)) {
            confirmPasswordView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            passwordView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "email fields must match\n";
        } if (username.length() == 0) {
            usernameView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "username field is empty\n";
        } if (birthDay.length() == 0) {
            birthDayView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "birthdate field is empty\n";
        } if (birthMonth.length() == 0) {
            birthMonthView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "birthdate field is empty\n";
        } if (birthYear.length() == 0) {
            birthYearView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "birthdate field is empty\n";
        } if (city.length() == 0){
            cityView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "city field is empty\n";
        } if (country.length() == 0) {
            countryView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "country field is empty\n";
        } if (bio.length() == 0) {
            bioView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "bio field is empty\n";
        }
        if (bio.length() > 150) {
            bioView.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "your bio is too long, must be under 150 characters\n";
        }

        if(formError){
            errorMessage.setVisibility(View.VISIBLE);
            Toast tst = Toast.makeText(this, errors, Toast.LENGTH_SHORT);
            tst.show();
        }  else {


            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date initialDate = new Date(2000,1,1);
            String defaultDate = dateFormat.format(initialDate);

            String dh = String.valueOf(new Date().getTime());
            BTUser newUser = new BTUser();
            newUser.setUserName(username);
            newUser.setEmail(emailText);
            newUser.setPassword(password);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setBirthday(birthMonth + "/" + birthDay + "/" + birthYear);
            newUser.setPronouns(pronounText);
            newUser.setCity(city);
            newUser.setCountry(country);
            newUser.setBio(bio);
            newUser.setId(dh);
            newUser.setAffStreakCount(0);
            newUser.setMedStreakCount(0);
            newUser.setYogaStreakCount(0);
            newUser.setJournStreakCount(0);
            newUser.setLastAffActivityDate(defaultDate);
            newUser.setLastMedActivityDate(defaultDate);
            newUser.setLastYogaActivityDate(defaultDate);
            newUser.setLastJournActivityDate(defaultDate);


            Date date = new Date();
            newUser.setJoined(dateFormat.format(date));
            databaseReference.child("users").child(username).child("info").setValue(newUser);
            Intent intent = new Intent(BTUserRegistration.this, BTUserLogin.class);
            String msg = "User " + newUser.getUserName() + " successfully registered!";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }}}



