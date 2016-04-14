package com.example.gioenmark.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gioenmark.myapplication.database.DatabaseHelper;
import com.example.gioenmark.myapplication.database.Databaseinfo;

public class DetailsScherm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Cursor rs;
    DatabaseHelper dbHelper;
    ContentValues values;
    String chosenName;
    String names[] = {"IIPMEDT", "IIPSEN", "IIPBIT", "IIPFIT"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_scherm);
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
        navigationView.getMenu().getItem(1).setChecked(true);

        Intent intent = getIntent();
        chosenName = intent.getStringExtra("chosenName");
//        TextView tView = (TextView) findViewById(R.id.textViewId);
//        String theId = String.valueOf(chosenId);
//        tView.setText(chosenName);
        grabPeriod();

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
        getMenuInflater().inflate(R.menu.details_scherm, menu);
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, InvoerScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

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

    public void grabPeriod() {

        TextView v0 = (TextView) findViewById(R.id.textView10);
        TextView v1 = (TextView) findViewById(R.id.textView11);
        EditText v2 = (EditText) findViewById(R.id.editText2);
        TextView v3 = (TextView) findViewById(R.id.textView13);
        CheckBox v4 = (CheckBox) findViewById(R.id.checkBox);

        dbHelper = DatabaseHelper.getHelper(this);
        values = new ContentValues();
        rs = dbHelper.query(Databaseinfo.CourseTables.COURSE, new String[]{"*"}, "name like '" + chosenName + "'", null, null, null, null);
//
        rs.moveToFirst();   // Skip : de lege elementen vooraan de rij.
// Maar : de rij kan nog steeds leeg zijn
// Hoe  : lossen we dit op ??

// Haalt de name uit de resultset
//        String name = (String) rs.getString(rs.getColumnIndex("name"));
//        String data;

            SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
            int studierichting = preferences2.getInt("Studierichting", 0);
            String name = rs.getString(rs.getColumnIndex("name"));
            String ects = rs.getString(rs.getColumnIndex("ects"));
            String grade = rs.getString(rs.getColumnIndex("grade"));
            String period = rs.getString(rs.getColumnIndex("period"));
            String gehaald = rs.getString(rs.getColumnIndex("gehaald"));
            if(name.equals("IIPXXXX"))
            {
                name = names[studierichting - 1];
            }
            v0.setText(name);
            v1.setText(ects);
            v2.setText(grade);
            v3.setText(period);

//            String v = "V";
            if(gehaald.equals("V"))
            {
                v4.setChecked(!v4.isChecked());
//                Log.i("Hier", gehaald);
            }
            else
            {
                v4.setChecked(v4.isChecked());
            }
    }
    public void toOverzicht(View v)
    {
        Intent intent = new Intent(this, InvoerScherm.class);
        startActivity(intent);
    }
    public void changeDatabase(View v)
    {
        rs = dbHelper.query(Databaseinfo.CourseTables.COURSE, new String[]{"*"}, "name like '" + chosenName + "'", null, null, null, null);
        rs.moveToFirst();   // Skip : de lege elementen vooraan de rij.
        int pk = rs.getInt(rs.getColumnIndex("_id"));
        String name = rs.getString(rs.getColumnIndex("name"));
        String ects = rs.getString(rs.getColumnIndex("ects"));
        String period = rs.getString(rs.getColumnIndex("period"));
        EditText v2 = (EditText) findViewById(R.id.editText2);
        CheckBox v4 = (CheckBox) findViewById(R.id.checkBox);
        String theGrade = v2.getText().toString();
        String hoi = String.valueOf(pk);
        String gehaald;
        if(v4.isChecked())
        {
            gehaald = "V";
//            Log.i("Hier",gehaald);
        }
        else
        {
            gehaald = "O";
//            Log.i("Hier",gehaald);
        }
        values.put(BaseColumns._ID, pk);
        values.put(Databaseinfo.CourseColumn.NAME, name);
        values.put(Databaseinfo.CourseColumn.ECTS, ects);
        values.put(Databaseinfo.CourseColumn.GRADE, theGrade);
        values.put(Databaseinfo.CourseColumn.GEHAALD, gehaald);
        values.put(Databaseinfo.CourseColumn.PERIOD, period);
//        dbHelper.insert(Databaseinfo.CourseTables.COURSE, null, values);
        dbHelper.replace(Databaseinfo.CourseTables.COURSE, null, values);
        Snackbar.make(this.findViewById(android.R.id.content), "De database is aangepast.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
