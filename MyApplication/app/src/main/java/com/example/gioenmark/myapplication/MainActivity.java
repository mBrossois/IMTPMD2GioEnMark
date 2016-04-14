package com.example.gioenmark.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.example.gioenmark.myapplication.Models.Course;
import com.example.gioenmark.myapplication.database.DatabaseHelper;
import com.example.gioenmark.myapplication.database.Databaseinfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Course> subjects;
    DatabaseHelper dbHelper;
    ContentValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OverzichtScherm overzicht = new OverzichtScherm();

        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences2.getString("Name", "");
        String jaargang = preferences2.getString("Jaargangtext", "");
        String periode = preferences2.getString("Periodetext", "");
        String studierichting = preferences2.getString("Studierichtingtext", "");
        int jaar = preferences2.getInt("Jaargang", 0);

        int richting = preferences2.getInt("Periode", 0);

        int period = preferences2.getInt("Studierichting", 0);
        calcEcts();
        overzicht.bepaalVoortgang(name, jaar, richting, period, subjects);
        String ectTekst = overzicht.getEct();
        String overgaanTekst = overzicht.getOver();
        String voortgangTekst = overzicht.getVoort();


        TextView textViewToChange = (TextView) findViewById(R.id.textView2);
        textViewToChange.setText("Welcome " + name);

        TextView textViewToChange2 = (TextView) findViewById(R.id.textView3);
        textViewToChange2.setText(jaargang);

        TextView textViewToChange3 = (TextView) findViewById(R.id.textView4);
        textViewToChange3.setText(periode);

        TextView textViewToChange4 = (TextView) findViewById(R.id.textView5);
        textViewToChange4.setText(studierichting);

        TextView textView12 = (TextView) findViewById(R.id.textView12);
        textView12.setText(ectTekst);

        TextView textView14 = (TextView) findViewById(R.id.textView14);
        textView14.setText(overgaanTekst);

        TextView textView15 = (TextView) findViewById(R.id.textView15);
        textView15.setText(voortgangTekst);


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
        navigationView.getMenu().getItem(0).setChecked(true);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, InvoerScherm.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, DetailsScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, OverzichtScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, Persoonsscherm.class);
            startActivity(intent);

/*
        } else if (id == R.id.nav_send) {
*/

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    public void calcEcts()
    {
        subjects = new ArrayList<Course>();
        dbHelper = DatabaseHelper.getHelper(this);
        values = new ContentValues();
        Cursor rs = dbHelper.query(Databaseinfo.CourseTables.COURSE, new String[]{"*"}, "gehaald like 'V'", null, null, null, null);
        rs.moveToFirst();


        do {
            String name = rs.getString(rs.getColumnIndex("name"));
            String ects = rs.getString(rs.getColumnIndex("ects"));
            String grade = rs.getString(rs.getColumnIndex("grade"));
            String period = rs.getString(rs.getColumnIndex("period"));
            Course course = new Course(name, ects, grade,period);
            subjects.add(course);
        }
        while(rs.moveToNext());
    }
}
