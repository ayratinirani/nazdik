package ir.ounegh.nazdik;
import android.app.*;
import android.content.*;
import android.database.sqlite.*;
import com.androidnetworking.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import android.text.*;

public class NZapp extends Application
{
	public static EnhancedActivity CURRENT_ACTIVITY;
	private static RequestQueue mRequestQueue;
	private static NZapp mInstance;
	public static String TAG;
	public static Context APPCONTEXT;
    public static SharedPreferences PREFS;
	public static SharedPreferences.Editor EDITOR;
	
	public static String APPNAME="NZApp";
	public static long LASTUPDATE_TIME=0l;
	public static String BASEURL;
	@Override
	public void onCreate()
	{
		super.onCreate();

		APPCONTEXT=getApplicationContext();
		PREFS=APPCONTEXT.getSharedPreferences(APPNAME,0);
		BASEURL="127.0.0.1:8080/nazdik/";
		LASTUPDATE_TIME = PREFS.getLong("LASTUPDATETIME", 0);
		mInstance=this;
		AndroidNetworking.initialize(getApplicationContext());
	}
	public static synchronized NZapp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
	public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
