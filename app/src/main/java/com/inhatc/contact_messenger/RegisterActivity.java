package com.inhatc.contact_messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
                               implements View.OnClickListener {

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference = null;

    HashMap<String, Object> Member_Value = null;

    EditText edtRegID;
    EditText edtRegPW;
    EditText edtRegPW2;
    EditText edtRegName;
    EditText edtRegEmail;
    EditText edtRegPhone;
    EditText edtRegLati;
    EditText edtRegLong;

    String strID;
    String strPW;
    String strPW2;
    String strName;
    String strPhone;
    String strLati;
    String strLong;
    String strEmail;
    String strHeader = "Member Information";
    String strNum;

    int MemberNum=1234;

    Button btnRegId;
    Button btnRegSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegID = (EditText) findViewById(R.id.edt_regID);
        edtRegPW = (EditText) findViewById(R.id.edt_regPW);
        edtRegPW2 = (EditText) findViewById(R.id.edt_regPW2);
        edtRegName = (EditText) findViewById(R.id.edt_regName);
        edtRegPhone = (EditText) findViewById(R.id.edt_regPhone);
        edtRegLati = (EditText) findViewById(R.id.edt_regLati);
        edtRegLong = (EditText) findViewById(R.id.edt_regLong);
        edtRegEmail = (EditText)findViewById(R.id.edt_regEmail);

        btnRegId = (Button) findViewById(R.id.btn_regID);
        btnRegId.setOnClickListener(this);

        btnRegSuccess = (Button) findViewById(R.id.btn_regSuccess);
        btnRegSuccess.setOnClickListener(this);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        Member_Value = new HashMap<>();

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_regSuccess:
                strID = edtRegID.getText().toString();
                strPW =edtRegPW.getText().toString();
                strPW2 = edtRegPW2.getText().toString();
                strName = edtRegName.getText().toString();
                strPhone = edtRegPhone.getText().toString();
                strLati = edtRegLati.getText().toString();
                strLong = edtRegLong.getText().toString();
                strEmail = edtRegEmail.getText().toString();

                Member_Value.put("Name",strID);
                Member_Value.put("ID",strPW);
                Member_Value.put("PW",strName);
                Member_Value.put("Phone",strPhone);
                Member_Value.put("Email",strEmail);
                Member_Value.put("Lati",strLati);
                Member_Value.put("Long",strLong);

                mSet_FirebaseDatabase(true);

                Toast.makeText(getApplicationContext(),"회원가입 되었습니다.", Toast.LENGTH_SHORT).show();

        }
    }

    private void mSet_FirebaseDatabase(boolean bFlag){
        if(bFlag){
            myDB_Reference.child(strHeader).child(String.valueOf(MemberNum)).setValue(Member_Value);
        }
    }
}