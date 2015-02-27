package com.example.user.contactsapp;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Created by user on 08/02/2015.
 *
 */
public class ContactSingle {
    public final static String ID        = "_id";
    public final static String FIRSTNAME = "FirstName";
    public final static String LASTNAME  = "LastName";
    public final static String PHOTO  = "Photo";

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @DatabaseField(generatedId = true,columnName = ID)      private int _id;
    @DatabaseField(columnName = FIRSTNAME)private String firstName;
    @DatabaseField(columnName = LASTNAME) private String lastName;
    @DatabaseField(columnName = PHOTO, dataType = DataType.BYTE_ARRAY) private byte[] mPhoto;

    public byte[] getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(byte[] mPhoto) {
        this.mPhoto = mPhoto;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
