package com.udacity.quiz.gbookapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android.gbookapi.R.layout.activity_detail);

        //pengambilan extra dari intent
        Intent intent = getIntent();
        Book book = (Book)intent.getSerializableExtra("book");

        ImageView imageView = (ImageView)findViewById(com.example.android.gbookapi.R.id.detaiBook_img);
        TextView titleView = (TextView)findViewById(com.example.android.gbookapi.R.id.detailTitle_view);
        TextView authorView = (TextView)findViewById(com.example.android.gbookapi.R.id.author_view);
        TextView dateView = (TextView)findViewById(com.example.android.gbookapi.R.id.publish_view);
        TextView descView = (TextView)findViewById(com.example.android.gbookapi.R.id.description_view);

        Picasso.with(DetailActivity.this).load(book.getImage()).into(imageView);
        titleView.setText(book.getTitle());
        authorView.setText(book.getAuthor());
        dateView.setText(book.getPublishDate());
        descView.setText(book.getDesc());
    }
}
