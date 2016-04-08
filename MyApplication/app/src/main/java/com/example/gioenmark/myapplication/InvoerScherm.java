package com.example.gioenmark.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    Course[] courses;
    List<Course> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoer_scherm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hardcoded From JSON" + courses[0].grade, Snackbar.LENGTH_LONG)
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
        navigationView.getMenu().getItem(1).setChecked(true);

        requestSubjects();
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
            Intent intent = new Intent(this, Persoonsscherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void showButtons(View view) {
        Intent intent = new Intent(this, InvoerScherm.class);
        startActivity(intent);
    }

    public void grabJsonFirstPeriod(View view) {
        int getLengte = subjects.size();
        int periode = 1;

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);
    }

    public void grabJsonSecondPeriod(View view) {

        int getLengte = subjects.size();
        ;
        int periode = 2;

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);

    }

    public void grabJsonThirthPeriod(View view) {
        int getLengte = subjects.size();
        int periode = 3;

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);
    }

    public void grabJsonFourthPeriod(View view) {
        int getLengte = subjects.size();
        int periode = 4;
//        for(int i = 0; i < getLengte; i++)
//        {
//
//            if(Integer.parseInt(subjects.get(i).period) == 4)
//            {
//                if(start == 0)
//                {
//                    start = i + 1;
//                }
//                lengte +=1;
//            }
//        }

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);


//        ImageView iv = new ImageView(this);
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 40);
//        params.leftMargin = 50;
//        params.topMargin = 60;
//        rl.addView(iv, params);
    }

    private void requestSubjects() {
        Type type = new TypeToken<List<Course>>() {
        }.getType();
        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) {
                        processRequestSucces(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                processRequestError(error);
            }
        }
        );
        VolleyHelper.getInstance(this).addToRequestQueue(request);
        //courses = Course;
    }

    private void processRequestSucces(List<Course> subjects) {

        this.subjects = subjects;
    }

    private void processRequestError(VolleyError error) {
    }


    public void grabPeriod(RelativeLayout rl, int lengte, int periode) {

        int groeyX = 150;
        int groeyY = 30;
        int positie = 0;
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);
        Button b4 = (Button) findViewById(R.id.button4);
        Button b5 = (Button) findViewById(R.id.button5);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.VISIBLE);
        for (int i = 0; i < (lengte + 1); i++) {
            if (i == 0) {
                groeyX = 135;
                for (int j = 0; j < 4; j++) {
                    makeLayout(rl, groeyX, groeyY, i, j, positie);
                }
            } else {
                groeyX = 150;
                if (Integer.parseInt(subjects.get(i - 1).period) != periode) {

                } else {
                    positie++;
                    for (int j = 0; j < 5; j++) {
                        makeLayout(rl, groeyX, groeyY, i, j, positie);
                    }
                }
            }


        }
    }

    public void makeLayout(RelativeLayout rl, int groeyX, int groeyY, int i, int j, int positie) {

        int x = 5;
        int y = 5;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(x + (groeyX * j), y + (groeyY * positie), 0, 0);

        TextView textView = new TextView(this);
        Button button = new Button(this);

        textView.setLayoutParams(layoutParams);
        button.setLayoutParams(layoutParams);

        if (i == 0) {
            if (j == 0) {
                textView.setText("Name");
            } else if (j == 1) {
                textView.setText("Ects");
            } else if (j == 2) {
                textView.setText("Grade");
            } else {
                textView.setText("Period");
            }
        } else {
            if (j == 0) {
                textView.setText(subjects.get(i - 1).name);
            } else if (j == 1) {
                textView.setText(subjects.get(i - 1).ects);
            } else if (j == 2) {
                textView.setText(subjects.get(i - 1).grade);
            } else {
                textView.setText(subjects.get(i - 1).period);
            }
        }
        if (j == 4) {
            rl.addView(button);
        } else {
            rl.addView(textView);
        }


    }
}
