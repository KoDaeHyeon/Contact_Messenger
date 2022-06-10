package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainContact extends AppCompatActivity
        implements View.OnClickListener{

    FirebaseDatabase myFirebase;
    DatabaseReference myDB_Reference=null;
    ChildEventListener mChild;

    MemberInfo myAccount = new MemberInfo();
    TextView lblMainTop;

    ListView listView;

    ArrayList<String> contactList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    Button btnMyInfo;
    Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contact);

        myFirebase = FirebaseDatabase.getInstance();
        myDB_Reference = myFirebase.getReference();

        lblMainTop = (TextView) findViewById(R.id.lbl_MainTop);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, contactList);

        listView = (ListView)findViewById(R.id.Contact_View);
        listView.setAdapter(adapter);


        //사용자의 정보 변경사항이 있으면 정보 불러옴 ex)이름 변경 -> 정보를 다시불러와 변경된 이름 출력
        initDatabase();

        btnMyInfo = (Button) findViewById(R.id.btn_MyInfo);
        btnMyInfo.setOnClickListener(this);
        btnAddContact = (Button) findViewById(R.id.btn_ContactAdd);
        btnAddContact.setOnClickListener(this);

        // 초기 진입시 사용자 정보 불러옴
        myDB_Reference.child("Member Information").child(getIntent().getStringExtra("myInfo")).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MemberInfo value = snapshot.getValue(MemberInfo.class);
                        if(value!=null) {
                            myAccount.set(value);
                        }
                        lblMainTop.setText(myAccount.Name+ "님 어서오세요.");

                        // 연락처 데이터를 불러와서 리스트뷰에 표시ㅅㄷ
                        myFirebase.getReference(myAccount.ID + " ContactList").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                contactList.clear();
                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    String sValue = dataSnapshot.child("Name").getValue(String.class);
                                    contactList.add(sValue);
                                }
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Tag: ","Failed to read value",error.toException());
                    }
        });

    }

    @Override
    public void onClick(View view) {
        Log.e("click", myAccount.ID);
        if(view == btnMyInfo){
            Intent myInfoIntent = new Intent(MainContact.this, MyInfo.class);
            myInfoIntent.putExtra("myInfo", myAccount.ID);
            startActivity(myInfoIntent);
        }else if(view == btnAddContact){
            Intent addContentIntent = new Intent(MainContact.this, ContactAdd.class);
            addContentIntent.putExtra("myInfo",myAccount.ID);
            startActivity(addContentIntent);
        }
    }



    private void initDatabase(){

        mChild = new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            //DB상에 데이터 변경이 일어나면 실행
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
                                //연락처 목록 새로고침
                                myFirebase.getReference("test1" + " ContactList").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        contactList.clear();
                                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                            String sValue = dataSnapshot.child("Name").getValue(String.class);
                                            contactList.add(sValue);
                                            Log.e("name",sValue);
                                        }
                                        listView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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

