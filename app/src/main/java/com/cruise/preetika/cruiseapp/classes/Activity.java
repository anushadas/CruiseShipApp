package com.cruise.preetika.cruiseapp.classes;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;


public class Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle b=getIntent().getExtras();
            String s = b.getString("heading");
            String s1 = b.getString("one");
            String s2 = b.getString("two");
            String s3 = b.getString("three");

        TextView tv = (TextView) findViewById(R.id.head);
        TextView tv1 = (TextView) findViewById(R.id.act1);
        TextView tv2 = (TextView) findViewById(R.id.act2);
        TextView tv3 = (TextView) findViewById(R.id.act3);
            tv.setText(s);
            tv1.setText(s1);
            tv2.setText(s2);
            tv3.setText(s3);



        Button btnAct1 = (Button)findViewById(R.id.btnOne);
        Button btnAct2 = (Button)findViewById(R.id.btnTwo);
        Button btnAct3 = (Button)findViewById(R.id.btnThree);
        final int n=SharedPrefManager.getIntPrefVal(Activity.this,"act");

        btnAct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity.this, ActivityDetails.class);
                if(n==1) {
                    SharedPrefManager.setIntPrefVal(Activity.this,"actNum",11);
                    i.putExtra("head", getString(R.string.POC1));
                    i.putExtra("desc",getString(R.string.poc1Desc));
                }
                else {
                    SharedPrefManager.setIntPrefVal(Activity.this,"actNum",21);
                    i.putExtra("head", getString(R.string.onBoardAct1));
                    i.putExtra("desc",getString(R.string.descAct1));
                }
                startActivity(i);
            }
        });
        btnAct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity.this, ActivityDetails.class);
                ImageView image=(ImageView) findViewById(R.id.img);
                if(n==1) {
                    SharedPrefManager.setIntPrefVal(Activity.this,"actNum",12);
                    i.putExtra("head", getString(R.string.POC2));
                    i.putExtra("desc",getString(R.string.poc2Desc));
                }
                else {
                    SharedPrefManager.setIntPrefVal(Activity.this,"actNum",22);
                    i.putExtra("head", getString(R.string.onBoardAct2));
                    i.putExtra("desc",getString(R.string.descAct2));
                }
                startActivity(i);
            }
        });
        btnAct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity.this, ActivityDetails.class);
                if(n==1) {
                    SharedPrefManager.setIntPrefVal(Activity.this,"actNum",13);
                    i.putExtra("head", getString(R.string.POC3));
                    i.putExtra("desc",getString(R.string.poc3Desc));
                }
                else {
                    SharedPrefManager.setIntPrefVal(Activity.this,"actNum",23);
                    i.putExtra("head", getString(R.string.onBoardAct3));
                    i.putExtra("desc",getString(R.string.descAct3));
                }
                startActivity(i);
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
