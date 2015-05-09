package com.elevenestates.shopmates2;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HowItWorksScreen extends Activity{

	CustomGallery gallery;
	 private Integer[] mImageIds = {
          R.drawable.howitworks1,
          R.drawable.howitworks1,
          R.drawable.howitworks1,
          R.drawable.howitworks1
     };
	 static TextView dotText[];
	 LinearLayout dotLayout;
	   
	Typeface customFont;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.how_it_works_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>How It Works</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("How It Works");
      	
        
        
        
        gallery = (CustomGallery) findViewById(R.id.gallery);
        gallery.setSpacing(2);
        gallery.setAdapter(new GalleryImageAdapter(this));
        dotText = new TextView[gallery.getAdapter().getCount()];
        dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        
        for (int i = 0, count = gallery.getAdapter().getCount(); i < count; i++) {
            dotText[i] = new TextView(this);
            dotText[i].setText(".");
            dotText[i].setTextSize(45);
            dotText[i].setTypeface(null, Typeface.BOLD);
            dotText[i].setTextColor(Color.rgb(235,236,238));
            dotLayout.addView(dotText[i]);
        }

         // click listener for Gallery
        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
            	
            	for (int i = 0, count = gallery.getAdapter().getCount(); i < count; i++) {
                    HowItWorksScreen.dotText[i]
                            .setTextColor(Color.rgb(235,236,238));
                }
 
            	HowItWorksScreen.dotText[pos]
                        .setTextColor(Color.rgb(104,110,122));
            	
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        
        
	}

	
	public class GalleryImageAdapter extends BaseAdapter
	{
	    private Context mContext;

	    private Integer[] mImageIds = {
	    		 R.drawable.howitworks1,
	             R.drawable.howitworks1,
	             R.drawable.howitworks1,
	             R.drawable.howitworks1
	    };
	    
	    private String[] texts = { "help 1","help 2","help 3","help 4" };
	    
	    		
	
	    public GalleryImageAdapter(Context context)
	    {
	        mContext = context;
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }


	    // Override this method according to your need
	    public View getView(int index, View view, ViewGroup viewGroup)
	    {
	        // TODO Auto-generated method stub
	    	
	    	View v = view;
			if(v == null)
			{
				LayoutInflater inflater = ((Activity) HowItWorksScreen.this).getLayoutInflater();
				v = inflater.inflate(R.layout.how_it_works_item,viewGroup,false);
			}
	    	
	    	ImageView imageView = (ImageView) v.findViewById(R.id.image);
	    	TextView textView = (TextView) v.findViewById(R.id.text);
			
	    	imageView.setImageResource(mImageIds[index]);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			
			textView.setText(texts[index]);
	        return v;
	    }
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.no_slide,R.anim.existing_down_slide);
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	private void setCustomTitle(String s)
	{
		int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
		actionBarTitleView.setText(s);
		actionBarTitleView.setTextColor(Color.rgb(104,110,122));
		actionBarTitleView.setTypeface(customFont,Typeface.BOLD);
		getActionBar().setTitle(s);
	}
	
}
