package com.example.aplikacija1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class bazaadapter {
	bazaheader helper;
	
	public bazaadapter(Context context) {
		// TODO Auto-generated constructor stub
		helper=new bazaheader(context);
	}
	
	public String getData(String name45){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String[] columns = { bazaheader.UID, bazaheader.NAME, bazaheader.SURNAME, bazaheader.JMBAG, bazaheader.DOLASCI };
		
		Cursor cursor = db.query ( bazaheader.TABLE_NAME, columns,null,null,null,null,null);
		StringBuffer buffer = new StringBuffer();
		while (cursor.moveToNext())
		{
			int index0 = cursor.getColumnIndex(bazaheader.UID);
			int index1 = cursor.getColumnIndex(bazaheader.NAME);
			int index2 = cursor.getColumnIndex(bazaheader.SURNAME);
			int index3 = cursor.getColumnIndex(bazaheader.JMBAG);
			int index4 = cursor.getColumnIndex(bazaheader.DOLASCI);
			//int personId = cursor.getInt(0);
			String personName = cursor.getString(index1);
			String personSurname = cursor.getString(index2);
			String personJmbag = cursor.getString(index3);
			int personDolasci = cursor.getInt(index4);
			Log.d( "teg2", "forpetlja"+ personJmbag + name45);
			if( personJmbag.equals(name45) )
			{
				Log.e( "teg2", "proslo za " + personJmbag);
				buffer.append(personJmbag+" "+personName+" "+personSurname+" "+personDolasci+"\n");
			}
		}
		return buffer.toString();
	}
	
	
	public long insertData(String ime, String prezime, String broj)
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues= new ContentValues();
		contentValues.put( bazaheader.NAME, ime);
		contentValues.put( bazaheader.SURNAME, prezime);
		contentValues.put( bazaheader.JMBAG, broj);
		contentValues.put( bazaheader.DOLASCI, 1);
		long id=db.insert(bazaheader.TABLE_NAME, null, contentValues);
				return id;
	}
	public int inkrement(String broj)
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		int count32=0;
		String[] columns = { bazaheader.UID, bazaheader.NAME, bazaheader.SURNAME, bazaheader.JMBAG, bazaheader.DOLASCI };
		Cursor cursor= db.query( bazaheader.TABLE_NAME, columns, null,null,null,null,null);
		while(cursor.moveToNext())
		{
			 Log.d( "teg2", "forpetlja"+cursor.getString(3)+broj);
			if(  broj.equals(cursor.getString(3))   )
			{
				 Log.e( "teg2", "proslo za " + broj);
				ContentValues contentValues= new ContentValues();
				int dol=cursor.getInt(4)+1;
				
				contentValues.put(bazaheader.DOLASCI, dol);
				String[] whereArgs={broj};
				count32 = db.update(bazaheader.TABLE_NAME, contentValues, bazaheader.JMBAG +"=?", whereArgs);
			}
		}
		return count32;
	}
	
	public int del(String broj)
	{   //DELETE * FROM TABLE WHERE JMBAG=broj
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={broj};
		int count = db.delete(bazaheader.TABLE_NAME, bazaheader.JMBAG+ "=?", whereArgs );
		return count;
	}
	
	public int delall()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		int count = db.delete(bazaheader.TABLE_NAME,null , null );
		return count;
	}
	
	public String getAllData()
	{
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] columns = { bazaheader.UID, bazaheader.NAME, bazaheader.SURNAME, bazaheader.JMBAG, bazaheader.DOLASCI };
		Cursor cursor= db.query( bazaheader.TABLE_NAME, columns, null,null,null,null,null);
		
		StringBuffer buffer=new StringBuffer();
		while(cursor.moveToNext())
		{
			//int cid=cursor.getInt(0);
			String name=cursor.getString(1);
			String surname=cursor.getString(2);
			String jmbag=cursor.getString(3);
			int dolasci=cursor.getInt(4);
			buffer.append(jmbag+" "+name+" "+surname+" "+dolasci+"\n");
		}
		return buffer.toString();
	}
	
	static class bazaheader extends SQLiteOpenHelper{
			private static final String DATABASE_NAME="BAZAJEDAN";
			private static final String TABLE_NAME="BAZATABLE";
			private static final int DATABASE_VERSION=2;
			private static final String UID="_id";
			private static final String NAME="Name";
			private static final String SURNAME="Surname";
			private static final String JMBAG="jmbag";
			private static final String DOLASCI="Dolasci";
			private Context context;
			private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME
					+ " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+SURNAME
					+ " VARCHAR(255), "+JMBAG+" VARCHAR(255), "+DOLASCI+" NUM );";
			private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
		
			
			
			public bazaheader(Context context){
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
				this.context=context;
			}
			
			@Override
			public void onCreate(SQLiteDatabase db) {
				// TODO Auto-generated method stub
				try {
					
					db.execSQL(CREATE_TABLE);
					Message.message(context, "Baza kreirana");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Message.message(context,"Baza nije kreirana");
				}
			}
		
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
				try {
					db.execSQL(DROP_TABLE);
					onCreate(db);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Message.message(context, ""+e);
				}
			}
		
		
		}


	}

