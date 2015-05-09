package com.elevenestates.shopmates2;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainScreen extends Activity{

	TextView typeText,working,title;
	ImageButton drawerButton;
	ArrayList<String> listValues;
	
	boolean orderPending;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.main_screen);
		getActionBar().hide();
        	
		SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
		orderPending = sharedPrefs.getBoolean("orderPending",false);
		
		//define custom font
      	Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	
		
        Parse.initialize(this, "yPiVUng1QaEHEfWRxY111LqvpF2QoGecWEHn0dpf", "YFXesKpPVUPIgD719giidQEkWhumoSjKCewBaDhI");
  		ParseUser user = ParseUser.getCurrentUser();
  		if(getIntent().getBooleanExtra("showToast",false))
  		{
  			if(user!=null)
  				Toast.makeText(this,"Logged in as "+user.getUsername(),Toast.LENGTH_LONG).show();
  		}
		
		typeText = (TextView) findViewById(R.id.next);
		working = (TextView) findViewById(R.id.working);
		title = (TextView) findViewById(R.id.title);
		drawerButton = (ImageButton) findViewById(R.id.drawerButton);
		
		typeText.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v) {
				if(!orderPending)
				{
					Intent i = new Intent("screen_1");
					startActivity(i);
					overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
				}
				else
					Toast.makeText(MainScreen.this,"You must wait till your current order is delivered!",Toast.LENGTH_LONG).show();
			}
			
		});
		
		drawerButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				Intent i = new Intent("optionsScreen");
				startActivity(i);
				overridePendingTransition(R.anim.down_slide, R.anim.no_slide);
			}
			
		});
		
		
		working.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				
				Intent i = new Intent("howItWorksScreen");
				startActivity(i);
				overridePendingTransition(R.anim.up_slide,R.anim.no_slide);
				
			}
				
		});
		working.setPaintFlags(working.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        
		typeText.setTypeface(customFont);
		working.setTypeface(customFont);
		title.setTypeface(customFont,Typeface.BOLD);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
		orderPending = sharedPrefs.getBoolean("orderPending",false);
		
	}

	

	
}
