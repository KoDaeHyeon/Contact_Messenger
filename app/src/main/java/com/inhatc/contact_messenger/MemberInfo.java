package com.inhatc.contact_messenger;

import android.widget.EditText;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;


public class MemberInfo {

    public int No;
    public String ID;
    private String PW;
    public String Name;
    public String Phone;
    public String Email;
    public String Lati;
    public String Long;

    public MemberInfo(){

    }
    public MemberInfo(int num){this.No = num;}
}
