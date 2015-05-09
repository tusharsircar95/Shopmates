package com.elevenestates.shopmates2;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class InvoiceScreen extends Activity{

	LinearLayout views;
	LayoutInflater inflater;
	String orderId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.invoice_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Invoice</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
		orderId = getIntent().getStringExtra("orderId");
		
		views = (LinearLayout) findViewById(R.id.views);
		inflater = this.getLayoutInflater();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
		query.getInBackground(orderId,new GetCallback<ParseObject>()
				{

					@Override
					public void done(ParseObject object, ParseException e) {
						if(e==null)
						{
							JSONArray names = object.getJSONArray("order");
							JSONArray prices = object.getJSONArray("prices");
							for(int i=0; i<names.length(); i++)
							{
								View v = inflater.inflate(R.layout.invoice_item,null);
					            EditText et = (EditText) v.findViewById(R.id.itemPrice);
					            TextView tv = (TextView) v.findViewById(R.id.itemName);
					            try {
									et.setText(prices.getString(i));
									tv.setText(names.getString(i));
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
					            
					            
					            views.addView(v);
							}
						}
						else
							Toast.makeText(InvoiceScreen.this,e.getMessage(),Toast.LENGTH_LONG).show();
						
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

	
	
}
