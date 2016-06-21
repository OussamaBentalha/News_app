package com.esgi.android.news.client.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
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
import com.esgi.android.news.metier.dao.ItemDAO;
import com.esgi.android.news.metier.dao.UserDAO;
import com.esgi.android.news.metier.data.DownloadTask;
import com.esgi.android.news.metier.enumeration.Newspaper;
import com.esgi.android.news.metier.model.Item;
import com.esgi.android.news.metier.model.User;
import com.esgi.android.news.metier.utils.JSONBodyParser;
import com.esgi.android.news.metier.utils.XmlBodyParser;
import com.esgi.android.news.physique.wb.MyHttpRequest;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

                User user = new User("oth@hotmail.com", "mdp");
                UserDAO userDAO = new UserDAO(getApplicationContext());
                userDAO.open();
                //userDAO.add(user);
                List<User> users = userDAO.getAll();
                userDAO.login(user);

                userDAO.close();


                /*DownloadTask load = new DownloadTask();
                load.setFluxRSS(Newspaper.LEQUIPE);
                load.downloadNews();*/

                /*Item item = new Item("Titre", "Ici la description", "img.jpg", null, "www.item.com");
                ItemDAO itemDAO = new ItemDAO(getApplicationContext());
                itemDAO.open();
                itemDAO.getAll();
                itemDAO.add(item);
                itemDAO.getAll();
                itemDAO.close();*/

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

        if (id == R.id.nav_all) {
            // Handle the camera action
        } else if (id == R.id.nav_eurosport) {
            EurosportFragment fragment = new EurosportFragment();
            fragmentTransaction.add(R.id.fragment_container, fragment, "Eurosport");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_begeek) {

        } else if (id == R.id.nav_clubic) {

        } else if (id == R.id.nav_ont_net) {

        } else if (id == R.id.nav_cnn) {

        } else if (id == R.id.nav_melty) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
