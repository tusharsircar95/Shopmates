package com.elevenestates.shopmates2;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class TipScreen extends Activity implements OnClickListener{

	
	Button payTip;
	EditText tip;
	
	String orderId,shopperId;
	float amount;
	float calculatedTip;
	float paidTip;
	
	Double toLat,toLng;
	Double storeLat,storeLng;
	
	float f;
	ProgressDialog progress;
	
	RatingBar ratingBar;
	TextView information,currentRating,title;
	CircularImageView shopperImage;
	
	float cRating,nRating;
	int noOfRatings;
	
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.tip_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().hide();
		
		 //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	
      
		 //setup progress bar
      	progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Calculating tip...");
        progress.setIndeterminate(true);
        progress.show();
		
		payTip = (Button) findViewById(R.id.payTip);
		tip = (EditText) findViewById(R.id.tip);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		information = (TextView) findViewById(R.id.information);
		currentRating = (TextView) findViewById(R.id.currentRating);
		title = (TextView) findViewById(R.id.title);
		shopperImage = (CircularImageView) findViewById(R.id.shopperImage);
		
		payTip.setOnClickListener(this);
		
		orderId = getIntent().getStringExtra("orderId");
		shopperId = getIntent().getStringExtra("shopperId");
		
		title.setTypeface(customFont);
		currentRating.setTypeface(customFont);
		information.setTypeface(customFont);
		tip.setTypeface(customFont);
		
		tipCalculate();
		getShopperInformation();
		
		
		
	}

	


	public void getShopperInformation()
	{
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Shopper");
		query.getInBackground(shopperId,new GetCallback<ParseObject>()
				{

					@Override
					public void done(ParseObject object, ParseException e) {
						// TODO Auto-generated method stub
						if(e==null)
						{
							information.setText(object.getString("name"));
							currentRating.setText(object.getString("rating"));
							noOfRatings = Integer.parseInt(object.getString("no_of_ratings"));
							cRating = Float.parseFloat(object.getString("rating"));
							
							ParseFile itemImage = (ParseFile) object.getParseFile("image");
							if(itemImage!=null)
							{
							itemImage.getDataInBackground(new GetDataCallback()
							{

								@Override
								public void done(byte[] data, ParseException e) {
									
									if(e==null)
									{
									Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
									
									if(bitmap!=null)
										shopperImage.setImageBitmap(bitmap);
								
									}
									else
										Toast.makeText(TipScreen.this,"Error in retrieving image!",Toast.LENGTH_LONG).show();
								}
							});
							
							}
								//
									
						}
						
					}
			
				});
		
		
	}
	
	public void tipCalculate()
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
		query.getInBackground(orderId,new GetCallback<ParseObject>()
				{

					@Override
					public void done(ParseObject object, ParseException e) {
						// TODO Auto-generated method stub
						if(e==null)
						{
							amount = Float.parseFloat(object.getString("amount"));
							
							toLat = Double.parseDouble(object.getString("toLat"));
							toLng = Double.parseDouble(object.getString("toLng"));
							storeLat = Double.parseDouble(object.getString("lat"));
							storeLng = Double.parseDouble(object.getString("lng"));
							
							Double dLat = toLat - storeLat;
							Double dLng = toLng - storeLng;
							
							Double a = Math.pow(Math.sin(Math.toRadians(dLat/2)),2) + (Math.cos(Math.toRadians(toLat)))*(Math.cos(Math.toRadians(storeLat)))*(Math.pow(Math.sin(Math.toRadians(dLng/2)),2));
							Double c = 2*Math.atan2(Math.pow(a,0.5),Math.pow(1-a,0.5));
							
							Double distance = (6371.0)*c;
							
							calculatedTip = (float)  (5*distance);
							
							int tipValue = (int) calculatedTip;	
							
							tip.setHint("Estimated Tip\nINR " + tipValue);
							progress.dismiss();
						}
						
					}
			
				});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.payTip:
			if(tip.getText().toString().equals(""))
				f = (float) Math.floor(calculatedTip);
			else
				f = Float.parseFloat(tip.getText().toString());
			
			if(ratingBar.getRating() == 0)
			{
				Toast.makeText(TipScreen.this,"You must provide a rating!",Toast.LENGTH_LONG).show();
			}
			else if(f<Math.floor(calculatedTip))
				Toast.makeText(TipScreen.this,"Tip lower than the minimum value!",Toast.LENGTH_LONG).show();
			else
			{
				ParseQuery<ParseObject> query = ParseQuery.getQuery("UserObject");
				query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
				query.findInBackground(new FindCallback<ParseObject>()
						{

							@Override
							public void done(List<ParseObject> objects,
									ParseException e) {
								if(e==null)
								{
									float wallet = Float.parseFloat(objects.get(0).getString("wallet"));
									if(wallet>=f)
									{
										wallet = wallet - f;
										objects.get(0).put("wallet",wallet+"");
										objects.get(0).saveInBackground();
										
										//proceed by editing order
										ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
										query.getInBackground(orderId,new GetCallback<ParseObject>()
												{

													@Override
													public void done(
															ParseObject object,
															ParseException e) {
														if(e==null)
														{
															object.put("tip_paid","yes");
															object.saveInBackground();
															
															Toast.makeText(TipScreen.this,"Payment made successfully! Thank you for using our service!",Toast.LENGTH_LONG).show();
															
															SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
															Editor editor = sharedPrefs.edit();
															editor.putBoolean("orderPending",false);
															editor.commit();
															
															Intent i = new Intent("main_screen");
															startActivity(i);
															finish();
														}
														
													}
											
												});
										
										//proceed also by editing rating
										nRating = ((cRating*noOfRatings) + ratingBar.getRating())/(noOfRatings+1);
										ParseQuery<ParseObject> q = ParseQuery.getQuery("Shopper");
										q.getInBackground(shopperId,new GetCallback<ParseObject>()
												{

													@Override
													public void done(
															ParseObject object,
															ParseException e) {
														if(e==null)
														{
															object.put("rating",nRating+"");
															object.put("no_of_ratings",(noOfRatings+1)+"");
															object.saveInBackground();
															
															
															
														}
														else
															Toast.makeText(TipScreen.this,e.getMessage()+"Couldn't submit rating!",Toast.LENGTH_LONG).show();
														
													}
												
												});
										
										
									}
									else
										Toast.makeText(TipScreen.this,"Insufficient balance in wallet! Recharge now!",Toast.LENGTH_LONG).show();
									
								}
								
							}
					
						});
				
			}
			
			break;
		}
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
