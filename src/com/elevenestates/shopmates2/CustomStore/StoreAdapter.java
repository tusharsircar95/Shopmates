package com.elevenestates.shopmates2.CustomStore;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elevenestates.shopmates2.R;


public class StoreAdapter extends BaseAdapter{

	List<StoreDetails> storeList;
	Context context;
	Typeface customFont;
	
	public StoreAdapter(Context context,List<StoreDetails> storeList)
	{
		this.context = context;
		this.storeList = storeList;
		//define custom font
      	customFont = Typeface.createFromAsset(context.getAssets(), "fonts/customFont.ttf");
      	
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return storeList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return storeList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		if(v == null)
		{
			LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
			v = inflater.inflate(R.layout.address_list_item,parent,false);
		}
		
		TextView tag,details,pincode;
		tag = (TextView) v.findViewById(R.id.addressTag);
		details = (TextView) v.findViewById(R.id.addressDetails);
		pincode = (TextView) v.findViewById(R.id.addressPincode);
		
		tag.setTypeface(customFont);
		details.setTypeface(customFont);
		pincode.setTypeface(customFont);
		
		StoreDetails ad = storeList.get(position);
		tag.setText(ad.getTag());
		details.setText(ad.getAddress());
		pincode.setText(ad.getPincode());
		
		return v;
	}

	
}
