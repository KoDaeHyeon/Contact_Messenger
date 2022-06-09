package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.ArrayList;

public class MainContent extends AppCompatActivity
        implements View.OnClickListener{

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;
    ChildEventListener mChild;

    MemberInfo myAccount = new MemberInfo();
    TextView lblMainTop;

    ArrayList<String> aryContents;

    Button btnMyInfo;
    Button btnAddContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        lblMainTop = (TextView) findViewById(R.id.lbl_MainTop);

        //사용자의 정보 변경사항이 있으면 정보 불러옴 ex)이름 변경 -> 정보를 다시불러와 변경된 이름 출력
        initDatabase();

        btnMyInfo = (Button) findViewById(R.id.btn_MyInfo);
        btnMyInfo.setOnClickListener(this);
        btnAddContent = (Button) findViewById(R.id.btn_ContentAdd);
        btnAddContent.setOnClickListener(this);

        myDB_Reference.child("Member Information").child(getIntent().getStringExtra("myInfo")).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MemberInfo value = snapshot.getValue(MemberInfo.class);
                        if(value!=null) {
                            myAccount.set(value);
                        }
                        lblMainTop.setText(myAccount.Name+ "님 어서오세요.");

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == btnMyInfo){
            Intent myInfoIntent = new Intent(MainContent.this, MyInfo.class);
            myInfoIntent.putExtra("myInfo", myAccount.ID);
            startActivity(myInfoIntent);
        }else if(view == btnAddContent){
            Intent addContentIntent = new Intent(MainContent.this,ContentAdd.class);
            addContentIntent.putExtra("myInfo",myAccount.ID);
            startActivity(addContentIntent);
        }
    }

    private void initDatabase(){

        mChild = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myDB_Reference.child("Member Information").child(getIntent().getStringExtra("myInfo")).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                MemberInfo value = snapshot.getValue(MemberInfo.class);
                                if(value!=null) {
                                    myAccount.set(value);
                                }
                                lblMainTop.setText(myAccount.Name+ "님 어서오세요.");

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.w("Tag: ","Failed to read value",error.toException());
                            }
                        });
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        myDB_Reference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        myDB_Reference.removeEventListener(mChild);
    }
}

