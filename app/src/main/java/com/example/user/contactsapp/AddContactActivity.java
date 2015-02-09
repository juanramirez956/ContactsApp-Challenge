package com.example.user.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddContactActivity extends ActionBarActivity {

    public final static String FIRSTNAME = "FIRSTNAME";
    public final static String LASTNAME  = "LASTNAME";
    private EditText mFirstName;
    private EditText mLastName;
    private Button mDone;

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
                Intent mainActivity = new Intent();
                mainActivity.putExtra(FIRSTNAME,mFirstName.getText().toString());
                mainActivity.putExtra(LASTNAME ,mLastName.getText().toString());
                setResult(Activity.RESULT_OK, mainActivity);
                finish();
            }
        });

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
}
