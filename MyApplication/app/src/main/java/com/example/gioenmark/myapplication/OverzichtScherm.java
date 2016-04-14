package com.example.gioenmark.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gioenmark.myapplication.Models.Course;
import com.example.gioenmark.myapplication.database.DatabaseHelper;
import com.example.gioenmark.myapplication.database.Databaseinfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class OverzichtScherm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Course[] courses;
    ArrayList<Course> subjects;
    DatabaseHelper dbHelper;
    ContentValues values;

    String name;
    int jaargang;
    int periode;
    int studierichting;
    int welkeKleur = 0;
    String overgaanTekst= "";
    String ec= "";
    String voort= "";
    String over= "";
    String keuze= "";

    String media[] = {"IWDR" , "IIPMEDT"};
    String soft[] = {"IMUML" , "IIPSEN"};
    String forens[] = {"IFIT" , "IIPFIT"};
    String bdm[] = {"IIBUI" , "IIPBIT"};
    String studieRichting[] = {"Mediatechnologie" , "Software-engineering" , "Business DataManagement" , "Forencische ICT"};
    int ects = 0;
    public static int ALLECTS = 60;
    //    int chosenId= 0;
    Cursor rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht_scherm);
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
        navigationView.getMenu().getItem(2).setChecked(true);
        bepaalVoortgang();
        vulTekstVelden();
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
        getMenuInflater().inflate(R.menu.overzicht_scherm, menu);
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
        }  else if (id == R.id.nav_gallery) {
        Intent intent = new Intent(this, InvoerScherm.class);
        startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, DetailsScherm.class);
            startActivity(intent);
        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, Persoonsscherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void bepaalVoortgang() {
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);

        name = preferences2.getString("Name", "");


        jaargang = preferences2.getInt("Jaargang", 0);

        studierichting = preferences2.getInt("Periode", 0);

        periode = preferences2.getInt("Studierichting", 0);
        getEcts();
        calcEcts();
        welkeKleur = kiesLicht();
        String voortgangTekst = studieAdvies();
        String keuzeTekst = "";
        if (studierichting == 0) {
            keuzeTekst = "Vul in het persoonsscherm de voorkeur voor je specialisatie in!";
        } else if (studierichting == 1) {
            keuzeTekst = richtingAdvies(media);
        } else if (studierichting == 2) {
            keuzeTekst = richtingAdvies(soft);
        } else if (studierichting == 3) {
            keuzeTekst = richtingAdvies(bdm);
        } else if (studierichting == 4) {
            keuzeTekst = richtingAdvies(forens);
        }
        //        SharedPreferences.Editor editor = preferences2.edit();
//        editor.putString("ectsTekst","Je hebt " + ects + " van de " + ALLECTS + " behaald.");
//        editor.putString("overgaantekst",overgaanTekst);
//        editor.putString("voortgangTekst", voortgangTekst);
        ec = "Je hebt " + ects + " van de " + ALLECTS + " behaald.";
        over = overgaanTekst;
        voort = voortgangTekst;
        keuze = keuzeTekst;
    }

    public void bepaalVoortgang(String name, int jaargang, int studierichting, int periode, ArrayList<Course> subjects)
    {
//        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);

        this.name = name;


        this.jaargang = jaargang;

        this.studierichting = studierichting;

        this.periode = periode;

        this.subjects = subjects;

        goStart();

    }
    public void goStart()
    {

        calcEcts();
        welkeKleur = kiesLicht();
        String voortgangTekst = studieAdvies();
        String keuzeTekst = "";
        if(studierichting == 0)
        {
            keuzeTekst = "Vul in het persoonsscherm de voorkeur voor je specialisatie in!";
        }
        else if(studierichting == 1)
        {
            keuzeTekst = richtingAdvies(media);
        }
        else if(studierichting == 2)
        {
            keuzeTekst = richtingAdvies(soft);
        }
        else if(studierichting == 3)
        {
            keuzeTekst = richtingAdvies(bdm);
        }
        else if(studierichting == 4)
        {
            keuzeTekst = richtingAdvies(forens);
        }

//        SharedPreferences.Editor editor = preferences2.edit();
//        editor.putString("ectsTekst","Je hebt " + ects + " van de " + ALLECTS + " behaald.");
//        editor.putString("overgaantekst",overgaanTekst);
//        editor.putString("voortgangTekst", voortgangTekst);
        ec = "Je hebt " + ects + " van de " + ALLECTS + " behaald.";
        over= overgaanTekst;
        voort = voortgangTekst;
        keuze = keuzeTekst;
    }
    public void getEcts()
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
//        Log.i("naam", subjects.get(0).grade);
//        String name = subjects.get(1).name;
    }
    public void calcEcts()
    {

        int lengteArray = subjects.size();
        for(int i = 0; i < lengteArray; i++ )
        {
            ects += Integer.parseInt(subjects.get(i).ects);


        }

    }

    public int kiesLicht()
    {

        int welkeKleur = 0;

        if(periode == 1 )
        {
            welkeKleur = rekenP(13, 10, -1);
        }
        if(periode == 2 )
        {
            welkeKleur = rekenP(34, 27, 13);
        }
        if(periode == 3 )
        {
            welkeKleur = rekenP(43, 33 ,22);
        }
        if(periode == 4 )
        {
            welkeKleur = rekenP(60, 50, 40);
        }
//        Log.i("KLEUR", "coolo" + welkeKleur);
        return welkeKleur;
    }

    public int rekenP(int ectsGroen, int ectsOranje, int ectsRood)
    {
        int deKleur = 0;
        if(ects < ectsRood)
        {
            deKleur = 0;
            if(periode == 4)
            {
                overgaanTekst = "Je hebt niet genoeg punten om het jaar te halen";
            }
            else {
                overgaanTekst = "Let op om over te gaan zijn 40 punten nodig, om de specialisatie te halen 50 en je Propedeuse 60.";
            }
        }
        else if(ects < ectsOranje)
        {
            deKleur = 1;
            overgaanTekst = "Let op om over te gaan zijn 40 punten nodig, om de specialisatie te halen 50 en je Propedeuse 60.";

        }
        else
        {
            overgaanTekst = "Let op om over te gaan zijn 40 punten nodig, om de specialisatie te halen 50 en je Propedeuse 60.";

            deKleur = 2;
        }
        return deKleur;
    }

    public String studieAdvies()
    {
        String kernvakTekst= "";
        int aantal = 0;
        for(int i = 0; i < subjects.size() ; i++) {
            if (periode == 1 && subjects.get(i).name.equals("IOPR1")) {
                aantal++;
            }
            if (periode == 3 && subjects.get(i).name.equals( "IOPR2")) {
                aantal++;
            }
            if (periode == 2 && subjects.get(i).name.equals( "INET")) {
                aantal++;
            }
            if (periode == 2 && subjects.get(i).name.equals("IRDB")) {
                aantal++;
            }
        }
        Log.i("kut","RAHH" + studierichting);
        if(periode == 1 || periode == 2)
        {
            kernvakTekst = "Let op, om over te gaan moeten alle kernvakken gehaald zijn. Deze zijn IOPR1, IOPR2, INET en IRDB";
        }
        else if(periode > 2 && aantal < 2)
        {
            kernvakTekst = "Je hebt te weinig kernvakken gehaald om over te gaan";
        }
        else
        {
            kernvakTekst = "Je hebt genoeg kernvakken gehaald!";
        }
        return kernvakTekst;
    }

    public String richtingAdvies(String vakken[])
    {
        String kernvakTekst= "";
        int aantal = 0;
        int lengte = subjects.size();
        for(int i = 0; i < lengte; i++) {
            if (subjects.get(i).name.equals(vakken[0])) {
                aantal++;
            }
            if ( subjects.get(i).name.equals(vakken[1])) {
                aantal++;
            }
        }
        if(periode == 1)
        {
            kernvakTekst = "Let op voor " + studieRichting[studierichting -1] + " moet je de volgende vakken wel halen: " + vakken[0]+ " en " + vakken[1];
        }
        else if(periode > 3 && aantal < 2)
        {
            kernvakTekst = "Let op voor " + studieRichting[studierichting - 1] + " moet je de volgende vakken wel halen: " + vakken[0] +" en " + vakken[1];
            if(welkeKleur == 2)
            {
                welkeKleur = 1;
            }
        }
        else if(aantal == 2)
        {
            kernvakTekst = "Je mag" + studieRichting[studierichting] +" volgen!";
        }
        return kernvakTekst;
    }

    public void vulTekstVelden()
    {
        TextView t0 = (TextView) findViewById(R.id.textViewAantalECTS);
        TextView t1 = (TextView) findViewById(R.id.textViewBehaaldPunten);
        TextView t2 = (TextView) findViewById(R.id.textViewBehaaldKernvakken);
        TextView t3 = (TextView) findViewById(R.id.textViewBehaaldSpecialisatieVakken);

        t0.setText("Je hebt " + ects + " van de " + ALLECTS + " behaald.");
        t1.setText(overgaanTekst);
        t2.setText(voort);
        t3.setText(keuze);

        ImageView im0 = (ImageView)findViewById(R.id.imageView4);
        ImageView im1 = (ImageView)findViewById(R.id.imageView5);
        ImageView im2 = (ImageView)findViewById(R.id.imageView6);

        if(welkeKleur == 0)
        {
            im0.setVisibility(im0.GONE);
            im1.setVisibility(im1.GONE);
            im2.setVisibility(im2.VISIBLE);
        }
        else if(welkeKleur == 1)
        {
            im0.setVisibility(im0.GONE);
            im1.setVisibility(im1.VISIBLE);
            im2.setVisibility(im2.GONE);
        }
        else if(welkeKleur == 2)
        {
            im0.setVisibility(im0.VISIBLE);
            im1.setVisibility(im1.GONE);
            im2.setVisibility(im2.GONE);
        }

    }
    public String getEct()
    {
        return ec;
    }
    public String getVoort()
    {
        return voort;
    }
    public String getOver()
    {
        return over;
    }

}
