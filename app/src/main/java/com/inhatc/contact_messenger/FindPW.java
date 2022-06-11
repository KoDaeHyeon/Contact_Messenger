package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FindPW extends AppCompatActivity implements View.OnClickListener{
    EditText edtFindID;         //아이디
    EditText edtFindName;       //이름
    EditText edtFindPhoneNum;   //전화번호
    EditText edtFindA;          //답변

    TextView lblFindQprint;     //질문 출력 TextView
    TextView lblFindPW;         //비밀번호 출력 TextView

    String strID;
    String strName;
    String strPhone;
    String strA;                //답변 입력 용 String

    Button btnFindInfo;
    Button btnFindPW;

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    HashMap<String, Object> Contact_Value = new HashMap<>();
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        edtFindID = (EditText) findViewById(R.id.edt_FIndID);
        edtFindName = (EditText) findViewById(R.id.edt_FindName);
        edtFindPhoneNum = (EditText) findViewById(R.id.edt_FindPhoneNum);
        edtFindA = (EditText) findViewById(R.id.edt_FindA);

        lblFindQprint = (TextView) findViewById(R.id.lbl_FindQprint);
        lblFindPW = (TextView) findViewById(R.id.lbl_FindPW);

        btnFindInfo = (Button) findViewById(R.id.btn_FindInfo);
        btnFindPW = (Button) findViewById(R.id.btn_FindPW);
        btnFindInfo.setOnClickListener(this);
        btnFindPW.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        //정보 검색 버튼 클릭 시 이벤트
        if(view == btnFindInfo){
            strID = edtFindID.getText().toString().trim();
            strName = edtFindName.getText().toString().trim();
            strPhone = edtFindPhoneNum.getText().toString().trim();
            if(strID.equals("") || strName.equals("") || strPhone.equals("")){
                Toast.makeText(getApplicationContext(), "아이디, 이름, 전화번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                myDB_Reference.child("Member Information").child(strID).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                MemberInfo value = snapshot.getValue(MemberInfo.class);
                                if (value != null) {
                                    if (value.Name.equals(strName) && value.ID.equals(strID) && value.Phone.equals(strPhone)) {
                                        //ID, Name, PhoneNumber가 맞다면 해당 유저의 질문 출력
                                        lblFindQprint.setText(value.FindQ);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "아이디와 유저번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.w("Tag: ", "Failed to read value", error.toException());
                            }
                        });
            }
        }

        //비밀번호 검색 클릭 시 이벤트
        if(view == btnFindPW){
            strA = edtFindA.getText().toString().trim();
            if(strA.equals("")){
                Toast.makeText(getApplicationContext(), "답변을 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                myDB_Reference.child("Member Information").child(strID).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                MemberInfo value = snapshot.getValue(MemberInfo.class);
                                if (value != null) {
                                    //질문에 대한 답변이 올바르다면 비밀번호 출력
                                    if (value.FindA.equals(strA)) {
                                        lblFindPW.setText("이용자 님의 비밀번호는 " + value.PW + " 입니다.");
                                    }else{
                                        Toast.makeText(getApplicationContext(), "질문에 대한 답변이 틀립니다. 답변을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
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