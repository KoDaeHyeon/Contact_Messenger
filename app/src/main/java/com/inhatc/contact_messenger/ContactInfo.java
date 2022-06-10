package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactInfo extends AppCompatActivity {

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    String strHeader;
    String strContact;

    TextView txtContactID;
    TextView txtContactNo;
    TextView txtContactName;
    TextView txtContactPhone;
    TextView txtContactEmail;
    TextView txtContactLati;
    TextView txtContactLong;

    Button btnContactMap;
    Button btnContactModify;
    Button btnContactCall;
    Button btnContactDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        strHeader = getIntent().getStringExtra("myInfo") + " ContactList";
        strContact = getIntent().getStringExtra("contactInfo");

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference(strHeader);

        txtContactName = (TextView) findViewById(R.id.txt_ContactName);
        txtContactPhone= (TextView) findViewById(R.id.txt_ContactPhone);
        txtContactEmail= (TextView) findViewById(R.id.txt_ContactEmail);
        txtContactLati= (TextView) findViewById(R.id.txt_ContactLati);
        txtContactLong= (TextView) findViewById(R.id.txt_ContactLong);
        txtContactID = (TextView) findViewById(R.id.txt_ContactID);
        txtContactNo = (TextView) findViewById(R.id.txt_ContactNo);

        btnContactMap=(Button) findViewById(R.id.btn_ContactMap);
        btnContactModify =(Button)findViewById(R.id.btn_ContactModify);
        btnContactCall=(Button)findViewById(R.id.btn_ContactCall);
        btnContactDelete=(Button)findViewById(R.id.btn_ContactDelete);

        btnContactMap.setOnClickListener(btn_listener);
        btnContactModify.setOnClickListener(btn_listener);
        btnContactCall.setOnClickListener(btn_listener);
        btnContactDelete.setOnClickListener(btn_listener);

        myDB_Reference.child(strContact).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Contact value = snapshot.getValue(Contact.class);

                        txtContactName.setText(value.Name);
                        txtContactPhone.setText(value.Phone);
                        txtContactLati.setText(value.Lati);
                        txtContactLong.setText(value.Long);
                        txtContactEmail.setText(value.Email);
                        txtContactID.setText(value.ID);
                        txtContactNo.setText(value.Uno);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());
                    }
                });
    }

    View.OnClickListener btn_listener = new View.OnClickListener() {
        public void onClick(View view){
            if (view == btnContactCall) {
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + txtContactPhone.getText()));
                dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialIntent);
            } else if (view == btnContactMap) {
                if (txtContactLati.getText().equals("") || txtContactLong.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "위치 정보가 정확하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {

                }

            } else if (view == btnContactModify) {
                Intent modifyIntent = new Intent(ContactInfo.this,ContactModify.class);
                modifyIntent.putExtra("contentInfo",strContact);
                modifyIntent.putExtra("myInfo",strHeader);
                startActivity(modifyIntent);
                finish();
            } else if (view == btnContactDelete) {
                myDB_Reference.child(strContact).setValue(null);
                finish();
            }

        }
    };
}