package com.example.questionpaperreader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (getIntent().getBooleanExtra("EXIT", false)) {
	         finish();
	    }
	}

	Button readFileButton;
	Button openCalibratorButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		readFileButton= (Button) findViewById(R.id.read_file);
		openCalibratorButton= (Button) findViewById(R.id.open_calibrator_button);
		
		readFileButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), com.example.questionpaperreader.FileChooser.class);
				startActivityForResult(i, 5555);
			}
		});
		
		openCalibratorButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), com.example.questionpaperreader.RecognizerCalibrator.class);
				startActivityForResult(i, 4444);
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
		if (requestCode == 5555 && resultCode == RESULT_OK)
		{
			
			Bundle b=data.getExtras();
			//Toast tt = Toast.makeText(getApplicationContext(), b.getString("path") ,Toast.LENGTH_SHORT);
	        //tt.show();
	        Intent i=new Intent(getApplicationContext(), com.example.questionpaperreader.questionViewer.class);
	        i.putExtra("path", b.getString("path"));
	        startActivity(i);
		}
		else if (requestCode == 4444 && resultCode == RESULT_OK)
		{
			Bundle b=data.getExtras();
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
