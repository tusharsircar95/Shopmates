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

public class DrawerListAdapter extends BaseAdapter{

	ArrayList<String> listValues;
	Context context;
	Typeface customFont;
	
	public DrawerListAdapter(Context context,ArrayList<String> listValues)
	{
		this.context = context;
		this.listValues = listValues;
		//define custom font
      	customFont = Typeface.createFromAsset(this.context.getAssets(), "fonts/customFont.ttf");
      	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listValues.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		if(v == null)
		{
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			v = inflater.inflate(R.layout.drawer_list_item,parent,false);
		}
		
		TextView option = (TextView) v.findViewById(R.id.option);
		option.setTypeface(customFont);
		option.setText(listValues.get(position));
		
		return v;
	}

}
