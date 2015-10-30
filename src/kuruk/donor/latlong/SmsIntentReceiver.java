package kuruk.donor.latlong;






import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;


public class SmsIntentReceiver extends BroadcastReceiver
{
	
	
	private void triggerAppLaunch(Context context)
	{
		Intent broadcast = new Intent("kuruk.donor.latlong.WAKE_UP");
		broadcast.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 	
		context.startActivity(new Intent(broadcast));
		
	}
	
	private SmsMessage[] getMessagesFromIntent(Intent intent)
	{
		SmsMessage retMsgs[] = null;
		Bundle bdl = intent.getExtras();
		try{
			Object pdus[] = (Object [])bdl.get("pdus");
			retMsgs = new SmsMessage[pdus.length];
			for(int n=0; n < pdus.length; n++)
			{
				byte[] byteData = (byte[])pdus[n];
				retMsgs[n] = SmsMessage.createFromPdu(byteData);
			}	
			
		}catch(Exception e)
		{
			Log.e("GetMessages", "fail", e);
		}
		return retMsgs;
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(!intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
		{
			return;
		}
		SmsMessage msg[] = getMessagesFromIntent(intent);
		
		
		
		for(int i=0; i < msg.length; i++)
		{
		
			String message = msg[i].getDisplayMessageBody();
			if(message != null && message.length() > 0)
			{
				Log.i("MessageListener:",  message);
				
				
				if(message.startsWith("get location"))
				{
					triggerAppLaunch(context);
					
				}
				
				
				
				
				}
				
			}
		
		
	}
	
}

