package com.example.bus_reservation;



public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;

    private  String dataDescTime;

    private  String dataLangTime;

    private  String dataPrice;

    private  String dataAvilability;
    private String dataImage;

    private String userUid;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public  String getDataDescTime(){ return  dataDescTime;}

    public  String getDataLangTime(){ return dataLangTime;}

    public String getDataPrice(){ return dataPrice;}

    public String getDataAvilability() { return dataAvilability;}



    public String getDataImage() {
        return dataImage;
    }

    public DataClass(String dataTitle, String dataDesc, String dataLang,String dataDescTime,String dataLangTime,String dataPrice,String dataAvilability, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataDescTime=dataDescTime;
        this.dataLangTime=dataLangTime;
        this.dataPrice=dataPrice;
        this.dataAvilability=dataAvilability;
        this.dataImage = dataImage;
    }
    public DataClass(){

    }
}
