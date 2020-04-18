package com.dilip.passwordprotectiondemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.sqlcipher.database.SQLiteDatabase;

public class SecondActivity extends AppCompatActivity {
    private EditText etEmail,etName,etPhone,etAddress;
    private String email ,name,phone,address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etEmail = findViewById(R.id.email);
        etName = findViewById(R.id.name);
        etPhone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);

        String emailID = getIntent().getExtras().getString("email");
        try {
            fetchDataFromDatabase(emailID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchDataFromDatabase(String emailid) throws Exception {


        SQliteHelper helper = new SQliteHelper ( getApplicationContext (),SQliteHelper.dbName,null,SQliteHelper.version);
        SQLiteDatabase db = helper.getReadableDatabase("test123");

        Cursor cursor = db.rawQuery ( "select * from user where email= '" + emailid + "'" , new String[] {} );

        if (cursor != null) {
            cursor.moveToFirst ();
        }
        if (cursor.getCount () > 0) {
            do {

                int id = cursor.getInt ( 0 );
                 email = cursor.getString ( 1 );
                 name = cursor.getString ( 2 );
                 phone = cursor.getString ( 3 );
                 address = cursor.getString ( 4 );



                if (!email.isEmpty ()) {
                    email = AES.decrypt ( email );
                } else {
                    email = "";
                }


                if (!name.isEmpty ()) {
                    name = AES.decrypt ( name );
                } else {
                    name = "";
                }


                if (!phone.isEmpty ()) {
                    phone = AES.decrypt ( phone );
                } else {
                    phone = "";
                }

                if (!address.isEmpty ()) {
                    address = AES.decrypt ( address );
                } else {
                    address = "";
                }
                Log.e ( "data fetch from db:" , "email:"+email+ " name: "+name +" phone: "+phone +" address: "+address );

            } while (cursor.moveToNext ());

        }


        etEmail.setText(email);
        etName.setText(name);
        etPhone.setText(phone);
        etAddress.setText(address);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId()==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
