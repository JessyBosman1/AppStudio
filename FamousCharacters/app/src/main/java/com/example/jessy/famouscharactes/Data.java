package com.example.jessy.famouscharactes;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Jessy on 6-12-2017.
 */

@IgnoreExtraProperties
// create Data class used to store name - image pairs in Firebase.
class Data {
    public String name;
    public String image;

    public Data(){}

    public Data(String name, String image){
        this.name = name;
        this.image = image;
    }
}
