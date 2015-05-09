package com.elevenestates.shopmates2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class OrderHistoryScreen extends Activity{

	String username;
	ListView ongoingList,pastList;
	ArrayList<OrderDetails> ongoingOrders;
	ArrayList<OrderDetails> pastOrders;
	ArrayList<String> ongoingListValues,pastListValues;
	ArrayList<String> ongoingIds,pastIds;
	OrderListAdapter adapter;
	ProgressDialog progress;
	Button ongoing;
	TextView info;
	Typeface customFont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_CustomTheme);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.order_history_screen);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>Order History</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("Order History");
      	
        Parse.initialize(this, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
        //set progress bar
  		progress = new ProgressDialog(this);
  		progress.setCancelable(false);
  		progress.setMessage("Retrieving orders...");
  		progress.setIndeterminate(true);
  		progress.show();
  
        ParseUser user = ParseUser.getCurrentUser();
        if(user!=null)
        	username = user.getUsername();
        ongoingList = (ListView) findViewById(R.id.ongoingList);
        pastList = (ListView) findViewById(R.id.pastList);
        ongoing = (Button) findViewById(R.id.ongoing);
        info = (TextView) findViewById(R.id.info);
        ongoing.setTypeface(customFont,Typeface.BOLD);
        info.setTypeface(customFont,Typeface.BOLD);
        
        setupOnGoingList();
       // setupPastList();
        
      /*  ongoingList.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent("trackShopperScreen");
				i.putExtra("shopperId",ongoingIds.get(position));
				i.putExtra("orderX",ongoingOrders.get(position).orderX);
				i.putExtra("orderY",ongoingOrders.get(position).orderY);
				startActivity(i);
				
			}
        	
        });  */
       
	}

	/*private void setupPastList() {
		pastListValues = new ArrayList<String>();
		pastIds = new ArrayList<String>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
		query.whereEqualTo("username",username);
		query.whereEqualTo("status","Completed");
		query.findInBackground(new FindCallback<ParseObject>()
			{

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e != null)
						Toast.makeText(OrderHistoryScreen.this,e.getMessage(),Toast.LENGTH_LONG).show();
					else
					{
						for(int i=0; i<objects.size(); i++)
						{
							JSONArray orderList = objects.get(i).getJSONArray("order");
							pastIds.add(objects.get(i).getString("shopperId"));
							String s = "";
							for(int j=0; j<orderList.length(); j++)
							{
								try {
									s = s + orderList.get(j) + "\n";
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							pastListValues.add(s);
						}
						
						adapter = new OrderListAdapter(OrderHistoryScreen.this,pastListValues);
						pastList.setAdapter(adapter);
					
					}
				}
				
			});
    	
		
	} */

	private void setupOnGoingList() {
		ongoingOrders = new ArrayList<OrderDetails>();
		ongoingListValues = new ArrayList<String>();
		ongoingIds = new ArrayList<String>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
		query.whereEqualTo("username",username);
		query.whereEqualTo("status","Delivered");
		query.findInBackground(new FindCallback<ParseObject>()
			{

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if(e != null)
						Toast.makeText(OrderHistoryScreen.this,e.getMessage(),Toast.LENGTH_LONG).show();
					else
					{
						
						if(objects.size()==0)
						{
							info.setVisibility(View.VISIBLE);
							ongoing.setText("");
						}
						
						for(int i=0; i<objects.size(); i++)
						{
							JSONArray orderList = objects.get(i).getJSONArray("order");
							ongoingIds.add(objects.get(i).getString("shopperId"));
							if(orderList!=null)
							{
							String s = "";
							for(int j=0; j<orderList.length(); j++)
							{
								try {
									s = s + orderList.get(j) + ", ";
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							ongoingListValues.add(s);
							ongoingOrders.add(new OrderDetails(objects.get(i).getObjectId(),objects.get(i).getString("shopperId"),objects.get(i).getString("lat"),objects.get(i).getString("lng"),s));
							}
							
						}
						
						adapter = new OrderListAdapter(OrderHistoryScreen.this,ongoingOrders);
						ongoingList.setAdapter(adapter);
					
						
					}
					progress.dismiss();
				}
				
			});
    	
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
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
