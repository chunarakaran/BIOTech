package com.exportershouse.biotech.Adapter;

/**
 * Created by Shrey on 31-01-2018.
 */
public class DataAdapter
{
    public String id;
    public String ImageTitle;

    public String From_Date;
    public String To_Date;
    public String price;

    public String getId() {

        return id;
    }

    public void setId(String Id) {

        this.id = Id;
    }

    public String getImageTitle() {

        return ImageTitle;
    }

    public void setImageTitle(String Imagetitlename) {

        this.ImageTitle = Imagetitlename;
    }

    public String getFrom_Date(){
        return From_Date;
    }

    public void setFrom_Date(String from_Date){
        this.From_Date=from_Date;
    }

    public String getTo_Date(){
        return To_Date;
    }

    public void setTo_Date(String to_Date){
        this.To_Date=to_Date;
    }


}