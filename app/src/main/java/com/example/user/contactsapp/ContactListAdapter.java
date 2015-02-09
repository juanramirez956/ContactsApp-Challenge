package com.example.user.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 09/02/2015.
 */
public class ContactListAdapter extends ArrayAdapter<ContactSingle> {
    private  List<ContactSingle> mContactsList;
    private  Context mContext;

    public  ContactListAdapter(Context context, List<ContactSingle> contactsList)
    {
        super(context,R.layout.contact_single,contactsList);
        mContext = context;
        mContactsList=contactsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View rowView;
        if (convertView !=null)
        {
            rowView = convertView;
        }
        else
        {  LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.contact_single,parent,false);
        }

        if (rowView !=null)
        {
            TextView edtFirstName = (TextView)rowView.findViewById(R.id.txt_first);
            edtFirstName.setText(mContactsList.get(position).getFirstName());
            TextView edtLastName = (TextView)rowView.findViewById(R.id.txt_last);
            edtLastName.setText(mContactsList.get(position).getLastName());
        }
        return  rowView;
    }
}
