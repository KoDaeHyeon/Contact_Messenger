package com.inhatc.contact_messenger;

import java.util.HashMap;
import java.util.Map;

public class Contact {
    public String Uno;
    public String ID;
    public String Name;
    public String Phone;
    public String Email;
    public String Lati;
    public String Long;

    public Contact(){

    }

    public void set_ContactInfo(String Uno, String ID, String Name, String Phone, String Email,
                               String Lati, String Long){
        this.Uno = Uno;
        this.ID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.Lati = Lati;
        this.Long = Long;
    }

    public String get_Uno(){return Uno;};
    public String get_ID(){return ID;};
    public String get_Name(){return Name;};
    public String get_Phone(){return Phone;};
    public String get_Email(){return Email;};
    public String get_Lati(){return Lati;};
    public String get_Long(){return Long;};

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("UNo", Uno);
        result.put("Name",ID);
        result.put("PW",Name);
        result.put("Phone",Phone);
        result.put("Email",Email);
        result.put("Lati",Lati);
        result.put("Long",Long);

        return result;
    };

}
