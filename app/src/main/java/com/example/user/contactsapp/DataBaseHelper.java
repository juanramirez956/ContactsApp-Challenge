package com.example.user.contactsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 09/02/2015.
 */
public class DataBaseHelper  extends OrmLiteSqliteOpenHelper {

    private final static String DATABASE_NAME = "contacts.db";
    private final static int DATABASE_VERSION = 1;
    private final static String LOG_TAG = DataBaseHelper.class.getSimpleName();

    public Dao<ContactSingle, Integer> getContactDao() throws SQLException {
        if(mContactDao == null)
        {
            mContactDao = getDao(ContactSingle.class);
        }
        return mContactDao;
    }

    private Dao<ContactSingle,Integer> mContactDao = null;

    public DataBaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(LOG_TAG, "CREATING DATABASE.");
            TableUtils.createTable(connectionSource, ContactSingle.class);
        } catch (SQLException e) {
            Log.i(LOG_TAG,"ERROR CREATING DATABASE",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public List<ContactSingle> selectAll() {
        List<ContactSingle> contactsResult = null;

        try {
            Dao<ContactSingle,Integer> contactDao = getContactDao();
            contactsResult = contactDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsResult;
    }


}


