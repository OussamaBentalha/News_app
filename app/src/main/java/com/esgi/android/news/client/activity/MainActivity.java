package com.esgi.android.news.client.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.esgi.android.news.R;
import com.esgi.android.news.client.fragment.EurosportFragment;
import com.esgi.android.news.metier.enumeration.EnumNewspaper;
import com.esgi.android.news.metier.model.IRefreshable;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.metier.service.RSSRequest;
import com.esgi.android.news.metier.utils.Refresher;
import com.esgi.android.news.physique.db.dao.ItemDAO;
import com.esgi.android.news.physique.wb.DownloadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IRefreshable {

    //private Refresher refreshHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view Item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        EurosportFragment fragment = new EurosportFragment();
        Bundle bundle = new Bundle();

        if (id == R.id.nav_all) {
            // Handle the camera action
        } else if (id == R.id.nav_eurosport) {
            Intent intent = new Intent(this, ItemListActivity.class);
            this.startActivity(intent);
            /*bundle.putSerializable(EnumNewspaper.class.getSimpleName(), EnumNewspaper.EUROSPORT);
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment, EnumNewspaper.EUROSPORT.name());
            fragmentTransaction.commit();*/
        } else if (id == R.id.nav_lequipe) {
            bundle.putSerializable(EnumNewspaper.class.getSimpleName(), EnumNewspaper.LEQUIPE);
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment, EnumNewspaper.LEQUIPE.name());
            fragmentTransaction.commit();
        }  else if (id == R.id.nav_deconnexion) {
            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
            editor.putInt(getString(R.string.user_id_key), 0);
            editor.commit();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Item> items = new ArrayList<>();

                DownloadTask load = new DownloadTask();
                for(EnumNewspaper enumNewspaper : EnumNewspaper.values()){
                    load.setFluxRSS(enumNewspaper);
                    items = load.downloadNews();

                    ItemDAO itemDAO = new ItemDAO(getApplicationContext());
                    itemDAO.open();
                    for(Item item : items){
                        itemDAO.add(item);
                    }

                    List<Item> itemsRecup = itemDAO.getAll();
                    itemDAO.close();
                }

            }
        }).start();
        //refreshHolder.startRefresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //refreshHolder.stopRefresh();
    }

    @Override
    public void refresh() {
        //refreshHolder.refreshTask = task.execute(map);
    }

    @Override
    public void cancelRefresh() {

    }
}
