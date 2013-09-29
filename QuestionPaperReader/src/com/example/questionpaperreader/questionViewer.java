package com.example.questionpaperreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class questionViewer extends Activity{
	String paper="";
	char qDelim=')', aDelim=')';
	int noOfQuestions=1;
	String ques[];
	String header;
	String lineSeperator=System.getProperty("line.separator");
	char answerChar[]={'A','B','C','D','E','F','G'};///only capital letters are identified.

	Question questions[];
	
	Button nextButton;
	Button previousButton;
	Button startProcess;
	EditText questionText;
	EditText answerText;
	
	int currentQuestionIndex;
	String path;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_questions);
		
		Intent i=getIntent();
		Bundle b=i.getExtras();
		path=b.getString("path");
		
		nextButton = (Button) findViewById(R.id.next_button);
		previousButton = (Button) findViewById(R.id.previous_button);
		questionText = (EditText) findViewById(R.id.question_text);
		answerText = (EditText) findViewById(R.id.answer_text);
		startProcess = (Button) findViewById(R.id.start_process);
		
		
		questionText.setFocusable(false);
		questionText.setFocusableInTouchMode(false);
		questionText.setClickable(false);
		
		answerText.setFocusable(false);
		answerText.setFocusableInTouchMode(false);
		answerText.setClickable(false);
		
		nextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentQuestionIndex<noOfQuestions-1 && questions[currentQuestionIndex+1]!=null){
					currentQuestionIndex++;
					questionText.setText(questions[currentQuestionIndex].question);
					String s="";
					for(String x:questions[currentQuestionIndex].answers){
						s+=x;
					}
					answerText.setText(s);					
				}
			}
		});
		
		previousButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentQuestionIndex>0 && questions[currentQuestionIndex-1]!=null){
					currentQuestionIndex--;
					questionText.setText(questions[currentQuestionIndex].question);
					String s="";
					for(String x:questions[currentQuestionIndex].answers){
						s+=x;
					}
					answerText.setText(s);					
				}
			}
		});
		
		startProcess.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), com.example.questionpaperreader.AnsweringProcess.class);
				i.putExtra("#questions", noOfQuestions);
				for(int j=0;j<noOfQuestions;j++){
					String data[]=new String[ questions[j].answers.size()+1 ];
					data[0]=questions[j].question;
					for(int k=1;k<= questions[j].answers.size() ; k++){
						data[k]=questions[j].answers.get(k-1);
					}
					i.putExtra("question"+j, data);
				}
				
				startActivity(i);
			}
		});
		
		currentQuestionIndex=0;
		
		readFile();
		readQuestionsAndHeader();
		identifyAnswers();
		if(questions[0]!=null){
			questionText.setText(questions[0].question);
			String s="";
			for(String x:questions[0].answers){
				s+=x;
			}
			answerText.setText(s);
		}
		
	}


	public void readFile(){
		BufferedReader br = null;        
		try {
			String s = null;
			br = new BufferedReader(new FileReader(path));//Environment.getExternalStorageDirectory().toString()+File.separator+"lahiru"+ile.separator+"paper.txt"));
			while ( (s = br.readLine()) != null) {
				if(s!=null){
					paper+=s+lineSeperator;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void readQuestionsAndHeader() {

		if(paper.contains("1)")){
			qDelim=')';
		}
		else if(paper.contains("1.")){
			qDelim='.';
		}

		if(paper.contains("A)")){
			aDelim=')';
		}
		else if(paper.contains("A.")){
			aDelim='.';
		}

		while(paper.contains(""+noOfQuestions+qDelim)){
			noOfQuestions++;
		}
		noOfQuestions--;

		ques=new String[noOfQuestions];
		questions=new Question[noOfQuestions];
		for (int i = 0; i < questions.length; i++) {
			questions[i]=new Question();
		} 
		int start=paper.indexOf("1"+qDelim);
		int end=paper.length();

		header=paper.substring(0, start);

		for(int i=2;i<=noOfQuestions;i++){
			end=paper.indexOf(""+i+qDelim);
			ques[i-2]=paper.substring(start,end);
			start=end;
		}
		ques[noOfQuestions-1]=paper.substring(end);
	}


	public void identifyAnswers(){
		int questionNo=0;
		for(String question:ques){

			LinkedList<String> list=new LinkedList<String>();
			int i=0;
			int start=0;
			int end=question.length();
			while(question.contains(lineSeperator+answerChar[i]+aDelim)){
				end=question.indexOf(lineSeperator+answerChar[i]+aDelim);
				if(i==0){
					questions[questionNo].question=question.substring(start, end);
				}
				else{
					list.addLast(question.substring(start, end));
				}
				start=end;
				i++;

			}
			list.addLast(question.substring(end));
			questions[questionNo].answers=list;
			questionNo++;
		}
	}

	public void show(){
		for(int i=0;i<questions.length;i++){
			System.out.println("question:"+questions[i].question);
			for(String s:questions[i].answers){
				System.out.println("----------");
				System.out.println(s);                
			}
		}
	}


}

class Question{
	String question;
	LinkedList<String> answers;
}
