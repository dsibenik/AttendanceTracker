package com.example.aplikacija1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

public class ocrtest extends Activity {
  private static final int REQUEST_GALLERY = 0;
  private static final int REQUEST_CAMERA = 1;

  private static final String TAG = MainActivity.class.getSimpleName();

  private TessBaseAPI baseAPI;
  private Uri imageUri;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    //copyAssets();

    baseAPI = new TessBaseAPI();
    File externalStorageDirectory = Environment.getExternalStorageDirectory();

    File appDir = new File(externalStorageDirectory, "ocrsample");

    if (!appDir.isDirectory())
      appDir.mkdir();

    final File baseDir = new File(appDir, "tessdata");

    if (!baseDir.isDirectory())
      baseDir.mkdir();

    baseAPI.init(appDir.getPath(), "eng");
    findViewById(R.id.choose_from_gallery).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
      }
    });

    findViewById(R.id.take_a_photo).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        String filename = System.currentTimeMillis() + ".jpg";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, filename);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CAMERA);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.layout.activity_main, menu);
    return true;
  }

  private void inspectFromBitmap(Bitmap bitmap) {
   // baseAPI.setPageSegMode(TessBaseAPI.AVS_MOST_ACCURATE);
    baseAPI.setPageSegMode(7);
    baseAPI.setImage(bitmap);
    String text = baseAPI.getUTF8Text();
    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    bitmap.recycle();
  }

  private void inspect(Uri uri) {
    InputStream is = null;
    try {
      is = getContentResolver().openInputStream(uri);
      Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      options.inSampleSize = 2;
      options.inScreenDensity = DisplayMetrics.DENSITY_LOW;
      Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
      inspectFromBitmap(bitmap);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
        }
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
    case REQUEST_GALLERY:
      if (resultCode == RESULT_OK) {
        inspect(data.getData());
      }
      break;
    case REQUEST_CAMERA:
      if (resultCode == RESULT_OK) {
        if (imageUri != null) {
          inspect(imageUri);
        }
      }
      break;
    default:
      super.onActivityResult(requestCode, resultCode, data);
      break;
    }
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
	*/
	
  
  
}





