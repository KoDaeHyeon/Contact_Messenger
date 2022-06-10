package com.inhatc.contact_messenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks {
    //Initialize Variable
    //변수 초기화

    CheckBox checkBox;
    GoogleApiClient googleApiClient;

    //Put SiteKey as a String
    //recaptcha 사이트 키를 String으로 정의
    String SiteKey = "6Lcxh10gAAAAAKUvRrPpy1IOQJ-bcAIK3wAMH_tH";

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

        //Assign Variable
        //승인 변수
        checkBox = findViewById(R.id.check_box);

        //Create Google Api Client
        //Api 생성
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(MainActivity.this)
                .build();
        googleApiClient.connect();

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set if condition when checkbox checked
                if (checkBox.isChecked()){
                    SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SiteKey)
                            .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                                @Override
                                public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                                    Status status = recaptchaTokenResult.getStatus();
                                    if((status != null) && status.isSuccess()){
                                        //Display Successful Message
                                        Toast.makeText(getApplicationContext()
                                                ,"Successfully Varified..."
                                                ,Toast.LENGTH_SHORT).show();
                                        checkBox.setTextColor(Color.GREEN);
                                    }
                                }
                            });
                }else{
                    //Default Checkbox text color
                    checkBox.setTextColor(Color.BLACK);
                }
            }
        });
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

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
                                Intent contentMain = new Intent(MainActivity.this, MainContact.class);
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
            Intent findQAIntent = new Intent(MainActivity.this, FindPW.class);
            startActivity(findQAIntent);
        }
    }
}