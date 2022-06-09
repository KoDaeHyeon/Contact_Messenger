package com.inhatc.contact_messenger;

import android.widget.EditText;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;


public class MemberInfo {

    public String Uno;
    public String ID;
    public String PW;
    public String Name;
    public String Phone;
    public String Email;
    public String Lati;
    public String Long;
    public String FindQ;
    public String FindA;

    public MemberInfo(){

    }
    public MemberInfo(String Uno, String ID, String PW, String Name, String Phone, String Email,
                      String Lati, String Long, String FindQ, String FindA){
        this.Uno = Uno;
        this.ID = ID;
        this.PW=PW;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.Lati = Lati;
        this.Long = Long;
        this.FindQ = FindQ;
        this.FindA = FindA;
    }

    public void set (MemberInfo memberInfo){
        set_MemberInfo(memberInfo.Uno, memberInfo.ID, memberInfo.PW, memberInfo.Name, memberInfo.Phone, memberInfo.Email,
                memberInfo.Lati, memberInfo.Long, memberInfo.FindQ, memberInfo.FindA);
    }

    public void set_MemberInfo(String Uno, String ID, String PW, String Name, String Phone, String Email,
                      String Lati, String Long, String FindQ, String FindA){
        this.Uno = Uno;
        this.ID = ID;
        this.PW=PW;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.Lati = Lati;
        this.Long = Long;
        this.FindQ = FindQ;
        this.FindA = FindA;
    }
    public String get_Uno(){return Uno;};
    public String get_ID(){return ID;};
    public String get_PW(){return PW;};
    public String get_Name(){return Name;};
    public String get_Phone(){return Phone;};
    public String get_Email(){return Email;};
    public String get_Lati(){return Lati;};
    public String get_Long(){return Long;};
    public String get_FindQ(){return FindQ;};
    public String get_FindA(){return FindA;};

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("UNo", Uno);
        result.put("Name",ID);
        result.put("ID",PW);
        result.put("PW",Name);
        result.put("Phone",Phone);
        result.put("Email",Email);
        result.put("Lati",Lati);
        result.put("Long",Long);
        result.put("FindQ",FindQ);
        result.put("FindA",FindA);

        return result;
    }
}
