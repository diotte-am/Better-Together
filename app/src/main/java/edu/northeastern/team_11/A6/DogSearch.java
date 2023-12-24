package edu.northeastern.team_11.A6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.team_11.R;

public class DogSearch extends AppCompatActivity {
    ImageView dice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_search);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        dice = findViewById(R.id.diceButton);
        dice.setImageResource(R.drawable.dice_6);

    }

    @Override
    public void onResume() {
        super.onResume();
        dice.setImageResource(R.drawable.dice_6);
    }



    public void startRandomDogActivity(View view) {
        dice.setImageResource(R.drawable.dice_4);
        Intent intent = new Intent(DogSearch.this, RandomDogActivity.class);
        intent.putExtra("SearchType", "Random");
        startActivity(intent);
    }


    public void startSearchDogActivity(View view) {
        EditText query = findViewById(R.id.dog_breed);
        String q = query.getText().toString();

        Intent intent = new Intent(DogSearch.this, RandomDogActivity.class);
        intent.putExtra("SearchType", "Query");
        intent.putExtra("Parameter", q);
        startActivity(intent);
    }
}
