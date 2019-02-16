package com.cruise.preetika.cruiseapp.classes;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Services extends AppCompatActivity {

    private LinearLayout l1;
    Button cBtn;
    Button aBtn;
    CheckBox bfast;
    CheckBox lunch;
    CheckBox dinner;
    Calendar c;
    DBHelper helper;
    Date currentTime;
    String d = "",d1="";
    static int i1 = 0, n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_services);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = CommonUtilities.getDBObject(getApplicationContext());
        cBtn = (Button) findViewById(R.id.clean);
        aBtn = (Button) findViewById(R.id.assistance);
        final Button fBtn = (Button) findViewById(R.id.food);


        bfast = (CheckBox) findViewById(R.id.cbBreakfast);
        lunch = (CheckBox) findViewById(R.id.cbLunch);
        dinner = (CheckBox) findViewById(R.id.cbDinner);

        l1 = findViewById(R.id.l1);

        collapse(l1, i1, fBtn);

        c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dpd = new DatePickerDialog(Services.this, myL, year, month, day);

        dpd.getDatePicker().setMinDate(c.getTimeInMillis());
        //dpd.getDatePicker().setMaxDate((c.add(Calendar.DATE, 6)));

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime = Calendar.getInstance().getTime();
                d1=currentTime.toString();
                dpd.show();
                n = 1;
            }
        });

        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i1 == 0) {
                    expand(l1, i1, fBtn);

                    fBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.user), null);
                    i1 = 1;

                } else {
                    collapse(l1, i1, fBtn);
                    fBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.d), null);
                    i1 = 0;
                }
                Button btn=(Button) findViewById(R.id.btnOrder);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentTime = Calendar.getInstance().getTime();
                        d1=currentTime.toString();
                        dpd.show();
                        n=2;
                    }
                });

            }
        });

        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
                d1=currentTime.toString();
                dpd.show();
                n=3;

            }
        });

    }

    public void expand(final View v, int i, Button b) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targettHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targettHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targettHeight / v.getContext().getResources()
                .getDisplayMetrics().density));
        v.startAnimation(a);
        i = 1;
        b.setCompoundDrawablesWithIntrinsicBounds(null, null, this.getResources().getDrawable(R.drawable.user), null);

    }

    public void collapse(final View v, int i, Button b) {
        final int initialHeight = v.getMeasuredHeight();
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight
                            - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources()
                .getDisplayMetrics().density));
        v.startAnimation(a);
        i = 0;
        b.setCompoundDrawablesWithIntrinsicBounds(null, null, this.getResources().getDrawable(R.drawable.d), null);

    }

    public void updateDB(String sname, String time,String ctime,double price) {

        ContentValues values = new ContentValues();
        values.put(Constants.USERID, SharedPrefManager.getIntPrefVal(Services.this, "" + Constants.USERID));
        values.put(Constants.ROOM_ID, SharedPrefManager.getIntPrefVal(Services.this, "" + Constants.ROOM_ID));
        values.put(Constants.SERVICE_NAME,sname);
        values.put(Constants.ACTIVITY_TYPE, "Services");
        values.put(Constants.BOOKING_DATETIME, ctime);
        values.put(Constants.BOOKING_PERSON, (helper.getValue(Constants.USER_INFO, Constants.NAME, Constants.USERID + "='" + SharedPrefManager.getIntPrefVal(Services.this, Constants.USERID) + "'")));
        values.put(Constants.DATE, d);
        values.put(Constants.TIME, time);
        values.put(Constants.CHARGES,price);
        helper.insertContentVals(Constants.TRANSACTION, values);


    }

    DateFormat fmtDate = DateFormat.getDateInstance();

    DatePickerDialog.OnDateSetListener myL = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            d = fmtDate.format(c.getTime());
            if (!(d.equals(""))) {
                if (n == 1) {
                    updateDB(cBtn.getText().toString(), "12:00pm", d1,50.00);
                    Toast.makeText(Services.this, "Room Cleaning will be there at 12:00pm on " + d, Toast.LENGTH_LONG).show();
                }
                else if(n==2) {
                    d=d1;
                    if(bfast.isChecked()) {
                        updateDB(bfast.getText().toString(), "",d1,0.00);


                    }
                    if(lunch.isChecked())
                        updateDB(lunch.getText().toString(), "",d1,80.00);
                    if(dinner.isChecked())
                        updateDB(dinner.getText().toString(), "",d1,80.00);
                    Toast.makeText(Services.this,"Food Services charged",Toast.LENGTH_LONG).show();
                }
                else if(n==3){
                    d=d1;
                    updateDB(aBtn.getText().toString(), "",d1,0.00);
                    Toast.makeText(Services.this, "Assistance requested " + d, Toast.LENGTH_LONG).show();

                }
            }
        }};

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

