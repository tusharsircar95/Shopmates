package com.elevenestates.shopmates2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.todddavies.components.progressbar.ProgressWheel;

public class SplashScreen extends Activity {

	TextView message;
	ProgressWheel pb;
	String username;
	
	boolean orderPending;
	SharedPreferences sharedPrefs;
	ParseUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_screen);
		
		Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
		
		message = (TextView) findViewById(R.id.message);
		pb = (ProgressWheel) findViewById(R.id.progressBar);
		pb.spin();
		
		
			
		sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
		orderPending = sharedPrefs.getBoolean("orderPending",false);
		
		
		
		
		//ParseQuery query = ParseQuery.getQuery("Order")
		if(isNetworkAvailable())
		{
			new Handler().postDelayed(new Runnable()
			{
				public void run()
				{
					
					if(false)
					{
						Intent i = new Intent("trackShopperScreen");
						i.putExtra("orderX",sharedPrefs.getString("orderX",""));
						i.putExtra("orderY",sharedPrefs.getString("orderY",""));
						i.putExtra("orderId",sharedPrefs.getString("orderId",""));
						
						ArrayList<String> orderList = new ArrayList<String>();
						int size = Integer.parseInt(sharedPrefs.getString("orderListSize","0"));
						for(int k=0; k<size; k++)
							orderList.add(sharedPrefs.getString("order"+k,""));
						
						i.putExtra("orderList",orderList);
						startActivity(i);
						finish();
							
					}
					
					else
					{
						user = ParseUser.getCurrentUser();
						
					if(user!=null)
					{	
						username = user.getUsername();
						
						Toast.makeText(SplashScreen.this,username,Toast.LENGTH_LONG).show();
						
						ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
						query.whereEqualTo("username",username);
						query.whereEqualTo("tip_paid","no");
						query.findInBackground(new FindCallback<ParseObject>()
								{

									@Override
									public void done(List<ParseObject> objectList,
											ParseException e) {
										// TODO Auto-generated method stub
										if(e==null)
										{
											if(objectList.size()!=0)
											{
												SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
												
												Intent i = new Intent("trackShopperScreen");
												i.putExtra("orderX",objectList.get(0).getString("lat"));
												i.putExtra("orderY",objectList.get(0).getString("lng"));
												i.putExtra("orderId",objectList.get(0).getObjectId());
												
												ArrayList<String> orderList = new ArrayList<String>();
												int size = Integer.parseInt(sharedPrefs.getString("orderListSize","0"));
												for(int k=0; k<size; k++)
													orderList.add(sharedPrefs.getString("order"+k,""));
												
												i.putExtra("orderList",orderList);
												startActivity(i);
												finish();
											}
											else
											{
												//user has logged in
												Intent intent = new Intent("main_screen");
												intent.putExtra("showToast",true);
												startActivity(intent);
												finish();
											}
										}
									}
							
								});
						
					}
					else
					{
						Toast.makeText(SplashScreen.this,"Not logged in!",Toast.LENGTH_LONG).show();
						
						Intent i = new Intent("start_screen");
						startActivity(i);
						finish();
					}
					
					}
					
					
				}
			},5000);
		}
		else
		{
			message.setVisibility(View.VISIBLE);
			pb.setVisibility(View.GONE);
		}
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}



	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
