package com.elevenestates.shopmates2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class OrderListAdapter extends BaseAdapter{
	
	Context context;
	Typeface customFont;
	ArrayList<String> orderIds;
	ArrayList<String> shopperIds;
	
	ArrayList<OrderDetails> orders;
	
	public OrderListAdapter(Context context, ArrayList<OrderDetails> orders)
	{
		this.context =context;
		this.orders = orders;
		customFont = Typeface.createFromAsset(context.getAssets(), "fonts/customFont.ttf");
		Parse.initialize(this.context, "t7mDyCOioYnWebf1yOC9cCTP5IDO1zmp1nMNY99i", "DVzr680uGKeRs8UQ1kaFdMKKToyXhPy42NQ148Ay");
		
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orders.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return orders.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		final int position = pos;
		View v = convertView;
		if(v == null)
		{
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			v = inflater.inflate(R.layout.order_list_item,parent,false);
		}
		
		TextView order = (TextView) v.findViewById(R.id.order);
		order.setTypeface(customFont);
		order.setText(orders.get(pos).orderList);
		
		Button viewInvoice = (Button) v.findViewById(R.id.viewInvoice);
		viewInvoice.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				
				String orderId = orders.get(position).orderId;
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
				query.getInBackground(orderId,new GetCallback<ParseObject>()
						{

							@Override
							public void done(ParseObject object,
									ParseException e) {
								if(e==null)
								{
									if(object.getString("invoice_ready").equals("yes"))
									{
										Intent i = new Intent("invoiceScreen");
										i.putExtra("orderId",orders.get(position).orderId);
										OrderListAdapter.this.context.startActivity(i);
									}
									else
										Toast.makeText(OrderListAdapter.this.context,"Invoice not ready!",Toast.LENGTH_LONG).show();
								}
								
							}
					
						});
				
			}
			
		});
		
		
		Button trackShopper = (Button) v.findViewById(R.id.trackShopper);
		trackShopper.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				
				
				String orderId = orders.get(position).orderId;
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
				query.getInBackground(orderId,new GetCallback<ParseObject>()
						{

							@Override
							public void done(ParseObject object,
									ParseException e) {
								if(e==null)
								{
									if(object.getString("status").equals("assigned"))
									{
										Intent i = new Intent("trackShopperScreen");
										i.putExtra("shopperId",orders.get(position).shopperId);
										i.putExtra("orderX",orders.get(position).orderX);
										i.putExtra("orderY",orders.get(position).orderY);
										OrderListAdapter.this.context.startActivity(i);
									}
									else
										Toast.makeText(OrderListAdapter.this.context,"Order not assigned!",Toast.LENGTH_LONG).show();
								}
								
							}
					
						});
				
				
				
			}
			
		});
		return v;

	}

}
