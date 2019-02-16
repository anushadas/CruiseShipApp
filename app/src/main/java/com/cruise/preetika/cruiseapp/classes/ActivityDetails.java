package com.cruise.preetika.cruiseapp.classes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/* ADD TO STRINGS
    <string name="POCage">All ages</string>
    <string name="POCprice">$500.00</string>
    <string name="POCtime">9:00 am</string>
    <string name="Act1age">All ages</string>
    <string name="Act2age">7+ ages</string>
    <string name="Act3age">3+ ages</string>
    <string name="Actprice">$20.00</string>
    <string name="ActTime">10:00 am</string>
 */
public class ActivityDetails extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    DBHelper helper;
    private TextView date;
    TextView tv;
    Calendar c;
    TextView time;
    int activity = 0;
    RadioButton groupRb, privateRb , ageRbg;
    RadioGroup lpoc;
    TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_details2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = CommonUtilities.getDBObject(this);
        tv = (TextView) findViewById(R.id.head);
        time = (TextView) findViewById(R.id.time);
        LinearLayout lgroup=(LinearLayout) findViewById(R.id.lpoc);
        groupRb= (RadioButton)findViewById(R.id.rdbGroup);
        privateRb=(RadioButton)findViewById(R.id.rdbPrivate);
        ageRbg= (RadioButton)findViewById(R.id.rdAge);
        lpoc=(RadioGroup)findViewById(R.id.lpoc);
        lpoc.setOnCheckedChangeListener(this);
        c = Calendar.getInstance();
        int n=SharedPrefManager.getIntPrefVal(ActivityDetails.this,"act");
        if(n==2)
            lgroup.setVisibility(View.GONE);
        else
            lgroup.setVisibility(View.VISIBLE);
        Bundle b = getIntent().getExtras();
        String s = b.getString("head");
        String s1 = b.getString("desc");

        TextView tv1 = (TextView) findViewById(R.id.desc);
        tv.setText(s);
        tv1.setText(s1);

        ImageView img = (ImageView) findViewById(R.id.img);
        TextView age = (TextView) findViewById(R.id.ageGroup);
         price = (TextView) findViewById(R.id.price);

        int n1 = SharedPrefManager.getIntPrefVal(ActivityDetails.this, "actNum");

        if (n1 == 11) {
            img.setImageResource(R.drawable.hubbard);
            age.setText(getString(R.string.POCage));
            price.setText(getPrice());
            time.setText(getString(R.string.POCtime));
            activity = 1;
        } else if (n1 == 12) {
            img.setImageResource(R.drawable.icystrait);
            age.setText(getString(R.string.POCage));
            price.setText(getPrice());
            time.setText(getString(R.string.POCtime));
            activity = 1;
        } else if (n1 == 13) {
            img.setImageResource(R.drawable.juneau);
            age.setText(getString(R.string.POCage));
            price.setText(getPrice());
            time.setText(getString(R.string.POCtime));
            activity = 1;
        } else if (n1 == 21) {
            img.setImageResource(R.drawable.fireworks);
            price.setText(getString(R.string.Actprice));
            age.setText(getString(R.string.Act1age));
            time.setText(getString(R.string.ActTime));
            activity = 0;
        } else if (n1 == 22) {
            img.setImageResource(R.drawable.cooking);
            price.setText(getString(R.string.Actprice));
            age.setText(getString(R.string.Act2age));
            time.setText(getString(R.string.ActTime));
            activity = 0;
        } else if (n1 == 23) {
            img.setImageResource(R.drawable.swimming);
            price.setText(getString(R.string.Actprice));
            age.setText(getString(R.string.Act3age));
            time.setText(getString(R.string.ActTime));
            activity = 0;


        }

        date = (TextView) findViewById(R.id.txtDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new DatePickerDialog(ActivityDetails.this, d, c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        Button btn = (Button) findViewById(R.id.btnReserv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.name);
                EditText numP = (EditText) findViewById(R.id.numPeople);
                String d = helper.getValue(Constants.TRANSACTION, Constants.DATE, Constants.USERID + " = '" + SharedPrefManager.getIntPrefVal(ActivityDetails.this, Constants.USERID) + "'");

                String t = helper.getValue(Constants.TRANSACTION, Constants.DATE, Constants.USERID + "= '" + SharedPrefManager.getIntPrefVal(ActivityDetails.this, Constants.USERID) + "'");
                if(name.getText().toString().equals("")||numP.getText().toString().equals("")||numP.getText().toString().equals("0")) {
                    Toast.makeText(ActivityDetails.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                } else if(date.getText().toString().equals(""))
                {
                    Toast.makeText(ActivityDetails.this,"Please select a date",Toast.LENGTH_LONG).show();
                }
                else if (helper.CheckIsDataAlreadyInDBorNot(Constants.TRANSACTION, Constants.DATE + "='" + date.getText().toString() + "'")) {

                    Toast.makeText(ActivityDetails.this, "You already have an activty booked for today", Toast.LENGTH_LONG).show();

                } else {


                    ContentValues values = new ContentValues();
                    values.put(Constants.USERID, SharedPrefManager.getIntPrefVal(ActivityDetails.this, "" + Constants.USERID));
                    values.put(Constants.ROOM_ID, SharedPrefManager.getIntPrefVal(ActivityDetails.this, "" + Constants.ROOM_ID));
                    values.put(Constants.SERVICE_NAME, tv.getText().toString());
                    values.put(Constants.ACTIVITY_TYPE, activity);
                    values.put(Constants.BOOKING_DATETIME,DateFormat.getDateTimeInstance().format(new Date()));
                    values.put(Constants.BOOKING_PERSON, name.getText().toString());
                    values.put(Constants.DATE, date.getText().toString());
                    values.put(Constants.TIME, time.getText().toString());
                    values.put(Constants.CHARGES,(Double.parseDouble(getPrice(price.getText().toString().trim()))*Integer.parseInt(numP.getText().toString().trim()) ));
                    helper.insertContentVals(Constants.TRANSACTION, values);

                    new AlertDialog.Builder(ActivityDetails.this)
                            .setTitle("Success")
                            .setMessage("Reservation is successfully done.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .show();



                }

            }
        });
    }


    DateFormat fmtDate = DateFormat.getDateInstance();

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date.setText(fmtDate.format(c.getTime()));
        }
    };

    public String getPrice(String price){
        String netPrice=price.substring(1, price.length());
        return netPrice;
    }

    private String getPrice(){
        String Price;
        if(privateRb.isChecked()){
          Price= "$300.00";
          price.setText(Price);
        }else if(groupRb.isChecked()){
            Price="$250";
            price.setText(Price);
        }
        else {
            Price = "$100";
            price.setText(Price);
        }
        return Price;
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        String Price;
        if(i== R.id.rdbPrivate){
            Price= "$300.00";
            price.setText(Price);
        }else if(i== R.id.rdbGroup){
            Price= "$250.00";
            price.setText(Price);
        }else if(i==R.id.rdAge){
            Price = "$100";
            price.setText(Price);
        }
    }
}
