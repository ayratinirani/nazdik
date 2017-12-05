package ir.ounegh.nazdik;

import android.app.*;
import android.os.*;
import android.Manifest;
import android.content.pm.*;
import android.support.v7.app.*;
import android.support.v7.app.AlertDialog;
import android.support.v4.content.*;
import android.support.v4.app.*;
import android.content.*;
import android.location.*;
import android.widget.*;
import android.graphics.*;
import java.util.*;


public class MainActivity extends AppCompatActivity implements LocationListener
{
	private static String mlt="37.";
	private static String mlg="54.";
	boolean isfirst=true;
	Location loc;
	mdb MDB;
	mLocation currnt= new mLocation("gps");
	public static boolean cancall=false;
    TextView tv;
	ArrayList<mLocation>amaken=new ArrayList<>();
    ArrayList<String>cats=new ArrayList<>();
	private String selecedcat;
	@Override
	public void onLocationChanged(Location p1)
	{
		
		// TODO: Implement this method
		tv.setBackgroundColor(0x223aff);
		
	  //  tv.setText(p1.bearingTo() +"");
		//tv.setText(p1.toString());
		if(!isfirst){
			//tv.setText(p1.bearingTo(loc)+"");
		}
		loc=p1;
		isfirst=false;
		currnt=findNearest("alley",loc);
		if(currnt==null){
			showtext("نتایج خالی");
		}
		tv.setText(currnt.getPhone() +"   \n "+ currnt.getName());
	}

	@Override
	public void onStatusChanged(String p1, int p2, Bundle p3)
	{
		// TODO: Implement this method
	}

	@Override
	public void onProviderEnabled(String p1)
	{
		// TODO: Implement this method
		showtext("جی پی اس راه افتاد");
	}

	@Override
	public void onProviderDisabled(String p1)
	{
		// TODO: Implement this method
		showtext("جی پی اس قطع شد لطفا انرا روشن کنید");
	}
	
	
	LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkLocationPermission();
		MDB=new mdb(this);
        getcategories();
        tv=(TextView) findViewById(R.id.mainTextView);
         loc=new Location("gps");
         locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.getLastKnownLocation("gps")!=null){
			
			 loc=locationManager.getLastKnownLocation("gpps");
			tv.setBackgroundColor(0x119f55);
		//	tv.setText(loc.getLatitude()+"");
			}
			///این تابع جهت تست اولیه بودfillAmaken();
		CalcheckLocationPermission();
		NZapp.LASTUPDATE_TIME=0;
		long now=System.currentTimeMillis();
		updateDatabase();
		
		
		
    }
//دریافت انواع کتگوری ها
	private void getcategories()
	{ 
		cats=MDB.getcats();
	
		
		
			
		if(cats==null){
			showtext("دیتابیس خال");
		}
	}
//اپدیت هفتگی دیتابیس
	private void updateDatabase()
	{
		
		networks net=new networks(this);
		amaken=net.getall();
		NZapp.LASTUPDATE_TIME=System.currentTimeMillis();
		for(mLocation ml:amaken){
			MDB.insertRecord(ml);
		}
		
		
	}
	
	
	
	
//چک کردن مجوزها
	public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

	public boolean checkLocationPermission() {
		if (ContextCompat.checkSelfPermission(this,
											  Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
																	Manifest.permission.ACCESS_FINE_LOCATION)) {

				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				new AlertDialog.Builder(this)
                    .setTitle("نیاز به مجوز مکانیابی")
                    .setMessage("با توجه به ایننه نزدیکترین خدمات دهنده برای شما صدا زده میشود نیاز به داتستن موقعیت شما داریم ")
                    .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this,
															  new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
															  MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();


			} else {
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(this,
												  new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
												  MY_PERMISSIONS_REQUEST_LOCATION);
			}
			return false;
		} else {
			return true;
		}
	}
	public static final int call_PERMISSIONS_REQUEST_LOCATION = 98;

	public boolean CalcheckLocationPermission() {
		if (ContextCompat.checkSelfPermission(this,
											  Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
																	Manifest.permission.CALL_PHONE)) {

				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				new AlertDialog.Builder(this)
                    .setTitle("نیاز به مجوز مکانیابی")
                    .setMessage("با توجه به ایننه نزدیکترین خدمات دهنده برای شما صدا زده میشود نیاز به داتستن موقعیت شما داریم ")
                    .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this,
															  new String[]{Manifest.permission.CALL_PHONE},
															  call_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();


			} else {
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(this,
												  new String[]{Manifest.permission.CALL_PHONE},
												  call_PERMISSIONS_REQUEST_LOCATION);
			}
			return false;
		} else {
			return true;
		}
	}
	//پاسخ درخواست مجوز
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_LOCATION: {
					// If request is cancelled, the result arrays are empty.
					if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

						// permission was granted, yay! Do the
						// location-related task you need to do.
						if (ContextCompat.checkSelfPermission(this,
															  Manifest.permission.ACCESS_FINE_LOCATION)
							== PackageManager.PERMISSION_GRANTED) {

							//Request location updates:
							locationManager.requestLocationUpdates("gps", 0, 0, this);
						}

					} else {

						// permission denied, boo! Disable the
						// functionality that depends on this permission.

					}
					return;
				}
       case call_PERMISSIONS_REQUEST_LOCATION:{
		   
			   if (grantResults.length > 0
				   && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				   // permission was granted, yay! Do the
				   // location-related task you need to do.
				   if (ContextCompat.checkSelfPermission(this,
														 Manifest.permission.CALL_PHONE)
					   == PackageManager.PERMISSION_GRANTED) {

					   //Request location updates:
					   cancall=true;
				   }

			   } else {
                  cancall=false;
				  
				   // permission denied, boo! Disable the
				   // functionality that depends on this permission.

			   }
		   return;
	   }
		}
	}
	//Then you would just modify your onResume() override to call checkLocationP

	@Override
	protected void onResume() {
		super.onResume();

		if (checkLocationPermission()) {
			if (ContextCompat.checkSelfPermission(this,
												  Manifest.permission.ACCESS_FINE_LOCATION)
				== PackageManager.PERMISSION_GRANTED) {

				//Request location updates:
				locationManager.requestLocationUpdates("gps", 0, 0, this);
			}
		}

	}
	private void showtext(String s){
		Toast.makeText(this,s,Toast.LENGTH_LONG).show();
	}
	//باید از دیتابیس‌پر شود
//	private void fillAmaken(){
//		Random rand=new Random(9999999);
//		String slt=null;
//		String slg=null;
//		for(int i=9999;i>1;i--){
//			int rnd=rand.nextInt();
//			mLocation m=new mLocation("gps");
//		     slt=mlt+rnd;
//			 slg=mlg+rnd;
//			m.setLatitude(Double.valueOf(mlt));
//			m.setLongitude(Double.valueOf(mlg));
//			m.setName("m+"+i);
//			int ctt=i%9;
//			m.setCategory("cat"+ctt);
//			m.setPhone("+9817"+i);
//			tv.append("\n "+m.getPhone());
//			amaken.add(m);
//		}
//	}
	//تشخیص نزدیکترین واحد
	private mLocation findNearest(String cat,Location l){
		mLocation min=new mLocation("gps");
		int i=0;
		double did=0;
		for(mLocation m:amaken){
			if(!m.getCategory().equals(cat)){
				continue;
			}
			if(i==0){
				min=m;
				i=1;
				did=m.distanceTo(l);
			}
			if(m.distanceTo(l)<did){
				did=m.distanceTo(l);
				min=m;
			}
			
		}
	
		return min;
	}
}
