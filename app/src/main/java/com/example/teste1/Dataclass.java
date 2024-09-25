package com.example.teste1;

public class Dataclass {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
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

    public String getDataImage() {
        return dataImage;
    }

    public Dataclass(String dataImage, String dataLang, String dataDesc, String dataTitle) {
        this.dataImage = dataImage;
        this.dataLang = dataLang;
        this.dataDesc = dataDesc;
        this.dataTitle = dataTitle;
    }
    public Dataclass(){

    }

}
