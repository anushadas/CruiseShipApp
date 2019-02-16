package com.cruise.preetika.cruiseapp.classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;


public class Login extends AppCompatActivity {

   DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper= CommonUtilities.getDBObject(getApplicationContext());

        Button btn=(Button) findViewById(R.id.signUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });

        Button btn2=(Button) findViewById(R.id.login);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText un=(EditText) findViewById(R.id.username);
                EditText pw=(EditText) findViewById(R.id.pword);

                if(un.getText().toString().equals("")||pw.getText().toString().equals(""))
                    Toast.makeText(Login.this,"Please enter username/password",Toast.LENGTH_LONG).show();
                else if(!helper.CheckIsDataAlreadyInDBorNot(Constants.USER_INFO,Constants.USERNAME+"='"+un.getText().toString()+"'")){
                    Toast.makeText(Login.this, "Username or Password does not exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String password= helper.getValue(Constants.USER_INFO, Constants.PASSWORD, Constants.USERNAME+"= '"+ un.getText().toString().trim()+"'");
                   // Toast.makeText(Login.this,password+"="+pw.getText().toString(),Toast.LENGTH_LONG).show();
                    if(password.equals(pw.getText().toString().trim())){
                       int uid= Integer.parseInt(helper.getValue(Constants.USER_INFO,Constants.USERID,Constants.USERNAME+"= '"+ un.getText().toString().trim()+"'"));
                        SharedPrefManager.setIntPrefVal(Login.this, Constants.USERID, uid);
                        String name= helper.getValue(Constants.USER_INFO,Constants.NAME,Constants.USERID+"= "+ uid);
                        SharedPrefManager.setStringPrefVal(Login.this, Constants.NAME, name);
                        String phone= helper.getValue(Constants.USER_INFO,Constants.PHONE,Constants.USERID+"= "+ uid);
                        SharedPrefManager.setStringPrefVal(Login.this, Constants.PHONE, phone);
                        String room_id= helper.getValue(Constants.ROOM,Constants.ROOM_ID,Constants.USERID+"= "+ uid);
                       if(room_id!=null){
                           int roomid= Integer.parseInt(room_id);
                           SharedPrefManager.setIntPrefVal(Login.this, Constants.ROOM_ID, roomid);
                       }

                        SharedPrefManager.setStringPrefVal(Login.this, Constants.USERNAME, un.getText().toString().trim());
                        SharedPrefManager.setStringPrefVal(Login.this, Constants.PASSWORD, pw.getText().toString().trim());
                        Toast toast = Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent i=new Intent(Login.this,MainActivity.class);
                        finish();
                        startActivity(i);
                    }
                    else
                        Toast.makeText(Login.this,"Password does not exist",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
