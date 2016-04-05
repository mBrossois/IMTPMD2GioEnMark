package com.example.gioenmark.myapplication;

import android.content.Intent;
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
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gioenmark.myapplication.GSON.GsonRequest;
import com.example.gioenmark.myapplication.Models.Course;
import com.example.gioenmark.myapplication.VOLLEYHELPER.VolleyHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class InvoerScherm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //String courses;
     Course[] courses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoer_scherm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String json = "[{name:'Progr.Mob.Devs', ects:3, code:’IMTPMD’, grade:6}," +
                "{name: 'MT PROJECT', ects: 6, code: ‘IPOMED3’, grade:6}]";

        Gson gson = new Gson();		// Dependency in gradle - see next slide

        courses = gson.fromJson(json, Course[].class);	// CONVERT JSON


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hardcoded From JSON"+courses[0].grade, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Snackbar.make(this.findViewById(android.R.id.content), "Hardcoded From JSON"+courses[0].ects, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setChecked(true);
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
        getMenuInflater().inflate(R.menu.invoer_scherm, menu);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, DetailsScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, OverzichtScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void grabJson(View view)
    {
        requestSubjects();
    }

    private void requestSubjects(){
        Type type = new TypeToken<List<Course>>(){}.getType();
        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) { processRequestSucces(response); }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){ processRequestError(error);     }
        }
        );
        VolleyHelper.getInstance(this).addToRequestQueue(request);
//        courses = Course;
    }

    private void processRequestSucces(List<Course> subjects ){   }

    private void processRequestError(VolleyError error){  }

}
