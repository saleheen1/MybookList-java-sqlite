package com.example.mytodo_java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
RecyclerView myRecyclerView;
FloatingActionButton addBookBtn;
DatabaseHelper myDB;
ArrayList<String> book_id,book_title,book_author,book_pages;
customAdapter customAdapter;
ImageView noDataImage;
TextView noDataTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = findViewById(R.id.myRecycler);
        addBookBtn = findViewById(R.id.bookPageBtnId);
        noDataImage = findViewById(R.id.noDataImage);
        noDataTxt = findViewById(R.id.noDataTxt);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //going to another page/activity
                Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
                startActivity(intent);
            }
        });
        myDB = new DatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArray();
        //make sure to call sotreDataInArray() before customAdapter so that we can read the data from our database and store it in our array
        customAdapter = new customAdapter(MainActivity.this,this,book_id,book_title,book_author,book_pages);
        myRecyclerView.setAdapter(customAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //need this method to refresh this activity when it gets result or data gets updated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount()==0){
            noDataImage.setVisibility(View.VISIBLE);
            noDataTxt.setVisibility(View.VISIBLE);
            Toast.makeText(this,"NO Data found!",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                noDataImage.setVisibility(View.GONE);
                noDataTxt.setVisibility(View.GONE);

                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //when we click on the menu then below code works
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            deleteAllCofirmation();



        }
        return super.onOptionsItemSelected(item);
    }

    void deleteAllCofirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all item");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.deleteAllData();
                //we need to refresh the page when all item gets deleted . so we will go the same activity we are already in (main activity)
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"All item deleted",Toast.LENGTH_SHORT).show();
                finish();
                 // pops from the current page and get back to previous page
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show(); //need to write this to show the alert dialogue
    }
}