package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //modify populateUI to accept Sandwich parameter
    private void populateUI(Sandwich s) {

        TextView tv_description = findViewById(R.id.description_tv);
        tv_description.setText(s.getDescription());
        TextView tv_ingredients = findViewById(R.id.ingredients_tv);
        String ingredients2 = s.getIngredients().toString();
        int lengthIngredients = ingredients2.length();
        tv_ingredients.setText(ingredients2.substring(1,lengthIngredients-3));
        TextView tv_alsoknown = findViewById(R.id.also_known_tv);
        String alsoKnownAs2 = s.getAlsoKnownAs().toString();
        int lengthAlsoKnownAs = alsoKnownAs2.length();
        if(lengthAlsoKnownAs == 2) tv_alsoknown.setText("N/A");
        else tv_alsoknown.setText(alsoKnownAs2.substring(1,lengthAlsoKnownAs-3)); //strip the brackets off
        TextView tv_orign = findViewById(R.id.origin_tv);
        tv_orign.setText(s.getPlaceOfOrigin());
    }
}
