package com.elevenestates.shopmates2;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;

public class StartScreen extends Activity implements OnClickListener{

	TextView signup,signin,howitworks,title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start_screen);
		getActionBar().hide();
		
		Parse.initialize(this, "yPiVUng1QaEHEfWRxY111LqvpF2QoGecWEHn0dpf", "YFXesKpPVUPIgD719giidQEkWhumoSjKCewBaDhI");
  		
		/*ParseCloud.callFunctionInBackground("sendMessage",new HashMap<String,String>(), new FunctionCallback<String>() {
			  public void done(String result, ParseException e) {
			    if (e == null) {
			      Toast.makeText(StartScreen.this,result,Toast.LENGTH_LONG).show();
			    }
			    else
			    	Toast.makeText(StartScreen.this,e.getMessage(),Toast.LENGTH_LONG).show();
			  }
			}); */
		
		
		
		
		//define custom font
		Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
		
		signup = (TextView) findViewById(R.id.signup);
		signin = (TextView) findViewById(R.id.signin);
		howitworks = (TextView) findViewById(R.id.howitworks);
		title = (TextView) findViewById(R.id.title);
		howitworks.setPaintFlags(howitworks.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        
		signup.setTypeface(customFont);
		signin.setTypeface(customFont);
		howitworks.setTypeface(customFont);
		title.setTypeface(customFont,Typeface.BOLD);
		
		signup.setOnClickListener(this);
		signin.setOnClickListener(this);
		howitworks.setOnClickListener(this);
		
		
	}

	public void onClick(View v) {
		Intent i = null;
		switch(v.getId())
		{
		case R.id.signup:
			i = new Intent("signUpScreen");
			startActivity(i);
			finish();
			overridePendingTransition(R.anim.up_slide,R.anim.no_slide);
			break;
		case R.id.signin:
			i = new Intent("signInScreen");
			startActivity(i);
			finish();
			overridePendingTransition(R.anim.up_slide,R.anim.no_slide);
			break;
		case R.id.howitworks:
			i = new Intent("howItWorksScreen");
			startActivity(i);
			overridePendingTransition(R.anim.up_slide,R.anim.no_slide);
			break;
		}
		
		
	}

	
}
