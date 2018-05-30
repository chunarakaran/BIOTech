package com.exportershouse.biotech.Adapter;

/**
 * Created by Shrey on 31-01-2018.
 */
public class DataAdapter1
{
    public String id;
    public String PartyName;

    public String Odate;
    public String OrderNo;

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

    public String getOdate(){
        return Odate;
    }

    public void setOdate(String O_date){
        this.Odate=O_date;
    }

    public String getOrderNo(){
        return OrderNo;
    }

    public void setOrderNo(String Order_No){
        this.OrderNo=Order_No;
    }


}