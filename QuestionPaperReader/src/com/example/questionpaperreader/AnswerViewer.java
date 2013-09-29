package com.example.questionpaperreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AnswerViewer extends Activity{
	
	TextView answersText;
	Button exitButton;
	Button saveFileButton;
	String s="";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer_viewer);
		
		answersText= (TextView) findViewById(R.id.TextView_answers);
		exitButton= (Button) findViewById(R.id.exit);
		saveFileButton= (Button) findViewById(R.id.save_as_file);
		
		
		Intent i=getIntent();
		Bundle b=i.getExtras();
		
		String answers[]=b.getStringArray("answers");
		
		for(String ss:answers){
			s+=ss+"\n";
		}
		answersText.setText(s);
		
		exitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), com.example.questionpaperreader.MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("EXIT", true);
				startActivity(intent);
			}
		});
		
		saveFileButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), com.example.questionpaperreader.SaveFile.class);
				i.putExtra("content", s);
				startActivity(i);
			}
		});
	}
}
