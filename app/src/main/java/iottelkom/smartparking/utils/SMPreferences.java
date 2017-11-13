package iottelkom.smartparking.utils;

/*
  Created by kawakibireku on 11/7/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class SMPreferences {
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    final public static String LOGGED_IN = "Logged In True";
    final public static String NOT_LOGIN = "Logged In False";

    final public static String KEY_LOGIN = "login";
    final public static String KEY_USERNAME = "username";

    public SMPreferences(Context context){
        this.context = context;
        this.pref = context.getSharedPreferences("Smarthome", MODE_PRIVATE);
        this.editor = pref.edit();
    }

    public void store(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String read(String key) {
        String value = pref.getString(key, null);
        //getting value of preferences
        if(key.equals("logged_in") && value==null){
            value = NOT_LOGIN;
        }

        return value;
    }

    public void clearAll(){
        editor.clear();
        editor.apply();
    }
}
