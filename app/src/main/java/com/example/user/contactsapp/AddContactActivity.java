package com.example.user.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;


public class AddContactActivity extends ActionBarActivity {

    public final static String FIRSTNAME = "FIRSTNAME";
    public final static String LASTNAME  = "LASTNAME";
    public final static String PHOTO  = "PHOTO";
    private final static String LOG_TAG = AddContactActivity.class.getSimpleName();
    private EditText mFirstName;
    private EditText mLastName;
    private Button mDone;
    private ImageView mImage;
    DataBaseHelper mDBHelper = null;

    public final static int CAMERA_REQUEST  = 31415;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mFirstName = (EditText) findViewById(R.id.edtFirstName);
        mLastName  = (EditText) findViewById(R.id.edtLastName);
        mDone = (Button)findViewById(R.id.btnDone);
        mImage = (ImageView) findViewById(R.id.imageButton);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactSingle contact = new ContactSingle();
                if(mImage.getDrawable() != null){
                    mImage.buildDrawingCache();
                    Bitmap bm=((BitmapDrawable)mImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    contact.setmPhoto(stream.toByteArray());
                }
                contact.setLastName(mFirstName.getText().toString());
                contact.setFirstName(mLastName.getText().toString());
                SaveContact(contact);
                setResult(Activity.RESULT_OK, null);
                finish();
            }
        });

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,"CLICK EN IMAGEN");
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST){

            switch (resultCode){
                case RESULT_OK:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mImage.setImageBitmap(photo);
                    break;
                case RESULT_CANCELED:
                    //mTextView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
    private void SaveContact(ContactSingle contact) {
        try {
            Dao<ContactSingle,Integer> dao = getDBHelper().getContactDao();
            dao.create(contact);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "failed to create dao");
        }
    }

    public DataBaseHelper getDBHelper() {
        if (mDBHelper == null)
            mDBHelper= OpenHelperManager.getHelper(this, DataBaseHelper.class);
        return mDBHelper;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        if(mDBHelper != null){
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
        super.onDestroy();
    }
}
