package com.example.jessy.famouscharactes;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Jessy on 6-12-2017.
 */
@IgnoreExtraProperties
class Person {
    public String name;
    public String image;

    public Person(){}

    public Person(String name, String image){
        this.name = name;
        this.image = image;
    }
}
