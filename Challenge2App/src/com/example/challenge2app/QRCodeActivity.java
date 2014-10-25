package com.example.challenge2app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class QRCodeActivity extends Activity{
	String url ="";
	Spinner sp1,sp2;
	ArrayAdapter<String> adp1,adp2;
	List<String> color,size;
	Button getQRCode,readQRC;
	EditText info;
	String ct="";
	WebView QBCode;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.qrcodemain);
        QBCode =(WebView) findViewById(R.id.webView1);
        info = (EditText) findViewById(R.id.editText1);
        // set sp1 for color
        sp1 = (Spinner) findViewById(R.id.spinner2);
        color = new ArrayList<String>();
        color.add("");
        color.add("Red");
        color.add("Green");
        color.add("Blue");
        
        adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, color);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        
        
        // set sp2 is size for QR Code
        sp2= (Spinner) findViewById(R.id.spinner1);
        size = new ArrayList<String>();
        size.add("50x50");
        size.add("75x75");
        size.add("100x100");
        size.add("150x150");
        size.add("200x200");
        adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, size);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adp2);
        find_and_modify_text_view();
        addListenerOnButton();
    } 
	private void find_and_modify_text_view() {
	    getQRCode = (Button) findViewById(R.id.button1);
	    getQRCode.setOnClickListener(get_edit_view_button_listener);
	  }
	private Button.OnClickListener get_edit_view_button_listener = new Button.OnClickListener() {
	    @Override
		public void onClick(View v) {
	      EditText info = (EditText) findViewById(R.id.editText1);
	      Spinner size = (Spinner) findViewById(R.id.spinner1);
	      Spinner color = (Spinner) findViewById(R.id.spinner2);
	      CharSequence infoText = info.getText();
	      CharSequence sizeText = (CharSequence)size.getSelectedItem();
	      CharSequence colorText = (CharSequence)color.getSelectedItem();
	      //call api server and get info from server
	      HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;;
				try {
					if (colorText.equals("Red"))
						ct = "f00";
					else if(colorText.equals("Green"))
						ct ="0f0";
					else if(colorText.equals("Blue"))
						ct="00f";
					else 
						ct="";	
				 HttpPost HttpPost = new HttpPost("http://api.qrserver.com/v1/create-qr-code/?data="+infoText+"&size="+sizeText+"&color="+ct+"");
	             response = httpclient.execute(HttpPost);
				    StatusLine statusLine = response.getStatusLine();
				    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				        ByteArrayOutputStream out = new ByteArrayOutputStream();
				        response.getEntity().writeTo(out);
				        out.close();				        
//				     String code = JSONAnalysis(responseString);
				     url = HttpPost.getURI().toString();
				     QBCode.loadUrl(url);			     
				     System.out.println(url);

				    } else{
				        //Closes the connection.
				        response.getEntity().getContent().close();
				        throw new IOException(statusLine.getReasonPhrase());
				    }	
				} catch (ClientProtocolException e) {
				    e.printStackTrace();
				} catch (IOException e) {
				    e.printStackTrace();
				}
			 	

			}
	    };
	    public void addListenerOnButton() {
		    readQRC = (Button) findViewById(R.id.button2);
		    final Context context = this;
		    readQRC.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {

				EditText info = (EditText) findViewById(R.id.editText1);
			      Spinner size = (Spinner) findViewById(R.id.spinner1);
			      Spinner color = (Spinner) findViewById(R.id.spinner2);
			      CharSequence infoText = info.getText();
			      CharSequence sizeText = (CharSequence)size.getSelectedItem();
			      CharSequence colorText = (CharSequence)color.getSelectedItem();
			      //call api server and get info from server
			      HttpClient httpclient = new DefaultHttpClient();
						HttpResponse response;
						try {
							if (colorText.equals("Red"))
								ct = "f00";
							else if(colorText.equals("Green"))
								ct ="0f0";
							else if(colorText.equals("Blue"))
								ct="00f";
							else 
								ct="";	
						 HttpPost HttpPost = new HttpPost("http://api.qrserver.com/v1/create-qr-code/?data="+infoText+"&size="+sizeText+"&color="+ct+"");
			             response = httpclient.execute(HttpPost);
						    StatusLine statusLine = response.getStatusLine();
						    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
						        ByteArrayOutputStream out = new ByteArrayOutputStream();
						        response.getEntity().writeTo(out);
						        out.close();			        
//						     String code = JSONAnalysis(responseString);
						     url = HttpPost.getURI().toString();
						     QBCode.loadUrl(url);			     
						     System.out.println(url);

							    Intent intent = new Intent(context, ReadQRCodeActivity.class);
							    Bundle b= new Bundle();
							    b.putString("URL", url);
							    intent.putExtras(b);
							    System.out.println(url+"-------"+b);
				                startActivity(intent);
				                
						    } else{
						        //Closes the connection.
						        response.getEntity().getContent().close();
						        throw new IOException(statusLine.getReasonPhrase());
						    }	
						} catch (ClientProtocolException e) {
						    e.printStackTrace();
						} catch (IOException e) {
						    e.printStackTrace();
						}
 
			}
		});
	    }
	    }
	  


