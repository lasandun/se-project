package com.example.check;

import java.io.FileInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText myText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myText =(EditText)findViewById(R.id.myText);
        Button  createButton=(Button)findViewById(R.id.btnCreate);
        Button readButton=(Button)findViewById(R.id.btnRead);
        createButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				createFile(myText.getText().toString());
				myText.setText("");
			}
		});

        readButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				readFile();

			}
		});
	}
	private void createFile(String Text){
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast tt = Toast.makeText(getApplicationContext(),"sd card present....",Toast.LENGTH_SHORT);
	        tt.show();
		} else {
		    File file = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"lahiru"+
		    		File.separator+"input.txt");
		    //file.mkdirs();
		    if(!file.exists()) {
		    	try {
					file.createNewFile();
					FileOutputStream os=new FileOutputStream(file);
					os.write(Text.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		    
		}
    }

    private void readFile(){

    	FileInputStream fis;

    	try {
			fis=new FileInputStream(new File(Environment.getExternalStorageDirectory().toString()+File.separator+"lahiru"+
		    		File.separator+"input.txt"));
			byte[] reader=new byte[fis.available()];
			while (fis.read(reader)!=-1) {
				
			}
		    myText.setText(new String(reader));
		    Toast.makeText(getApplicationContext(), "File read succesfully", Toast.LENGTH_SHORT).show();
		    if(fis!=null){
		    	fis.close();
		    }
		}
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			Log.e("Read File", e.getLocalizedMessage());
		}

    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
