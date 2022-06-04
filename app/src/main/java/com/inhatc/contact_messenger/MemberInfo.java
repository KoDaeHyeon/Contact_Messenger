package com.inhatc.contact_messenger;

import android.widget.EditText;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;


public class MemberInfo {

    public int No;
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
    public MemberInfo(int No, String ID, String PW, String Name, String Phone, String Email,
                      String Lati, String Long, String FindQ, String FindA){
        this.No = No;
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
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("No", No);
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
