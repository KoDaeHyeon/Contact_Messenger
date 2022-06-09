package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Member;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;

    String strHeader = "Member Information";

    EditText edtID;
    EditText edtPWD;

    String strID;
    String strPWD;

    private Button btnRegister;
    private Button btnLogin;
    private Button btnQA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID=(EditText)findViewById(R.id.edt_ID);
        edtPWD=(EditText)findViewById(R.id.edt_PWD);

        btnLogin = (Button)findViewById(R.id.btn_Login);
        btnLogin.setOnClickListener(this);

        btnRegister = (Button)findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        btnQA = (Button)findViewById(R.id.btn_find);
        btnQA.setOnClickListener(this);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();
    }

    public void onClick(View v){
        if(v==btnLogin){
            strID =  edtID.getText().toString().trim();
            strPWD = edtPWD.getText().toString().trim();
            if(strID.equals("") || strPWD.equals("")){
                Toast.makeText(getApplicationContext(), "로그인 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            }else{
                myDB_Reference.child(strHeader).child(strID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MemberInfo value = snapshot.getValue(MemberInfo.class);
                        if(value!=null) {
                            if(value.ID.equals(strID) && value.PW.equals(strPWD)){
                                Intent contentMain = new Intent(MainActivity.this, MainContent.class);
                                //액티비티를 통해 계정 정보 전달
                                contentMain.putExtra("myInfo", value.ID);
                                startActivity(contentMain);
                                Toast.makeText(getApplicationContext(), value.Name + "님 어서오세요", Toast.LENGTH_SHORT).show();
                                finish(); //로그인 성공하면 액티비티 종료
                            }else{
                                Toast.makeText(getApplicationContext(), "로그인 정보를 확인해 주세요", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "로그인 정보를 확인해 주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());

                    }
                });
            }
        }
        else if(v== btnRegister){
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        }
        else if(v== btnQA){
            Intent findQAIntent = new Intent(MainActivity.this, FindIDPW.class);
            startActivity(findQAIntent);
        }
    }
}