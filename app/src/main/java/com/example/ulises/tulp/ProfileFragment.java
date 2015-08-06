package com.example.ulises.tulp;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;


public class ProfileFragment extends Fragment {

	        /**
	         * The fragment argument representing the section number for this
	         * fragment.
	         */
	        private static final String ARG_SECTION_NUMBER = "section_number";
			String accountName;
			GoogleAccountCredential credential;
			static final String WEB_CLIENT_ID = "1091886568043-ecrprpv21383k4rnnneme4h1bfi5kdil.apps.googleusercontent.com";
			static final int REQUEST_ACCOUNT_PICKER = 1;
	        /**
	         * Returns a new instance of this fragment for the given section
	         * number.
	         */
	        TextView nombre;
	        TextView puntos;
	        View root;
			String name;
			Long points;
	    	private ProgressDialog progress;
			//GoogleAccountCredential credential;

	        public static ProfileFragment newInstance(int sectionNumber) {
	        	ProfileFragment fragment = new ProfileFragment();
	            Bundle args = new Bundle();
	            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	            fragment.setArguments(args);
	            
	            return fragment;
	        }

	        public ProfileFragment() {
	        	
	        }
	        

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

		        nombre =(TextView) rootView.findViewById(R.id.txvName);
				nombre.setTextSize(26);
	    		puntos = (TextView) rootView.findViewById(R.id.txvPoints);
				puntos.setTextSize(26);
	    		root = rootView;

	    		progress = new ProgressDialog(getActivity());
	            progress.setTitle("Please Wait!!");
	            progress.setMessage("Wait!!");
	            progress.setCancelable(false);
	            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.show();
	    		String mail = ((MainActivity) getActivity()).getUserMail();
				credential = GoogleAccountCredential.usingAudience(getActivity(), "server:client_id:"+WEB_CLIENT_ID);
				startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);



	            return rootView;
	        }

			@Override
			public void onActivityResult(int requestCode, int resultCode, Intent data) {
				super.onActivityResult(requestCode, resultCode, data);
				switch (requestCode) {
					case REQUEST_ACCOUNT_PICKER:
						if (data != null && data.getExtras() != null) {

							accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
							if (accountName != null) {
								credential.setSelectedAccountName(accountName);
								new GetFromTulpServer().execute(accountName);
							}
						}
						break;
				}
			}

			public void mostrarAmigo(String name, Long points) {
				nombre.setText(name);
				puntos.setText(""+points);
				setImgRange(points);
			}

			public void volverAPerfil() {
				nombre.setText(name);
				puntos.setText(""+points);
				setImgRange(points);
			}

	        


	    	public void setImgRange(Long puntos){
	    		long points = puntos;
				int rangoint = 0;
				if(points<100){
					rangoint = 1;//mostro
				}
				else{
					if(points<200){
						rangoint = 2;//maquinola
					}
					else{
						if(points<300){
							rangoint = 3;//titan
						}
						else{
							if(points<400){
								rangoint = 4;//Troesma
							}
							else{
								if(points>400){
									rangoint = 5;//Lince
								}
							}
						}
					}
				}
				
				
				switch (rangoint) {
				case 1:
					Drawable mostro = getResources().getDrawable(R.drawable.mostro);
					root.setBackground(mostro);
					break;
				case 2:
					Drawable maquinola = getResources().getDrawable(R.drawable.maquinola);
					root.setBackground(maquinola);
					break;
				case 3:
					Drawable titan = getResources().getDrawable(R.drawable.titan);
					root.setBackground(titan);
					break;
				case 4:
					Drawable troesma = getResources().getDrawable(R.drawable.troesma);
					root.setBackground(troesma);
					break;
				case 5:
					Drawable lince = getResources().getDrawable(R.drawable.lince);
					root.setBackground(lince);
					break;
				}
	    		
	    	}
	    	
	    	
	    	
	    	/*
	    	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    		String mail = ((MainActivity) getActivity()).getUserMail();
	    		new GetFromTulpServer().execute(mail);
	    	    
	    	}
	    	*/
	    	
	    	
	    	
	    	
	    	public class GetFromTulpServer extends AsyncTask<String, Void, String> {

	    		
	    		@Override
	    		protected String doInBackground(String... user) {
					String result ="";
					String url="http://tulp-project.appspot.com";
					HttpTransport transport = new NetHttpTransport();

					HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
					GenericUrl genUrl= new GenericUrl(url+"/tulpserver?user="+((MainActivity) getActivity()).getUserMail());


					try {
						HttpRequest request = requestFactory.buildGetRequest(genUrl);
						HttpResponse resp = request.execute();
						result = resp.parseAsString();
					} catch (IOException e) {
						result = e.getMessage();
					}

	    			return result;
	    		}
	    		
	    		protected void onPostExecute(String result) {
	    			if(result != ""){
	    				if(result.startsWith("FAIL")){
	    					Intent intent = new Intent(getActivity(), NewAcc.class);
	    					intent.putExtra("user_mail", ((MainActivity) getActivity()).getUserMail());
	    					startActivityForResult(intent, 1);
	    					progress.dismiss();
	    				}
	    				else{
	    					TulpUser usuario = new TulpUser(result);
			    			nombre.setText(usuario.getName());
							name= usuario.getName();
			    			puntos.setText(""+usuario.getPoints());
							setImgRange(usuario.getPoints());
							points=usuario.getPoints();
			    			progress.dismiss();
	    				}
	    			}
	    			else{
	    				String mail = ((MainActivity) getActivity()).getUserMail();
	    	    		new GetFromTulpServer().execute(mail);
	    			}
	    	     }
	    	 
	    		
	    		
	    	}
	        
}


