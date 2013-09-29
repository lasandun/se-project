package com.example.swapinterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class activitySecond extends Activity{
	
	EditText text;
	String message;
	Button button;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		
		text=(EditText) findViewById(R.id.text);
		Intent i=getIntent();
		Bundle b=i.getExtras();
		Toast t=Toast.makeText(getApplicationContext(), b.getString("name"), Toast.LENGTH_LONG);
		t.show();
		button= (Button) findViewById(R.id.button2);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("id", "successful");
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
	}	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1 && resultCode == RESULT_OK)
		{
			//ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			//resultList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,matches));
			Bundle b=data.getExtras();
			
			Toast tt = Toast.makeText(getApplicationContext(), b.getString("id") ,Toast.LENGTH_SHORT);
	        tt.show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
