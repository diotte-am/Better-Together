package edu.northeastern.team_11.BetterTogether;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.team_11.BetterTogether.Tabs.BTUser;
import edu.northeastern.team_11.R;

public class BTProfileEdit extends AppCompatActivity {
    private TextView errorMessage;
    private EditText email, password, passwordConfirm, firstName, lastName, birthDay, birthYear, birthMonth, pronouns, city, country, bio;
    private BTUser currentUser, oldUserData;
    private String month, day, year, newPassword, newPasswordConfirm;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String userId;
    FirebaseUser user;

    private TextWatcher makeTextWatcher(BTUserFields userField){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch(userField) {
                    case EMAIL:
                        currentUser.setEmail(email.getText().toString());
                        break;
                    case PASSWORD:
                        newPassword = password.getText().toString();
                        break;
                    case PASSWORD_CONFIRM:
                        newPasswordConfirm = passwordConfirm.getText().toString();
                        break;
                    case FIRST_NAME:
                        currentUser.setFirstName(firstName.getText().toString());
                        break;
                    case LAST_NAME:
                        currentUser.setLastName(lastName.getText().toString());
                        break;
                    case PRONOUNS:
                        currentUser.setPronouns(pronouns.getText().toString());
                        break;
                    case CITY:
                        currentUser.setCity(city.getText().toString());
                        break;
                    case COUNTRY:
                        currentUser.setCountry(country.getText().toString());
                        break;
                    case BIO:
                        currentUser.setBio(bio.getText().toString());
                        break;
                    case BIRTH_DAY:
                        day = birthDay.getText().toString();
                        break;
                    case BIRTH_MONTH:
                        month = birthMonth.getText().toString();
                        break;
                    case BIRTH_YEAR:
                        year = birthYear.getText().toString();
                        break;
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_activity_edit_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        newPassword = "";
        newPasswordConfirm = "";

        errorMessage = (TextView) findViewById(R.id.editError);
        errorMessage.setVisibility(View.INVISIBLE);
        currentUser = (BTUser) getIntent().getSerializableExtra("currentUser");

        email = findViewById(R.id.editEmail);
        email.setText(currentUser.getEmail());
        email.addTextChangedListener(makeTextWatcher(BTUserFields.EMAIL));

        firstName = findViewById(R.id.editFirstName);
        firstName.setText(currentUser.getFirstName());
        firstName.addTextChangedListener(makeTextWatcher(BTUserFields.FIRST_NAME));

        lastName = findViewById(R.id.editLastName);
        lastName.setText(currentUser.getLastName());
        lastName.addTextChangedListener(makeTextWatcher(BTUserFields.LAST_NAME));

        pronouns = findViewById(R.id.editPronouns);
        pronouns.setText(currentUser.getPronouns());
        pronouns.addTextChangedListener(makeTextWatcher(BTUserFields.PRONOUNS));

        city = findViewById(R.id.editCity);
        city.setText(currentUser.getCity());
        city.addTextChangedListener(makeTextWatcher(BTUserFields.CITY));

        country = findViewById((R.id.editCountry));
        country.setText(currentUser.getCountry());
        country.addTextChangedListener(makeTextWatcher(BTUserFields.COUNTRY));

        bio = findViewById(R.id.editBio);
        bio.setText(currentUser.getBio());
        bio.addTextChangedListener(makeTextWatcher(BTUserFields.BIO));

        password = findViewById(R.id.editPassword);
        password.addTextChangedListener(makeTextWatcher(BTUserFields.PASSWORD));

        passwordConfirm = findViewById(R.id.editConfirmPassword);
        passwordConfirm.addTextChangedListener(makeTextWatcher(BTUserFields.PASSWORD_CONFIRM));

        birthDay = findViewById(R.id.editBirthdayDay);
        birthDay.setText(currentUser.birthDay());
        birthDay.addTextChangedListener(makeTextWatcher(BTUserFields.BIRTH_DAY));

        birthMonth = findViewById(R.id.editBirthdayMonth);
        birthMonth.setText(currentUser.birthMonth());
        birthMonth.addTextChangedListener(makeTextWatcher(BTUserFields.BIRTH_MONTH));

        birthYear = findViewById(R.id.editBirthdayYear);
        birthYear.setText(currentUser.birthYear());
        birthYear.addTextChangedListener(makeTextWatcher(BTUserFields.BIRTH_YEAR));
        oldUserData = new BTUser(currentUser);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("/BT");
        user = mAuth.getCurrentUser();
    }

    public void submitChanges(View view){
        Boolean formError = false;
        String errors = "";
        if(currentUser.getEmail().length() == 0){
            email.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "email field is empty\n";
        } if (currentUser.getFirstName().length() == 0) {
            firstName.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "first name field is empty\n";
        } if(currentUser.getLastName().length() == 0) {
            lastName.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "last name field is empty\n";
        } if (currentUser.getCity().length() == 0){
            city.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "city field is empty\n";
        } if (currentUser.getCountry().length() == 0) {
            country.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "country field is empty\n";
        } if (currentUser.getBio().length() == 0) {
            bio.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "bio field is empty\n";
        }
        if (currentUser.getBio().length() > 150) {
            bio.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "your bio is too long, must be under 150 characters\n";
        } if(month!=null && day!=null && year!=null){
            String bday = month + "/" + day + "/" + year;
            currentUser.setBirthday(bday);
        } if(newPasswordConfirm.length() > 0 && newPassword.length() > 0 ){
            if(newPassword.equals(newPasswordConfirm)){
                currentUser.setPassword(newPassword);
            } else {
                formError = true;
                errors += "two password fields must match!\n";
                password.setBackgroundResource(R.drawable.rounded_registration_5_error);
                passwordConfirm.setBackgroundResource(R.drawable.rounded_registration_5_error);
            }
        }
    }

    public void savePage(View view){
        Boolean formError = false;
        String errors = "";
        if(currentUser.getEmail().length() == 0){

            email.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "email field is empty\n";
        } if (currentUser.getFirstName().length() == 0) {
            firstName.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "first name field is empty\n";
        }

        if(currentUser.getLastName().length() == 0) {
            lastName.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "last name field is empty\n";
        }

        if (currentUser.getCity().length() == 0){
            city.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "city field is empty\n";
        }
        if (currentUser.getCountry().length() == 0) {
            country.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "country field is empty\n";
        }

        if (currentUser.getBio().length() == 0) {
            bio.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "bio field is empty\n";
        }

        if (currentUser.getBio().length() > 150) {
            bio.setBackgroundResource(R.drawable.rounded_registration_5_error);
            formError = true;
            errors += "your bio is too long, must be under 150 characters\n";
        }

        if(month!=null && day!=null && year!=null) {
            String bday = month + "/" + day + "/" + year;
            currentUser.setBirthday(bday);
        }

        if(newPasswordConfirm != "" && newPassword != "" ){
            if(newPassword.equals(newPasswordConfirm)){
                currentUser.setPassword(newPassword);
            } else {

                formError = true;
                errors += "two password fields must match!\n";
                password.setBackgroundResource(R.drawable.rounded_registration_5_error);
                passwordConfirm.setBackgroundResource(R.drawable.rounded_registration_5_error);
            }
        }
        if (formError) {
            errorMessage.setText(errorMessage.getText().toString() + "\n" + errors);
            errorMessage.setVisibility(View.VISIBLE);
            Toast tst = Toast.makeText(this, "Update failed, see errors above!", Toast.LENGTH_SHORT);
            tst.show();
            return;
        }
        if(!currentUser.equals(oldUserData)) {
            databaseReference.child("users").child(currentUser.getUserName()).child("info").setValue(currentUser);
        }
        Intent intent = new Intent(BTProfileEdit.this, BTLandingPage.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}
