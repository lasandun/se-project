package com.example.questionpaperreader;

import java.util.LinkedList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AnsweringProcess extends Activity implements TextToSpeech.OnInitListener{
	
	private TextToSpeech tts;
	Button answerButton;
	Question questions[];
	int noOfQuestions;
	boolean ttsReady=false;
	int currentQuestion;
	String status="q";
	String answers[];
	
	String answerCharacters[]= {"a", "b","c","d","e","f"};
	String answerNumbers[]={"1","2","3","4","5","6"};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answering_process);
		
		currentQuestion=0;
		tts = new TextToSpeech(this, this);
		answerButton= (Button) findViewById(R.id.answer_button);
		

		answerButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ttsReady && !tts.isSpeaking()){
					if(currentQuestion<noOfQuestions){
						process();	
					}
					else{
						Intent i = new Intent(getApplicationContext(), com.example.questionpaperreader.AnswerViewer.class);
						i.putExtra("answers", answers);
						startActivity(i);
					}
				}
				
			}
		});
		
		Intent i=getIntent();
		Bundle b=i.getExtras();
		noOfQuestions=b.getInt("#questions");
		questions=new Question[noOfQuestions];		
		for(int j=0;j<noOfQuestions;j++){
			questions[j]=new Question();
			String data[]=b.getStringArray("question"+j);
			questions[j].question=data[0];
			questions[j].answers=new LinkedList<String>();
			for(int k=1;k<data.length;k++){
				questions[j].answers.addLast(data[k]);
			}
		}
		answers=new String[noOfQuestions];
	}
	
	void process(){
		
		if(status.equals("q")){
			if(currentQuestion>=noOfQuestions)
				return;
			int i=currentQuestion;
				String toSpeak="";
				Question q=questions[i];
				toSpeak+="Question number ";
				toSpeak+=q.question;
				for(int j=0;j<q.answers.size();j++){
					toSpeak+=q.answers.get(j);
				}
				toSpeak+="touch the scree and give the answer";
				speakOut(toSpeak);
				status="a";
				return;
		}
		else if(status.equals("a")){
			Intent i = new Intent(getApplicationContext(), com.example.questionpaperreader.VoiceActivity.class);
			startActivityForResult(i, 4321);
		}		
		
	}

	@Override
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA	|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {				
				speakOut("touch the screen to start answering");
				ttsReady=true;
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}

	private void speakOut(String text) {
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 4321 && resultCode == RESULT_OK)
		{
			Bundle b=data.getExtras();
			
	        status="q";
	        String s[]=b.getStringArray("matches");
	        String ss="";
	        for(String sss:s){
	        	ss+=sss+" ";
	        }
	        answers[currentQuestion]=ss;
	        currentQuestion++;
	        
	        Toast tt = Toast.makeText(getApplicationContext(), ss ,Toast.LENGTH_LONG);
	        tt.show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
