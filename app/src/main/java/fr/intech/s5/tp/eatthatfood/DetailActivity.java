package fr.intech.s5.tp.eatthatfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import fr.intech.s5.tp.eatthatfood.model.ListRecipe;
import fr.intech.s5.tp.eatthatfood.service.MyIntentService;
import fr.intech.s5.tp.eatthatfood.utils.RequestPackage;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by vprig on 02/10/2017.
 */

public class DetailActivity extends AppCompatActivity {
    private String RENT_URL = "http://www.marmiton.org/";

    private ListRecipe recipe;

    private TextView tvName, tvDescription, tvPrice;
    private ImageView ivImage;
    private EditText etFrom, etTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        recipe = getIntent().getExtras().getParcelable(RecipeAdapter.ITEM_KEY);
        if (recipe == null) throw new AssertionError("Null data etienne received!");

        tvName = (TextView) findViewById(R.id.tvItemName);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        tvName.setText("Recipe " + recipe.getRecipeName());
        tvDescription.setText(recipe.getDescription());

        try {
            Picasso.with(this).load(RecipeAdapter.PHOTOS_BASE_URL + recipe.getImage()).resizeByMaxSide().into(ivImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rentClickHandler(View view) {
        if (recipe == null) throw new AssertionError("Recipe can't be null!");

        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setEndPoint(RENT_URL);
        requestPackage.setMethod("POST");

        Map<String, String> params = new HashMap<>(3);
        params.put("recipeId", "" + recipe.getRecipeId());
        requestPackage.setParams(params);

        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra(MyIntentService.REQUEST_PACKAGE, requestPackage);
        startService(intent);
    }
}
