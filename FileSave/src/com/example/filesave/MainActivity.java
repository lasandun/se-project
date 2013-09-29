package com.example.filesave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	 ArrayList<String> listItems=new ArrayList<String>();
	 ArrayAdapter<String> adapter;
	 ListView list;
	 Button backButton;
	 Button saveButton;
	 EditText fileName;
	 String path=Environment.getExternalStorageDirectory().toString();
	 String contentOfFile="not implemented yet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Intent i=getIntent();
		Bundle b=i.getExtras();
		contentOfFile=b.getString("content");*/
		
		
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		backButton= (Button) findViewById(R.id.back_button);
		saveButton= (Button) findViewById(R.id.save_button);
		fileName= (EditText) findViewById(R.id.file_name);
		list= (ListView) findViewById(R.id.list_save);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
				
				TextView textView=(TextView) viewClicked;
				String targetPath=path+File.separator+textView.getText().toString();
				
				File f=new File(targetPath);
				if(f.isDirectory()){
					try{
						addItemsToList(f);
						path=targetPath;
					} catch(Exception e){
						Toast.makeText(MainActivity.this, "Cannot open directory!", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					Toast.makeText(MainActivity.this, "Exception....Opened a file!", Toast.LENGTH_SHORT).show();
				}
			}		
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addItemsToList(new File(getPreviousPath()));
			}
		});
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					Toast tt = Toast.makeText(getApplicationContext(),"sd card cannot found",Toast.LENGTH_SHORT);
			        tt.show();
				} 
				else if(fileName.getText()==null || fileName.getText().equals("")){
					Toast tt = Toast.makeText(getApplicationContext(),"Enter name for file.",Toast.LENGTH_SHORT);
			        tt.show();
				} else {
				    File file = new File(path+File.separator+fileName.getText()+".txt");
				    if(!file.exists()) {
				    	try {
							file.createNewFile();
							FileOutputStream os=new FileOutputStream(file);
							os.write(contentOfFile.getBytes());
							
							Intent resultIntent = new Intent();
							resultIntent.putExtra("status", "file saved");
							os.close();
							setResult(Activity.RESULT_OK, resultIntent);
							finish();
						} catch (IOException e) {
							Toast tt = Toast.makeText(getApplicationContext(),"Error while writing to file",Toast.LENGTH_SHORT);
					        tt.show();
							e.printStackTrace();
							
						}
				    }
				    else{
				    	Toast tt = Toast.makeText(getApplicationContext(),"File already exists",Toast.LENGTH_SHORT);
				        tt.show();
				    }
				    
				}
			}
		});
		
		addItemsToList(new File((Environment.getExternalStorageDirectory()).toString())); // File.separator
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void addItemsToList(File file) {
		
		File[] list=file.listFiles();
		listItems.removeAll(listItems);
       for (int i = 0; i < list.length; i++) {
           if(list[i].isDirectory()){
               listItems.add(list[i].getName());
           }
           
       }
		adapter.notifyDataSetChanged();
	}
	
	String getPreviousPath(){
		if(path.equals(Environment.getExternalStorageDirectory().toString())){
			return Environment.getExternalStorageDirectory().toString();
		}
		else{
			String s=path;
			String parts[]=s.split(File.separator);
			s="";
			for(int i=0;i<parts.length-1;i++){
				s+=parts[i];
				if(i!=parts.length-2){
					s+=File.separator;
				}
			}
			path=s;
			return s;
		}		
	}
}