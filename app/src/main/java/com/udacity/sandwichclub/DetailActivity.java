package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
    public static final String DefaultValue="N/A";
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
 // variables declaration
    ImageView swImage;
    TextView swName;
    TextView swAlias;
    TextView swOrigin;
    TextView swIngredients;
    TextView swDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       //variables to layout map
        swImage = findViewById(R.id.image_iv);
        swName = findViewById(R.id.name_tv);
        swAlias = findViewById(R.id.also_known_tv);
        swOrigin = findViewById(R.id.origin_tv);
        swIngredients= findViewById(R.id.ingredients_tv);
        swDescription = findViewById(R.id.description_tv);


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

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

      //Image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(swImage);
       //Name
         swName.setText(sandwich.getMainName());
        //Alsoknownas
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder aliasList = new StringBuilder();
            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                if(i==sandwich.getAlsoKnownAs().size()-1){
                    aliasList.append(sandwich.getAlsoKnownAs().get(i));
                }
                else{
                    aliasList.append(sandwich.getAlsoKnownAs().get(i));
                    aliasList.append(",");
                }

            }
            swAlias.setText(aliasList.toString());
        } else {
            swAlias.setText(DefaultValue);
        }
        //Origin
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            swOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            swOrigin.setText(DefaultValue);
        }
       //Ingredients
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder swIngList = new StringBuilder();
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                if(i==sandwich.getIngredients().size()-1){
                    swIngList.append(sandwich.getIngredients().get(i));
                }
                else{
                    swIngList.append(sandwich.getIngredients().get(i));
                    swIngList.append(",");
                }

            }
            swIngredients.setText(swIngList.toString());
        }
        //Description
        swDescription.setText(sandwich.getDescription());



    }
}
