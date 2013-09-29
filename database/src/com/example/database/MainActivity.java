package com.example.database;

import android.os.Bundle;
import android.app.Activity;
import android.database.Observable;
import android.view.Menu;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DatabaseObj o=new DatabaseObj();
		Thread t=new Thread(o);
		//t.start();
		try {
			o.insertData("hell", 10);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showToast("exception");
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			showToast("no driver found");
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showToast(String s) {
		Toast tt = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        tt.show();
	}
	
	
	class DatabaseObj extends Observable<MainActivity> implements Runnable{
		
		int state=-1;

		private Connection con;

		public void getData() throws ClassNotFoundException {
			try {
				con=getConnection();
				PreparedStatement query = con.prepareStatement("SELECT name, age FROM table1");
				ResultSet result = query.executeQuery();

				while (result.next()) {
					System.out.println(result.getString("name")+"  "+result.getInt("age"));
				}
			} catch (SQLException d) {
				d.printStackTrace();
			}
		}

		public void insertData(String name, int age) throws SQLException, ClassNotFoundException{
			PreparedStatement statement;
			con=getConnection();
			String query="INSERT INTO table1 VALUES ('"+ name + "',"+age+");";
			statement=con.prepareStatement(query);
			statement.executeUpdate();
		}

		public Connection getConnection() throws SQLException, ClassNotFoundException{
			Class.forName("com.mysql.jdbc.Driver");
			if(con==null){
				con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/database", "root", "");
			}
			return con;
		}

		public void createTable() throws ClassNotFoundException{
			PreparedStatement statement=null;
			try {
				con=getConnection();
				String query="CREATE TABLE table1( name VARCHAR(20) , age INTEGER(5) )";
				statement=con.prepareStatement(query);
				statement.executeUpdate();
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseObj.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally{
				try{
					if(statement!=null){
						statement.close();
					}
					if(con!=null){
						con.close();
					}
				} catch (SQLException ex) {
					Logger.getLogger(DatabaseObj.class.getName()).log(Level.SEVERE, null, ex);
				}           
			}        
		}


		public void func(){
			DatabaseObj x=new DatabaseObj();
			try {
				//x.createTable();
				x.getData();
				//x.insertData("hi", 5);
			} catch (Exception ex) {
				Logger.getLogger(DatabaseObj.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
		
		public void run() {
			DatabaseObj x=new DatabaseObj();
			/*while(true){
				if(state==-1){
					System.out.println("");
				}
				else if(state==0){
					try {
						x.insertData("mama", 100);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					state=-1;
				}
				else if(state==1){
					return;
				}
			}*/
			

		}		
			
		
	}

}
