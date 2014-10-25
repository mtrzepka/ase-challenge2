package com.example.challenge2app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReadQRCodeActivity extends Activity{
	static final String QRType ="type";
	static final String info ="data";
	String URL = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readqrcode_main);
    // Creating service handler class instance
            HttpHandler sh = new HttpHandler();
            Intent in = getIntent();
            String link = in.getStringExtra("URL");
    		System.out.println("1. "+link);
    		URL = "http://api.qrserver.com/v1/read-qr-code/?fileurl="+link;

    		System.out.println(URL);
            // Making a request to url and getting response
            String js = sh.makeHttpCall(URL, HttpHandler.GET);
 
            Log.d("Response: ", "> " + js);
 
            if (js != null) {
                try {
                    JSONArray jsonObj = new JSONArray(js); 
                    
                    // looping through All Contacts
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject jo1 = jsonObj.getJSONObject(i);
                    	String tp = jo1.getString(QRType);
                    	String sym=jo1.getString("symbol");
                        TextView tTitle = (TextView) findViewById(R.id.type_Label);
                        TextView dTitle = (TextView) findViewById(R.id.data_label);   
                        tTitle.setText(tp);
                        dTitle.setText(sym);
                    
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "cannot get data from the API");
            }
 

        }}
 
        

