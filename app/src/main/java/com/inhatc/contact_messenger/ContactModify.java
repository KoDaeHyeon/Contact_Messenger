package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ContactModify extends AppCompatActivity implements View.OnClickListener {

    EditText edtModifyID;
    EditText edtModifyNo;
    EditText edtModifyName;
    EditText edtModifyPhone;
    EditText edtModifyEmail;
    EditText edtModifyLati;
    EditText edtModifyLong;

    String strID;
    String strNo;
    String strName;
    String strPhone;
    String strEmail;
    String strLati;
    String strLong;

    String strHeader;
    String strContact;

    Button btnModifyGrab;
    Button btnModifySuccess;

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    HashMap<String, Object> Contact_Value = new HashMap<>();

    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_modify);

        strHeader = getIntent().getStringExtra("myInfo");
        strContact = getIntent().getStringExtra("contentInfo");

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        edtModifyID = (EditText) findViewById(R.id.edt_ModifyID);
        edtModifyNo = (EditText) findViewById(R.id.edt_ModifyNo);
        edtModifyName = (EditText) findViewById(R.id.edt_ModifyName);
        edtModifyPhone = (EditText) findViewById(R.id.edt_ModifyPhone);
        edtModifyEmail = (EditText) findViewById(R.id.edt_ModifyEmail);
        edtModifyLati = (EditText) findViewById(R.id.edt_ModifyLati);
        edtModifyLong = (EditText) findViewById(R.id.edt_ModifyLong);

        btnModifyGrab = (Button) findViewById(R.id.btn_ModifyGrab);
        btnModifySuccess = (Button) findViewById(R.id.btn_ModifySuccess);
        btnModifyGrab.setOnClickListener(this);
        btnModifySuccess.setOnClickListener(this);

        myDB_Reference.child(strHeader).child(strContact).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Contact value = snapshot.getValue(Contact.class);

                        edtModifyName.setText(value.Name);
                        edtModifyPhone.setText(value.Phone);
                        edtModifyLati.setText(value.Lati);
                        edtModifyLong.setText(value.Long);
                        edtModifyEmail.setText(value.Email);
                        edtModifyID.setText(value.ID);
                        edtModifyNo.setText(value.Uno);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());
                    }
                });

    }

    @Override
    public void onClick(View view){
        if(view == btnModifyGrab){
            //?????? ????????????
            strID = edtModifyID.getText().toString().trim();
            strNo = edtModifyNo.getText().toString().trim();
            if(strID.equals("") || strNo.equals("")){
                Toast.makeText(getApplicationContext(), "???????????? ??????????????? ??????????????????.",
                        Toast.LENGTH_SHORT).show();
            }else {
                myDB_Reference.child("Member Information").child(strID).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                MemberInfo value = snapshot.getValue(MemberInfo.class);
                                if (value != null) {
                                    if (value.Uno.equals(strNo) && value.ID.equals(strID)) {
                                        contact.set_ContactInfo(value.Uno, value.ID, value.Name,
                                                value.Phone,value.Email, value.Lati, value.Long);
                                        edtModifyName.setText(contact.Name);
                                        edtModifyPhone.setText(contact.Phone);
                                        edtModifyEmail.setText(contact.Email);
                                        edtModifyLati.setText(contact.Lati);
                                        edtModifyLong.setText(contact.Long);
                                    }else{
                                        Toast.makeText(getApplicationContext(),
                                                "???????????? ??????????????? ??????????????????.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.w("Tag: ", "Failed to read value", error.toException());
                            }
                        });
            }
        }else if(view == btnModifySuccess){
            strID = edtModifyID.getText().toString().trim();
            strNo = edtModifyNo.getText().toString().trim();
            strName = edtModifyName.getText().toString().trim();
            strPhone = edtModifyPhone.getText().toString().trim();
            strEmail = edtModifyEmail.getText().toString().trim();
            strLati = edtModifyLati.getText().toString().trim();
            strLong = edtModifyLong.getText().toString().trim();

            Contact_Value.clear();
            Contact_Value.put("Uno",strNo);
            Contact_Value.put("Name",strName);
            Contact_Value.put("ID",strID);
            Contact_Value.put("Phone",strPhone);
            Contact_Value.put("Email",strEmail);
            Contact_Value.put("Lati",strLati);
            Contact_Value.put("Long",strLong);


            if(strName.equals("") || strPhone.equals("")) {
                Toast.makeText(getApplicationContext(), "?????? ?????? ????????? ??????????????????.",
                        Toast.LENGTH_SHORT).show();
            }else if(!isStringDouble(strLati) || !isStringDouble(strLong)){
                Toast.makeText(getApplicationContext(), "?????? ????????? ????????? ????????????.",
                        Toast.LENGTH_SHORT).show();
            }else {

                //??????????????? ???????????? ????????????
                if(strContact.equals(strPhone)){
                    //?????? ?????? ?????? ??? ????????? ?????? ??????
                    myDB_Reference.child(strHeader).child(strContact).setValue(null);
                    myDB_Reference.child(strHeader).child(strPhone).setValue(Contact_Value);
                    //????????? ????????????
                    Toast.makeText(getApplicationContext(), "????????? ?????? ???????????????.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    //??????????????? ?????????????????? ????????? ?????? ???????????? ??????
                    myDB_Reference.child(strHeader).child(strPhone).child("Phone").
                            addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.getValue(String.class);
                            if (value != null) {
                                Toast.makeText(getApplicationContext(),
                                        "?????? ?????????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            } else {
                                //?????? ?????? ?????? ??? ????????? ?????? ??????
                                myDB_Reference.child(strHeader).child(strContact).setValue(null);
                                myDB_Reference.child(strHeader).child(strPhone).setValue(Contact_Value);
                                //????????? ????????????
                                Toast.makeText(getApplicationContext(),
                                        "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                                finish();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("Tag: ", "Failed to read value", error.toException());
                        }
                    });
                }
            }
        }
    }
    public boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}