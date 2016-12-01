package com.example.aplikacija1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView.OnEditorActionListener;

public class Dodaj_novi extends Activity implements OnClickListener {
	Button gumb21;
	Button gumb23;
	Button gumb24;
	String _path2;
	EditText editirajtext21;
	EditText editirajtext22;
	EditText editirajtext23;
	String jmbag21;
	Bitmap bitmap;
	String paramString;
	String basedir;
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_2);


        editirajtext21 = (EditText) findViewById(R.id.editText21);;

        editirajtext22 = (EditText) findViewById(R.id.editText224);

        editirajtext23 = (EditText) findViewById(R.id.editText234);

        gumb21=(Button)findViewById( R.id.button21 );
        gumb21.setOnClickListener(this);

        gumb23=(Button)findViewById( R.id.button23 );
        gumb23.setOnClickListener(this);
        
        gumb24=(Button)findViewById( R.id.button24 );
        gumb24.setOnClickListener(this);
 
        _path2 = Environment.getExternalStorageDirectory() + "/make_machine_example.jpg";
        basedir = Environment.getExternalStorageDirectory()+"";

        //paramString = Environment.getExternalStorageDirectory() + "eng.traineddata";
        

       /* copyfile("eng.cube.bigrams");       
        copyfile("eng.cube.fold");     
        copyfile("eng.cube.lm");
        copyfile("eng.cube.nn");
        copyfile("eng.cube.params");
        copyfile("eng.cube.size");  
        copyfile("eng.cube.word-freq");
        copyfile("eng.tesseract_cube.nn");*/
        
        paramString = "eng.traineddata";
       // copyfile("eng.traineddata");
        
        //editirajtext21 = (EditText) findViewById(R.id.editText21);
       // jmbag21 = editirajtext21.getText().toString();
       // editirajtext21.setText(jmbag21);
        

        
	}
	
	
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.button21:
			//generiraj jmbag
			
			
			
			Intent nextScreen2 = new Intent(getApplicationContext(), kamera.class);
			nextScreen2.putExtra("_path23",_path2);
			final int result21 = 1;
			startActivityForResult(nextScreen2, result21);

			/*TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(DATA_PATH, "eng");
			editirajtext21.setText(jmbag21);*/
			break;
		case R.id.button23:
			//Dodaj u bazu usera sa tim imenima (provjeri prvo je li prazan string)
			Intent intent21=new Intent();
    	    intent21.putExtra("ime", editirajtext22.getText().toString() );
    	    intent21.putExtra("prezime", editirajtext23.getText().toString());
    	    intent21.putExtra("jmbag", editirajtext21.getText().toString());
    	    setResult(1,intent21);
    	    finish();
			break;
			
		case R.id.button24:
			Intent intent24=new Intent();
			setResult(3,intent24);
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
	Log.i("teg2","bananko4");
	baseAPI.setImage( bitmap3 );
	Log.i("teg2","bananko5");
	jmbag21 = baseAPI.getUTF8Text();
	baseAPI.end();
	Log.i("teg2","bananko6");
	editirajtext21.setText(jmbag21);

	
    }
	
	/*
	private void copyAssets() {
	    AssetManager assetManager = getAssets();
	    String[] files = null;
	    try {
	        files = assetManager.list("");
	    } catch (IOException e) {
	        Log.e("juzer", "Failed to get asset file list.", e);
	    }
	    for(String filename : files) {
	        InputStream in = null;
	        OutputStream out = null;
	        try {
	          in = assetManager.open(filename);
	          File outFile = new File(Environment.getExternalStorageDirectory(), filename);
	          Log.d("juzer", "File " + filename + " copied successfully.");
	          out = new FileOutputStream(outFile);
	          copyFile(in, out);
	          in.close();
	          in = null;
	          out.flush();
	          out.close();
	          out = null;
	        } catch(IOException e) {
	            Log.e("juzer", "Failed to copy asset file: " + filename, e);
	        }       
	    }
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
	
	
	
	public void copyfile(String fileName)
    {
      if (!new File(fileName).exists()){
          try
          {
            InputStream localInputStream = getAssets().open(fileName);
            FileOutputStream localFileOutputStream = getBaseContext().openFileOutput(fileName, MODE_WORLD_READABLE);

            byte[] arrayOfByte = new byte[1024];
            int offset;
            while ((offset = localInputStream.read(arrayOfByte))>0)
            {
              localFileOutputStream.write(arrayOfByte, 0, offset);
            }
            localFileOutputStream.close();
            localInputStream.close();
            // The next 3 lines are because I'm copying a binary that I plan
            // to execute, so I need it to have execute permission.
            StringBuilder command = new StringBuilder("chmod 700 ");
            command.append(basedir + "/" + fileName);
            Runtime.getRuntime().exec(command.toString());
            Log.d("juzer", "File " + fileName + " copied successfully.");
          }
          catch (IOException localIOException)
          {
              localIOException.printStackTrace();
              return;
          }
      }
      else
          Log.d("juzer", "No need to copy file " + paramString);
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
	
	private Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
	    Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
	    Canvas canvas = new Canvas(convertedBitmap);
	    Paint paint = new Paint();
	    paint.setColor(Color.BLACK);
	    canvas.drawBitmap(bitmap, 0, 0, paint);
	    return convertedBitmap;
	}
	
}
