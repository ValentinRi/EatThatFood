package fr.intech.s5.tp.eatthatfood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import fr.intech.s5.tp.eatthatfood.model.ListRecipe;
import fr.intech.s5.tp.eatthatfood.model.Recipes;
import fr.intech.s5.tp.eatthatfood.service.MyIntentService;
import fr.intech.s5.tp.eatthatfood.utils.NetworkHelper;
import fr.intech.s5.tp.eatthatfood.utils.RequestPackage;

public class MainActivity extends AppCompatActivity {

    private static final String BACKEND_URL = "http://10.8.110.223:8080/recipe/info/v1";
    private boolean networkOk;

    Recipes mRecipe;
    RecyclerView mRecyclerView;
    RecipeAdapter mRecipeAdapter;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Recipes Recipe = (Recipes) intent
                    .getParcelableExtra(MyIntentService.MY_INTENT_SERVICE_MESSAGE);

            Toast.makeText(
                    MainActivity.this,
                    "Reception d'un item du service",
                    Toast.LENGTH_SHORT
            ).show();

            mRecipe = Recipe;
            displayDataItems();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvRecipe);

        networkOk = NetworkHelper.hasNetworkAccess(this);
            if( networkOk ) {
                RequestPackage requestPackage = new RequestPackage();
                requestPackage.setEndPoint(BACKEND_URL);

            Intent intent = new Intent(this, MyIntentService.class);
            intent.putExtra(MyIntentService.REQUEST_PACKAGE, requestPackage);
            startService(intent);
        } else {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        }

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(
                        broadcastReceiver,
                        new IntentFilter(MyIntentService.MY_INTENT_SERVICE_ID)
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(broadcastReceiver);
    }

    private void displayDataItems() {
        if( mRecipe == null ) return;

        List<ListRecipe> listRecipe = mRecipe.getListRecipe();
        if( listRecipe == null || listRecipe.isEmpty() ) return;

        mRecipeAdapter = new RecipeAdapter(this, listRecipe);
        mRecyclerView.setAdapter( mRecipeAdapter );
    }
}