package uk.co.alt236.btlescan.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.alt236.btlescan.R;

public class Searchbyroom extends AppCompatActivity {
    static SQLiteDatabase mDb;
    static DBHelper mHelper;
    static Cursor mCursor;

    static final ArrayList<String> dirArray = new ArrayList<String>();
    static final ArrayList<Integer> status = new ArrayList<Integer>();
    static ShowbeaconAdapter adapterDir ;
    String name="";
    TextView nametime,namecheck,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbyroom);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        Log.i("timenamecheck", "" + name);



        nametime = (TextView) findViewById(R.id.search_timename);
        namecheck = (TextView) findViewById(R.id.search_timename_check);
        time = (TextView) findViewById(R.id.search_time);

        String[] res = DB.selectime(getApplicationContext(), name);

        if(res[0]!=null) {
            nametime.setText(res[0]);
            time.setText(res[1]);

        }



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.showlistbeacon, menu);
        return true;
    }

    Boolean currentstatus=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            for(int i=0;i<status.size();i++){
                if(currentstatus==false){
                    status.set(i,View.VISIBLE);
                }

                else {
                    status.set(i,View.GONE);
                }
            }

            // set if false then true
            currentstatus = !currentstatus;

            adapterDir.notifyDataSetChanged();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void checkupdate (){
        mCursor  = mDb.rawQuery("SELECT "+DBHelper.COL_ITEM_NAME +" FROM " + DBHelper.TABLE_NAME,null );
        dirArray.clear();
        status.clear();
        mCursor.moveToFirst();
        while ( !mCursor.isAfterLast() ){
            dirArray.add( mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAME)));
            status.add(View.VISIBLE);
            mCursor.moveToNext();
        }
        adapterDir.notifyDataSetChanged();

    }



    @SuppressWarnings("StatementWithEmptyBody")

    public void onPause() {
        super.onPause();
        mHelper.close();
        mDb.close();
    }
}
