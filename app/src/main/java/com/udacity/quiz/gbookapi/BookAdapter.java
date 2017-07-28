package com.udacity.quiz.gbookapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nagabonar on 7/22/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private View listItemView;

    private static Context mContext;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, 0, books);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(com.example.android.gbookapi.R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextview = (TextView) listItemView.findViewById(com.example.android.gbookapi.R.id.title_text_view);
        titleTextview.setText(currentBook.getTitle());

        TextView dateTextview = (TextView) listItemView.findViewById(com.example.android.gbookapi.R.id.date_view);
        dateTextview.setText(currentBook.getPublishDate());

        ImageView bookImageview = (ImageView) listItemView.findViewById(com.example.android.gbookapi.R.id.img_view);
        Picasso.with(mContext).load(currentBook.getImage()).into(bookImageview);

        return listItemView;
    }
}
