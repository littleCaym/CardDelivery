package com.example.carddelivery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myNewDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table zayavka ("+
                "id integer primary key autoincrement,"+
                "keyy text,"+
                "paysys text," +
                "valuta text," +
                "surname text," +
                "name text,"+
                "second_name text,"+
                "eng_name_sur text," +
                "birth text," +
                "email text," +
                "phone text," +
                "document text," +
                "nationality text," +
                "doc_num INT," +
                "issuance text," +
                "date_fil text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
