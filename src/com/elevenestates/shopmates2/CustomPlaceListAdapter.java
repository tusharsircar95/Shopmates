package com.elevenestates.shopmates2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomPlaceListAdapter extends BaseAdapter{

	Context context;
	ArrayList<PlaceClass> placeClasses;
	Typeface customFont;
	
	public CustomPlaceListAdapter(Context context,ArrayList<PlaceClass> placeClasses)
	{
		this.context = context;
		this.placeClasses = placeClasses;
		//define custom font
      	customFont = Typeface.createFromAsset(this.context.getAssets(), "fonts/customFont.ttf");
      	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return placeClasses.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return placeClasses.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if(v == null)
		{
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			v = inflater.inflate(R.layout.custom_list_item,parent,false);
		}
		TextView tv = (TextView) v.findViewById(R.id.tv);
		tv.setText(placeClasses.get(position).getPlaceName());
		tv.setTypeface(customFont);
		return v;
	}

}
