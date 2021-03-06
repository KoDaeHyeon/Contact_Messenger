package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Value;

import java.lang.reflect.Member;
import java.util.HashMap;

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
    EditText edtRegFindQ;
    EditText edtRegFindA;

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
    int Uno;

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
        edtRegFindQ = (EditText)findViewById(R.id.edt_regQ);
        edtRegFindA = (EditText)findViewById(R.id.edt_regA);

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
                strID = edtRegID.getText().toString().trim();
                strPW =edtRegPW.getText().toString().trim();
                strPW2 = edtRegPW2.getText().toString().trim();
                strName = edtRegName.getText().toString().trim();
                strPhone = edtRegPhone.getText().toString().trim();
                strLati = edtRegLati.getText().toString().trim();
                strLong = edtRegLong.getText().toString().trim();
                strEmail = edtRegEmail.getText().toString().trim();
                strFindQ = edtRegFindQ.getText().toString().trim();
                strFindA = edtRegFindA.getText().toString().trim();
                Uno=(int)(Math.random() * (9999-1000+1))+1000;
                String strUno = String.valueOf(Uno);
                //?????? ????????? ???????????? ?????? 4?????? ??????
                // ???????????? ?????? HashMap??? put
                Member_Value.clear();
                Member_Value.put("Uno",strUno);
                Member_Value.put("Name",strName);
                Member_Value.put("ID",strID);
                Member_Value.put("PW",strPW);
                Member_Value.put("Phone",strPhone);
                Member_Value.put("Email",strEmail);
                Member_Value.put("Lati",strLati);
                Member_Value.put("Long",strLong);
                Member_Value.put("FindQ",strFindQ);
                Member_Value.put("FindA",strFindA);
                
                //????????????????????? ??? ID ????????? ????????? ??????
                myDB_Reference.child(strHeader).child(strID).child("ID")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if(value!=null) {
                            //?????? ???????????? ?????????
                            Toast.makeText(getApplicationContext(), "????????? ?????? ????????? ????????????.",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            //???????????? ????????? ID, PW, PW2, ??????, ?????????, ??????, ??? ??????????????? ??????
                            if (!strID.equals("") && !strPW.equals("")
                                    && !strPW2.equals("") && !strID.equals("")
                                    && !strName.equals("") && !strPhone.equals("")) {
                                if (strPW.equals(strPW2)) {
                                    if (!strFindQ.equals("") && !strFindA.equals("")) {
                                        if(!isStringDouble(strLati) || !isStringDouble(strLong)){
                                            Toast.makeText(getApplicationContext(),
                                                    "?????? ????????? ????????? ????????????.",
                                                    Toast.LENGTH_SHORT).show();
                                        }else {
                                            mSet_FirebaseDatabase(true);
                                            Toast.makeText(getApplicationContext(),
                                                    "???????????? ???????????????.",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "?????? ??????/?????? ??????????????????.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "??????????????? ????????? ?????????.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "????????? ????????? ?????????",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());

                    }
                });
                    
                
                break;

            case R.id.btn_regID:
                //ID ????????????
                strID = edtRegID.getText().toString().trim();
                if(!strID.equals("")) {
;                   myDB_Reference.child(strHeader).child(strID).child("ID")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.getValue(String.class);
                            if(value!=null) {
                                Toast.makeText(getApplicationContext(),
                                        "?????? ?????? ??????????????????.",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        "?????? ????????? ??????????????????.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("Tag: ","Failed to read value",error.toException());

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),
                            "???????????? ????????? ?????????.",
                            Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void mSet_FirebaseDatabase(boolean bFlag){
        if(bFlag){
            myDB_Reference.child(strHeader).child(strID).setValue(Member_Value);
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