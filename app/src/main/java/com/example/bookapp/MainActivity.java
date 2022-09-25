package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myyDB;
    ArrayList<String> book_id, book_title, book_author, book_pages, book_summary;

    CustomerAdapter customerAdapter;

    ImageView empty_image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.open_add_activity);

        empty_image = findViewById(R.id.empty_image);
        text = findViewById(R.id.text_no_data);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myyDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_summary = new ArrayList<>();
        book_pages = new ArrayList<>();


        storeDataInArray();

        customerAdapter = new CustomerAdapter(MainActivity.this,this, book_id, book_title, book_author, book_pages, book_summary);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor = myyDB.readAllData();

        if (cursor.getCount()== 0){
            //Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
            empty_image.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }else {

            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_summary.add(cursor.getString(3));
                book_pages.add(cursor.getString(4));

            }
            empty_image.setVisibility(View.GONE);
            text.setVisibility(View.GONE);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            //Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }




    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete ALL ?");
        builder.setMessage("Are you sure you want to delete  ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                // Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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