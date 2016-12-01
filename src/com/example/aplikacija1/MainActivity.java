package com.example.aplikacija1;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener {
	
	String dbime;
	String dbprezime;
	String dbjmbag;
	bazaadapter BAZA;
	
	Button gumb01;
	Button gumb02;
	Button gumb03;
	Button gumb04;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_main);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        
        gumb01=(Button)findViewById( R.id.button01 );
        gumb01.setOnClickListener(this);
        gumb02=(Button)findViewById( R.id.button02 );
        gumb02.setOnClickListener(this);
        gumb03=(Button)findViewById( R.id.button03 );
        gumb03.setOnClickListener(this);
        gumb04=(Button)findViewById( R.id.button04 );
        gumb04.setOnClickListener(this);
        
        
        BAZA = new bazaadapter(this);
    }
    
    
    public void brisijednog()
    {
    	int fi=BAZA.del(dbjmbag);
    	Message.message(this, "izbrisano ("+fi+")");
    }
    
    public void brisisve()
    {
    	int ka=BAZA.delall();
    	Message.message(this, "izbrisano sve (" + ka + ")");
    }
    
    public void inkrementiraj()
    {
    	int ro=BAZA.inkrement(dbjmbag);
    	//Message.message(this, "inkr "+dbjmbag);
    	Message.message(this, "inkrementirano (" + ro+")");
    }
    
    public void isprintaj()
    {
    	String data=BAZA.getAllData();
    	Message.message(this, data);
    }
    
    public void printajjednog()
    {
    	String data=BAZA.getData(dbjmbag);
    	Message.message(this, data);
    }
    
    
    public void addUser()
    {
    	long id=BAZA.insertData(dbime, dbprezime, dbjmbag);
    	if (id < 0 )
    	{
    		//nije dodano
    		Message.message(this,"Nije dodano");
    	}
    	else 
    	{
    		Message.message(this,"Uspjesno dodano");
    		//dodano
    	}

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.button01:
			Intent nextScreen = new Intent(getApplicationContext(), Plus_jedan.class);
			final int resultprvi = 1;
			startActivityForResult(nextScreen, resultprvi);
			
			
			/*nextScreen2.putExtra("_path23",_path2);
			final int result21 = 1;
			startActivityForResult(nextScreen2, result21);*/
			
			break;
		case R.id.button02:
			//Intent nextScreen2 = new Intent(getApplicationContext(), Dodaj_novi.class);
			Intent nextScreen2 = new Intent(getApplicationContext(), Dodaj_novi.class);
			final int resultdrugi = 2;
			startActivityForResult(nextScreen2, resultdrugi);
			break;
			
			
		case R.id.button03:
			Intent nextScreen3 = new Intent(getApplicationContext(), Vidi_sve.class);
			final int resulttreci = 3;
			//nextScreen3.putExtra("BAZA2", BAZA);
			startActivityForResult(nextScreen3,resulttreci);
			break;
			
		case R.id.button04:
			finish();
			break;
			
		default:
			
		break;
		};
		
		}
	
		public void onActivityResult(int requestCode,int resultCode,Intent data1)
		{
			super.onActivityResult(requestCode, resultCode, data1);
			
			if(requestCode==1)
			{
				if(resultCode!=3)
				{
				//inkrement
				dbjmbag=data1.getStringExtra("jmbag");
				inkrementiraj();
				}
			}
			
			if(requestCode==2)
			{
				//dodaj
				if(resultCode!=3)
				{
				dbime=data1.getStringExtra("ime");
				dbprezime=data1.getStringExtra("prezime");
				dbjmbag=data1.getStringExtra("jmbag");
				addUser();
				}
			}
			
			if(requestCode==3)
			{
				if(resultCode==4)
				{
					//pogledaj jednog
					dbjmbag=data1.getStringExtra("jedan");	
					//Message.message(this, "printam jednog "+dbjmbag);
					printajjednog();
				}
				
				if(resultCode==1)
				{
					//isprintaj sve
					isprintaj();
				}
				
				if(resultCode==2)
				{					//izbrisi jednog imam jmbag
					dbjmbag=data1.getStringExtra("jedan");
					//Message.message(this,"brisem jednog "+dbjmbag);
					brisijednog();
					
		
				}
				if(resultCode==3)
				{
					//Message.message(this, "Brisem sve");
					brisisve();
					//izbrisi sve
				}
			
			}
		}
	

}
