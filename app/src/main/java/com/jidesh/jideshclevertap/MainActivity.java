package com.jidesh.jideshclevertap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
Button button;
int i=0;
    CleverTapAPI clevertap;
    HashMap<String, Object> prodViewedAction;
    HashMap<String, Object> profileUpdate;
    HashMap<String, Object> profileUpdate2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       clevertap = CleverTapAPI.getDefaultInstance(getApplicationContext());
        prodViewedAction = new HashMap<String, Object>();
        profileUpdate= new HashMap<String, Object>();
        profileUpdate2= new HashMap<String, Object>();
        prodViewedAction.put("Product Name", "CleverTap");
        prodViewedAction.put("Product Image", "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg");
        prodViewedAction.put("Product ID", 1);
readData();
        profileUpdate.put("Email", "jjideshnair8@gmail.com");
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clevertap.pushEvent("Product viewed", prodViewedAction);
                clevertap.pushProfile(profileUpdate);
            }
        });
    }
    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.test);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");
                i++;
                profileUpdate2.put("User ID", tokens[0]);
                profileUpdate2.put("Location", tokens[1]);
                profileUpdate2.put("Customer Type",tokens[2]);
                profileUpdate2.put("Subcription ID",tokens[3]);
                profileUpdate2.put("Gender",tokens[4]);
                clevertap.pushProfile(profileUpdate);
                profileUpdate2.clear();
                Log.d("MainActivity" ,"Just Created " + tokens[0]+"danme"+tokens[1]+"danm"+tokens[2]+"danm"+tokens[3]);
            }
            Log.e("MainActivity", "total" + i);
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }
    }
}
