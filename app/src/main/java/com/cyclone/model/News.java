package com.cyclone.model;

/**
 * Created by solusi247 on 15/12/15.
 */
public class News {
    String id;
    String title;
    String date;
    String file ;
    String gambar ;
    String radio ;

    public News ( String id,String title,String date, String file, String gambar, String radio){

        this.id = id;
        this.title = title;
        this.date = date;
        this.file = file ;
        this.gambar = gambar ;
        this.radio = radio ;
    }
}
