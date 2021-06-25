package com.example.mytodo_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
EditText title,author,pages;
Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        title = findViewById(R.id.bookName_inputId);
        author = findViewById(R.id.authorName_inputId);
        pages = findViewById(R.id.pages_inputId);
        addBtn = findViewById(R.id.addItem_btnId);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddItemActivity.this);
                dbHelper.addItem(title.getText().toString().trim(),
                        author.getText().toString().trim(),
                        Integer.parseInt(pages.getText().toString().trim()) //converting string to int
                );
            }
        });
    }
}