package com.dilip.passwordprotectiondemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    EditText etEmail,etName,etPhone,etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeSQLCipher();

        etEmail = findViewById(R.id.email);
        etName = findViewById(R.id.name);
        etPhone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);
        Button fab = findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveData_in_db();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void InitializeSQLCipher() {

        try {
            AES.generateYek();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (item.getItemId()==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    private void saveData_in_db() throws Exception{
        /*data save into sqlite and encryption and decryption */
        String email = etEmail.getText().toString();
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        String created_at = AES.getFormatTime ();

        if (null != email) {
            email = AES.encrypt ( email );
        } else {
            email = "";
        }


        if (!name.isEmpty ()) {
            name = AES.encrypt ( name );
        } else {
            name = "";
        }


        if (!phone.isEmpty ()) {
            phone = AES.encrypt ( phone );
        } else {
            phone = "";
        }


        if (!address.isEmpty ()) {
            address = AES.encrypt ( address );
        } else {
            address = "";
        }





        /*check in sqlite that data is already prsent or not*/
        SQliteHelper helper = new SQliteHelper ( getApplicationContext (),SQliteHelper.dbName,null,SQliteHelper.version);
        SQLiteDatabase db = helper.getReadableDatabase("test123");;

        Cursor cursor = db.rawQuery ( "select * from user where email= '" + email + "'" , new String[] {} );


        if (cursor != null) {
            cursor.moveToFirst ();
        }
        if (cursor.getCount () > 0) {
            //Data is present in database so update query
            ContentValues values = new ContentValues ();
            values.put ( "email" , email );
            values.put ( "name" , name );
            values.put ( "phone" , phone );
            values.put ( "address" , address );
            long id = db.update ( "user" , values , "email" + " = ?" , new String[] { email } );
            Log.e ( "data updated :" , String.valueOf ( id ) );

        } else {
            /*insert in sqlite*/
            helper = new SQliteHelper ( getApplicationContext (),SQliteHelper.dbName,null,SQliteHelper.version );
            db = helper.getWritableDatabase ("test123");
            long id = helper.insertUser ( email , name , phone , address  , created_at , db );
            Log.e ( "data insert in sqlite :" , String.valueOf ( id ) );
        }

        startActivity(new Intent(this,SecondActivity.class).putExtra("email",email));
    }
}
