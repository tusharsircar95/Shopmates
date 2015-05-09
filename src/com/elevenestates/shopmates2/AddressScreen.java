package com.elevenestates.shopmates2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.elevenestates.shopmates2.CustomAddress.AddressAdapter;
import com.elevenestates.shopmates2.CustomAddress.AddressDetails;
import com.elevenestates.shopmates2.Database.DatabaseAddress;

public class AddressScreen extends Activity implements OnClickListener{
 
	int DEFAULT_POSITION = 0;
	boolean editable;
	
	ListView addressListView;
	TextView addAddress,helpText;
	
	AddressAdapter adapter;
	ArrayList<AddressDetails> addressList;
	
	AlertDialog dialog;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.address_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Addresses</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Addresses");
        
        editable = getIntent().getBooleanExtra("editable",false);
        
        addressListView = (ListView) findViewById(R.id.addressList);
        addAddress = (TextView) findViewById(R.id.addAddress);
        helpText = (TextView) findViewById(R.id.helpText);
        
        if(!editable)
        	helpText.setText("Select an address");
        else
        	helpText.setText("");
        
        addAddress.setOnClickListener(this);
        addAddress.setTypeface(customFont);
        helpText.setTypeface(customFont);
        
        populateAddressList();
        setListAdapter();
        setupListClick();
        
        
        
	}

	
	private void populateAddressList() {
		addressList = new ArrayList<AddressDetails>();
		DatabaseAddress da = new DatabaseAddress(AddressScreen.this);
		da.open();
		da.getData(addressList);
		da.close();
		
	}


	private void setListAdapter()
	{
		adapter = new AddressAdapter(AddressScreen.this,addressList);
        addressListView.setAdapter(adapter);
	}
	
	private void setupListClick() {
		addressListView.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {
				
				final int position = pos;
				if(editable)
				{
					Intent i = new Intent("editAddressScreen");
					i.putExtra("tag",addressList.get(position).getTag());
					i.putExtra("details",addressList.get(position).getAddress());
					i.putExtra("pincode",addressList.get(position).getPincode());
					//startActivity(i);
				}
				else
				{
					SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
					Editor editor = sharedPrefs.edit();
					editor.putString("tag",addressList.get(position).getTag());
					editor.putString("details",addressList.get(position).getAddress());
					editor.putString("pincode",addressList.get(position).getPincode());
					editor.putString("lat",addressList.get(position).getLat());
					editor.putString("lng",addressList.get(position).getLng());
					editor.commit();
					finish();
				}
				
				
			}
			
		});
		
	}

	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.addAddress:
			Intent i = new Intent("addAddressScreen");
			startActivity(i);
		}
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 populateAddressList();
	     setListAdapter();
	     setupListClick();
	        
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.no_slide,R.anim.existing_down_slide);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.no_slide,R.anim.existing_down_slide);
		
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

