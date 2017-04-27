package com.academy.sda.contactsapp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView list;
    private Integer counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.list = (ListView) findViewById(R.id.list);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        this.list.setAdapter(this.adapter);

        this.adapter.add("sth");
    }

    public void onButtonClick(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            logDebugActivity("We need permissions to read contacts");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                logDebugActivity("Permission Rationale?");

                startRequestScreen();
            } else {
                requestPermissions();
            }


        } else {
            logDebugActivity("We already have permission to READ_CONTACTS");

            readContacts();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 0) {
            if (grantResults.length == 0) {
                logDebugActivity("Permission request was cancelled");

                startRequestScreen();

            } else {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    logDebugActivity("Permission was granted");
                    makeLongToast("We've got permissions!");

                    readContacts();

                } else {
                    logDebugActivity("Permission was not granted!");
                    makeLongToast("Permission was not granted");

                    startRequestScreen();
                }
            }

            return;
        }
    }


    private void readContacts() {
        logDebugActivity("Reading Contacts");
        Cursor cursor =
                getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        logDebugActivity("Found " + cursor.getCount() + " contacts");

        //adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            logDebugActivity("Read contact " + name);
            this.adapter.add(name);
        }
        cursor.close();
    }

    private void requestPermissions() {
        logDebugActivity("Trying to acquire permissions from the user!");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);

    }

    private void addNumber(Integer number) {
        adapter.add(number.toString());
    }

    private void logDebugActivity(String str) {
        Log.d(this.getClass().getSimpleName(), str);
    }

    private void startRequestScreen() {
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }

    public void makeLongToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public void closeActivity(View view) {
        finish();
    }


}
