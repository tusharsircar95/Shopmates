package com.elevenestates.shopmates2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class CustomGallery extends Gallery{

	public CustomGallery(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}
	
	public CustomGallery(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	public CustomGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public CustomGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	 @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	    {

	        //limit the max speed in either direction
	        if (velocityX > 1200.0f)
	        {
	            velocityX = 1200.0f;
	        }
	        else if(velocityX < -1200.0f)
	        {
	            velocityX = -1200.0f;
	        }

	        return super.onFling(e1, e2, velocityX, velocityY);
	    }
}
