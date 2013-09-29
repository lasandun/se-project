package com.example.googleapi;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class MainActivity extends Activity {
	String s=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*try {
			demo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast t = Toast.makeText(getApplicationContext(),"Exception occured",Toast.LENGTH_SHORT);
            t.show();
		}*/
		Thread t=new Thread(new x());
		t.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public class x implements Runnable{
		
		public void run() {
			while(true){
				try {
					demo();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					writeFile("Exception oooi");
				}
				
			}
			
		}
	}
	public void demo() throws IOException{
		
		//Toast tt = Toast.makeText(getApplicationContext(),"starting....",Toast.LENGTH_SHORT);
        //tt.show();
        
        File f = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"lahiru"+
	    		File.separator+"out.flac");
        //tt = Toast.makeText(getApplicationContext(),"file....",Toast.LENGTH_SHORT);
        //tt.show();
        FileInputStream fin;
        FileChannel ch;

        fin = new FileInputStream(f);
        ch = fin.getChannel();
        int size = (int) ch.size();
        MappedByteBuffer buf = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);
        byte[] data = new byte[size];
        buf.get(data);
        
        writeFile("1a");
        
        String request = "https://www.google.com/"
                + "speech-api/v1/recognize?"
                + "xjerr=1&client=speech2text&lang=en-US&maxresults=10";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        writeFile("1b");
        
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "audio/x-flac; rate=16000");
        connection.setRequestProperty("User-Agent", "speech2text");
        connection.setConnectTimeout(60000);
        connection.setUseCaches(false);
        
        writeFile("1c");
        
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());    
        wr.write(data);        
        wr.flush();
        wr.close();
        connection.disconnect();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                connection.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
        	s=decodedString;
        }        
        writeFile(s);
	}
	
	public void writeFile(String s) {
		File file = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"lahiru"+
        		File.separator+"input.txt");
        if(!file.exists()) {
        	try {
        		file.createNewFile();
        		FileOutputStream os=new FileOutputStream(file);
        		os.write(s.getBytes());
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
	}

}
