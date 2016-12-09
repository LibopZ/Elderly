package uk.co.alt236.btlescan.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.btlescan.R;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.adapters.LeDeviceListAdapter;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.btlescan.util.Constants;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;


public class DisplayImage extends Activity {
    private MyImage image;
    private ImageView imageView;
    private TextView description;
    private String jstring;

    static SQLiteDatabase mDb;
    static DBHelper mHelper;
    static Cursor mCursor;
    TextView showname,show_roomdis;
    TextView showdistance,showroom,showunit,show_unit2,show_room_dis,show_room_address;
    Button scan;


    boolean isScan = false;

    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothLeDeviceStore mDeviceStore;
    IBeaconManufacturerData iBeaconData;
    IBeaconDevice iBeacon;
    String name;
    int count =0;
    int j=0;
    int x=0;
    int []node1= new int[20];
    int []node2= new int[20];
    int []node3= new int[20];
    int []node4= new int[20];
    int []node5= new int[20];
    int []node6= new int[20];
    int []node7= new int[20];
    int []node8= new int[20];
    int []node9= new int[20];
    int []node10= new int[20];
    int []node11= new int[20];
    int []node12= new int[20];
    int []node13= new int[20];
    int []node14= new int[20];
    int []node15= new int[20];
    int checkc=0,temps=0,jj=0;
    double []tempresult = new double[15];
    double []newresult = new double[15];


    ArrayList nameroom = new ArrayList();
    ArrayList adressroom = new ArrayList();
    double z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12,z13,z14,z15,outputnode1,outputnode2,outputnode3,result,lower,upper;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        imageView = (ImageView) findViewById(R.id.display_image_view);
   //      description = (TextView) findViewById(R.id.text_view_description);
        Bundle extras = getIntent().getExtras();
         name = extras.getString("name");


        mBluetoothUtils = new BluetoothUtils(this);
        mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();

        mDeviceStore = new BluetoothLeDeviceStore();

        scan = (Button) findViewById(R.id.scan);
        showname = (TextView) findViewById(R.id.show_name_beaconn);
        showroom = (TextView) findViewById(R.id.show_room);
        showunit = (TextView) findViewById(R.id.show_unit);
        show_unit2 = (TextView) findViewById(R.id.show_unit2);
        showdistance = (TextView) findViewById(R.id.show_distance_beacon);
//       show_room_dis = (TextView) findViewById(R.id.show_room_dis);
      //  show_room_address = (TextView) findViewById(R.id.show_roomadd);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });


        if (extras != null) {
            jstring = extras.getString("IMAGE");
        }
        image = getMyImage(jstring);
//      description.setText(image.toString());
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        imageView.setImageBitmap(ImageResizer
                .decodeSampledBitmapFromFile(image.getPath(), width, height));
    }




    final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
            mDeviceStore.addDevice(deviceLe);
            final EasyObjectCursor<BluetoothLeDevice> c = mDeviceStore.getDeviceCursor();
            try{
                iBeaconData = new IBeaconManufacturerData(deviceLe);
                iBeacon = new IBeaconDevice(deviceLe);
            }catch (Exception e){iBeaconData= null;}
            runOnUiThread(new Runnable() {
                @Override
                public void run() {



                    if(iBeaconData != null) {


                        ImageView signal = (ImageView) findViewById(R.id.image_signal);
                        show_roomdis = (TextView) findViewById(R.id.show_roomdis);
                        showroom = (TextView) findViewById(R.id.show_room);


                        mHelper = new DBHelper(getApplicationContext());
                        mDb = mHelper.getWritableDatabase();
                        mCursor = mDb.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAMEQ  ,null);
                        mCursor.moveToFirst();


                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => "+c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = df.format(c.getTime());


                        while ( !mCursor.isAfterLast() ){

                            nameroom.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ITEM_NAMEQ)));
                            adressroom.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.COL_ADDRESSQ)));
                            mCursor.moveToNext();
                            count++;
                        }

                        mHelper.close();
                        mDb.close();


                        for (int i=0;i<=nameroom.size()-1;i++){
                            if(adressroom.get(i).equals(iBeacon.getAddress())&&Double.parseDouble(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()))<=10){
                                showroom.setText(""+nameroom.get(i));
                             //   show_room_address.setText(""+System.currentTimeMillis());
                                show_roomdis.setText(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()));
                                DB.insertime(getApplicationContext(),""+nameroom.get(i),name,formattedDate);
                            }
                            else {
                                showroom.setText("   ");
                            }
                            Log.i("adressroomsss", "" + adressroom.get(i));
                   /*         showroom.setText(""+nameroom.get(i));
                            //show_room_address.setText(iBeacon.getAddress());
                            show_roomdis.setText(Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy()));*/
                        }
                        String[] res = DB.selectitem(getApplicationContext(), name);
                        if(res[0]!=null) {
                            showname.setText(res[0]);

                            show_unit2.setText(" Meter");
                            //tempresult[checkc]=iBeacon.getAccuracy();
                            result=result+iBeacon.getAccuracy();

                            checkc++;



                            if(checkc==20){
                                result=result/checkc;
                               /* Arrays.sort(tempresult);
                                lower = (1/4*checkc+1)-1.5*((3/4*checkc+1)-(1/4*checkc+1));
                                upper = (3/4*checkc+1)+1.5*((3/4*checkc+1)-(1/4*checkc+1));
                                Log.i("lower", "" + lower);
                                Log.i("upper", "" + upper);
                                for(int i=0;i<checkc;i++){

                                    Log.i("xxxx ", "" + tempresult[i]);

                                    if(tempresult[i]<=lower||tempresult[i]>=upper){
                                        jj++;
                                        Log.i("jj", "" + jj);
                                    }else{
                                        newresult[i]=tempresult[i];


                                    }
                                }

                                for(int i=0;i<temps;i++){

                                    result=result+newresult[i];

                                    Log.i("newresult", "" + result);*/
                                showunit.setText(result+"");

                                if(result<5){
                                    showdistance.setText("Near");
                                    showunit.setText("1-3");
                                    signal.setImageResource(R.drawable.ic_signal_4);

                                }else if (result>5&&result<10){
                                    signal.setImageResource(R.drawable.ic_signal_3);
                                    showunit.setText("3-5");
                                    showdistance.setText("Medium");
                                }
                                else if (result>10&&result<15){
                                    signal.setImageResource(R.drawable.ic_signal_2);
                                    showunit.setText("5-7");
                                    showdistance.setText("Far");
                                }
                                else if (result>15&&result<20){
                                    signal.setImageResource(R.drawable.ic_signal_1);
                                    showunit.setText("7-12");
                                    showdistance.setText("Very Far");
                                }
                                else if (result>20){
                                    signal.setImageResource(R.drawable.ic_signal_0);
                                    showdistance.setText("Out Of Range");
                                }
                                Log.i("RES", "" + res[1]);
                                Log.i("UUID", device.getAddress());

                                checkc=0;

                                }







                            }





                            if (res[1].equals(iBeacon.getAddress())&&x<15) {
                                node1[x]=iBeacon.getRssi();
                                node2[x]=iBeacon.getRssi();
                                node3[x]=iBeacon.getRssi();
                                node4[x]=iBeacon.getRssi();
                                node5[x]=iBeacon.getRssi();
                                node6[x]=iBeacon.getRssi();
                                node7[x]=iBeacon.getRssi();
                                node8[x]=iBeacon.getRssi();
                                node9[x]=iBeacon.getRssi();
                                node10[x]=iBeacon.getRssi();
                                node11[x]=iBeacon.getRssi();
                                node12[x]=iBeacon.getRssi();
                                node13[x]=iBeacon.getRssi();
                                node14[x]=iBeacon.getRssi();
                                node15[x]=iBeacon.getRssi();
                                Log.i("Nodetest", "" + node1[x]);
                                x++;
                            }



                         if(x==7){
                             /*Node1*/
                             z1=(-9.287047438)+(1.514974891)*node1[7];
                             z1=z1+(-9.287047438)+(0.31679212)*node1[6];
                             z1=z1+(-9.287047438)+(-3.295696508)*node1[5];
                             z1=z1+(-9.287047438)+(15.60420553)*node1[4];
                             z1=z1+(-9.287047438)+(5.459554424)*node1[3];
                             z1=z1+(-9.287047438)+(-1.839865245)*node1[2];
                             z1=z1+(-9.287047438)+(-1.373809894)*node1[1];
                             z1=z1+(-9.287047438)+(-16.33488642)*node1[0];
                             String str = String.format("%1.5f", z1);
                             z1 = Double.valueOf(str);
                             z1=1/(1 +Math.pow(2.71828,(-z1)));
                             str = String.format("%1.5f", z1);
                             z1 = Double.valueOf(str);
                             Log.i("ValueOfZ1", ""+z1);

                             /*Node2*/

                             z2=(0.740262661)+(-3.227215712)*node2[7];
                             z2=z2+(0.740262661)+(-12.13245244)*node2[6];
                             z2=z2+(0.740262661)+(-0.008174007)*node2[5];
                             z2=z2+(0.740262661)+(2.941180886)*node2[4];
                             z2=z2+(0.740262661)+(-6.606772456)*node2[3];
                             z2=z2+(0.740262661)+(-6.425030447)*node2[2];
                             z2=z2+(0.740262661)+(4.683528212)*node2[1];
                             z2=z2+(0.740262661)+(-4.881378554)*node2[0];
                             str = String.format("%1.5f", z2);
                             z2 = Double.valueOf(str);
                             z2=1/(1 +Math.pow(2.71828,(-z2)));
                             str = String.format("%1.5f", z2);
                             z2 = Double.valueOf(str);
                             Log.i("ValueOfZ2", ""+z2);

                               /*Node3*/

                             z3=(0.659020553)+(-4.25206212)*node3[7];
                             z3=z3+(0.659020553)+(4.160439639)*node3[6];
                             z3=z3+(0.659020553)+(-13.29344606)*node3[5];
                             z3=z3+(0.659020553)+(-3.829927066)*node3[4];
                             z3=z3+(0.659020553)+(1.480920083)*node3[3];
                             z3=z3+(0.659020553)+(-6.412849023)*node3[2];
                             z3=z3+(0.659020553)+(-9.676107527)*node3[1];
                             z3=z3+(0.659020553)+(1.577371903)*node3[0];
                             str = String.format("%1.5f", z3);
                             z3 = Double.valueOf(str);
                             z3=1/(1 +Math.pow(2.71828,(-z3)));
                             str = String.format("%1.5f", z3);
                             z3 = Double.valueOf(str);
                             Log.i("ValueOfZ3", ""+z3);

                              /*Node4*/

                             z4=(-45.95512632)+(-3.621035891)*node4[7];
                             z4=z4+(-45.95512632)+(-2.544705339)*node4[6];
                             z4=z4+(-45.95512632)+(5.9088071)*node4[5];
                             z4=z4+(-45.95512632)+(18.80727774)*node4[4];
                             z4=z4+(-45.95512632)+(34.75012362)*node4[3];
                             z4=z4+(-45.95512632)+(4.416423421)*node4[2];
                             z4=z4+(-45.95512632)+(3.78907937)*node4[1];
                             z4=z4+(-45.95512632)+(0.711712344)*node4[0];
                             str = String.format("%1.5f", z4);
                             z4 = Double.valueOf(str);
                             z4=1/(1 +Math.pow(2.71828,(-z4)));
                             str = String.format("%1.5f", z4);
                             z4 = Double.valueOf(str);
                             Log.i("ValueOfZ4", ""+z4);

                             /*Node5*/

                             z5=(9.928355046)+(-6.165180401)*node5[7];
                             z5=z5+(9.928355046)+(-1.689977964)*node5[6];
                             z5=z5+(9.928355046)+(-8.435232138)*node5[5];
                             z5=z5+(9.928355046)+(-4.471568917)*node5[4];
                             z5=z5+(9.928355046)+(-4.13105435)*node5[3];
                             z5=z5+(9.928355046)+(-5.55318845)*node5[2];
                             z5=z5+(9.928355046)+(1.990868587)*node5[1];
                             z5=z5+(9.928355046)+(-9.996652981)*node5[0];
                             str = String.format("%1.5f", z5);
                             z5 = Double.valueOf(str);
                             z5=1/(1 +Math.pow(2.71828,(-z5)));
                             str = String.format("%1.5f", z5);
                             z5 = Double.valueOf(str);
                             Log.i("ValueOfZ5", ""+z5);

                                /*Node6*/

                             z6=(28.38449445)+(-11.17337171)*node6[7];
                             z6=z6+(28.38449445)+(-8.888757763)*node6[6];
                             z6=z6+(28.38449445)+(-10.66147814)*node6[5];
                             z6=z6+(28.38449445)+(-5.692637617)*node6[4];
                             z6=z6+(28.38449445)+(-7.101765762)*node6[3];
                             z6=z6+(28.38449445)+(-7.614967733)*node6[2];
                             z6=z6+(28.38449445)+(-6.118834963)*node6[1];
                             z6=z6+(28.38449445)+(-16.05546553)*node6[0];
                             str = String.format("%1.5f", z6);
                             z6 = Double.valueOf(str);
                             z6=1/(1 +Math.pow(2.71828,(-z6)));
                             str = String.format("%1.5f", z6);
                             z6 = Double.valueOf(str);
                             Log.i("ValueOfZ6", ""+z6);

                             /*Node7*/

                             z7=(-25.26039402)+(23.85368648)*node7[7];
                             z7=z7+(-25.26039402)+(23.12098342)*node7[6];
                             z7=z7+(-25.26039402)+(-7.508573877)*node7[5];
                             z7=z7+(-25.26039402)+(-9.647846697)*node7[4];
                             z7=z7+(-25.26039402)+(3.68933298)*node7[3];
                             z7=z7+(-25.26039402)+(-4.383798975)*node7[2];
                             z7=z7+(-25.26039402)+(-3.558349405)*node7[1];
                             z7=z7+(-25.26039402)+(1.153470927)*node7[0];
                             str = String.format("%1.5f", z7);
                             z7 = Double.valueOf(str);
                            z7=1/(1 +Math.pow(2.71828,(-z7)));
                             str = String.format("%1.5f", z7);
                             z7 = Double.valueOf(str);
                             Log.i("ValueOfZ7", ""+z7);

                               /*Node8*/

                             z8=(26.96944774)+(0.928376393)*node8[7];
                             z8=z8+(26.96944774)+(-3.363905217)*node8[6];
                             z8=z8+(26.96944774)+(-38.44306262)*node8[5];
                             z8=z8+(26.96944774)+(-5.804756294)*node8[4];
                             z8=z8+(26.96944774)+(-3.029392812)*node8[3];
                             z8=z8+(26.96944774)+(0.19356627)*node8[2];
                             z8=z8+(26.96944774)+(-2.678938871)*node8[1];
                             z8=z8+(26.96944774)+(-0.312428571)*node8[0];
                             str = String.format("%1.5f", z8);
                             z8 = Double.valueOf(str);
                             z8=1/(1 +Math.pow(2.71828,(-z8)));
                             str = String.format("%1.5f", z8);
                             z8 = Double.valueOf(str);
                             Log.i("ValueOfZ8", ""+z8);

                              /*Node9*/

                             z9=(-8.861340937)+(5.422406355)*node9[7];
                             z9=z9+(-8.861340937)+(-0.185737022)*node9[6];
                             z9=z9+(-8.861340937)+(-19.27892855)*node9[5];
                             z9=z9+(-8.861340937)+(-0.671681722)*node9[4];
                             z9=z9+(-8.861340937)+(31.899499)*node9[3];
                             z9=z9+(-8.861340937)+(0.766025943)*node9[2];
                             z9=z9+(-8.861340937)+(1.036584478)*node9[1];
                             z9=z9+(-8.861340937)+(2.508021748)*node9[0];
                             str = String.format("%1.5f", z9);
                             z9 = Double.valueOf(str);
                             z9=1/(1 +Math.pow(2.71828,(-z9)));
                             str = String.format("%1.5f", z9);
                             z9 = Double.valueOf(str);
                             Log.i("ValueOfZ9", ""+z9);

                             /*Node10*/

                             z10=(-36.22478568)+(23.55325971)*node10[7];
                             z10=z10+(-36.22478568)+(6.35929515)*node10[6];
                             z10=z10+(-36.22478568)+(22.02556849)*node10[5];
                             z10=z10+(-36.22478568)+(10.39981308)*node10[4];
                             z10=z10+(-36.22478568)+(10.39984295)*node10[3];
                             z10=z10+(-36.22478568)+(-2.378612783)*node10[2];
                             z10=z10+(-36.22478568)+(2.994239488)*node10[1];
                             z10=z10+(-36.22478568)+(-0.53907619)*node10[0];
                             str = String.format("%1.5f", z10);
                             z10 = Double.valueOf(str);
                             z10=1/(1 +Math.pow(2.71828,(-z10)));
                             str = String.format("%1.5f", z10);
                             z10 = Double.valueOf(str);
                             Log.i("ValueOfZ10", ""+z10);

                                /*Node11*/

                             z11=(-51.10994727)+(33.95033293)*node11[7];
                             z11=z11+(-51.10994727)+(29.89240528)*node11[6];
                             z11=z11+(-51.10994727)+(3.05143541)*node11[5];
                             z11=z11+(-51.10994727)+(-1.532907623)*node11[4];
                             z11=z11+(-51.10994727)+(2.297175726)*node11[3];
                             z11=z11+(-51.10994727)+(-0.534027092)*node11[2];
                             z11=z11+(-51.10994727)+(-0.598826382)*node11[1];
                             z11=z11+(-51.10994727)+(4.029470719)*node11[0];
                             str = String.format("%1.5f", z11);
                             z11 = Double.valueOf(str);
                             z11=1/(1 +Math.pow(2.71828,(-z11)));
                             str = String.format("%1.5f", z11);
                             z11 = Double.valueOf(str);
                             Log.i("ValueOfZ11", ""+z11);

                             /*Node12*/

                             z12=(-1.482784631)+(-1.354010931)*node12[7];
                             z12=z12+(-1.482784631)+(2.056564441)*node12[6];
                             z12=z12+(-1.482784631)+(-4.254437402)*node12[5];
                             z12=z12+(-1.482784631)+(-9.339743362)*node12[4];
                             z12=z12+(-1.482784631)+(1.105115035)*node12[3];
                             z12=z12+(-1.482784631)+(3.548536247)*node12[2];
                             z12=z12+(-1.482784631)+(3.469804682)*node12[1];
                             z12=z12+(-1.482784631)+(-6.980145526)*node12[0];
                             str = String.format("%1.5f", z12);
                             z12 = Double.valueOf(str);
                             z12=1/(1 +Math.pow(2.71828,(-z12)));
                             str = String.format("%1.5f", z12);
                             z12 = Double.valueOf(str);
                             Log.i("ValueOfZ12", ""+z12);

                              /*Node13*/

                             z13=(17.31484769)+(-2.956516632)*node13[7];
                             z13=z13+(17.31484769)+(-18.64699291)*node13[6];
                             z13=z13+(17.31484769)+(-20.01560823)*node13[5];
                             z13=z13+(17.31484769)+(-1.463062362)*node13[4];
                             z13=z13+(17.31484769)+(0.80951075)*node13[3];
                             z13=z13+(17.31484769)+(-1.093364069)*node13[2];
                             z13=z13+(17.31484769)+(-3.747916607)*node13[1];
                             z13=z13+(17.31484769)+(-3.414952156)*node13[0];
                             str = String.format("%1.5f", z13);
                             z13 = Double.valueOf(str);
                             z13=1/(1 +Math.pow(2.71828,(-z13)));
                             str = String.format("%1.5f", z13);
                             z13 = Double.valueOf(str);
                             Log.i("ValueOfZ13", ""+z13);

                              /*Node14*/

                             z14=(5.82314213)+(0.776014301)*node14[7];
                             z14=z14+(5.82314213)+(-1.878105507)*node14[6];
                             z14=z14+(5.82314213)+(5.382268847)*node14[5];
                             z14=z14+(5.82314213)+(0.2782921)*node14[4];
                             z14=z14+(5.82314213)+(-27.59198516)*node14[3];
                             z14=z14+(5.82314213)+(-0.554095619)*node14[2];
                             z14=z14+(5.82314213)+(-1.3045515)*node14[1];
                             z14=z14+(5.82314213)+(0.789229762)*node14[0];
                             str = String.format("%1.5f", z14);
                             z14 = Double.valueOf(str);
                            z14=1/(1 +Math.pow(2.71828,(-z14)));
                             str = String.format("%1.5f", z14);
                             z14 = Double.valueOf(str);
                             Log.i("ValueOfZ14", ""+z14);

                              /*Node15*/

                             z15=(-6.886000837)+(-3.760348552)*node15[7];
                             z15=z15+(-6.886000837)+(-3.175976397)*node15[6];
                             z15=z15+(-6.886000837)+(-4.192195726)*node15[5];
                             z15=z15+(-6.886000837)+(-3.491396547)*node15[4];
                             z15=z15+(-6.886000837)+(-0.492059327)*node15[3];
                             z15=z15+(-6.886000837)+(-1.409025563)*node15[2];
                             z15=z15+(-6.886000837)+(16.09737781)*node15[1];
                             z15=z15+(-6.886000837)+(-1.012477127)*node15[0];
                             str = String.format("%1.5f", z15);
                             z15 = Double.valueOf(str);
                             z15=1/(1 +Math.pow(2.71828,(-z15)));
                             str = String.format("%1.5f", z15);
                             z15 = Double.valueOf(str);
                             Log.i("ValueOfZ15", ""+z15);



                             /*NodeOutput1*/
                             outputnode1=(-8.86708353)+(-9.131742784)*z1;
                             outputnode1=outputnode1+(-8.86708353)+(9.185140209)*z2;
                             outputnode1=outputnode1+(-8.86708353)+(9.956499177)*z3;
                             outputnode1=outputnode1+(-8.86708353)+(-5.307246498)*z4;
                             outputnode1=outputnode1+(-8.86708353)+(8.776206442)*z5;
                             outputnode1=outputnode1+(-8.86708353)+(9.259113284)*z6;
                             outputnode1=outputnode1+(-8.86708353)+(-18.66221999)*z7;
                             outputnode1=outputnode1+(-8.86708353)+(10.19892379)*z8;
                             outputnode1=outputnode1+(-8.86708353)+(-3.796292538)*z9;
                             outputnode1=outputnode1+(-8.86708353)+(-2.527876184)*z10;
                             outputnode1=outputnode1+(-8.86708353)+(-7.103936374)*z11;
                             outputnode1=outputnode1+(-8.86708353)+(9.600153376)*z12;
                             outputnode1=outputnode1+(-8.86708353)+(-4.923310223)*z13;
                             outputnode1=outputnode1+(-8.86708353)+(-6.816514125)*z14;
                             outputnode1=outputnode1+(-8.86708353)+(-18.06550527)*z15;

                             str = String.format("%1.5f", outputnode1);
                             outputnode1 = Double.valueOf(str);
                            outputnode1=1/(1 +Math.pow(2.71828,(-outputnode1)));
                             str = String.format("%1.5f", outputnode1);
                             outputnode1 = Double.valueOf(str);
                             Log.i("outputnode1", ""+outputnode1);

                             /*NodeOutput2*/
                             outputnode2=(1.057542018)+(5.478952569)*z1;
                             outputnode2=outputnode2+(1.057542018)+(-4.260136137)*z2;
                             outputnode2=outputnode2+(1.057542018)+(-9.100762731)*z3;
                             outputnode2=outputnode2+(1.057542018)+(-7.950906124)*z4;
                             outputnode2=outputnode2+(1.057542018)+(-5.074266208)*z5;
                             outputnode2=outputnode2+(1.057542018)+(-2.730089189)*z6;
                             outputnode2=outputnode2+(1.057542018)+(13.58512668)*z7;
                             outputnode2=outputnode2+(1.057542018)+(-10.1238903)*z8;
                             outputnode2=outputnode2+(1.057542018)+(5.882071344)*z9;
                             outputnode2=outputnode2+(1.057542018)+(-4.847236789)*z10;
                             outputnode2=outputnode2+(1.057542018)+(-18.52212289)*z11;
                             outputnode2=outputnode2+(1.057542018)+(-3.932678787)*z12;
                             outputnode2=outputnode2+(1.057542018)+(4.935914812)*z13;
                             outputnode2=outputnode2+(1.057542018)+(7.548777572)*z14;
                             outputnode2=outputnode2+(1.057542018)+(8.43816469)*z15;

                             str = String.format("%1.5f", outputnode2);
                             outputnode2 = Double.valueOf(str);
                            outputnode2=1/(1 +Math.pow(2.71828,(-outputnode2)));
                             str = String.format("%1.5f", outputnode2);
                             outputnode2 = Double.valueOf(str);
                             Log.i("outputnode2", ""+outputnode2);

                             /*NodeOutput3*/
                             outputnode3=(-1.10049946)+(-3.175384636)*z1;
                             outputnode3=outputnode3+(-1.10049946)+(-1.769780635)*z2;
                             outputnode3=outputnode3+(-1.10049946)+(0.614381272)*z3;
                             outputnode3=outputnode3+(-1.10049946)+(6.22457393)*z4;
                             outputnode3=outputnode3+(-1.10049946)+(3.314835127)*z5;
                             outputnode3=outputnode3+(-1.10049946)+(-11.38948333)*z6;
                             outputnode3=outputnode3+(-1.10049946)+(-8.788744724)*z7;
                             outputnode3=outputnode3+(-1.10049946)+(7.774047549)*z8;
                             outputnode3=outputnode3+(-1.10049946)+(-5.00233419)*z9;
                             outputnode3=outputnode3+(-1.10049946)+(4.440815281)*z10;
                             outputnode3=outputnode3+(-1.10049946)+(12.82109399)*z11;
                             outputnode3=outputnode3+(-1.10049946)+(-7.808773814)*z12;
                             outputnode3=outputnode3+(-1.10049946)+(-4.501955705)*z13;
                             outputnode3=outputnode3+(-1.10049946)+(-5.897642932)*z14;
                             outputnode3=outputnode3+(-1.10049946)+(3.083920847)*z15;

                             str = String.format("%1.5f", outputnode3);
                             outputnode3 = Double.valueOf(str);
                             outputnode3=1/(1 +Math.pow(2.71828,(-outputnode3)));
                             str = String.format("%1.5f", outputnode3);
                             outputnode3 = Double.valueOf(str);

                             Log.i("outputnode3", ""+outputnode3);




                            x++;

                         }

                        }






                }
            });
        }
    };




    private void startScan() {
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();

      /*  mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceCursor());
        mList.setAdapter(mLeDeviceListAdapter);*/
        mDeviceStore.getDeviceCursor();

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (mIsBluetoothOn && mIsBluetoothLePresent) {
            mScanner.scanLeDevice(-1, true);
        }
    }




    private MyImage getMyImage(String image) {
        try {
            JSONObject job = new JSONObject(image);
            return (new MyImage(job.getString("title"),
                    job.getString("description"), job.getString("path"),
                    job.getLong("datetimeLong")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * go back to main activity
     *
     * @param v
     */
    public void btnBackOnClick(View v) {
        startActivity(new Intent(this, ShowlistbeaconActivity.class));
        finish();
    }

    /**
     * delete the current item;
     *
     * @param v
     */
    public void btnDeleteOnClick(View v) {
        DAOdb db = new DAOdb(this);
        db.deleteImage(image);
        db.close();
        startActivity(new Intent(this, ShowlistbeaconActivity.class));
        finish();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (jstring != null) {
            outState.putString("jstring", jstring);
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("jstring")) {
            jstring = savedInstanceState.getString("jstring");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_image, menu);
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
