package com.example.aplikacija1;

import java.io.File;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore;

public class kamera extends Activity implements OnClickListener {
	
	Button _button;
	ImageView _image;
	TextView _field;
	String _path;
	boolean _taken;
	Button gumb24;
		
	protected static final String PHOTO_TAKEN = "photo_taken";
	
	
	
	public void onCreate (Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	        
	    setContentView(R.layout.kamera_1);
	       
	    _image = ( ImageView ) findViewById( R.id.image23 );
	    //_field = ( TextView ) findViewById( R.id.edittext23 );
	    _button = (Button)findViewById(R.id.button23);
	    _button.setOnClickListener( new ButtonClickHandler() );
	    
	    gumb24 = (Button)findViewById(R.id.button24);
	    gumb24.setOnClickListener( this );
	        
	    Intent sender=getIntent();
	    _path=sender.getExtras().getString("_path23");
	    //_path = Environment.getExternalStorageDirectory() + "/make_machine_example.jpg";
	}
	


	public class ButtonClickHandler implements View.OnClickListener 
	{
	    public void onClick( View view ){
	    	startCameraActivity();
	    }
	}
	
	
	protected void startCameraActivity()
	{
	    File file = new File( _path );
	    Uri outputFileUri = Uri.fromFile( file );
	    	
	    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
	    intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
	    	
	    startActivityForResult( intent, 0 );
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{	
	    Log.i( "MakeMachine", "resultCode: " + resultCode );
	    switch( resultCode )
	    {
	    	case 0:
	    		Log.i( "MakeMachine", "User cancelled" );
	    		break;
	    			
	    	case -1:
	    		Intent intent31=new Intent();
	    	    intent31.putExtra("ComingFrom", _path);
	    	    setResult(1,intent31);
	    		onPhotoTaken();
	    		break;
	    }
	}
	
	protected void onPhotoTaken()
	{
	    _taken = true;
	    	
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inSampleSize = 4;
	    	
	    Bitmap bitmap = BitmapFactory.decodeFile( _path, options );
	   // double temp = 50;
	   // _image.setImageBitmap( adjustedContrast(bitmap,temp) );
	    Bitmap bitmap2= toGrayscale(bitmap);
		Bitmap bitmap3 = Bitmap.createBitmap( bitmap2, 10, 15, bitmap2.getWidth()-30, (int)(bitmap2.getHeight()*0.1), null, false);
	    _image.setImageBitmap( bitmap3 );	
	    //_field.setVisibility( View.GONE );
	}
	
	/*
	protected void onSaveInstanceState( Bundle outState ) {
	    outState.putBoolean( PhotoCaptureExample.PHOTO_TAKEN, _taken );
	}
	
	protected void onRestoreInstanceState( Bundle savedInstanceState)
	{
	    Log.i( "MakeMachine", "onRestoreInstanceState()");
	    if( savedInstanceState.getBoolean( PhotoCaptureExample.PHOTO_TAKEN ) ) {
	    	onPhotoTaken();
	    }
	    onPhotoTaken();
	}*/


	@Override
	public void onClick(View k) {
			// TODO Auto-generated method stub
			switch (k.getId()){

			case R.id.button24:
				finish();
				
			break;
			
			default:
				
			break;
			};
	}
	/*
	private Bitmap adjustedContrast(Bitmap src, double value)
	{
	    // image size
	    int width = src.getWidth();
	    int height = src.getHeight();
	    // create output bitmap

	    // create a mutable empty bitmap
	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

	    // create a canvas so that we can draw the bmOut Bitmap from source bitmap
	    Canvas c = new Canvas();
	    c.setBitmap(bmOut);

	    // draw bitmap to bmOut from src bitmap so we can modify it
	    c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


	    // color information
	    int A, R, G, B;
	    int pixel;
	    // get contrast value
	    double contrast = Math.pow((100 + value) / 100, 2);

	    // scan through all pixels
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // get pixel color
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            // apply filter contrast for every channel R, G, B
	            R = Color.red(pixel);
	            R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
	            if(R < 0) { R = 0; }
	            else if(R > 255) { R = 255; }

	            G = Color.green(pixel);
	            G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
	            if(G < 0) { G = 0; }
	            else if(G > 255) { G = 255; }

	            B = Color.blue(pixel);
	            B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
	            if(B < 0) { B = 0; }
	            else if(B > 255) { B = 255; }

	            // set new pixel color to output bitmap
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	    return bmOut;
	}*/
	
	public Bitmap toGrayscale(Bitmap bmpOriginal)
	{        
	    int width, height;
	    height = bmpOriginal.getHeight();
	    width = bmpOriginal.getWidth();    

	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	}

}
