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
import android.view.*;
import android.net.*;
import android.widget.AdapterView.*;
import com.jaredrummler.materialspinner.*;


public class MainActivity extends EnhancedActivity implements LocationListener
{
	boolean isfirst=true;
	Location loc;
	mdb MDB;
	mLocation currnt= new mLocation();
	public static boolean cancall=false;
    TextView tv;
	ArrayList<mLocation>amaken=new ArrayList<>();
    ArrayList<String>cats=new ArrayList<>();
	private String selecedcat;
	networks neti;
	ListView lv;
	Spinner spinner;
	String[]catha;
	Button b;
	@Override
	public void onLocationChanged(Location p1)
	{
		if(amaken.size()==0 ||amaken==null){
			return;
		}
		
		tv.setBackgroundColor(0x223aff);
		
	 
		locationManager.removeUpdates(this);
		loc=p1;
		b.setVisibility(View.VISIBLE);
		
		isfirst=false;
		currnt=findNearest(loc);
		
		
		if(currnt==null){
			showtext("نتایج خالی");
			return;
		}else{
		tv.setText(currnt.getPhone() +"\n"+ currnt.getName()+"\n"+"فاصله"+(int)currnt.distanceTo(loc)+"\n"+"سمت  "+currnt.jahat(loc));
	}
	}

	@Override
	public void onStatusChanged(String p1, int p2, Bundle p3)
	{
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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkLocationPermission();
		CalcheckLocationPermission();
		//MDB=new mdb(this);
		neti=new networks(this);
		lv=(ListView) findViewById(R.id.mainListView);
		tv=(TextView) findViewById(R.id.mainTextView);
		loc=new Location("gps");
		locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.getLastKnownLocation("gps")!=null){

			loc=locationManager.getLastKnownLocation("gpps");
			tv.setBackgroundColor(0x119f55);

		}



		b=(Button) findViewById(R.id.mainButton);
		b.setVisibility(View.GONE);
      // getcategories();
	 updateDatabase();
        
         
		
    }
//دریافت انواع کتگوری ها
	private void getcategories()
	{ 
		//cats=MDB.getcats();
		
		
	cats=neti.getcats();
		//updateDatabase();
	if(cats==null || cats.size()<1){
		showtext("server didnt give categories");
		return;
	}
		MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
		spinner.setItems(cats);
		spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

				@Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
					showtext(item);
				}
			});
	
	
		
		
	}
//اپدیت هفتگی دیتابیس
	private void updateDatabase()
	{
		
		//networks net=new networks(this);
	   amaken=neti.getall();
		//amaken=neti.getFromServer();
		if(amaken==null || amaken.size()<1){
			showtext("server error");
			return;
		}
		showtext(amaken.toString());
		ArrayAdapter a=new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,amaken);
		
		lv.setAdapter(a);
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
	
	//تشخیص نزدیکترین واحد
	private mLocation findNearest(Location l){
		mLocation min=new mLocation();
		int i=0;
		double did=0;
		for(mLocation m:amaken){
			//if(!m.getCategory().equals(cat)){
				//continue;
			//}
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

	@Override
	protected void onPause()
	{
		locationManager.removeUpdates(this);
		// TODO: Implement this method
		super.onPause();
	}

	@Override
	public void onBackPressed()
	{
		locationManager.removeUpdates(this);
		// TODO: Implement this method
		super.onBackPressed();
	}
	
	public void call(View v){
		String phone_number=currnt.getPhone();
		
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_number));
		startActivity(intent);
		
	}
	
}
