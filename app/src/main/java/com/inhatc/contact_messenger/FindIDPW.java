package com.inhatc.contact_messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class FindIDPW extends AppCompatActivity {
    TabHost myTabHost = null;
    TabHost.TabSpec myTabSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_idpw);


    }
}