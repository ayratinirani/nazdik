package ir.ounegh.nazdik;
import android.content.*;
import java.util.*;
import com.androidnetworking.*;
import com.androidnetworking.interfaces.*;
import org.json.*;
import com.androidnetworking.common.*;
import com.androidnetworking.error.*;
import android.widget.*;
import okhttp3.*;
import android.app.*;
import com.android.volley.toolbox.*;
import com.android.volley.*;
import com.android.volley.Response;
import android.support.v7.app.*;

public class networks
{ AppCompatActivity mcontext;
	 ArrayList<mLocation> amaken=new ArrayList<>();
	public networks(AppCompatActivity c){
		mcontext=c;
	}
	public ArrayList<mLocation>getFromServer(){
		AndroidNetworking.initialize(mcontext,new OkHttpClient());
		AndroidNetworking.get(NZapp.BASEURL+"getlist.php")
			//.addPathParameter("pageNumber", "0")
			//.addQueryParameter("limit", "3")
			//.addHeaders("token", "1234")
			.setTag("test")
			.setPriority(Priority.HIGH)
			.build()
			.getAsJSONArray(new JSONArrayRequestListener() {
				@Override
				public void onResponse(JSONArray response) {
                    // do anything with response
					
					for(int i=0;i<response.length();i++){
						mLocation m=new mLocation("gps");
						try
						{
							JSONObject obj=response.getJSONObject(i);
							m.setLatitude(obj.getDouble("latitude"));
							m.setLongitude(obj.getDouble("longitude"));
							m.setId(obj.getInt("id"));
							m.setName(obj.getString("name"));
							m.setCategory(obj.getString("category"));
							amaken.add(m);
						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}
						
					}
					
				}
				@Override
				public void onError(ANError error) {
                    // handle error
					error.printStackTrace();
				Toast.makeText(	mcontext,error.getMessage(),Toast.LENGTH_LONG).show();
				}
			});
		
		
		
		
		return amaken;
	}
	
	
	public ArrayList<mLocation> getall(){
		// Tag used to cancel the request
		String tag_json_arry = "json_array_req";
RequestQueue mQueue=Volley.newRequestQueue(mcontext);
		String url ="http://127.0.0.1:8080/nazdik/"+ "getlist.php";
		 url ="http://ounegh.ir/nazdik/"+ "getlist.php";
		final ProgressDialog pDialog = new ProgressDialog(mcontext);
		pDialog.setMessage("دریافت اماکن");
		pDialog.show();     

		//You can send json array request as below.

			JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
			new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					pDialog.dismiss();
					//Toast.makeText(mcontext,response.toString(),Toast.LENGTH_LONG).show();
					Toast.makeText(mcontext,response.toString(),Toast.LENGTH_LONG).show();
					parseJsnArray(response);
					
					
				}
			},
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					pDialog.dismiss();
					Toast.makeText(mcontext,error.getMessage(),Toast.LENGTH_LONG).show();
				}
			});
// Adding JsonObject request to request queue
		//AppController.getInstance().addToRequestQueue(jsonArrayReq, REQUEST_TAG);
		//You ca

// Adding request to request queue
		//NZapp.getInstance().addToRequestQueue(jsonArrayReq, tag_json_arry);
	mQueue.add(jsonArrayReq);
	mQueue.start();
		
		return amaken;
	}
	public ArrayList<mLocation> parseJsnArray(JSONArray response){
		for(int i=0;i<response.length();i++){
			mLocation m=new mLocation("gps");
			try
			{
				JSONObject obj=response.getJSONObject(i);
				m.setLatitude(obj.getDouble("latitude"));
				m.setLongitude(obj.getDouble("longitude"));
				m.setId(obj.getInt("id"));
				m.setPhone(obj.getString("phone"));
				m.setName(obj.getString("name"));
				m.setCategory(obj.getString("category"));
				amaken.add(m);
			}catch(Exception e){
				
				e.printStackTrace();
			}
		}
		return amaken;
	}
	public ArrayList<String>getcats(){
		final ArrayList<String>cats=new ArrayList<>();
		
		
			// Tag used to cancel the request
			String tag_json_arry = "json_array_req";
			RequestQueue mQueue=Volley.newRequestQueue(mcontext);
			//String url ="http://127.0.0.1:8080/nazdik/"+ "getcats.php";
		String url="http://ounegh.ir/nazdik/getcats.php";
			final ProgressDialog pDialog = new ProgressDialog(mcontext);
			pDialog.setMessage("دریافت دسته بندی ها");
			pDialog.show();     

			//You can send json array request as below.

			JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						pDialog.dismiss();
						Toast.makeText(mcontext,response.toString(),Toast.LENGTH_LONG).show();
						//Toast.makeText(mcontext,response.toString(),Toast.LENGTH_LONG).show();
                        for(int i=0;i<response.length();i++){
							try
							{
								
								JSONObject object =response.getJSONObject(i);
								cats.add(object.getString("category"));
							}
							catch (JSONException e)
							{
								pDialog.dismiss();
								e.printStackTrace();
							}
						}
						


					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pDialog.dismiss();
						Toast.makeText(mcontext,error.getMessage(),Toast.LENGTH_LONG).show();
					}
				});
			mQueue.start();

	
		
		return cats;
	}
	
}
