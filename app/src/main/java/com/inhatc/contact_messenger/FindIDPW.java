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

        myTabHost = (TabHost) findViewById(R.id.tabhost);
        myTabHost.setup();

        myTabSpec = myTabHost.newTabSpec("findID").setIndicator("아이디찾기").setContent(R.id.tab1);
        myTabHost.addTab(myTabSpec);

        myTabSpec = myTabHost.newTabSpec("findPW").setIndicator("비밀번호찾기").setContent(R.id.tab2);
        myTabHost.addTab(myTabSpec);

        myTabHost.setCurrentTab(0);
    }
}