package com.example.mytodo_java;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";

     DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_AUTHOR + " TEXT, " + COLUMN_PAGES + " INTEGER);";
    db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addItem(String title,String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvalues = new ContentValues(); //we want store in this object, all our data from our application and then pass this to the db

        cvalues.put(COLUMN_TITLE,title); //first parameter is the column name and second is the data
        cvalues.put(COLUMN_AUTHOR,author);
        cvalues.put(COLUMN_PAGES,pages);

       long result = db.insert(TABLE_NAME,null,cvalues);
       if(result == -1){
           //-1 means operation failed
           Toast.makeText(context,"Operation Failed",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context,"Data successfully added to database",Toast.LENGTH_SHORT).show();
       }
    }

    //Read Data from database
    Cursor readAllData(){
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void updateData(String title, String author, String pages,String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(COLUMN_TITLE,title);
         cv.put(COLUMN_AUTHOR,author);
         cv.put(COLUMN_PAGES,pages);
        long result=  db.update(TABLE_NAME,cv,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Operation failed",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Successfully added to database",Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
        long result=  db.delete(TABLE_NAME,"id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Successfully deleted from database",Toast.LENGTH_SHORT).show();
        }
    }

}
