package com.udacity.quiz.gbookapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    //maksimal buku yang ditampilkan
    private int MAX_BOOK = 10;

    //prefix dari search URI
    private static final String urlBook = "https://www.googleapis.com/books/v1/volumes?q=";

    //deklarasi main view
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android.gbookapi.R.layout.activity_main);

        searchText = (EditText) findViewById(com.example.android.gbookapi.R.id.search_text);
    }

    //methode search onClick
    public void search(View view) {
        //ambil user input dan di trim depan belakang
        String search = searchText.getText().toString().trim();

        //ganti setiap spasi dengan tanda plus
        if(search.contains(" ")){
            search = search.replaceAll("\\s+", "+");
        }

        //gabung url dengan user input
        search = urlBook + search;

        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        intent.putExtra("searchKey", search);
        startActivity(intent);
    }
}
