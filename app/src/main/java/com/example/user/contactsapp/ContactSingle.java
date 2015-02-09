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

    @DatabaseField(generatedId = true,columnName = ID)      private int _id;
    @DatabaseField(columnName = FIRSTNAME)private String firstName;
    @DatabaseField(columnName = LASTNAME) private String lastName;

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
