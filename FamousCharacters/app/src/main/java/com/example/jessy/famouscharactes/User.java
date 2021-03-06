package com.example.jessy.famouscharactes;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Jessy on 13-12-2017.
 */

@IgnoreExtraProperties
// create User class used to get Email and Highscore of a user.
public class User {
    public String Email;
    public Integer Highscore;

    public User(){}

    public User(String Email, Integer Highscore){
        this.Email = Email;
        this.Highscore = Highscore;
    }
}
