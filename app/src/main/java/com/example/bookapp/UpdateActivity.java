package com.example.bookapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input, summary_input;
    Button update_button, delete_button;

    String id, title, author, pages, summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.editTextTitle2);
        author_input = findViewById(R.id.editTextAuthor2);
        pages_input = findViewById(R.id.editTextPageNumber2);
        summary_input = findViewById(R.id.editTextSummary2);

        update_button = findViewById(R.id.btn_update);

        delete_button = findViewById(R.id.btn_delete);

        //First we call this
        getAndSetIntentData();

        //set actionbar title after getAndSetData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                //and only then we call this
                myDB.updateData(id,title_input.getText().toString().trim(),author_input.getText().toString().trim(),summary_input.getText().toString().trim(),pages_input.getText().toString().trim());

                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
        });


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmDialog();
            }
        });



    }

    void getAndSetIntentData(){

        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("page")&& getIntent().hasExtra("summary")){
            //getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            summary = getIntent().getStringExtra("summary");
            pages = getIntent().getStringExtra("page");

            // setting data from intent
            title_input.setText(title);
            author_input.setText(author);
            summary_input.setText(summary);
            pages_input.setText(pages);

        }else{
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}