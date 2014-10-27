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

public class SignUpActivity extends Activity{
	Button b;
	EditText userN, Pw,FN,LN,P,DOB;
	AlertDialog.Builder alt_bld;
	String URL;
	static final String UserName ="userN";
	static final String PassWord="PW";
	static final String Firstname ="FirstName";
	static final String Lastname="Lastname";
	static final String Phone ="PhoneN";
	static final String DofB="DOB";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_main);
		userN = (EditText) findViewById(R.id.editText1);
		Pw = (EditText) findViewById(R.id.editText2);
		FN = (EditText) findViewById(R.id.editText3);
		LN = (EditText) findViewById(R.id.editText4);
		P = (EditText) findViewById(R.id.editText5);
		DOB = (EditText) findViewById(R.id.editText6);
		addListenerOnButton();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
	}
	public void addListenerOnButton() {
		final Context context = this;
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			userN = (EditText) findViewById(R.id.editText1);
			Pw = (EditText) findViewById(R.id.editText2);
			FN = (EditText) findViewById(R.id.editText3);
			LN = (EditText) findViewById(R.id.editText4);
			P = (EditText) findViewById(R.id.editText5);
			DOB = (EditText) findViewById(R.id.editText6);
			CharSequence UNText = userN.getText();
			CharSequence PWText = Pw.getText();
			CharSequence FNText = FN.getText();
			CharSequence LNText = LN.getText();
			CharSequence PText = P.getText();
			CharSequence DOBText = DOB.getText();

	      //call api server and get info from server
	      HttpHandler sh = new HttpHandler();
	      URL = "http://127.0.0.1:52846/Service1.svc/insertInfo/"+UNText+"/"+PWText+"/"+FNText+"/"+LNText+"/"+PText+"/"+DOBText;
  		  System.out.println(URL);
          // Making a request to url and getting response
          String js = sh.makeHttpCall(URL, HttpHandler.POST);

          Log.d("Response: ", "> " + js);

          if (js != null) {
              try {
                  JSONArray jsonObj = new JSONArray(js); 
                  
                  // looping through All Contacts
                  for (int i = 0; i < jsonObj.length(); i++) {
                      JSONObject jo1 = jsonObj.getJSONObject(i);
                  	String uN = jo1.getString(UserName);
                  	String pW = jo1.getString(PassWord);
                  	if (uN.equals(UNText)){
                  		alt_bld = new AlertDialog.Builder(SignUpActivity.this);
            			alt_bld.setMessage("Sign Up Successfully");
            			alt_bld.setCancelable(false);
            			alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    		public void onClick(DialogInterface dialog, int id) {
                    		dialog.cancel();
                    		}
                    		});
                    	AlertDialog alert = alt_bld.create();
                    	
            			// Title for AlertDialog
            			alert.setTitle("Great");
                    	alert.show();
                    	Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                  	} 
                  
                  	}} catch (JSONException e) {
                  e.printStackTrace();
              }
              } }  {
              Log.e("ServiceHandler", "cannot get data from the API");
          }


		}); }};