package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText edit_title, edit_author, edit_summary, edit_page;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edit_title = findViewById(R.id.editTextTitle);
        edit_author = findViewById(R.id.editTextAuthor);
        edit_summary = findViewById(R.id.editTextSummary);
        edit_page = findViewById(R.id.editTextPageNumber);

        btn_add = findViewById(R.id.btn_save);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    MyDatabaseHelper myDb = new MyDatabaseHelper(AddActivity.this);
                    myDb.addBook(edit_title.getText().toString().trim(),
                            edit_author.getText().toString().trim(),
                            edit_summary.getText().toString().trim(),
                            Integer.valueOf(edit_page.getText().toString().trim()));

                    edit_title.setText("");
                    edit_author.setText("");
                    edit_summary.setText("");
                    edit_page.setText("");

            }
        });

    }
}