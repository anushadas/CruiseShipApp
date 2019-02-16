package com.cruise.preetika.cruiseapp.classes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;


public class SignUp extends AppCompatActivity {

    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        helper= CommonUtilities.getDBObject(this);
        Button btn=(Button) findViewById(R.id.create);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=(EditText) findViewById(R.id.name);
                EditText uname=(EditText) findViewById(R.id.uname);
                EditText email=(EditText) findViewById(R.id.email);
                EditText phone = (EditText)findViewById(R.id.phoneNum);
                EditText pw=(EditText) findViewById(R.id.pword);
                EditText cpw=(EditText) findViewById(R.id.conPword);

                if(name.getText().toString().equals("")||
                        uname.getText().toString().equals("")||
                        email.getText().toString().equals("")||
                        pw.getText().toString().equals("")||
                        cpw.getText().toString().equals("")){
                    Toast.makeText(SignUp.this,"Please complete all fields",Toast.LENGTH_LONG).show();

                }else if(uname.getText().equals(helper.getValue(Constants.USER_INFO,Constants.USERNAME,null)))
                {
                    Toast.makeText(SignUp.this,"Username already exists",Toast.LENGTH_LONG).show();
                }                else if(uname.getText().toString().length()<5)
                {
                    Toast.makeText(SignUp.this,"Username has to be minimum 5 characters",Toast.LENGTH_LONG).show();
                    uname.requestFocus();
                }
                else if(!(email.getText().toString().contains("@")))
                {
                    Toast.makeText(SignUp.this,"Invalid email",Toast.LENGTH_LONG).show();
                    email.requestFocus();
                }
                else if(pw.getText().toString().length()<5)
                {
                    Toast.makeText(SignUp.this,"Password has to be minimum 5 charcaters",Toast.LENGTH_LONG).show();
                    pw.requestFocus();
                }
                else if(!(pw.getText().toString().equals(cpw.getText().toString()))) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    cpw.requestFocus();
                }
                else{
                    ContentValues values = new ContentValues();
                    values.put(Constants.USERNAME, uname.getText().toString());
                    values.put(Constants.NAME, name.getText().toString());
                    values.put(Constants.EMAIL, email.getText().toString());
                    values.put(Constants.PASSWORD, pw.getText().toString());
                    values.put(Constants.PHONE, phone.getText().toString());
                    helper.insertContentVals(Constants.USER_INFO, values);
                    int uid= Integer.parseInt(helper.getValue(Constants.USER_INFO,Constants.USERID,Constants.USERNAME+"= '"+ uname.getText().toString().trim()+"'"));
                    SharedPrefManager.setIntPrefVal(SignUp.this, Constants.USERID, uid);
                    SharedPrefManager.setStringPrefVal(SignUp.this, Constants.USERNAME, uname.getText().toString().trim());
                    SharedPrefManager.setStringPrefVal(SignUp.this, Constants.PASSWORD, pw.getText().toString().trim());
                    SharedPrefManager.setStringPrefVal(SignUp.this, Constants.NAME, name.getText().toString().trim());
                    SharedPrefManager.setStringPrefVal(SignUp.this, Constants.EMAIL, email.getText().toString().trim());
                    SharedPrefManager.setStringPrefVal(SignUp.this, Constants.PHONE, phone.getText().toString().trim());
                    finish();
                    startActivity(new Intent(SignUp.this, Room_booking_info.class));
                }
            }
        });
    }
}
