package com.elevenestates.shopmates2;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.siyamed.shapeimageview.CircularImageView;

public class RateScreen extends Activity{

	CircularImageView shopperImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.rate_screen);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().hide();
		
		shopperImage.setImageResource(R.drawable.ic_launcher);
		
	}

	
}
