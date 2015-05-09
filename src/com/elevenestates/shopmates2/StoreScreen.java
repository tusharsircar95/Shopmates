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
import com.elevenestates.shopmates2.CustomStore.StoreAdapter;
import com.elevenestates.shopmates2.CustomStore.StoreDetails;
import com.elevenestates.shopmates2.Database.DatabaseStore;

public class StoreScreen extends Activity implements OnClickListener{
 
	int DEFAULT_POSITION = 0;
	boolean editable;
	
	ListView storeListView;
	TextView addStore,helpText;
	
	StoreAdapter adapter;
	ArrayList<StoreDetails> storeList;
	
	AlertDialog dialog;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.store_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Stores</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Stores");
        
        editable = false;
        
        storeListView = (ListView) findViewById(R.id.storeList);
        addStore = (TextView) findViewById(R.id.addStore);
        helpText = (TextView) findViewById(R.id.helpText);
        
        if(!editable)
        	helpText.setText("Select a store");
        
        addStore.setOnClickListener(this);
        
        addStore.setTypeface(customFont);
        helpText.setTypeface(customFont);
        
        populateAddressList();
        setListAdapter();
        setupListClick();
        
        
        
	}

	
	private void populateAddressList() {
		storeList = new ArrayList<StoreDetails>();
		DatabaseStore da = new DatabaseStore(StoreScreen.this);
		da.open();
		da.getData(storeList);
		da.close();
		
	}


	private void setListAdapter()
	{
		adapter = new StoreAdapter(StoreScreen.this,storeList);
        storeListView.setAdapter(adapter);
	}
	
	private void setupListClick() {
		storeListView.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {
				
				final int position = pos;
				if(editable)
				{
					Intent i = new Intent("editAddressScreen");
					i.putExtra("tag",storeList.get(position).getTag());
					i.putExtra("details",storeList.get(position).getAddress());
					i.putExtra("pincode",storeList.get(position).getPincode());
					startActivity(i);
				}
				else
				{
					SharedPreferences sharedPrefs = getSharedPreferences("defaultStore",Context.MODE_PRIVATE);
					Editor editor = sharedPrefs.edit();
					editor.putString("tag",storeList.get(position).getTag());
					editor.putString("details",storeList.get(position).getAddress());
					editor.putString("pincode",storeList.get(position).getPincode());
					editor.putString("lat",storeList.get(position).getLat());
					editor.putString("lng",storeList.get(position).getLng());
					editor.commit();
					
					Intent i = new Intent("screen_3");
					i.putExtra("store",storeList.get(position).getTag()+"\n"+storeList.get(position).getAddress()+"\n"+storeList.get(position).getPincode());
					i.putStringArrayListExtra("orderList",StoreScreen.this.getIntent().getStringArrayListExtra("orderList"));
					i.putExtra("lat",storeList.get(position).getLat());
					i.putExtra("lng",storeList.get(position).getLng());
					startActivity(i);
					overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
					finish();
				}
				
				
			}
			
		});
		
	}

	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.addStore:
			Intent i = new Intent("addStoreScreen");
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
			Intent i = new Intent("storeSearchScreen");
			i.putStringArrayListExtra("orderList",getIntent().getStringArrayListExtra("orderList"));
			startActivity(i);
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
		Intent i = new Intent("storeSearchScreen");
		i.putStringArrayListExtra("orderList",getIntent().getStringArrayListExtra("orderList"));
		startActivity(i);
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

