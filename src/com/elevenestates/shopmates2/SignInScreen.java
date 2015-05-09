package com.elevenestates.shopmates2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class SignInScreen extends Activity implements OnClickListener{

	EditText email,password;
	Button signin;
	TextView forgotPassword;
	ProgressDialog progress;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.sign_in_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Sign In</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Sign In");
        
        Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		

      //set progress bar
      		progress = new ProgressDialog(this);
      		progress.setCancelable(false);
      		progress.setMessage("Logging in...");
      		progress.setIndeterminate(true);
        
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        
        signin.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        forgotPassword.setTypeface(customFont);
        signin.setTypeface(customFont);
        email.setTypeface(customFont);
        password.setTypeface(customFont);
        
	}

	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.signin:
			if(email.getText().toString().equals("") || password.getText().toString().equals(""))
			{
				Toast.makeText(SignInScreen.this,"Fields can't be left empty!",Toast.LENGTH_LONG).show();
				break;
			}
			progress.show();
			ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(), new LogInCallback()
			{

				@Override
				public void done(ParseUser parseUser, ParseException e) {
					if(e!=null)
					{
						Toast.makeText(SignInScreen.this,e.getMessage(), Toast.LENGTH_LONG).show();
						if(progress!=null && progress.isShowing())
							progress.dismiss();
					}
					else
					{
						ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
						query.whereEqualTo("username",parseUser.getUsername());
						query.whereEqualTo("tip_paid","no");
						query.findInBackground(new FindCallback<ParseObject>()
								{

									@Override
									public void done(List<ParseObject> objectList,
											ParseException e) {
										// TODO Auto-generated method stub
										if(e==null)
										{
											if(objectList.size()!=0)
											{
												//user has logged in
											
												SharedPreferences sharedPrefs = getSharedPreferences("orderPending",Context.MODE_PRIVATE);
												
												Intent i = new Intent("trackShopperScreen");
												i.putExtra("orderX",sharedPrefs.getString("orderX",""));
												i.putExtra("orderY",sharedPrefs.getString("orderY",""));
												i.putExtra("orderId",sharedPrefs.getString("orderId",""));
												
												ArrayList<String> orderList = new ArrayList<String>();
												int size = Integer.parseInt(sharedPrefs.getString("orderListSize","0"));
												for(int k=0; k<size; k++)
													orderList.add(sharedPrefs.getString("order"+k,""));
												
												i.putExtra("orderList",orderList);
												
												if(progress!=null && progress.isShowing())
													progress.dismiss();
												
												startActivity(i);
												overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
												finish();
												
											}
											else
											{
												//user has logged in
												if(progress!=null && progress.isShowing())
													progress.dismiss();
												Intent i = new Intent("main_screen");
												startActivity(i);
												overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
												finish();
												
											}
										}
									}
							
								});
						
						
						
					}
				}
				
			});
			break;
		case R.id.forgotPassword:
			if(email.getText().toString().equals(""))
				Toast.makeText(SignInScreen.this,"You must enter your email ID!",Toast.LENGTH_LONG).show();
			else
			{
				ParseUser.requestPasswordResetInBackground(email.getText().toString(),
                    new RequestPasswordResetCallback() {
				public void done(ParseException e) {
					if (e == null) {
						// An email was successfully sent with reset instructions.
						Toast.makeText(SignInScreen.this,"An email has been sent with reset instructions to "+email.getText().toString()+"!",Toast.LENGTH_LONG).show();	
					} else {
						// Something went wrong. Look at the ParseException to see what's up.
						Toast.makeText(SignInScreen.this,e.getMessage(),Toast.LENGTH_LONG).show();		
					}
				}
			});
			}
			break;
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			Intent i = new Intent("start_screen");
			startActivity(i);
			overridePendingTransition(R.anim.no_slide,R.anim.existing_down_slide);
			finish();
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
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
		Intent i = new Intent("start_screen");
		startActivity(i);
		overridePendingTransition(R.anim.no_slide,R.anim.existing_down_slide);
		Toast.makeText(SignInScreen.this,"dfs",Toast.LENGTH_LONG).show();
	}
	
}
