package com.td.diaryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.td.diaryapp.IndividualClasses.Contact;
import com.td.diaryapp.MyLibraries.CommonRoutines;
import com.td.diaryapp.adapters.PhoneAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhoneActivity extends AppCompatActivity {

    private ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        lvContacts = findViewById(R.id.lvContacts);

        //create ArrayAdapter
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getContacts());

        PhoneAdapter adapter = new PhoneAdapter(this);

        adapter.addAll(getContacts());

        lvContacts.setAdapter(adapter);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the contact
                Contact c = (Contact) parent.getItemAtPosition(position);

                //create a new AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);

                //set the title
                builder.setTitle(c.getName());

                String phone = "";

                if (c.getPhoneNumbers() != null && !c.getPhoneNumbers().isEmpty())
                {
                    for(int i = 0; i < c.getPhoneNumbers().size(); i++)
                    {
                        phone += c.getPhoneNumbers().get(i);

                        if ((i + 1) < c.getPhoneNumbers().size()){

                            phone += "; ";
                        }
                    }
                }
                else
                {
                    phone = "No phone number found";
                }

                builder.setMessage(phone);

                builder.setNeutralButton("Ok", null);

                builder.show();
            }
        });
    }

    private List<Contact> getContacts() {

        //create an empty list
        List<Contact> contactList = new ArrayList<>();

        //create a new instance of content resolver
        ContentResolver contentResolver = getContentResolver();

        //get the content and save it in a cursor
        Cursor cr = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cr != null && cr.moveToFirst()) {
            //loop and add contact to list

            do {

                //create a contact to add to list
                Contact c = new Contact();

                try {
                    c.setId(Integer.parseInt(cr.getString(cr.getColumnIndex(ContactsContract.Contacts._ID))));
                } catch (NumberFormatException fe) {
                    Log.e("Error in: " + this, fe.getMessage());
                }

                c.setName(cr.getString(cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                List<String> phoneList = new ArrayList<>();

                Cursor phoneCursor = null;

                try {

                    if (Integer.parseInt(cr.getString(cr.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){

                        //get the phone number
                        phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + c.getId(), null, null);

                        while(phoneCursor.moveToNext())
                        {
                            phoneList.add(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }
                    }
                } catch (NumberFormatException fe) {
                    Log.e("Error in: " + this, fe.getMessage());
                } finally {

                    if (phoneCursor != null){
                        phoneCursor.close();
                    }
                }

                c.setPhoneNumbers(phoneList);

                //add to contact list
                contactList.add(c);


            } while (cr.moveToNext());

            //close cr
            cr.close();

        } else {
            CommonRoutines.displayMessage(this, null, "No contact found", 0, Toast.LENGTH_SHORT);
        }

        return contactList;
    }
}
