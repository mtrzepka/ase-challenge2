package com.example.challenge2app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity{
	Button b1,b2;
	EditText userN, Pw;
	String URL;
	static final String UserName ="userN";
	static final String PassWord="PW";
	AlertDialog.Builder alt_bld;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		userN = (EditText) findViewById(R.id.editText1);
		Pw = (EditText) findViewById(R.id.editText2);
		addListenerOnButton();
		//find_and_modify_text_view();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
	}
//	private void find_and_modify_text_view() {
//	    b1 = (Button) findViewById(R.id.button1);
//	    b1.setOnClickListener(get_edit_view_button_listener);
//	  }
	public void addListenerOnButton() {
		final Context context = this;
		b2 =(Button) findViewById(R.id.button2);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
	      EditText UN = (EditText) findViewById(R.id.editText1);
	      EditText PW = (EditText) findViewById(R.id.editText2);
	      CharSequence UNText = UN.getText();
	      CharSequence PWText = PW.getText();

	      //call api server and get info from server
	      HttpHandler sh = new HttpHandler();
	      URL = "http://localhost:52846/Service1.svc/queryInfo/"+UNText;
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
                  	String uN = jo1.getString(UserName);
                  	String pW = jo1.getString(PassWord);
                  	if (uN.equals("")){
                  		alt_bld = new AlertDialog.Builder(LoginActivity.this);
            			alt_bld.setMessage("Username does not exits");
            			alt_bld.setCancelable(false);
            			alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    		public void onClick(DialogInterface dialog, int id) {
                    		dialog.cancel();
                    		}
                    		});
                    	AlertDialog alert = alt_bld.create();
                    	
            			// Title for AlertDialog
            			alert.setTitle("Sorry");
                    	alert.show();
                  	}else if(uN.equals(UNText)&& pW.equals(PWText)){
                  		Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);         
                  	}else{
                	  alt_bld = new AlertDialog.Builder(LoginActivity.this);
          			alt_bld.setMessage("Password doesn't match");
          			alt_bld.setCancelable(false);
          			alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  		public void onClick(DialogInterface dialog, int id) {
                  		dialog.cancel();
                  		}
                  		});
                  	AlertDialog alert = alt_bld.create();
                  	
          			// Title for AlertDialog
          			alert.setTitle("Sorry");
                  	alert.show(); 
                  
                  }}} catch (JSONException e) {
                  e.printStackTrace();
              }
              } }  {
              Log.e("ServiceHandler", "cannot get data from the API");
          }


	      });
	   

		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, SignUpActivity.class);
                            startActivity(intent);   
			}
		});
	}
}
