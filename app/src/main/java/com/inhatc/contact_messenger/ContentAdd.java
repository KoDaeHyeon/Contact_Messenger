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

public class ContentAdd extends AppCompatActivity
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
    String strHeader = "contentList";

    Button btnAddGrab;
    Button btnAddSuccess;

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    HashMap<String, Object> Content_Value = new HashMap<>();

    Content content = new Content();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add);

        strHeader = getIntent().getStringExtra("myInfo") + " ContentList";

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
                                        content.set_ContentInfo(value.Uno, value.ID, value.Name, value.Phone,
                                                value.Email, value.Lati, value.Long);
                                        edtAddName.setText(content.Name);
                                        edtAddPhone.setText(content.Phone);
                                        edtAddEmail.setText(content.Email);
                                        edtAddLati.setText(content.Lati);
                                        edtAddLong.setText(content.Long);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "아이디와 유저번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
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

            Content_Value.clear();
            Content_Value.put("Uno",strNo);
            Content_Value.put("Name",strName);
            Content_Value.put("ID",strID);
            Content_Value.put("Phone",strPhone);
            Content_Value.put("Email",strEmail);
            Content_Value.put("Lati",strLati);
            Content_Value.put("Long",strLong);


            if(strName.equals("") || strPhone.equals("")) {
                Toast.makeText(getApplicationContext(), "필수 입력 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                myDB_Reference.child(strHeader).child(strPhone).child("Phone").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null) {
                            Toast.makeText(getApplicationContext(), "이미 등록되어있는 번호입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            //메인화면을 새로 열면서 새로고침 역할ㅅ
                            myDB_Reference.child(strHeader).child(strPhone).setValue(Content_Value);
                            Intent myInfoIntent = new Intent(ContentAdd.this, MainContent.class);
                            startActivity(myInfoIntent);
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
}