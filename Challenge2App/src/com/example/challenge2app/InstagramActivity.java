package com.example.challenge2app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class InstagramActivity extends Activity {
	Button b1;
	EditText et1;
	JSONParser jParser = new JSONParser();
	HttpHandler httpHandler = new HttpHandler();
	String tag;
	ArrayList<String> imageUrls = new ArrayList<String>();
	int currentImageIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instagram);
		
		et1 = (EditText)findViewById(R.id.editText1);

		et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		      @Override
		      public void onFocusChange(View v, boolean hasFocus) {
		    	if (hasFocus == false)
		    	{
		        	currentImageIndex = 0;
		        	imageUrls.clear();
		        	tag = et1.getText().toString();
		    		if (!tag.isEmpty()) {
		    			//String jsonString = httpHandler.makeHttpCall("https://api.instagram.com/v1/tags/" + tag + "/media/recent?client_id=a2f1dc6fd0c6418997800c83dfd43d84&count=10", HttpHandler.GET);
		    			JSONObject json = jParser.getJSONFromUrl("https://api.instagram.com/v1/tags/" + tag + "/media/recent?client_id=a2f1dc6fd0c6418997800c83dfd43d84&count=10");
		    			System.out.println(json);
		    			try {
							//JSONObject json = new JSONObject(jsonString);
		    				JSONArray jsonDatas = json.getJSONArray("data");
		    				for (int i = 0; i < jsonDatas.length(); i++) {
		    					JSONObject jsonDataModel = jsonDatas.getJSONObject(i);
		    					JSONObject jsonImageModel = jsonDataModel.getJSONObject("images");
		    					JSONObject jsonImage = jsonImageModel.getJSONObject("standard_resolution");
		    					imageUrls.add(jsonImage.getString("url"));
		    					setImage(imageUrls.get(currentImageIndex));
		    				}
		    			} catch (JSONException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    		}
		    	}
	        }
		});
		
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				currentImageIndex++;
				setImage(imageUrls.get(currentImageIndex));
			}
		});
	}
	
	void setImage(String url) {
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute(url);
	}
}
