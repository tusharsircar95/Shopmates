package com.elevenestates.shopmates2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;

public class CustomEditText extends EditText{

	Context context;
	ScrollView scrollView;
	
	public CustomEditText(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public CustomEditText(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		
	}
	
	public CustomEditText(Context context,ScrollView scrollView) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.scrollView = scrollView;
	}
	
	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
	  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	    // Do your thing.
		// Toast.makeText(context,"Removed",Toast.LENGTH_SHORT).show();
		
		 RelativeLayout.LayoutParams layoutParams =  new LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		 layoutParams.addRule(RelativeLayout.BELOW,R.id.helpText);
		 layoutParams.addRule(RelativeLayout.ABOVE,R.id.bottom);
		 if(scrollView!=null)
			 scrollView.setLayoutParams(layoutParams);
			
		 return super.dispatchKeyEvent(event);  // So it is not propagated.
	  }
	  else if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
	  {
		  return false;
	  }
	  else
		  return super.dispatchKeyEvent(event);
	
	}
}
