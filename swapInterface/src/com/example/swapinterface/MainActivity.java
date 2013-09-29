package com.example.swapinterface;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button button;
	EditText text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button=  (Button) findViewById(R.id.button1);
		
		text= (EditText) findViewById(R.id.editText1);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s=text.getText().toString();
				Intent i = new Intent(getApplicationContext(), com.example.swapinterface.activitySecond.class);
				i.putExtra("name", s);
				//startActivity(i);
				startActivityForResult(i, 1234);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;			
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1234 && resultCode == RESULT_OK)
		{
			Bundle b=data.getExtras();
			Toast tt = Toast.makeText(getApplicationContext(), b.getString("id") ,Toast.LENGTH_SHORT);
	        tt.show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	

}
