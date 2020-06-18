package com.example.ac_twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    public void onCreate() {

        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("mK6DijR7S6HRUSGRp64FC6ZfFM3umqFGJGpWMaBo")
                // if defined
                .clientKey("enVqDseOpkL72F0CxvJXKlSJJUDNngTZ14cxIaIT")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
