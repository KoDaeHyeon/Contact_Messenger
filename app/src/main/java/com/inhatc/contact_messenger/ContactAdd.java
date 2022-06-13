package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ContactAdd extends AppCompatActivity
                                implements View.OnClickListener{

    EditText edtAddID;
    EditText edtAddNo;
    EditText edtAddName;
    EditText edtAddPhone;
    EditText edtAddEmail;
    EditText edtAddLati;
    EditText edtAddLong;

    String strID;
    String strNo;
    String strName;
    String strPhone;
    String strEmail;
    String strLati;
    String strLong;
    String strHeader = "ContactList";

    Button btnAddGrab;
    Button btnAddSuccess;

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    HashMap<String, Object> Contact_Value = new HashMap<>();

    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        strHeader = getIntent().getStringExtra("myInfo") + " ContactList";

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        edtAddID = (EditText) findViewById(R.id.edt_AddID);
        edtAddNo = (EditText) findViewById(R.id.edt_AddNo);
        edtAddName = (EditText) findViewById(R.id.edt_AddName);
        edtAddPhone = (EditText) findViewById(R.id.edt_AddPhone);
        edtAddEmail = (EditText) findViewById(R.id.edt_AddEmail);
        edtAddLati = (EditText) findViewById(R.id.edt_AddLati);
        edtAddLong = (EditText) findViewById(R.id.edt_AddLong);

        btnAddGrab = (Button) findViewById(R.id.btn_AddGrab);
        btnAddSuccess = (Button) findViewById(R.id.btn_AddSuccess);
        btnAddGrab.setOnClickListener(this);
        btnAddSuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == btnAddGrab){
            strID = edtAddID.getText().toString().trim();
            strNo = edtAddNo.getText().toString().trim();
            if(strID.equals("") || strNo.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디와 유저번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                myDB_Reference.child("Member Information").child(strID).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                MemberInfo value = snapshot.getValue(MemberInfo.class);
                                if (value != null) {
                                    if (value.Uno.equals(strNo) && value.ID.equals(strID)) {
                                        contact.set_ContactInfo(value.Uno, value.ID, value.Name, value.Phone,
                                                value.Email, value.Lati, value.Long);
                                        edtAddName.setText(contact.Name);
                                        edtAddPhone.setText(contact.Phone);
                                        edtAddEmail.setText(contact.Email);
                                        edtAddLati.setText(contact.Lati);
                                        edtAddLong.setText(contact.Long);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "아이디와 유저번호를 확인해주세요.",
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
        }else if(view == btnAddSuccess){
            strID = edtAddID.getText().toString().trim();
            strNo = edtAddNo.getText().toString().trim();
            strName = edtAddName.getText().toString().trim();
            strPhone = edtAddPhone.getText().toString().trim();
            strEmail = edtAddEmail.getText().toString().trim();
            strLati = edtAddLati.getText().toString().trim();
            strLong = edtAddLong.getText().toString().trim();

            Contact_Value.clear();
            Contact_Value.put("Uno",strNo);
            Contact_Value.put("Name",strName);
            Contact_Value.put("ID",strID);
            Contact_Value.put("Phone",strPhone);
            Contact_Value.put("Email",strEmail);
            Contact_Value.put("Lati",strLati);
            Contact_Value.put("Long",strLong);


            if(strName.equals("") || strPhone.equals("")) {
                Toast.makeText(getApplicationContext(), "필수 입력 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else if(!isStringDouble(strLati) || !isStringDouble(strLong)){
                Toast.makeText(getApplicationContext(), "위치 정보가 실수가 아닙니다.", Toast.LENGTH_SHORT).show();
            }else {
                myDB_Reference.child(strHeader).child(strPhone).child("Phone").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null) {
                            Toast.makeText(getApplicationContext(), "이미 등록되어있는 번호입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            //추가후 닫기
                            myDB_Reference.child(strHeader).child(strPhone).setValue(Contact_Value);
                            Toast.makeText(getApplicationContext(), "추가가 완료 되었습니다.", Toast.LENGTH_SHORT).show();
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
    public boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}