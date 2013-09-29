package com.example.questionpaperreader;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileChooser extends Activity {
	 ArrayList<String> listItems=new ArrayList<String>();
	 ArrayAdapter<String> adapter;
	 ListView list;
	 Button backButton;
	 String path=Environment.getExternalStorageDirectory().toString();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_chooser);
		
		
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		backButton= (Button) findViewById(R.id.open_calibrator_button);
		list= (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
				
				TextView textView=(TextView) viewClicked;
				String targetPath=path+File.separator+textView.getText().toString();
				//path=
				File f=new File(targetPath);
				if(f.isDirectory()){
					try{
						addItemsToList(f);
						path=targetPath;
					} catch(Exception e){
						Toast.makeText(FileChooser.this, "Cannot open directory!", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					//Toast.makeText(FileChooser.this, "choose "+f.getName(), Toast.LENGTH_SHORT).show();
					Intent resultIntent = new Intent();
					resultIntent.putExtra("path", path+File.separator+f.getName());
					setResult(Activity.RESULT_OK, resultIntent);
					finish();
				}
			}		
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addItemsToList(new File(getPreviousPath()));
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
           if(list[i].isFile()){
               listItems.add(list[i].getName());
           }
       }
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