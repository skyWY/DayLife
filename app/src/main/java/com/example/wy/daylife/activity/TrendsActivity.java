package com.example.wy.daylife.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wy.daylife.Interface.Defs;
import com.example.wy.daylife.R;

public class TrendsActivity extends AppCompatActivity {

    private static final String TAG = "TrendsActivity";
    private static final Uri PROFILE_URI = Uri.parse(Defs.TRENDS_SCHEMA);

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        extractUidFromUri();
        setTitle("Profile1:Hello, " + uid);
    }

    private void extractUidFromUri() {
        Uri uri = getIntent().getData();
        if (uri != null && PROFILE_URI.getScheme().equals(uri.getScheme())) {
            uid = uri.getQueryParameter(Defs.PARAM_UID);
            Log.d(TAG, "uid from url: " + uid);
        }
    }
}
