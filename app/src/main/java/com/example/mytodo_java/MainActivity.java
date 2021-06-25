package com.example.mytodo_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
RecyclerView myRecyclerView;
FloatingActionButton addBookBtn;
DatabaseHelper myDB;
ArrayList<String> book_id,book_title,book_author,book_pages;
customAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = findViewById(R.id.myRecycler);
        addBookBtn = findViewById(R.id.bookPageBtnId);

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
        customAdapter = new customAdapter(this,book_id,book_title,book_author,book_pages);
        myRecyclerView.setAdapter(customAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount()==0){
            Toast.makeText(this,"NO Data found!",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }
}