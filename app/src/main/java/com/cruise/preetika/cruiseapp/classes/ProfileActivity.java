package com.cruise.preetika.cruiseapp.classes;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cruise.preetika.cruiseapp.R;
import com.cruise.preetika.cruiseapp.customviews.RoundedImageView;
import com.cruise.preetika.cruiseapp.database.DBHelper;
import com.cruise.preetika.cruiseapp.utils.CommonUtilities;
import com.cruise.preetika.cruiseapp.utils.Constants;
import com.cruise.preetika.cruiseapp.utils.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.provider.MediaStore;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper helper;
    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/cruise";
    private RoundedImageView profileDp;
    private EditText nameEdt, phoneEdt, emailEdt;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = CommonUtilities.getDBObject(getApplicationContext());

        nameEdt = (EditText) findViewById(R.id.name);
        phoneEdt = (EditText) findViewById(R.id.phone);
        emailEdt = (EditText) findViewById(R.id.email);
        submit = (Button) findViewById(R.id.btnSubmit);
        profileDp = (RoundedImageView) findViewById(R.id.profile_dp);
        RelativeLayout picEditIcon = (RelativeLayout) findViewById(R.id.profile_pic_edit_icon);

        String name = helper.getValue(Constants.USER_INFO, Constants.NAME, Constants.USERID + "= '" + SharedPrefManager.getIntPrefVal(this, Constants.USERID) + "'");
        if (name != null) {
            nameEdt.setText(name);
        }
        String phoneNum = helper.getValue(Constants.USER_INFO, Constants.PHONE, Constants.USERID + "= '" + SharedPrefManager.getIntPrefVal(this, Constants.USERID) + "'");
        if (phoneNum != null) {
            phoneEdt.setText(phoneNum);
        }
        String email = helper.getValue(Constants.USER_INFO, Constants.EMAIL, Constants.USERID + "= '" + SharedPrefManager.getIntPrefVal(this, Constants.USERID) + "'");
        if (email != null) {
            emailEdt.setText(email);
        }
        picEditIcon.setOnClickListener(this);

        Button btn=(Button) findViewById(R.id.btnSubmit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_pic_edit_icon:
                showPictureDialog();
                break;
            case R.id.btnSubmit:
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);

                if (nameEdt.getText().toString().equals("") || phoneEdt.getText().toString().equals("")
                        || emailEdt.getText().toString().equals(""))
                    Toast.makeText(ProfileActivity.this, "Please enter Name/Phone number/Email Address", Toast.LENGTH_LONG).show();
                else {
                    ContentValues values = new ContentValues();
                    values.put(Constants.USERID, SharedPrefManager.getIntPrefVal(this, Constants.USERID));
                    values.put(Constants.NAME, nameEdt.getText().toString().trim());
                    values.put(Constants.EMAIL, emailEdt.getText().toString().trim());
                    values.put(Constants.PHONE, phoneEdt.getText().toString().trim());
                    helper.insertContentVals(Constants.USER_INFO, values);
                    SharedPrefManager.setStringPrefVal(ProfileActivity.this, Constants.USERNAME, nameEdt.getText().toString().trim());
                    SharedPrefManager.setStringPrefVal(ProfileActivity.this, Constants.PHONE, phoneEdt.getText().toString().trim());
                    SharedPrefManager.setStringPrefVal(ProfileActivity.this, Constants.EMAIL, phoneEdt.getText().toString().trim());

                    Toast toast = Toast.makeText(ProfileActivity.this, "Submit Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(i);
                }
            }
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

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ((ImageView) profileDp).setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ((ImageView) profileDp).setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


}
