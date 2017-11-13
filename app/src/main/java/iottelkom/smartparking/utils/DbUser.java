package iottelkom.smartparking.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by A450CC on 11/7/2017.
 */

public class DbUser {
    //class untuk menyimpan record
    public static class Akun{
        public String username;
        public String password;
    }

    private SQLiteDatabase db;
    private final OpenHelper dbHelper;

    public DbUser(Context c){
        dbHelper = new OpenHelper(c);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long insertAkun(String username, String password){
        ContentValues newVal = new ContentValues();
        newVal.put("USERNAME", username);
        newVal.put("PASSWORD", password);
        System.out.println(username);
        return db.insert("TB_USER", null, newVal);
    }

    //ambil data akun berdasarkan username
    public Akun getAkun(String username){
        Cursor cur = null;
        Akun A = new Akun();

        //kolom yang diambil
        String[] cols = new String[] {"ID", "USERNAME", "PASSWORD"};
        //parameter, akan mengganti ? pada USERNAME=?
        String[] param = {username};

        cur = db.query("TB_USER",cols,"USERNAME=?",param,null,null,null);

        if(cur.getCount()>0){   //ada data? ambil
            cur.moveToFirst();
            A.username = cur.getString(1);
            A.password = cur.getString(2);
        }
        cur.close();
        return A;
    }

    //ambil semua data akun (hati2 kalau datanya banyak)
    //menggunaka raw query
    public ArrayList<Akun> getAllUser(){
        Cursor cur = null;
        ArrayList<Akun> out = new ArrayList<>();
        cur = db.rawQuery("SELECT username, password FROM Tb_User Limit 10", null);
        if(cur.moveToFirst()){
            do{
                Akun akn = new Akun();
                akn.username = cur.getString(0);
                akn.password = cur.getString(1);
                out.add(akn);
            }while(cur.moveToNext());
        }
        cur.close();
        System.out.println(out);
        return out;
    }

    public void delAllUser(){
        db.delete("TB_USER",null,null);
    }
}
