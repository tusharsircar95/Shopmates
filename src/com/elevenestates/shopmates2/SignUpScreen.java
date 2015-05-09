package com.elevenestates.shopmates2;

import org.json.JSONArray;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpScreen extends Activity implements OnClickListener{

	EditText fullname,email,password,confirmpassword,phoneno;
	ProgressDialog progress;
	Button signup;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.sign_up_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Sign Up</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        progress = new ProgressDialog(this);
		progress.setMessage("Signing up");
		progress.setCancelable(false);
		progress.setIndeterminate(true);
		
		  //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Sign Up");
		
		Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		

		
		
		fullname = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		confirmpassword = (EditText) findViewById(R.id.confirmpassword);
		phoneno = (EditText) findViewById(R.id.phoneno);
		signup = (Button) findViewById(R.id.signUp);
		
		fullname.setTypeface(customFont);
		email.setTypeface(customFont);
		password.setTypeface(customFont);
		confirmpassword.setTypeface(customFont);
		phoneno.setTypeface(customFont);
		signup.setTypeface(customFont);
		
		fullname.clearFocus();
		email.clearFocus();
		password.clearFocus();
		confirmpassword.clearFocus();
		
		signup.setOnClickListener(this);
		

	}
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.signUp:
			progress.show();
			if(fullname.getText().toString().equals("") ||  email.getText().toString().equals("") || password.getText().toString().equals("") || confirmpassword.getText().toString().equals("") || phoneno.getText().toString().equals(""))
			{
				Toast.makeText(SignUpScreen.this,"Fields can't be left empty!", Toast.LENGTH_LONG).show();
				if(progress!=null && progress.isShowing())
					progress.dismiss();
			}
			else if(!password.getText().toString().equals(confirmpassword.getText().toString()))
			{
				if(progress!=null && progress.isShowing())
					progress.dismiss();
				Toast.makeText(SignUpScreen.this,"Passwords do not match!", Toast.LENGTH_LONG).show();
			}
			else
			{
				//if data is entered correctly
				ParseUser user = new ParseUser();
				user.setUsername(email.getText().toString());
				user.setPassword(password.getText().toString());
				user.put("name",fullname.getText().toString());
				user.put("phoneno",phoneno.getText().toString());
				user.signUpInBackground(new SignUpCallback()
				{

					
					public void done(ParseException e) {
						if(e!=null)
							Toast.makeText(SignUpScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
						else
						{
							
							//create empty arrays for current user
							JSONArray array = new JSONArray();
							ParseObject object = new ParseObject("UserObject");
							object.put("username",email.getText().toString());
							object.put("tag_array",array);
							object.put("details_array",array);
							object.put("pincode_array",array);
							object.put("user_full_name",fullname.getText().toString());
							object.put("phoneno",phoneno.getText().toString());
							object.put("wallet","500");
							object.saveInBackground();
							
							progress.setMessage("Logging in...");
							//log the user in
							ParseUser.logInInBackground(email.getText().toString(), password.getText().toString(),new LogInCallback()
							{

								@Override
								public void done(ParseUser user,
										ParseException e) {
									
									Toast.makeText(SignUpScreen.this, "Logged In as " + email.getText().toString() , Toast.LENGTH_LONG).show();
									
									//clear all fields
									fullname.setText("");
									email.setText("");
									password.setText("");
									confirmpassword.setText("");
									phoneno.setText("");
									if(progress!=null && progress.isShowing())
										progress.dismiss();
									Intent i = new Intent("main_screen");
									i.putExtra("showToast",true);
									startActivity(i);
									overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
									finish();
									
									
								}
								
							});
							
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent("start_screen");
		startActivity(i);
		overridePendingTransition(R.anim.no_slide,R.anim.existing_down_slide);	
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
}

