package com.example.aplikacija1;

import java.io.File;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Plus_jedan extends Activity implements OnClickListener {
	
	EditText editirajtext11;
	Button gumb11;  //dodaj
	Button gumb12; //dodaj kamerom
	Button gumb13;//nazad
	String _path2;
	String jmbag21;
	Bitmap bitmap;
	
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_1);
        
        
        editirajtext11 = (EditText) findViewById(R.id.editText11);
        //editirajtext11.setText("JMBAG");
        
        gumb11=(Button)findViewById( R.id.button11 );
        gumb11.setOnClickListener(this);
        
        gumb12=(Button)findViewById( R.id.button12 );
        gumb12.setOnClickListener(this);
        
        gumb13=(Button)findViewById( R.id.button13 );
        gumb13.setOnClickListener(this);
        
        _path2 = Environment.getExternalStorageDirectory() + "/make_machine_example.jpg";
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.button12:
			//generiraj jmbag kamerom
			Intent nextScreen2 = new Intent(getApplicationContext(), kamera.class);
			nextScreen2.putExtra("_path23",_path2);
			final int result21 = 1;
			startActivityForResult(nextScreen2, result21);
			
			
			break;
		case R.id.button11:
			//Dodaj u bazu usera sa tim imenima (provjeri prvo je li prazan string)
			Intent intent11=new Intent();
    	    intent11.putExtra("jmbag", editirajtext11.getText().toString());
    	    setResult(1,intent11);
			finish();
			break;
			
		case R.id.button13:
			Intent intent13=new Intent();
    	    setResult(3,intent13);
			finish();
		default:
			
		break;
		};
		
	}
	
	@Override
    public void onActivityResult(int requestCode,int resultCode,Intent data2)
    {
     super.onActivityResult(requestCode, resultCode, data2);
     	
      //editirajtext21 = (EditText) findViewById(R.id.editText21);
      
      
      
      _path2=data2.getStringExtra("ComingFrom");
      		
      bitmap = BitmapFactory.decodeFile(_path2);
      
		/*	ExifInterface exif = null;
	
			try {
				exif = new ExifInterface( _path2 );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int exifOrientation = exif.getAttributeInt(
			        ExifInterface.TAG_ORIENTATION,
			        ExifInterface.ORIENTATION_NORMAL);
			
			int rotate = 0;
			Log.i("juzer", "prije bananka2");
			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
			    rotate = 90;
			    break;
			case ExifInterface.ORIENTATION_ROTATE_180:
			    rotate = 180;
			    break;
			case ExifInterface.ORIENTATION_ROTATE_270:
			    rotate = 270;
			    break;
			 default:
				 break;
			}
			Log.i("juzer", "prije bananka3");
			if (rotate != 0) {
				Log.i("juzer", "prije bananka3.2");
			    int w = bitmap.getWidth();
			    int h = bitmap.getHeight();
			
			    // Setting pre rotate
			    Matrix mtx = new Matrix();
			    mtx.preRotate(rotate);
			    Log.i("juzer", "prije bananka3.5");
			    // Rotating Bitmap & convert to ARGB_8888, required by tess
			    bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
			}
			Log.i("juzer", "prije bananka4");*/
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			
	//copyAssets();
	
	TessBaseAPI baseAPI = new TessBaseAPI();

	
	
    File externalStorageDirectory = Environment.getExternalStorageDirectory();
    File appDir = new File(externalStorageDirectory, "ocrsample");
	baseAPI.init( appDir.getPath(), "eng");
	
	
	Bitmap convertedBitmap = convert( toGrayscale(bitmap), Bitmap.Config.ARGB_8888);
	//source start start, width, height
	Bitmap bitmap3 = Bitmap.createBitmap( convertedBitmap, 10, 15, convertedBitmap.getWidth()-30, (int)(convertedBitmap.getHeight()*0.1), null, false);
	baseAPI.setImage( bitmap3 );
	
	jmbag21 = baseAPI.getUTF8Text();
	baseAPI.end();
	
	editirajtext11.setText(jmbag21);

	
    }
	
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
	
	private Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
	    Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
	    Canvas canvas = new Canvas(convertedBitmap);
	    Paint paint = new Paint();
	    paint.setColor(Color.BLACK);
	    canvas.drawBitmap(bitmap, 0, 0, paint);
	    return convertedBitmap;
	}
	
	
}
