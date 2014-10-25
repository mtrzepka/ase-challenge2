package com.example.challenge2app;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
Button b1,b2,b3,b4,b5,b6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	public void addListenerOnButton() {
		 
		final Context context = this;
 
		b6 = (Button) findViewById(R.id.button6);

		
		
		b6.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, QRCodeActivity.class);
                            startActivity(intent);   
 
			}
 
		});
	}
 
}
