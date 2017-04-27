package com.academy.sda.contactsapp2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class SecondActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.button = (Button) findViewById(R.id.buttonListenedFromSecondActivity);
        this.button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
    }


    public void requestPermissions() {
        logDebugActivity("Trying to acquire permissions from the user!");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 0) {
            if (grantResults.length == 0) {
                logDebugActivity("Permission request was cancelled");

            } else {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    logDebugActivity("Permission was granted");
                    makeLongToast("We've got permissions");

                    closeActivity();
                } else {
                    logDebugActivity("Permission was not granted!");
                    makeLongToast("Permission was not granted");
                }
            }

        }
    }


    private void logDebugActivity(String str) {
        Log.d(this.getClass().getSimpleName(), str);
    }

    private void makeLongToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    public void closeActivity() {
        finish();
    }
}
