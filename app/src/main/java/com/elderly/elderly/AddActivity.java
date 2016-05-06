package com.elderly.elderly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    SQLiteDatabase mDb;
    MyDbHelper mHelper;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHelper = new MyDbHelper(this);
        mDb = mHelper.getWritableDatabase();
        //"INSERT INTO Name (name) VALUES(XXX)"
      ;

        Button b = (Button) findViewById(R.id.saveButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText n = (EditText)findViewById(R.id.addname);
                String nn = n.getText().toString();
                mDb.execSQL("INSERT INTO "+MyDbHelper.TABLE_NAME+" ("+MyDbHelper.COL_NAME+") VALUES('"+nn+"')");
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return false;
    }


}
