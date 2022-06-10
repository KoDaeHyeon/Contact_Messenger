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
                //회원 보안용 식별번호 랜덤 4자리 생성
                // 적어놓은 정보 HashMap에 put
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
                
                //파이어베이스에 쓴 ID 객체가 있는지 확인
                myDB_Reference.child(strHeader).child(strID).child("ID").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if(value!=null) {
                            //이미 아이디가 있다면
                            Toast.makeText(getApplicationContext(), "아이디 중복 검사를 해주세요.", Toast.LENGTH_SHORT).show();
                        }else {
                            //아이디가 없으면 ID, PW, PW2, 이름, 폰번호, 질문, 답 써져있는지 확인
                            if (!strID.equals("") && !strPW.equals("") && !strPW2.equals("") && !strID.equals("")
                                    && !strName.equals("") && !strPhone.equals("")) {
                                if (strPW.equals(strPW2)) {
                                    if (!strFindQ.equals("") && !strFindA.equals("")) {
                                        if(!isStringDouble(strLati) || !isStringDouble(strLong)){
                                            Toast.makeText(getApplicationContext(), "위치 정보가 실수가 아닙니다.", Toast.LENGTH_SHORT).show();
                                        }else {
                                            mSet_FirebaseDatabase(true);
                                            Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
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
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());

                    }
                });
                    
                
                break;

            case R.id.btn_regID:
                //ID 중복검사
                strID = edtRegID.getText().toString().trim();
                if(!strID.equals("")) {
;                   myDB_Reference.child(strHeader).child(strID).child("ID").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.getValue(String.class);
                            if(value!=null) {
                                Toast.makeText(getApplicationContext(), "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("Tag: ","Failed to read value",error.toException());

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
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