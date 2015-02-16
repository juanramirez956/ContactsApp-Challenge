package com.example.user.contactsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ContactsActivity extends ActionBarActivity {

    public static final int REQUEST_CODE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public static final int REQUEST_CODE = 0;
        ContactListAdapter mAdapter;
        List<ContactSingle> contactsList = new ArrayList<>();
        DataBaseHelper mDBHelper = null;


        public PlaceholderFragment() {
        }

        public DataBaseHelper getDBHelper() {
            if (mDBHelper == null)
                mDBHelper= OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);
            return mDBHelper;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
            setHasOptionsMenu(true);
            final ListView listView = (ListView)rootView.findViewById(android.R.id.list);
            try {
                Dao<ContactSingle,Integer> dao = getDBHelper().getContactDao();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            contactsList = getDBHelper().selectAll();
            mAdapter = new ContactListAdapter(getActivity(),contactsList);
            listView.setAdapter(mAdapter);
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_fragment_main,menu);

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (resultCode){
                case Activity.RESULT_OK:
                    ContactSingle contact= new ContactSingle();
                    contact.setFirstName(data.getStringExtra(AddContactActivity.FIRSTNAME));
                    contact.setLastName(data.getStringExtra(AddContactActivity.LASTNAME));
                    contactsList.add(contact);
                    mAdapter.notifyDataSetChanged();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getActivity(), R.string.canceled_message, Toast.LENGTH_LONG).show();
                    break;
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int menuId = item.getItemId();
            Boolean handled=false;
            switch (menuId){
                case R.id.add_todo:
                    Intent createTaskActivity = new Intent(getActivity(), AddContactActivity.class);
                    startActivityForResult(createTaskActivity, REQUEST_CODE);
                    handled = true;
                    break;
            }
            if(!handled)
            {
                handled = super.onOptionsItemSelected(item);
            }

            return handled;
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
}
