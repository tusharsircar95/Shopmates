package com.elevenestates.shopmates2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class TrackShopperScreen extends Activity implements OnMapReadyCallback{

	TextView order,name,status,header,cancel;
	ImageButton call;
	
	boolean orderPending = true;
	boolean zoomed = false;
	
	
	String assignedShopperId = "QLD55cuQFG",orderId="";
	String prevX,prevY;
	String currentX,currentY;
	ArrayList<LatLng> locations;
	int noOfPoints = 0;
	PolylineOptions plOptions;
	int timeInterval = 10000;
	String orderX,orderY;
	String phoneno = "";
	LatLng storeLocation,toLocation;
	
	
	Marker prevLocation,curLocation;
	
	ProgressDialog progress;
	Handler handler;
	MapFragment mapFragment;
	
	ImageView[] dots;
	int[] dotImage;
	int progressIndicator = 1;
	
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.track_shopper_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Track Shopper</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().hide();
		
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Track Shopper");
      	
      	
      	//order pending
      	SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putBoolean("orderPending",true);
      	editor.commit();
		
      	
      	
        dots = new ImageView[5];
        dotImage = new int[5];
        dots[1] = (ImageView) findViewById(R.id.dot1);
        dots[2] = (ImageView) findViewById(R.id.dot2);
        dots[3] = (ImageView) findViewById(R.id.dot3);
        dots[4] = (ImageView) findViewById(R.id.dot4);
        
        dotImage[1] = R.drawable.b1;
        dotImage[2] = R.drawable.b2;
        dotImage[3] = R.drawable.b3;
        dotImage[4] = R.drawable.b4;
        
      //setup progress bar
      	progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Looking for shopmate...");
        progress.setIndeterminate(true);
        progress.show();
        
        
        handler = new Handler();
        
        
        order = (TextView) findViewById(R.id.order);
        name = (TextView) findViewById(R.id.name);
        status = (TextView) findViewById(R.id.status);
        header = (TextView) findViewById(R.id.header);
        cancel = (TextView) findViewById(R.id.cancel);
        call = (ImageButton) findViewById(R.id.call);
        
        
        order.setTypeface(customFont);
        name.setTypeface(customFont);
        status.setTypeface(customFont);
        cancel.setTypeface(customFont);
        header.setTypeface(customFont);
        
        
        
	//	assignedShopperId = getIntent().getStringExtra("shopperId");
        orderId = getIntent().getStringExtra("orderId");
		orderX = getIntent().getStringExtra("orderX");
		orderY = getIntent().getStringExtra("orderY");
		storeLocation = new LatLng(Double.parseDouble(orderX),Double.parseDouble(orderY));
		
		Toast.makeText(this,orderId+" "+orderX+" "+orderY,Toast.LENGTH_LONG).show();
		
		Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
		handler = new Handler();
	
		mapFragment = (MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map);
		checkShopper.run();
		
		
		call.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				
				if(phoneno.equals(""))
					Toast.makeText(TrackShopperScreen.this,"Unable to retrieve phone number. Try again later!",Toast.LENGTH_LONG).show();
				else
				{
				 Intent callIntent = new Intent(Intent.ACTION_CALL);
				    callIntent.setData(Uri.parse("tel:"+phoneno));
				    startActivity(callIntent);
				}
			
			}
		
		});
		
		
		cancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				cancelOrder();
			}
			
		});
		
	}

	
	private void cancelOrder()
	{
		
		
		
		
	}
	
	 private void getInformation() {
		 
		String orderText="";
		ArrayList<String> orderList = getIntent().getStringArrayListExtra("orderList");
		for(int i=0; i<orderList.size(); i++)
			orderText +=  orderList.get(i) + ", ";
		if(orderText.length()<35)
			order.setText(orderText);
		else
			order.setText(orderText.substring(30)+" ...");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Shopper");
		query.getInBackground(assignedShopperId,new GetCallback<ParseObject>()
				{

					@Override
					public void done(ParseObject object, ParseException e) {
						// TODO Auto-generated method stub
						if(e==null)
						{
							phoneno = object.getString("phoneno");
							name.setText("Your shopmate is " + object.getString("name"));
						}
						
					}
				
				});
		
		ParseQuery<ParseObject> q = ParseQuery.getQuery("Order");
		q.getInBackground(orderId,new GetCallback<ParseObject>()
				{

					@Override
					public void done(ParseObject object, ParseException e) {
						// TODO Auto-generated method stub
						if(e==null)
						{
							String s= object.getString("status");
							if(s.equals("Assigned to shopper!"))
								progressIndicator = 1;
							else if(s.equals("Shopper started shopping!"))
								progressIndicator = 2;
							else if(s.equals("Shopper is on the way!"))
								progressIndicator = 3;
							else if(s.equals("Delivered"))
								progressIndicator = 4;
								
							status.setText(s);
							
							for(int i=1; i<=progressIndicator; i++)
								dots[i].setImageResource(dotImage[i]);
							
							toLocation = new LatLng(Double.parseDouble(object.getString("toLat")),Double.parseDouble(object.getString("toLng")));
							
						}
						
					}
			
				});
		
		
		
	}

	@Override
	    public void onMapReady(GoogleMap map) {
	        //LatLng sydney = new LatLng(-33.867, 151.206);
		LatLng shopper = null;
		if(!currentX.equals("") && !currentY.equals(""))
			shopper = new LatLng(Double.parseDouble(currentX),Double.parseDouble(currentY));
		 
	       if(!zoomed)
	       {
	    	   map.setMyLocationEnabled(true);
	    	   map.moveCamera(CameraUpdateFactory.newLatLngZoom(shopper, 10));
	    	   zoomed = true;
	       }
	        
	        
	        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	      /*  Polyline polyline = map.addPolyline(plOptions);
	        polyline.setColor(Color.rgb(104,111,122));
	        polyline.setWidth(5); */
	        
	        //shoppers location
	        if(curLocation==null && !currentX.equals("") && !currentY.equals(""))
	        {
	        	curLocation = map.addMarker(new MarkerOptions()
	        		.position(shopper)
	        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.shopper_pointer)));
	        }
	        else
	        	curLocation.setPosition(shopper);
	        
	        //stores location
	        if(storeLocation!=null)
	        {
	        	map.addMarker(new MarkerOptions()
	                .position(storeLocation))
	                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.store_pointer)); 
	        }
	        
	       if(toLocation!=null)
	       {
	    	   map.addMarker(new MarkerOptions()
	    	   .position(toLocation))
	    	   .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.me_pointer));
	       }
	       
	    }

	 Runnable checkShopper = new Runnable()
	 {
		 public void run()
		 {
			 ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
			 query.getInBackground(orderId,new GetCallback<ParseObject>()
					 {

						@Override
						public void done(ParseObject object, ParseException e) {
							if(e==null)
							{
								assignedShopperId = object.getString("shopperId");
								if(assignedShopperId!=null && !assignedShopperId.equals("null"))
								{
									//start retrieving coordinates
									progress.dismiss();
									handler.removeCallbacks(checkShopper);
									
									SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
									Editor editor = sharedPrefs.edit();
									editor.putBoolean("orderPending",true);
									editor.putString("orderId",orderId);
									editor.putString("shopperId",assignedShopperId);
									editor.putString("orderX",orderX);
									editor.putString("orderY",orderY);
									
									ArrayList<String> orderList = getIntent().getStringArrayListExtra("orderList");
									int size = orderList.size();
									editor.putString("orderListSize",size+"");
									for(int i=0; i<size; i++)
										editor.putString("order"+i,orderList.get(i));
									editor.commit();
									
									handler.removeCallbacks(checkShopper);
									plotPath.run();
									getStatus.run();
									getInformation();
									
								}
								else
								{
									//call function again
									handler.postDelayed(checkShopper,timeInterval);
								}
							}
							
						}
				 
					 });
		 }
	 };
	 
	 Runnable getStatus = new Runnable()
	 {
		 public void run()
		 {
			 
			 Toast.makeText(TrackShopperScreen.this,"Fetching shopper location...",Toast.LENGTH_SHORT).show();
			 ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
			 query.getInBackground(orderId,new GetCallback<ParseObject>()
					 {

						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(e==null)
							{
								String s= object.getString("status");
								if(s.equals("Assigned to shopper!"))
									progressIndicator = 1;
								else if(s.equals("Shopper started shopping!"))
									progressIndicator = 2;
								else if(s.equals("Shopper is on the way!"))
									progressIndicator = 3;
								else if(s.equals("Delivered"))
									progressIndicator = 4;
									
								status.setText(s);
								
								for(int i=1; i<=progressIndicator; i++)
									dots[i].setImageResource(dotImage[i]);
								
								if(status.getText().toString().equals("Delivered"))
								{
									handler.removeCallbacks(plotPath);
									handler.removeCallbacks(getStatus);
									
									SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
									Editor editor = sharedPrefs.edit();
									editor.putBoolean("orderPending",false);
									editor.commit();
									
									Intent i = new Intent("tipScreen");
									i.putExtra("orderId",orderId);
									i.putExtra("shopperId",assignedShopperId);
									startActivity(i);
									finish();
								}
								else
									handler.postDelayed(getStatus,5000);
							}
							
						}
				 
					 });
		 
		 }
	 };
	 
	 Runnable plotPath = new Runnable()
	 {
		 public void run()
		 {
			 ParseQuery<ParseObject> query = ParseQuery.getQuery("Shopper");
			 query.getInBackground(assignedShopperId,new GetCallback<ParseObject>()
					 {

						@Override
						public void done(ParseObject object, ParseException e) {
							// TODO Auto-generated method stub
							if(e==null)
							{
								prevX = currentX;
								prevY = currentY;
								
								currentX = object.getString("lat");
								currentY = object.getString("lng");
								
								if(currentX!=null && currentY!=null)
								{
							/*		locations.add(new LatLng(Double.parseDouble(currentX),Double.parseDouble(currentY)));
									noOfPoints++;
									plOptions.add(locations.get(noOfPoints-1));
									Toast.makeText(TrackShopperScreen.this,currentX+" "+currentY,Toast.LENGTH_LONG).show();
							*/		mapFragment.getMapAsync(TrackShopperScreen.this);
								}
								
								handler.postDelayed(plotPath,timeInterval);
							}
							
						}
				 
					 });
		 
		 }
	 };

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		handler.removeCallbacks(plotPath);
		handler.removeCallbacks(getStatus);
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			Intent i = new Intent("main_screen");
			startActivity(i);
			overridePendingTransition(R.anim.right_slide,R.anim.no_slide);
			finish();
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent("main_screen");
		startActivity(i);
		overridePendingTransition(R.anim.right_slide,R.anim.no_slide);
		finish(); 
		
	}

	private void setCustomTitle(String s)
	{
		int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
		actionBarTitleView.setText(s);
		actionBarTitleView.setTypeface(customFont,Typeface.BOLD);
		actionBarTitleView.setTextColor(Color.rgb(104,110,122));
		getActionBar().setTitle(s);
	}
	 
}
