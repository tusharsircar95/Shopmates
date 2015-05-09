package com.elevenestates.shopmates2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Screen_2 extends Activity implements OnClickListener{

	int TOTAL_STORES = 3;
	Button[] stores;
	Button customStore;
	
	String selectedStore = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.screen_2);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>From</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
            
        customStore = (Button) findViewById(R.id.customStore);
        customStore.setOnClickListener(this);
        stores = new Button[TOTAL_STORES];
        stores[0] = (Button) findViewById(R.id.store1);
        stores[1] = (Button) findViewById(R.id.store2);
        stores[2] = (Button) findViewById(R.id.store3);
        for(Button b: stores)
        	b.setOnClickListener(this);
        
        
        
	}

	public void onClick(View v) {
		if(v.getId() == R.id.customStore)
		{
			
		}
		else
		{
			for(Button b: stores)
				b.setTextColor(Color.rgb(104,110,122));
			((Button) v).setTextColor(Color.rgb(115,211,147));
			selectedStore = ((Button) v).getText().toString();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu_screen2,menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
		case R.id.proceed:
			if(selectedStore.equals(""))
				Toast.makeText(Screen_2.this,"You must select a store!",Toast.LENGTH_LONG).show();
			else
			{
				Intent i = new Intent("screen_3");
				i.putExtra("store",selectedStore);
				i.putStringArrayListExtra("orderList",this.getIntent().getStringArrayListExtra("orderList"));
				startActivity(i);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	

	
}
