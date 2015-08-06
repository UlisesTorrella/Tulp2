package com.example.ulises.tulp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class NewAcc extends ActionBarActivity {

	String userMail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_acc);
		Intent intent = getIntent();
		userMail = intent.getExtras().getString("user_mail");
		TextView tv = (TextView) findViewById(R.id.txvNewAccMail);
		tv.setText(userMail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_acc, menu);
		return true;
	}


	
	
	public void create(View v){
		EditText et = (EditText) findViewById(R.id.etNewName);
		String name = et.getText().toString();
		new AddTulpUser().execute(name,userMail);
		finish();
	}
	
	
	public class AddTulpUser extends AsyncTask<String, Void, String> {

		
		@Override
		protected String doInBackground(String... user) {
			String result = "";
    		//ServiceCall srvc = new ServiceCall("http://1-dot-tulp-project.appspot.com");
    		ServiceCall srvc = new ServiceCall("http://localhost:8888");
    		try {
				srvc.post("/tulpadduser?name="+user[0]+"&mail="+user[1]);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}

			return result;
		}
		
		protected void onPostExecute(String result) {
			
	     }
	 
		
		
	}
}
