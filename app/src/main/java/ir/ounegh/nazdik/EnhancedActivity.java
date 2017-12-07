package ir.ounegh.nazdik;
import android.support.v7.app.*;
import android.os.*;

public class EnhancedActivity extends AppCompatActivity
{
	
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		NZapp.CURRENT_ACTIVITY=this;
		
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
	}

	@Override
	protected void onStop()
	{
		// TODO: Implement this method
		super.onStop();
	}
	
	
	
	
	
	
	
}
