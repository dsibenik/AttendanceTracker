package com.example.aplikacija1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Vidi_sve extends Activity implements OnClickListener  {
	
	Button gumb30;
	Button gumb31;
	Button gumb32;
	Button gumb33;
	Button gumb34;
	EditText editirajtext31;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_3);
        
        
        gumb31=(Button)findViewById( R.id.button31 );
        gumb31.setOnClickListener(this);

        gumb32=(Button)findViewById( R.id.button322 );
        gumb32.setOnClickListener(this);
        
        gumb33=(Button)findViewById( R.id.button33 );
        gumb33.setOnClickListener(this);
        
        gumb34=(Button)findViewById( R.id.button34 );
        gumb34.setOnClickListener(this);
        
        gumb30=(Button)findViewById( R.id.button30);
        gumb30.setOnClickListener(this);
        
        editirajtext31 = (EditText) findViewById(R.id.editText31);
        
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()){
		case R.id.button30:
			//vidi jednog
			Intent intent30=new Intent();
			intent30.putExtra("jedan", editirajtext31.getText().toString());
    	    setResult(4,intent30);
			finish();
		break;
		
		case R.id.button31:
			//pogledaj sve
			Intent intent31=new Intent();
    	    setResult(1,intent31);
			finish();
		break;
		
		case R.id.button322:
			//izbrisi jednog
			Intent intent32=new Intent();
			intent32.putExtra("jedan", editirajtext31.getText().toString());
    	    setResult(2,intent32);
			finish();
		break;
		
		case R.id.button33:
			//izbrisi sve
			Intent intent33=new Intent();
    	    setResult(3,intent33);
			finish();
		break;
		
		case R.id.button34:
			//nazad
			Intent intent34=new Intent();
    	    setResult(5,intent34);
			finish();
		break;
		
		default:
		break;
		
		}
	}
	
	
}
