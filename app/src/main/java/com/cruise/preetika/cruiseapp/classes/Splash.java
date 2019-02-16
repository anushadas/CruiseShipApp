package com.cruise.preetika.cruiseapp.classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        TimerTask tt=new TimerTask() {
            @Override
            public void run()
            {
                finish();
                int userId= SharedPrefManager.getIntPrefVal(Splash.this, Constants.USERID);
                if(userId!=0){
                    startActivity(new Intent(Splash.this, MainActivity.class));
                }else{
                    startActivity(new Intent(Splash.this, Login.class));
                }
            }
        };
        Timer op=new Timer();
        op.schedule(tt,3000);
    }
}
