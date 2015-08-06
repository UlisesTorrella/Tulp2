package com.example.ulises.tulp;

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {


    private CharSequence mTitle;
    String userMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userMail = intent.getExtras().getString("user_mail");
        setContentView(R.layout.activity_main);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String getUserMail(){
        return userMail;
    }
    public void setUserMail(String mail){
        userMail = mail;
    }



    ProfileFragment frag;
    void showFriend(int p, TulpUser[] friends){

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        frag = (ProfileFragment)getSupportFragmentManager().findFragmentById(R.id.profile_container);
        frag.mostrarAmigo(friends[p].getName(), friends[p].getPoints());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        actionBar.setTitle("Friend Profile:");
        new Wait().execute();

    }

    public void setImgRangeFriend(int points, View imgRango){
        int rango = 0;
        if(points < 100){
            rango = 1;//mostro
        }
        else{
            if(points<200){
                rango = 2;//maquinola
            }
            else{
                if(points<300){
                    rango = 3;//titan
                }
                else{
                    if(points<400){
                        rango = 4;//Troesma
                    }
                    else{
                        if(points>500){
                            rango = 5;//Lince
                        }
                    }
                }
            }
        }


        switch (rango) {
            case 1:
                Drawable mostro = getResources().getDrawable(R.drawable.mostro);
                imgRango.setBackground(mostro);
                break;
            case 2:
                Drawable maquinola = getResources().getDrawable(R.drawable.maquinola);
                imgRango.setBackground(maquinola);
                break;
            case 3:
                Drawable titan = getResources().getDrawable(R.drawable.titan);
                imgRango.setBackground(titan);
                break;
            case 4:
                Drawable troesma = getResources().getDrawable(R.drawable.troesma);
                imgRango.setBackground(troesma);
                break;
            case 5:
                Drawable lince = getResources().getDrawable(R.drawable.lince);
                imgRango.setBackground(lince);
                break;
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            AlertDialog.Builder cartel = new AlertDialog.Builder(this);
            RelativeLayout linearLayout=new RelativeLayout(this);

            final EditText picker = new EditText(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50,50);
            RelativeLayout.LayoutParams numPicerParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            linearLayout.addView(picker,numPicerParams);
            linearLayout.setLayoutParams(params);
            linearLayout.isClickable();
            cartel.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String contacto = picker.getText().toString();
                    new AddFriend().execute(contacto, getUserMail());
                }
            });
            cartel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            cartel.setTitle("Friend Email:");
            cartel.setView(linearLayout);
            //Dialog dialog = cartel.create();
            cartel.show();
        }

        return super.onOptionsItemSelected(item);
    }


    public class AddFriend extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... user) {
            String result = "";
            ServiceCall srvc = new ServiceCall("http://tulp-project.appspot.com");
            //ServiceCall srvc = new ServiceCall("http://localhost:8888");
            try {
                String data = srvc.post("/tulpsearch?busq="+user[0]+"&user="+user[1]);

                result = data;
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(String result) {
            Toast toast = Toast.makeText(getApplicationContext(), result, 999099);
            toast.show();
        }

    }

    public class Wait extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... user) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String coso) {
            frag.volverAPerfil();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Tulp");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

    }


}

