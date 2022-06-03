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

    public MemberInfo(){

    }
    public MemberInfo(int No, String ID, String PW, String Name, String Phone, String Email,
                      String Lati, String Long){
        this.No = No;
        this.ID = ID;
        this.PW=PW;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.Lati = Lati;
        this.Long = Long;
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

        return result;
    }
}
