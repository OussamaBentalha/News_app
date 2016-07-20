package com.esgi.android.news.client.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.esgi.android.news.R;
import com.esgi.android.news.client.fragment.ItemDetailFragment;
import com.esgi.android.news.metier.model.Favorite;
import com.esgi.android.news.physique.db.dao.FavoriteDAO;

public class ItemDetailActivity extends AppCompatActivity {

    public int userIntId;
    private int itemIntId;
    private Favorite favorite;
    private boolean FAVORITE_TAG = false;
    private FloatingActionButton fab;

    public static final String ARG_ITEM_URL = "itemUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = getIntent().getExtras();

            String itemId = arguments.getString(ItemDetailFragment.ARG_ITEM_ID);

            //USER & ITEM ID FOR ADD TO FAVORITE
            itemIntId = Integer.parseInt(itemId);
            userIntId = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getInt(getString(R.string.user_id_key), 0);
            favorite = new Favorite(userIntId, itemIntId);

            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, itemId);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            configureFavoriteProperty();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteDAO favDAO = new FavoriteDAO(getApplicationContext());
                    favDAO.open();
                    if(FAVORITE_TAG){
                        favDAO.remove(favorite);
                        FAVORITE_TAG = false;
                    } else {
                        favDAO.add(favorite);
                        FAVORITE_TAG = true;
                    }
                    favDAO.close();
                    configureFavoriteProperty();
                }
            });

            //LAUNCH ITEM DETAIL FRAGMENT
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    //CONFIGURE FAVORITE ICON BUTTON
    public void configureFavoriteProperty(){
        FavoriteDAO favoriteDAO = new FavoriteDAO(getApplicationContext());
        favoriteDAO.open();
        FAVORITE_TAG = favoriteDAO.isFavorite(favorite);
        if(FAVORITE_TAG){
            fab.setImageResource(R.drawable.ic_thumb_down_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_thumb_up_white_24dp);
        }
    }

    public void launchItemWebview(String itemUrl){
        Intent intent = new Intent(this, ItemWebview.class);
        intent.putExtra(ARG_ITEM_URL, itemUrl);
        startActivity(intent);
    }

}
