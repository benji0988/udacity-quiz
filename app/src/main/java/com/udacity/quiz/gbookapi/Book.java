package com.udacity.quiz.gbookapi;

import java.io.Serializable;

/**
 * Created by nagabonar on 7/22/2017.
 */

public class Book implements Serializable {
    protected String mTitle, mImage, publishDate, mAuthor, mDesc;

    public Book(String title, String image, String date, String author, String desc){
        mTitle = title;
        mImage = image;
        publishDate = date;
        mAuthor = author;
        mDesc = desc;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getImage(){
        return mImage;
    }

    public String getPublishDate(){
        return publishDate;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getDesc(){
        return mDesc;
    }
}
