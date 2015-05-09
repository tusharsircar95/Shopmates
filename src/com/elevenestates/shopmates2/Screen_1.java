package com.elevenestates.shopmates2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Screen_1 extends Activity implements OnClickListener{

	Button selectAddress;
	CustomEditText[] orderTexts;
	
	TextView helpText;
	LinearLayout orderList;
	ScrollView scrollView;
	RelativeLayout screen;
	int noOfFields = 1;
	int noOfOrders = 0;
	int dummy = 0;
	
	int height;
	Typeface customFont;
	ArrayList<String> orders;
	boolean editing = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.screen_1);
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
      
       
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Order");
      	
        helpText = (TextView) findViewById(R.id.helpText);
        selectAddress = (Button) findViewById(R.id.selectAddress);
        orderList = (LinearLayout) findViewById(R.id.orderList);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        screen = (RelativeLayout) findViewById(R.id.screen);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int w = size.x;
        int h = size.y;
        height = (int) (0.30*h);
        Toast.makeText(this,height+"",Toast.LENGTH_SHORT).show();
        
        orderTexts = new CustomEditText[50];
        
        orderTexts[1] = (CustomEditText) findViewById(R.id.orderText);
        orderTexts[1].addTextChangedListener(new EditTextChangeListener());
        orderTexts[1].setTypeface(customFont);
        orderTexts[1].setOnTouchListener(new EditTextTouchListener());
       
        
        selectAddress.setOnClickListener(this);
        selectAddress.setTypeface(customFont);
        helpText.setTypeface(customFont,Typeface.BOLD);
        
        //receive order list
        orders = getIntent().getStringArrayListExtra("orderList");
        if(orders!=null)
        {
        	orderTexts[1].setText(orders.get(0));
        	noOfOrders = 1;
        	for(int i=1; i<orders.size(); i++)
        	{
        		orderTexts[++noOfFields] = createNewEditText("Item #"+noOfFields);
        		orderTexts[noOfFields].setText(orders.get(i));
				orderList.addView(orderTexts[noOfFields]);
				noOfOrders++;
        	}
        	orderTexts[++noOfFields] = createNewEditText("Item #"+noOfFields);
			orderList.addView(orderTexts[noOfFields]);
        }
		
	}

	private CustomEditText createNewEditText(String hint) {
	    
		
		final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	    final CustomEditText textView = new CustomEditText(Screen_1.this,scrollView);
	    textView.setLayoutParams(lparams);
	    textView.setHint(hint)
	    textView.setBackgroundColor(Color.WHITE);
	    textView.addTextChangedListener(new EditTextChangeListener());
	    textView.setOnTouchListener(new EditTextTouchListener());
	    textView.setGravity(Gravity.CENTER);
	    textView.setTypeface(customFont);
	    
	    
	    
	    
	    return textView;
	}
		
	
	
	private class EditTextTouchListener implements OnTouchListener
	{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if(event.getAction() == MotionEvent.ACTION_UP)
			{
				RelativeLayout.LayoutParams layoutParams =  new LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, height);
				layoutParams.addRule(RelativeLayout.BELOW,R.id.helpText);
				scrollView.setLayoutParams(layoutParams);
			}
			
			
			return false;
		}
		
	}
	private class EditTextChangeListener implements TextWatcher
	{

		@Override
		public void afterTextChanged(Editable e) {
			if(e.toString().length() == 1 && noOfFields-noOfOrders<2 && dummy!=2)
			{
				orderTexts[++noOfFields] = createNewEditText("Item #"+noOfFields);
				orderList.addView(orderTexts[noOfFields]);
				noOfOrders++;
			}
			else if(e.toString().length() == 0)
			{
				noOfOrders--;
			}
			
		}

		@Override
		public void beforeTextChanged(CharSequence c, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			dummy = c.toString().length();
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.selectAddress:
			Intent i = new Intent("addressScreen");
			startActivity(i);
			overridePendingTransition(R.anim.up_slide,R.anim.no_slide);
			
		}
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		if(!editing)
		{
		  //define custom font
      	Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	
		getMenuInflater().inflate(R.menu.menu_screen1,menu);
		final MenuItem item = menu.getItem(0);
		TextView text = (TextView) item.getActionView().findViewById(R.id.menuText);
		text.setText("SELECT STORE");
		text.setTypeface(customFont,Typeface.BOLD);
		 item.getActionView().setOnClickListener(new OnClickListener()
		 {
			 public void onClick(View v)
			 {
				 onOptionsItemSelected(item);
			 }
		 });
		
		}
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			if(editing)
			{
				SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
		        if(sharedPrefs.getString("tag","").equals("") || sharedPrefs.getString("details","").equals("") || sharedPrefs.getString("pincode","").equals(""))
		        		Toast.makeText(Screen_1.this,"You must select atleast one delivery address!",Toast.LENGTH_LONG).show();
		        else
		        {
		        	orders = new ArrayList<String>();
		        	for(int i=1; i<=noOfFields; i++)
		        		if(!orderTexts[i].getText().toString().equals(""))
		        			orders.add(orderTexts[i].getText().toString());
		        	if(orders.size() == 0)
		        		Toast.makeText(Screen_1.this,"You haven't typed your order!",Toast.LENGTH_LONG).show();
		        	else
		        	{
		        		Intent i = new Intent("screen_3");
						i.putExtra("store",getIntent().getStringExtra("store"));
						i.putExtra("lat",getIntent().getStringExtra("lat"));
						i.putExtra("lng",getIntent().getStringExtra("lng"));
						i.putStringArrayListExtra("orderList",orders);
						startActivity(i);
		        		overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
		        		finish();
		        	}
		        }
			}
			else
			{
				finish();
				overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
			}
			break;
		case R.id.proceed:
			SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
	        if(sharedPrefs.getString("tag","").equals("") || sharedPrefs.getString("details","").equals("") || sharedPrefs.getString("pincode","").equals(""))
	        		Toast.makeText(Screen_1.this,"You must select atleast one delivery address!",Toast.LENGTH_LONG).show();
	        else
	        {
	        	orders = new ArrayList<String>();
	        	for(int i=1; i<=noOfFields; i++)
	        		if(!orderTexts[i].getText().toString().equals(""))
	        			orders.add(orderTexts[i].getText().toString());
	        	if(orders.size() == 0)
	        		Toast.makeText(Screen_1.this,"You haven't typed your order!",Toast.LENGTH_LONG).show();
	        	else
	        	{
	        		Intent i = new Intent("storeSearchScreen");
	        		i.putStringArrayListExtra("orderList",orders);
	        		startActivity(i);
	        		overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
	        		finish();
	        	}
	        }
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		editing = getIntent().getBooleanExtra("editing",false);
		SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
		selectAddress.setText("Deliver To: " + sharedPrefs.getString("tag",""));
		if(selectAddress.getText().toString().equals(""))
			selectAddress.setText("Delivery Address");
		
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		
		if(editing)
		{
			SharedPreferences sharedPrefs = getSharedPreferences("defaultAddress",Context.MODE_PRIVATE);
	        if(sharedPrefs.getString("tag","").equals("") || sharedPrefs.getString("details","").equals("") || sharedPrefs.getString("pincode","").equals(""))
	        		Toast.makeText(Screen_1.this,"You must select atleast one delivery address!",Toast.LENGTH_LONG).show();
	        else
	        {
	        	orders = new ArrayList<String>();
	        	for(int i=1; i<=noOfFields; i++)
	        		if(!orderTexts[i].getText().toString().equals(""))
	        			orders.add(orderTexts[i].getText().toString());
	        	if(orders.size() == 0)
	        		Toast.makeText(Screen_1.this,"You haven't typed your order!",Toast.LENGTH_LONG).show();
	        	else
	        	{
	        		Intent i = new Intent("screen_3");
					i.putExtra("store",getIntent().getStringExtra("store"));
					i.putExtra("lat",getIntent().getStringExtra("lat"));
					i.putExtra("lng",getIntent().getStringExtra("lng"));
					i.putStringArrayListExtra("orderList",orders);
					startActivity(i);
	        		overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
	        		finish();
	        	}
	        }
			
		
		}
		else
		{
			finish();
			overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
		}
	
		
	}
	
	
	
}