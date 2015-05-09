package com.elevenestates.shopmates2;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CreditsScreen extends Activity{

	TextView wallet,text,addCredits;
	String username;
	ProgressDialog progress;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.credits_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Credits</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
        //set progress bar
  		progress = new ProgressDialog(this);
  		progress.setCancelable(false);
  		progress.setMessage("Retrieving information...");
  		progress.setIndeterminate(true);
  		progress.show();
  		
        username = ParseUser.getCurrentUser().getUsername();
        
        
        //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Credits");
      	
        wallet = (TextView) findViewById(R.id.wallet);
        text = (TextView) findViewById(R.id.text);
        addCredits = (TextView) findViewById(R.id.addCredits);
        
        wallet.setTypeface(customFont,Typeface.BOLD);
        text.setTypeface(customFont);
        addCredits.setTypeface(customFont);
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserObject");
        query.whereEqualTo("username",username);
        query.findInBackground(new FindCallback<ParseObject>()
        		{

					@Override
					public void done(List<ParseObject> objectList, ParseException e) {
						// TODO Auto-generated method stub
						if(e==null && objectList.size()!=0)
						{
							wallet.setText(objectList.get(0).getString("wallet"));
						}
						else
							Toast.makeText(CreditsScreen.this,"Some error occurred!",Toast.LENGTH_LONG).show();
						progress.dismiss();
					}
        		
        		});
        
        addCredits.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				//use payment gateway here
				
			}
        	
        });
        
        		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
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
