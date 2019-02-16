
package com.cruise.preetika.cruiseapp.classes;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;

import java.util.Random;

public class Room_booking_info extends AppCompatActivity {

    private EditText phoneEdt, nameEdt, emailEdt, adultEdt, childEdt;
    private TextView costTxt,t1,t2,t4 ;
    private RadioButton ocean, verandah, concierge, inside;
    private Spinner adultSp, childSp, deckSp;
    private Button bookBtn, personalBtn, peopleAccBtn, roomTypeBtn, deckBtn, roomid;
    private LinearLayout l1, l2, l3,l4;

    int room_cost = 0, id;
    String deck = "";
    String selected_room = "";
    DBHelper helper;
    static int i1 = 0, i2 = 0, i3 = 0, i4 = 0, adultcount, childcount,roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_room_booking_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = CommonUtilities.getDBObject(getApplicationContext());
        Log.e("rooms", ""+helper.getFullCount(Constants.ROOM, null));
        phoneEdt = findViewById(R.id.ph_et);
        nameEdt = findViewById(R.id.name_et);
        emailEdt = findViewById(R.id.email_et);
        adultSp = findViewById(R.id.adult_et);
        childSp = findViewById(R.id.child_et);
        costTxt = findViewById(R.id.tv_cost);
        ocean = findViewById(R.id.r1);
        verandah = findViewById(R.id.r2);
        concierge = findViewById(R.id.r3);
        inside = findViewById(R.id.r4);
        deckSp = findViewById(R.id.deck_spinner);
        bookBtn = findViewById(R.id.bt_book);

        personalBtn = findViewById(R.id.p_btn);
        peopleAccBtn = findViewById(R.id.pep_btn);
        roomTypeBtn = findViewById(R.id.type_btn);
        deckBtn = findViewById(R.id.sel_btn);

        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);


        t4 = findViewById(R.id.info);

        id = SharedPrefManager.getIntPrefVal(Room_booking_info.this, Constants.USERID + "");


        collapse(l2, i2, peopleAccBtn);
        collapse(l3, i3, roomTypeBtn);
        collapse(l4, i4, deckBtn);
        i1 = 1;


        phoneEdt.setText(helper.getValue(Constants.USER_INFO, Constants.PHONE, Constants.USERID + "= '" + id + "'").toString());
        nameEdt.setText(helper.getValue(Constants.USER_INFO, Constants.USERNAME, Constants.USERID + "= '" + id + "'").toString());
        emailEdt.setText(helper.getValue(Constants.USER_INFO, Constants.EMAIL, Constants.USERID + "= '" + id + "'").toString());


        personalBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (i1 == 0) {
                    expand(l1, i1, personalBtn);

                    personalBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.user), null);
                    i1=1;

                } else {
                    collapse(l1, i1, personalBtn);
                    personalBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.d), null);
                    i1=0;
                }
                collapse(l2, i2, peopleAccBtn);
                collapse(l3, i3, roomTypeBtn);
                collapse(l4, i4, deckBtn);
            }
        });
        peopleAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (i2 == 0) {
                    expand(l2, i2, peopleAccBtn);
                    roomTypeBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.user), null);
                    i2=1;

                } else {
                    collapse(l2, i2, peopleAccBtn);
                    peopleAccBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.d), null);

                    i2=0;
                }
                collapse(l1, i1, personalBtn);
                collapse(l3, i3, roomTypeBtn);
                collapse(l4, i4, deckBtn);


            }
        });
        roomTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (i3 == 0) {
                    expand(l3, i3, roomTypeBtn);
                    roomTypeBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.user), null);
                    i3=1;
                } else {
                    collapse(l3, i3, roomTypeBtn);
                    roomTypeBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.d), null);
                    i3=0;
                }

                collapse(l1, i1, personalBtn);
                collapse(l2, i3, peopleAccBtn);
                collapse(l4, i4, deckBtn);

                 adultcount = adultSp.getSelectedItemPosition();
                 childcount = childSp.getSelectedItemPosition();
                if (adultcount == 0 && childcount == 0) {
                    ocean.setVisibility(View.GONE);
                    verandah.setVisibility(View.GONE);
                    concierge.setVisibility(View.GONE);
                    inside.setVisibility(View.GONE);
                    t4.setVisibility(View.VISIBLE);
                }

                if (adultcount == 2 && childcount == 2) {
                    verandah.setVisibility(View.GONE);
                    concierge.setVisibility(View.GONE);
                    inside.setVisibility(View.GONE);

                }
                if (adultcount == 2 && childcount == 1) {
                    ocean.setVisibility(View.VISIBLE);
                    verandah.setVisibility(View.VISIBLE);
                    concierge.setVisibility(View.GONE);
                    inside.setVisibility(View.GONE);

                }
                if (adultcount == 1 && childcount >= 1) {
                    ocean.setVisibility(View.VISIBLE);
                    verandah.setVisibility(View.VISIBLE);
                    inside.setVisibility(View.VISIBLE);
                    concierge.setVisibility(View.GONE);


                }
                if (adultcount >= 1 && childcount == 0) {
                    ocean.setVisibility(View.VISIBLE);
                    verandah.setVisibility(View.VISIBLE);
                    concierge.setVisibility(View.VISIBLE);
                    inside.setVisibility(View.VISIBLE);
                }
                if (adultcount == 2 || childcount == 2) {

                    inside.setVisibility(View.GONE);
                    verandah.setVisibility(View.GONE);

                }


            }
        });

        deckBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (i4 == 0) {
                    expand(l4, i4, deckBtn);
                    deckBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.user), null);
                    i4=1;
                } else {
                    collapse(l4, i4, deckBtn);
                    deckBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.d), null);
                    i4=0;}
                collapse(l1, i1, personalBtn);
                collapse(l3, i3, roomTypeBtn);
                collapse(l2, i2, peopleAccBtn);

                String[] a1=new String[3];
                String[] a2=new String[3];
                String[] a3=new String[3];
                String[] arr=getResources().getStringArray(R.array.Deck_Selection);
                for(int i=0;i<arr.length;i++)
                {
                    if(i<3) {
                        a1[i] = arr[i];
                    }
                    if((i>2) &&(i<6)) {
                        a2[i-3]=arr[i];
                    }
                    if((i>5)&&(i<8))
                    {
                        a3[i-6]=arr[i];
                    }
                }
                ArrayAdapter<String> ar1 = new ArrayAdapter<String>(Room_booking_info.this,R.layout.support_simple_spinner_dropdown_item,a1);
                ArrayAdapter<String> ar2 = new ArrayAdapter<String>(Room_booking_info.this,R.layout.support_simple_spinner_dropdown_item,a2);
                ArrayAdapter<String> ar3 = new ArrayAdapter<String>(Room_booking_info.this,R.layout.support_simple_spinner_dropdown_item,a3);

                if (ocean.isChecked()) {
                    selected_room = "OceanView";
                    deckSp.setAdapter(ar1);
                    room_cost = 1500;
                }
                if (verandah.isChecked()) {
                    selected_room = "Verandha";
                    deckSp.setAdapter(ar2);
                    room_cost = 1300;
                }
                if (concierge.isChecked()) {
                    selected_room = "Concierge";
                    deckSp.setAdapter(ar2);
                    room_cost = 1100;
                }
                if (inside.isChecked()) {
                    selected_room = "Inside";
                    deckSp.setAdapter(ar3);
                    room_cost = 900;
                }
                costTxt.setText("Cost= $ " + room_cost);
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomId = roomIdGenerator();
                deck = deckSp.getSelectedItem().toString();
                ContentValues values= new ContentValues();
                values.put(Constants.USERID, SharedPrefManager.getIntPrefVal(Room_booking_info.this, Constants.USERID));
                values.put(Constants.ROOMT_TYPE, selected_room );
                values.put(Constants.DECK_NO, deck);
                values.put(Constants.PEOPLE_ACCOMPANYING,adultcount+childcount );
                if(helper.CheckIsDataAlreadyInDBorNot(Constants.ROOM,Constants.ROOM_ID+"='"+roomId+"'"))
                    roomId=roomIdGenerator();
                values.put(Constants.ROOM_ID,roomId);
                helper.insertContentVals(Constants.ROOM, values);

                SharedPrefManager.setIntPrefVal(Room_booking_info.this,Constants.ROOM_ID,roomId);


                new AlertDialog.Builder(Room_booking_info.this)
                        .setTitle("Room Booked")
                        .setMessage("Thank you for booking room! Your room number is " + roomId)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPrefManager.setIntPrefVal(Room_booking_info.this, Constants.ROOM_ID, roomId);
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(Room_booking_info.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        })
                        .setCancelable(false)
                        .show();
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

    /**
     * Collapse Specified View
     *
     * @param
     */
    public int roomIdGenerator() {
        Random rand = new Random();
        return rand.nextInt(500);
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

