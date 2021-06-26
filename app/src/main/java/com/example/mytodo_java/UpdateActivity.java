package com.example.mytodo_java;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
EditText title_input,author_input,pages_input;
Button updatebtn,deletebtn;
String title,author,pages,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.bookName_updateID);
        author_input = findViewById(R.id.authorName_updateId);
        pages_input = findViewById(R.id.pages_updateId);
        updatebtn = findViewById(R.id.updateItem_btnId);
        deletebtn = findViewById(R.id.deleteItem_BtnId);

        getAndSetIntentData();

        //to change the appbar title we need to call these after getAndSetIntentData() method
        ActionBar actionBar= getSupportActionBar();
        if(actionBar !=null){
            actionBar.setTitle(title);
        }

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbhelper = new DatabaseHelper(UpdateActivity.this);
                dbhelper.updateData(title_input.getText().toString(),author_input.getText().toString(),pages_input.getText().toString(),id);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialoague();
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


    void confirmDialoague(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+title + " ?");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateActivity.this);
                databaseHelper.deleteOneRow(id);
                finish(); //finish() method pops from the current page and gets back to previous page
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