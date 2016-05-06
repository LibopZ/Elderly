package com.elderly.elderly;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> name = new ArrayList<>();
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t2= new Intent(getApplicationContext(), AddActivity.class);
                startActivity(t2);
            }
        });



        itemAdapter = new ItemAdapter(getApplicationContext(), name);

    }

    @Override
    protected void onResume() {
        super.onResume();

        MyDbHelper mHelper = new MyDbHelper(this);
        SQLiteDatabase mDb = mHelper.getWritableDatabase();

        Cursor mCursor = mDb.rawQuery("SELECT "+MyDbHelper.COL_NAME+" FROM "+MyDbHelper.TABLE_NAME ,null);
        mCursor.moveToFirst();
        name.clear();
        while ( !mCursor.isAfterLast() ){
            name.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_NAME)));
            mCursor.moveToNext();
        }


        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent t= new Intent(getApplicationContext(), Main2.class);
                startActivity(t);
            }
        });
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

        return super.onOptionsItemSelected(item);
    }
}
