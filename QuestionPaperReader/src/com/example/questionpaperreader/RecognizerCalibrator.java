package com.example.questionpaperreader;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RecognizerCalibrator extends Activity implements TextToSpeech.OnInitListener{
	
	private TextToSpeech tts;
	boolean ttsReady=false;
	private static final int REQUEST_CODE = 1234;
	
	Button buttonSpeak;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recognizer_calibrator);
		tts = new TextToSpeech(this, this);
		buttonSpeak= (Button) findViewById(R.id.speak_button);
		
		buttonSpeak.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ttsReady){
					//speakOut("hi how are you?");
				}
				Toast tt = Toast.makeText(getApplicationContext(), " hi " ,Toast.LENGTH_LONG);
		        tt.show();
		        startVoiceRecognitionActivity();
			}
		});
	}
	
	void calibrate(){
		
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

			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
				Toast tt = Toast.makeText(getApplicationContext(), "speaker not found" ,Toast.LENGTH_LONG);
		        tt.show();
			} else {				
				//speakOut("touch the screen to start answering");
				ttsReady=true;
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}

	}
	
	private void speakOut(String text) {
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	private void startVoiceRecognitionActivity()
	{
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "AndroidBite Voice Recognition...");
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		{
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	        String s[]=new String[matches.size()];
	        matches.toArray(s);
	        
	        Toast tt = Toast.makeText(getApplicationContext(), matches.toString() ,Toast.LENGTH_LONG);
	        tt.show();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
