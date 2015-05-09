package com.elevenestates.shopmates2;

import java.util.ArrayList;

import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OptionsScreen extends Activity{

	ArrayList<String> listValues;
	DrawerListAdapter adapter;
	ListView listView;
	ProgressDialog progress;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.options_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Menu</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setMessage("Loading data");
        progress.setIndeterminate(true);
		
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Menu");
      	
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setVerticalScrollBarEnabled(false);
        setupList();
        setupListListener();
        
	}

	private void setupListListener() {
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String option = listValues.get(position);
				Intent i = null;
				if(option.equals("Addresses"))
				{
					i = new Intent("addressScreen");
					i.putExtra("editable",true);
				}
				else if(option.equals("Sign Out"))
				{
					progress.show();
					ParseUser.logOut();
					if(progress!=null && progress.isShowing())
						progress.dismiss();
					i = new Intent("start_screen");
					startActivity(i);
					overridePendingTransition(R.anim.no_slide,R.anim.existing_up_slide);
					finish();
				}
				else if(option.equals("My Orders"))
					i = new Intent("orderHistoryScreen");
				else if(option.equals("New Order"))
					i = new Intent("main_screen");
				else if(option.equals("Password"))
					i = new Intent("passwordScreen");
				else if(option.equals("Credits"))
					i = new Intent("creditsScreen");
				else if(option.equals("About"))
					i = new Intent("aboutScreen");
				else if(option.equals("Offers"))
					i = new Intent("offersScreen");
					
				if(i!=null && !option.equals("Sign Out"))
				{
					startActivity(i);
					overridePendingTransition(R.anim.no_slide,R.anim.existing_up_slide);
					finish();
				}
			}
			
		});
	}

	private void setupList() {
		listValues = new ArrayList<String>();
		listValues.add("New Order");
		listValues.add("My Orders");
		listValues.add("Offers");
		listValues.add("Addresses");
		listValues.add("Credits");
		//listValues.add("Password");
		//listValues.add("Invite Friends");
		listValues.add("About");
		listValues.add("Sign Out");
		adapter = new DrawerListAdapter(OptionsScreen.this,listValues);
		listView.setAdapter(adapter);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.no_slide,R.anim.existing_up_slide);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.no_slide,R.anim.existing_up_slide);
		
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
