package com.elevenestates.shopmates2;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseUser;

public class PasswordScreen extends Activity{

	
	EditText currentPassword,newPassword;
	ParseUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.password_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Password</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		user = ParseUser.getCurrentUser();
        
        currentPassword = (EditText) findViewById(R.id.currentPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        
        
        
        
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_screen_password,menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
		case R.id.save:
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
