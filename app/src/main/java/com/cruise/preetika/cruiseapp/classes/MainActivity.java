package com.cruise.preetika.cruiseapp.classes;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_activities) {
            SharedPrefManager.setIntPrefVal(MainActivity.this,"act",2);//1 for POC,2 for OBA
            Intent i=new Intent(MainActivity.this,Activity.class);
            i.putExtra("heading",getString(R.string.onBoardActs));
            i.putExtra("one",getString(R.string.onBoardAct1));
            i.putExtra("two",getString(R.string.onBoardAct2));
            i.putExtra("three",getString(R.string.onBoardAct3));
            startActivity(i);

        } else if (id == R.id.nav_book_room) {
            startActivity(new Intent(MainActivity.this, Room_booking_info.class));
        } else if (id == R.id.nav_ports) {
            SharedPrefManager.setIntPrefVal(MainActivity.this,"act",1);//1 for POC,2 for OBA
            Intent i=new Intent(MainActivity.this,Activity.class);
            i.putExtra("heading",getString(R.string.PortsOfCall));
            i.putExtra("one",getString(R.string.POC1));
            i.putExtra("two",getString(R.string.POC2));
            i.putExtra("three",getString(R.string.POC3));
            startActivity(i);

        } else if (id == R.id.nav_services) {
            startActivity(new Intent(MainActivity.this, Services.class));


        }else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            SharedPrefManager.clearPreferences(MainActivity.this);
                            dialog.dismiss();
                            finish();
                            startActivity(new Intent(MainActivity.this, Login.class));
                        }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.dismiss();//hide the dialog UI
                        }
                    })
                    .show();
        }else if(id== R.id.generate_invoice){
            startActivity(new Intent(this, InvoiceActivity.class));
        }

        else if (id == R.id.nav_manage) {
            Intent i  = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
