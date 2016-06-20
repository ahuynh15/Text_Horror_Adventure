package com.example.texthorroradventure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class mainmenu extends Activity {
	
	Context context;
	Button start, exit;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		context = this;
		
		start = (Button) findViewById(R.id.start);
		exit = (Button) findViewById(R.id.exit);
		
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i1 = new Intent(context, MainActivity.class );
				startActivity(i1);
			}
		});
		exit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);
			}
		});
	}
}
