package com.elevenestates.shopmates2;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class ApplicationClass extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Parse.initialize(this, "yPiVUng1QaEHEfWRxY111LqvpF2QoGecWEHn0dpf", "YFXesKpPVUPIgD719giidQEkWhumoSjKCewBaDhI");
  		
		Toast.makeText(this,"testing",Toast.LENGTH_LONG).show();
		
		ParsePush.subscribeInBackground("channel", new SaveCallback() {
			  @Override
			  public void done(ParseException e) {
			    if (e == null) {
			      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
			    } else {
			      Log.e("com.parse.push", "failed to subscribe for push", e);
			    }
			  }
			});
	}
	

	
}
