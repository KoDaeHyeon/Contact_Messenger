package com.inhatc.contact_messenger;

public class Content {
    public String Uno;
    public String ID;
    public String Name;
    public String Phone;
    public String Email;
    public String Lati;
    public String Long;

    public Content(){

    }

    public void set_ContentInfo(String Uno, String ID, String Name, String Phone, String Email,
                               String Lati, String Long){
        this.Uno = Uno;
        this.ID = ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.Lati = Lati;
        this.Long = Long;
    }
}
