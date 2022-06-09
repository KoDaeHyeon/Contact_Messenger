package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MyInfo extends AppCompatActivity implements View.OnClickListener {

    MemberInfo myAccount = new MemberInfo();
    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    TextView lblMyInfoNo;
    TextView lblMyInfoID;

    EditText edtMyInfoPW;
    EditText edtMyInfoPW2;
    EditText edtMyInfoName;
    EditText edtMyInfoEmail;
    EditText edtMyInfoPhone;
    EditText edtMyInfoLati;
    EditText edtMyInfoLong;
    EditText edtMyInfoFindQ;
    EditText edtMyInfoFindA;

    Button btnMyInfoSuccess;

    String strUno;
    String strID;
    String strPW;
    String strPW2;
    String strName;
    String strPhone;
    String strLati;
    String strLong;
    String strEmail;
    String strFindQ;
    String strFindA;
    String strHeader = "Member Information";

    HashMap<String, Object> Member_Value = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        lblMyInfoNo = (TextView) findViewById(R.id.lbl_myNo2) ;
        lblMyInfoID = (TextView) findViewById(R.id.lbl_myID2);
        edtMyInfoPW = (EditText) findViewById(R.id.edt_myPW);
        edtMyInfoPW2 = (EditText) findViewById(R.id.edt_myPW2);
        edtMyInfoName = (EditText) findViewById(R.id.edt_myName);
        edtMyInfoPhone = (EditText) findViewById(R.id.edt_myPhone);
        edtMyInfoLati = (EditText) findViewById(R.id.edt_myLati);
        edtMyInfoLong = (EditText) findViewById(R.id.edt_myLong);
        edtMyInfoEmail = (EditText)findViewById(R.id.edt_myEmail);
        edtMyInfoFindQ = (EditText)findViewById(R.id.edt_myQ);
        edtMyInfoFindA = (EditText)findViewById(R.id.edt_myA);

        btnMyInfoSuccess = (Button)findViewById(R.id.btn_MyInfoSuccess);
        btnMyInfoSuccess.setOnClickListener(this);

        myDB_Reference.child("Member Information").child(getIntent().getStringExtra("myInfo")).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MemberInfo value = snapshot.getValue(MemberInfo.class);
                        if(value!=null) {
                            myAccount.set(value);
                        }
                        lblMyInfoNo.setText(myAccount.Uno);
                        lblMyInfoID.setText(myAccount.ID);
                        edtMyInfoPW.setText(myAccount.PW);
                        edtMyInfoName.setText(myAccount.Name);
                        edtMyInfoPhone.setText(myAccount.Phone);
                        edtMyInfoLati.setText(myAccount.Lati);
                        edtMyInfoLong.setText(myAccount.Long);
                        edtMyInfoEmail.setText(myAccount.Email);
                        edtMyInfoFindQ.setText(myAccount.FindQ);
                        edtMyInfoFindA .setText(myAccount.FindA);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnMyInfoSuccess) {

            strUno = lblMyInfoNo.getText().toString().trim();
            strID = lblMyInfoID.getText().toString().trim();
            strPW = edtMyInfoPW.getText().toString().trim();
            strPW2 = edtMyInfoPW2.getText().toString().trim();
            strName = edtMyInfoName.getText().toString().trim();
            strPhone = edtMyInfoPhone.getText().toString().trim();
            strLati = edtMyInfoLati.getText().toString().trim();
            strLong = edtMyInfoLong.getText().toString().trim();
            strEmail = edtMyInfoEmail.getText().toString().trim();
            strFindQ = edtMyInfoFindQ.getText().toString().trim();
            strFindA = edtMyInfoFindA.getText().toString().trim();
            // 적어놓은 정보 HashMap에 put
            Member_Value.clear();
            Member_Value.put("Uno", strUno);
            Member_Value.put("ID", strID);
            Member_Value.put("Name", strName);
            Member_Value.put("PW", strPW);
            Member_Value.put("Phone", strPhone);
            Member_Value.put("Email", strEmail);
            Member_Value.put("Lati", strLati);
            Member_Value.put("Long", strLong);
            Member_Value.put("FindQ", strFindQ);
            Member_Value.put("FindA", strFindA);

            if (!strID.equals("") && !strPW.equals("") && !strPW2.equals("") && !strID.equals("")
                    && !strName.equals("") && !strPhone.equals("")) {
                if (strPW.equals(strPW2)) {
                    if (!strFindQ.equals("") && !strFindA.equals("")) {

                        myDB_Reference.child(strHeader).child(strID).setValue(Member_Value);

                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "찾기 질문/답을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "정보를 입력해 주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

}