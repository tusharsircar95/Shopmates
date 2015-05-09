package com.elevenestates.shopmates2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Screen_3 extends Activity implements OnClickListener{

	String username = "";
	String fullName = "";
	String phoneNo = "";
	String sLat,sLng;
	
	ArrayList<String> orderList;
	TextView orderText,fromText,toText;
	TextView orderHeader,fromHeader,toHeader;
	Button placeOrder;
	
	String toAddress = "";
	String toLat,toLng;
	boolean shopperAvailable = false;
	boolean walletSatisfied = false;
	
	ProgressDialog progress;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.screen_3);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Summary</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
      
        
        //setup progress bar
      	progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Placing order...");
        progress.setIndeterminate(true);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Summary");
      	
        sLat = getIntent().getStringExtra("lat");
        sLng = getIntent().getStringExtra("lng");
      	
        Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
        placeOrder = (Button) findViewById(R.id.placeOrder);
        
        ParseUser user = ParseUser.getCurrentUser();
        if(user!=null)
        {
        	username = user.getUsername();
        }
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserObject");
        query.whereEqualTo("username",username);
        query.findInBackground(new FindCallback<ParseObject>()
        		{

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if(e!=null)
							Toast.makeText(Screen_3.this,e.getMessage(),Toast.LENGTH_LONG).show();
						else if(objects.size()!=0)
						{
							fullName = objects.get(0).getString("user_full_name");
				        	phoneNo = objects.get(0).getString("phoneno");
				        	
						}
					}
        	
        		});
        
       
        orderText = (TextView) findViewById(R.id.orderText);
        fromText = (TextView) findViewById(R.id.fromText);
        toText = (TextView) findViewById(R.id.toText);
        orderHeader = (TextView) findViewById(R.id.orderHeader);
        fromHeader = (TextView) findViewById(R.id.fromHeader);
        toHeader = (TextView) findViewById(R.id.toHeader);
        
        orderHeader.setTypeface(customFont,Typeface.BOLD);
        orderText.setOnClickListener(this);
        fromHeader.setTypeface(customFont,Typeface.BOLD);
        fromText.setOnClickListener(this);
        toHeader.setTypeface(customFont,Typeface.BOLD);
        toText.setOnClickListener(this);
        
        orderText.setTypeface(customFont);
        fromText.setTypeface(customFont);
        toText.setTypeface(customFont);
        placeOrder.setTypeface(customFont);
        
        placeOrder.setOnClickListener(this);
        
       
        String finalOrder = "";
        orderList = getIntent().getStringArrayListExtra("orderList");
        for(int i=0; i<orderList.size(); i++)
        	finalOrder = finalOrder + orderList.get(i) + "\n";
        orderText.setText(finalOrder);
        fromText.setText(getIntent().getStringExtra("store"));
        
        SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
		toAddress = sharedPrefs.getString("tag","")+"\n"+sharedPrefs.getString("details","")+"\n"+sharedPrefs.getString("pincode","");
        toText.setText(toAddress);
        toLat = sharedPrefs.getString("lat","");
        toLng = sharedPrefs.getString("lng","");
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		  //define custom font
      	Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	
		
		getMenuInflater().inflate(R.menu.menu_screen1,menu);
		final MenuItem item = menu.getItem(0);
		TextView text = (TextView) item.getActionView().findViewById(R.id.menuText);
		text.setText("APPLY CODE");
		text.setVisibility(View.GONE);
		text.setTypeface(customFont);
		 item.getActionView().setOnClickListener(new OnClickListener()
		 {
			 public void onClick(View v)
			 {
				 onOptionsItemSelected(item);
			 }
		 });
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.applyCode:
			break;
		case android.R.id.home:
			Intent i = new Intent("storeSearchScreen");
			i.putStringArrayListExtra("orderList",orderList);
			startActivity(i);
			overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
			finish();
			
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent i = new Intent("storeSearchScreen");
		i.putStringArrayListExtra("orderList",orderList);
		startActivity(i);
		overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
		finish();
		
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch(v.getId())
		{
		case R.id.placeOrder:
			placeOrder();
			break;
		case R.id.orderText:
			i = new Intent("screen_1");
			i.putExtra("editing",true);
			i.putStringArrayListExtra("orderList",getIntent().getStringArrayListExtra("orderList"));
			i.putExtra("lat",getIntent().getStringExtra("lat"));
			i.putExtra("lng",getIntent().getStringExtra("lng"));
			i.putExtra("store",getIntent().getStringExtra("store"));
			startActivity(i);
			finish();
			break;
		case R.id.fromText:
			i = new Intent("storeSearchScreen");
			i.putExtra("editing",true);
			i.putStringArrayListExtra("orderList",orderList);
			startActivity(i);
			finish();
			break;
		case R.id.toText:
			i = new Intent("addressScreen");
			startActivity(i);
			break;
		}
	
		
		
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private void placeOrder() {
		
		progress.show();
		
		ParseQuery<ParseObject> uq = ParseQuery.getQuery("UserObject");
		uq.whereEqualTo("username",username);
		uq.findInBackground(new FindCallback<ParseObject>()
				{

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if(e==null && objects.size()!=0)
						{
							float bal = Float.parseFloat(objects.get(0).getString("wallet"));
							if(bal<200)
							{
								Toast.makeText(Screen_3.this,"You must have minimum of INR 200 in your wallet. Recharge now!",Toast.LENGTH_LONG).show();
								progress.dismiss();
							}
							else
							{
								walletSatisfied = true;
								ParseQuery<ParseObject> query = ParseQuery.getQuery("Shopper");
								query.whereEqualTo("status","available");
								query.findInBackground(new FindCallback<ParseObject>()
										{

											@Override
											public void done(List<ParseObject> objectList, ParseException e) {
												// TODO Auto-generated method stub
												if(e==null)
												{
													if(objectList.size() == 0)
													{
														Toast.makeText(Screen_3.this,"Sorry, no shopper are available at the moment!",Toast.LENGTH_LONG).show();
														progress.dismiss();
													}
													else
													{
														shopperAvailable = true;
														JSONArray orderJSONArray = new JSONArray();
														for(int i=0; i<orderList.size(); i++)
															orderJSONArray.put(orderList.get(i));
														
														final ParseObject orderObject = new ParseObject("Order");
														orderObject.put("username",username);
														orderObject.put("order",orderJSONArray);
														orderObject.put("from",fromText.getText().toString());
														orderObject.put("to",toText.getText().toString());
														orderObject.put("status","Assigned to shopper!");
														orderObject.put("user_full_name",fullName);
														orderObject.put("phoneno",phoneNo);
														orderObject.put("lat",sLat);
														orderObject.put("lng",sLng);
														orderObject.put("toLat",toLat);
														orderObject.put("tip_paid","no");
														orderObject.put("toLng",toLng);
														orderObject.saveInBackground(new SaveCallback()
														{

															@Override
															public void done(ParseException e) {
																
																if(e==null)
																{
																final String orderId = orderObject.getObjectId();
																
																//calling the cloud function
																HashMap<String,String> params = new HashMap<String,String>();
																params.put("orderId",orderId);
																ParseCloud.callFunctionInBackground("hello",params, new FunctionCallback<String>() {
																	  public void done(String result, ParseException e) {
																	    if (e == null) {
																	      Toast.makeText(Screen_3.this,result,Toast.LENGTH_LONG).show();
																	      
																	   /*   SharedPreferences sp = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
																	      Editor ed = sp.edit();
																	      ed.put("order")  */
																	      progress.dismiss();
																	      Intent i = new Intent("trackShopperScreen");
																	      i.putExtra("orderX",sLat);
																	      i.putExtra("orderY",sLng);
																	      i.putExtra("orderId",orderId);
																	      i.putExtra("orderList",getIntent().getStringArrayListExtra("orderList"));
																	      finish();
																	      
																	      startActivity(i);
																	      overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
																			
																	      
																	    
																	      
																	    }
																	    else
																	    {
																	    	Toast.makeText(Screen_3.this,e.getMessage(),Toast.LENGTH_LONG).show();
																	    	progress.dismiss();
																	    }
																	  }
																	});
															}
																else
																	Toast.makeText(Screen_3.this,"Error saving!",Toast.LENGTH_LONG).show();
																
																
															}
															
														});
														
													}
													
												}
												else if(progress.isShowing())
												{
													progress.dismiss();
													Toast.makeText(Screen_3.this,e.getMessage(),Toast.LENGTH_LONG).show();
												}
												
											}
									
										});
								
								
							}
						}
						else if(progress.isShowing())
						{
							progress.dismiss();
							Toast.makeText(Screen_3.this,"Some error occurred. Try again!",Toast.LENGTH_LONG).show();
						}
						
						
					}
			
				});
		
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
		toAddress = sharedPrefs.getString("tag","")+"\n"+sharedPrefs.getString("details","")+"\n"+sharedPrefs.getString("pincode","");
        toText.setText(toAddress);
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
