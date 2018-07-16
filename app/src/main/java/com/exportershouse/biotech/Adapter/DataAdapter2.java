package com.exportershouse.biotech.Adapter;

/**
 * Created by Shrey on 31-01-2018.
 */
public class DataAdapter2
{
    public String id;
    public String PartyName;

    public String Idate;
    public String Istatus;

    public String getId() {

        return id;
    }

    public void setId(String Id) {

        this.id = Id;
    }

    public String getPartyName() {

        return PartyName;
    }

    public void setPartyName(String Party_Name) {

        this.PartyName = Party_Name;
    }

    public String getInqdate(){
        return Idate;
    }

    public void setInqdate(String I_date){
        this.Idate=I_date;
    }

    public String getstatus(){
        return Istatus;
    }

    public void setOrderNo(String I_status){
        this.Istatus=I_status;
    }


}