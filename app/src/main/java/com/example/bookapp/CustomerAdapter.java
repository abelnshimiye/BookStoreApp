package com.example.bookapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
   private ArrayList book_id, book_title, book_author, book_page_number, book_summary;

   Animation translate_anim;


    public CustomerAdapter(Activity activity,Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_page_number, ArrayList book_summary) {
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_page_number = book_page_number;
        this.book_summary = book_summary;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtbook_id.setText(String.valueOf(book_id.get(position)));
        holder.txtbook_title.setText(String.valueOf(book_title.get(position)));
        holder.txtbook_author.setText(String.valueOf(book_author.get(position)));
        holder.txtbook_page.setText(String.valueOf(book_page_number.get(position)));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("page", String.valueOf(book_page_number.get(position)));
                intent.putExtra("summary", String.valueOf(book_summary.get(position)));

                activity.startActivityForResult(intent,1);
//                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        TextView txtbook_id, txtbook_title, txtbook_author, txtbook_page;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtbook_id =  itemView.findViewById(R.id.text_book_id);
            txtbook_title =  itemView.findViewById(R.id.text_book_title);
            txtbook_author =  itemView.findViewById(R.id.text_book_author);
            txtbook_page =  itemView.findViewById(R.id.text_book_pages_number);

            constraintLayout = itemView.findViewById(R.id.row);
            //Animate recyclereview
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            constraintLayout.setAnimation(translate_anim);

        }
    }


}
