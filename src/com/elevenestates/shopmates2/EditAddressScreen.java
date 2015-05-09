package com.elevenestates.shopmates2;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.elevenestates.shopmates2.CustomAddress.AddressDetails;
import com.elevenestates.shopmates2.Database.DatabaseAddress;

public class EditAddressScreen extends Activity{

	EditText tag;
	EditText details;
	EditText pincode;
	ImageButton cancel;
	Button done;
	int sno;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.address_edit_dialog);
	/*	getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Edit Address</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //define custom font
      	Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	
        
		
		tag = (EditText) findViewById(R.id.tag);
		details = (EditText) findViewById(R.id.details);
		pincode = (EditText) findViewById(R.id.pincode);
		done = (Button) findViewById(R.id.done);
		
		tag.setTypeface(customFont);
		details.setTypeface(customFont);
		pincode.setTypeface(customFont);
		done.setTypeface(customFont);
		
		tag.setHint(getIntent().getStringExtra("tag"));
		details.setHint(getIntent().getStringExtra("details"));
		pincode.setHint(getIntent().getStringExtra("pincode"));
		
		final String currentTag = tag.getHint().toString();
		final String currentDetails = details.getHint().toString();
		final String currentPincode = pincode.getHint().toString();
		
		DatabaseAddress da = new DatabaseAddress(this);
		da.open();
		sno = da.getPositionOf(currentTag,currentDetails,currentPincode);
		da.close();
		
		done.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v) {
				
				AddressDetails ad = new AddressDetails("","","");
				if(tag.getText().toString().equals(""))
					ad.setTag(currentTag);
				else
					ad.setTag(tag.getText().toString());
				
				if(details.getText().toString().equals(""))
					ad.setAddress(currentDetails);
				else
					ad.setAddress(details.getText().toString());
				
				if(pincode.getText().toString().equals(""))
					ad.setPincode(currentPincode);
				else
					ad.setPincode(pincode.getText().toString());
				
				DatabaseAddress da = new DatabaseAddress(EditAddressScreen.this);
				da.open();
				da.editSno(sno,ad);
				da.close();
				
				finish();
				
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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();   */
	}
	
	
	
}
