package com.example.ulises.tulp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends ActionBarActivity {
	String accountName;
	GoogleAccountCredential credential;
	static final String WEB_CLIENT_ID = "1091886568043-ecrprpv21383k4rnnneme4h1bfi5kdil.apps.googleusercontent.com";
	static final int REQUEST_ACCOUNT_PICKER = 1;
	private ProgressDialog progress;
	Account[] acc ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AccountManager am = AccountManager.get(this);
		acc = am.getAccountsByType("com.google");

		signIn();
		
	
	}
	private void signIn()
	{
		startActivityForResult(GoogleAccountCredential.usingAudience(this, "server:client_id:{id}.apps.googleusercontent.com").newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
			case REQUEST_ACCOUNT_PICKER:
				if (data != null && data.getExtras() != null)
				{
					String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
					if (accountName != null)
					{
						Intent mIntent = new Intent(getApplicationContext(),MainActivity.class);

						mIntent.putExtra("user_mail", accountName);
						//mIntent.putExtra("user_name", "ulises.torrella@gmail.com");
						startActivity(mIntent);
					}
				}
				break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	
	

	


}
