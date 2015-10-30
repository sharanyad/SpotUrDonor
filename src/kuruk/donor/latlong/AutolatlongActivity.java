package kuruk.donor.latlong;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;



public class AutolatlongActivity extends Activity implements LocationListener{
	
	TextView latituteField;
	 TextView longitudeField;
	 LocationManager locationManager;
	 String provider;
	 String message,number;
	 float lat,lng;
	 
	 public  AutolatlongActivity(){}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        latituteField = (TextView) findViewById(R.id.TextView02);
		longitudeField = (TextView) findViewById(R.id.TextView04);
		
		
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//  to select the location provider
		
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		Toast.makeText(this, " provider "  + provider +  "has been selected",
				Toast.LENGTH_SHORT).show();

		// Initialize the location fields
		if (location != null) {
			
			Toast.makeText(this, " provider " +  provider  + "  has been selected",
					Toast.LENGTH_SHORT).show();
			lat = (float) (location.getLatitude());
			 lng = (float) (location.getLongitude());
			latituteField.setText(String.valueOf(lat));
			longitudeField.setText(String.valueOf(lng));
		} else {
		latituteField.setText("Provider not available");
		longitudeField.setText("Provider not available");
		}
		
    	
    	number = "+919791134554";
		message="Kurukshetra 2012 app test reply onC:\n" + "The latitude and Longitude is:\n" + lat + "," + lng;		
		
    }
 public void sendMess()
 {
	 SmsManager sm = SmsManager.getDefault();
 	sm.sendTextMessage(number, null, message, null, null);
 	finish();
 }
   
    
    /* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
		
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}
	public void onDestroy()
    {
    	super.onDestroy();
    }
    

	@Override
	public void onLocationChanged(Location location) {
		 lat = (float) (location.getLatitude());
		 lng = (float) (location.getLongitude());
		latituteField.setText(String.valueOf(lat));
		longitudeField.setText(String.valueOf(lng));
		Toast.makeText(this, "Location changed" + provider,
				Toast.LENGTH_SHORT).show();
		
		message="Kurukshetra 2012 app test reply:\n" + "The latitude and Longitude is:\n" + lat + "," + lng;
		sendMess();
	
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider please witch on the 'use wireless networks'option in settings" + provider,
				Toast.LENGTH_SHORT).show();
	}
	
	
}


	 


