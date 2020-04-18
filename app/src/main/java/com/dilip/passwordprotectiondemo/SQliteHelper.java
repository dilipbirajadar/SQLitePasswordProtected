package com.dilip.passwordprotectiondemo;

import android.content.ContentValues;
import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class SQliteHelper extends SQLiteOpenHelper {
    public static final String dbName = "demodatabase";
    public static final int version = 1;

    public SQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //*creating new databse table for the User feilds •
        //•	email
        //•	name
        //•	phone
        //•	address number
        //•	*/
        String sql_query = "create table user(_id integer primary key autoincrement, email text, name text, " +
                " phone text, address text,  created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(sql_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// If you need to add a column
        if (newVersion > oldVersion) {
            String sql_query = "create table user(_id integer primary key autoincrement, email text, name text, " +
                    " phone text, address text,  created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";
            db.execSQL(sql_query);
        }
    }


    public long insertUser(String email, String name, String phone, String address, String createdAt, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("name", name);
        values.put("phone", phone);
        values.put("address", address);
        values.put("created_at", createdAt);
        return db.insert("user",null,values);
    }

}
