package com.elevenestates.shopmates2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreSearchScreen extends Activity {
	 
    AutoCompleteTextView atvPlaces;
    PlacesTask placesTask;
    ParserTask parserTask;
    
    TextView textView;
    ListView listView;
    Button customStore;
    
    ArrayList<String> placeNames;
    ArrayList<PlaceClass> placeClasses;
    
    String sLat;
    String sLng;
    boolean clicked = false;
    
    String selectedStore = "";
    boolean editing;
    ProgressDialog progress;
    Typeface customFont;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setTheme(R.style.Theme_CustomTheme);
		setContentView(R.layout.activity_main);
		getActionBar().setTitle(Html.fromHtml("<font color='#686e7a'><b>From</b></font>"));
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
 
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        
        //setup progress bar
      	progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Fetching location...");
        progress.setIndeterminate(true);
        
      //define custom font
      	customFont = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
      	setCustomTitle("From");
        
        atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
        atvPlaces.setThreshold(1);
        atvPlaces.setTypeface(customFont);
        textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);
        customStore = (Button) findViewById(R.id.customStore);
        
        textView.setTypeface(customFont,Typeface.BOLD);
        customStore.setTypeface(customFont);
        customStore.setOnClickListener(new OnClickListener()
        		{
        			@Override
					public void onClick(View v) {
						Intent i = new Intent("storeScreen");
						i.putStringArrayListExtra("orderList",StoreSearchScreen.this.getIntent().getStringArrayListExtra("orderList"));
						startActivity(i);
						overridePendingTransition(R.anim.up_slide,R.anim.no_slide);
						finish();
						
					}
        		});
        
        listView.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				if(!clicked)
				{
					atvPlaces.clearFocus();
					progress.show();
					selectedStore = placeClasses.get(position).getPlaceName();
					PlacesDetailsTask placesDetailsTask = new PlacesDetailsTask();
					placesDetailsTask.execute(placeClasses.get(position).getPlaceId());
					clicked = true;
				}
				
			}
        	
        });
        
        atvPlaces.clearFocus();
        atvPlaces.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }
 
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }
 
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
 
    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String>{
 
        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";
 
            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyAc9tGoAxxyEhZ3OXqz5Gx0imDKmj0WUpw";
 
            String input="";
 
            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
 
            // place type to be searched
          
            String components = "components=country:in";
            // Building the parameters to the web service
            String parameters = input+"&"+components+"&"+ key;
 
            // Output format
            String output = "json";
 
            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" + parameters;
 
            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
            // Creating ParserTask
            parserTask = new ParserTask();
            //textView.setText(result);
            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }
    
    
    private class PlacesDetailsTask extends AsyncTask<String, Void, String>{
    	 
        @Override
        protected String doInBackground(String... ref) {
            // For storing data from web service
           String data = "";
            // Building the url to the web service
        	// reference of place
            String reference = "placeid="+ref[0];
            String mKey = "key=AIzaSyAc9tGoAxxyEhZ3OXqz5Gx0imDKmj0WUpw";
           
            // Building the parameters to the web service
            String parameters = reference+"&"+mKey;
     
            // Output format
            String output = "json";
     
            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/details/"+output+"?"+parameters;
 
            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
            // Creating ParserTask
            ParserDetailsTask parserDetailsTask = new ParserDetailsTask();
            //textView.setText(result);
            // Starting Parsing the JSON string returned by Web Service
            parserDetailsTask.execute(result);
        }
    }
    
    private class ParserDetailsTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
    	 
        JSONObject jObject;
 
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
 
            List<HashMap<String, String>> placesDetails = null;
 
            PlaceDetailsJSONParser placeDetailsJsonParser = new PlaceDetailsJSONParser();
 
            try{
                jObject = new JSONObject(jsonData[0]);
 
                // Getting the parsed data as a List construct
                placesDetails = placeDetailsJsonParser.parse(jObject);
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return placesDetails;
        }
 
        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
 
            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };
         //   textView.setText(result.size()+"Size");
         //   String s = "";
           
            sLat = result.get(0).get("lat");
            sLng = result.get(0).get("lng");
            progress.dismiss();
            
            if(selectedStore.equals(""))
				Toast.makeText(StoreSearchScreen.this,"You must select a store!",Toast.LENGTH_LONG).show();
			else
			{
				Intent i = new Intent("screen_3");
				i.putExtra("store",selectedStore);
				i.putExtra("lat",sLat);
				i.putExtra("lng",sLng);
				i.putStringArrayListExtra("orderList",StoreSearchScreen.this.getIntent().getStringArrayListExtra("orderList"));
				startActivity(i);
				overridePendingTransition(R.anim.left_slide,R.anim.no_slide);
				finish();
			}
            
            
        }
    }
 
    
    
    
    
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
 
        JSONObject jObject;
 
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
 
            List<HashMap<String, String>> places = null;
 
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
 
            try{
                jObject = new JSONObject(jsonData[0]);
 
                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }
 
        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
 
            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };
         //   textView.setText(result.size()+"Size");
         //   String s = "";
            placeClasses = new ArrayList<PlaceClass>();
            for(int i=0; i<result.size(); i++)
            {
            	   
            	placeClasses.add(new PlaceClass(result.get(i).get("name"),result.get(i).get("reference")));
            }
            
         //   for(int i=0; i<placeNames.size(); i++)
         //   	textView.setText(placeNames.get(i));
            
            CustomPlaceListAdapter adapter = new CustomPlaceListAdapter(StoreSearchScreen.this,placeClasses);
            listView.setAdapter(adapter);
           
            
            
        }
    }
 
    
    private String getPlaceDetailsUrl(String ref){
    	 
        // reference of place
        String reference = "placeid="+ref;
        String mKey = "key=AIzaSyAc9tGoAxxyEhZ3OXqz5Gx0imDKmj0WUpw";
       
        // Building the parameters to the web service
        String parameters = reference+"&"+mKey;
 
        // Output format
        String output = "json";
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/place/details/"+output+"?"+parameters;
 
        return url;
    }
 
   
    
 
    private String getPlaceDetails(String reference){
        String data = "";
        String url = getPlaceDetailsUrl(reference);
        try {
            data = downloadUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		//getMenuInflater().inflate(R.menu.menu_screen2,menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			if(!editing)
			{
			Intent i = new Intent("screen_1");
			i.putExtra("orderList",getIntent().getStringArrayListExtra("orderList"));
			startActivity(i);
			overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
			finish();
			
			}
			else
			{
				Toast.makeText(StoreSearchScreen.this,"You must select a store!",Toast.LENGTH_LONG).show();
			}
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		if(!editing)
		{
		Intent i = new Intent("screen_1");
		i.putExtra("orderList",getIntent().getStringArrayListExtra("orderList"));
		startActivity(i);
		overridePendingTransition(R.anim.no_slide,R.anim.existing_right_slide);
		finish();
		
		}
		else
		{
			Toast.makeText(StoreSearchScreen.this,"You must select a store!",Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		clicked = false;
		editing = getIntent().getBooleanExtra("editing",false);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
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