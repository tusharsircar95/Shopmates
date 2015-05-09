package com.elevenestates.shopmates2;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class AboutScreen extends Activity{

	TextView heading,header1,header2,para1,para2;
	
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.about_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>About</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("About");
      	
        heading = (TextView) findViewById(R.id.heading);
        header1 = (TextView) findViewById(R.id.header1);
        header2 = (TextView) findViewById(R.id.header2);
        para1 = (TextView) findViewById(R.id.para1);
        para2 = (TextView) findViewById(R.id.para2);
        
        heading.setTypeface(customFont,Typeface.BOLD);
        header1.setTypeface(customFont,Typeface.BOLD);
        header2.setTypeface(customFont,Typeface.BOLD);
        para1.setTypeface(customFont);
        para2.setTypeface(customFont);
        
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
