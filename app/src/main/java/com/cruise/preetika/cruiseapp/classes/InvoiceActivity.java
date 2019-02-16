package com.cruise.preetika.cruiseapp.classes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.adapters.InvoiceAdpater;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.handlers.InvoiceHandler;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    DBHelper helper;
    TextView nameTxt, roomnoTxt, phoneTxt, overallTxt, gstTxt, totalTxt;
    List<InvoiceHandler> invoices= new ArrayList<>();
    double overallCharges=0;
    ListView invoiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper= CommonUtilities.getDBObject(this);
        nameTxt=(TextView)findViewById(R.id.name);
        roomnoTxt=(TextView)findViewById(R.id.roomno);
        phoneTxt=(TextView)findViewById(R.id.phone);
        overallTxt= (TextView)findViewById(R.id.overall);
        gstTxt=(TextView)findViewById(R.id.gst);
        totalTxt=(TextView)findViewById(R.id.totalcost);
        invoiceList= (ListView)findViewById(R.id.invoiceList);

        nameTxt.setText("Name: " + SharedPrefManager.getPrefVal(this, Constants.NAME));
        roomnoTxt.setText("Room No: " + SharedPrefManager.getIntPrefVal(this, Constants.ROOM_ID));
        phoneTxt.setText("Phone: " + SharedPrefManager.getPrefVal(this, Constants.PHONE));

        invoices= helper.getAllInvoices(SharedPrefManager.getIntPrefVal(this, Constants.USERID));

        Log.e("count", helper.getFullCount(Constants.TRANSACTION, null)+"");
        for (int i = 0; i < invoices.size(); i++) {
            overallCharges+= invoices.get(i).getCharges();
        }

        overallTxt.setText("Overall Charges: $" + overallCharges);
        gstTxt.setText("GST + PST: 12%");
        totalTxt.setText("Grand Total: $" + ((overallCharges* 0.12)+overallCharges));

        InvoiceAdpater adpater= new InvoiceAdpater(this, invoices);
        invoiceList.setAdapter(adpater);

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
