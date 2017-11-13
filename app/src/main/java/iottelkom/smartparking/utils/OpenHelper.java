package iottelkom.smartparking.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by A450CC on 11/7/2017.
 */

public class OpenHelper extends SQLiteOpenHelper {
    //kalau ada upgrade, increment versi database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbUser.db";

    public static final String TABLE_CREATE =
            "CREATE TABLE TB_USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)";

    public OpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        //jika app diupgrade (diinstall yang baru) maka database akan dicreate ulang (data hilang)
        //jika tidak ingin hilang, bisa diproses disini
        db.execSQL("DROP TABLE IF EXISTS TB_AKUN");
    }
}
