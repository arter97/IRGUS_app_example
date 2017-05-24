package com.arter97.myapplication;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(mClickListener);

        try {
            String contents = new Scanner(new File("/sdcard/tmp.txt")).useDelimiter("\\A").next();
            ((EditText)findViewById(R.id.editText)).setText(contents);
        } catch (Exception e) {
            Log.e("Exception", "File read failed: ", e);
        }
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            String msgData = "";
            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {
                    for(int idx=0;idx<cursor.getColumnCount();idx++)
                    {
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                    }
                    // use msgData
                } while (cursor.moveToNext());
            } else {
                // empty box, no SMS
            }
            ((EditText)findViewById(R.id.editText)).setText(msgData);
        }
    };
}
