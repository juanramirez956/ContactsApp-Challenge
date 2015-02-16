package com.example.user.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;


public class AddContactActivity extends ActionBarActivity {

    public final static String FIRSTNAME = "FIRSTNAME";
    public final static String LASTNAME  = "LASTNAME";
    private final static String LOG_TAG = AddContactActivity.class.getSimpleName();
    private EditText mFirstName;
    private EditText mLastName;
    private Button mDone;
    DataBaseHelper mDBHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mFirstName = (EditText) findViewById(R.id.edtFirstName);
        mLastName  = (EditText) findViewById(R.id.edtLastName);
        mDone = (Button)findViewById(R.id.btnDone);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactSingle contact = new ContactSingle();
                contact.setLastName(mFirstName.getText().toString());
                contact.setFirstName(mLastName.getText().toString());
                SaveContact(contact);
                Intent mainActivity = new Intent();
                mainActivity.putExtra(FIRSTNAME,contact.getFirstName());
                mainActivity.putExtra(LASTNAME ,contact.getLastName());
                setResult(Activity.RESULT_OK, mainActivity);
                finish();
            }
        });

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
