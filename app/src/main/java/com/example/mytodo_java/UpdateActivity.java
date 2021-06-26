package com.example.mytodo_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
EditText title_input,author_input,pages_input;
Button updatebtn;
String title,author,pages,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.bookName_updateID);
        author_input = findViewById(R.id.authorName_updateId);
        pages_input = findViewById(R.id.pages_updateId);
        updatebtn = findViewById(R.id.updateItem_btnId);

        getAndSetIntentData();

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbhelper = new DatabaseHelper(UpdateActivity.this);
                dbhelper.updateData(title_input.getText().toString(),author_input.getText().toString(),pages_input.getText().toString(),id);
            }
        });


    }
    void getAndSetIntentData(){
        //receiving data that was sent by putExtra
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            //getting data from intent that was sent from previous page
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            //Showing the intent data
             title_input.setText(title);
             author_input.setText(author);
             pages_input.setText(pages);

        }else {
            Toast.makeText(this,"NO data",Toast.LENGTH_SHORT).show();
        }
    }
}