package com.example.contentprovider;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

    Cursor cursorContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cursorContacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);

        ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                cursorContacts,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{android.R.id.text1},
                0);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object o = getListAdapter().getItem(position);
                if (o instanceof Cursor) {
                    Cursor w = (Cursor) o;
                    int columnIndex = w.getColumnIndex(
                            ContactsContract.Contacts._ID);
                    long contactId = w.getLong(columnIndex);
                    Uri uri = Uri.withAppendedPath(
                            ContactsContract.Contacts.CONTENT_URI,
                            Long.toString(contactId));
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            uri);
                    startActivity(intent);
                }
            }
        });
    }
}