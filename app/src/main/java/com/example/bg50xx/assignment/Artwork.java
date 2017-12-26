package com.example.bg50xx.assignment;

import android.media.Image;
import android.widget.ImageView;

import java.sql.Blob;

/**
 * Created by Lewis on 18/12/2017.
 */

public class Artwork {
    public int id;
    private String author;
    private String description;
    private String title;
    private int room;
    private int year;
    private byte[] image;
    private int rating;

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public int getRoom() {return room;}
    public void setRoom(int room) {this.room = room;}
    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}
    public byte[] getImage() {return image;}
    public void setImage(byte[] image) {this.image = image; }
    public int getRating() {return rating;}
    public void setRating(int rating) {this.rating = rating;}

    //empty constructor with no action taken
    public Artwork(){

    }
    //constructors, with title and author passed in as variables
    public Artwork(String title, String author, String description, int room, int year, byte[] image, int rating){
        //super();
        setTitle(title);
        setAuthor(author);
        setDescription(description);
        setRoom(room);
        setYear(year);
        setImage(image);
        setRating(rating);
    }

    //getters & setters
    @Override
    public String toString() {
        return "Artwork [id=" + id + ", title=" + getTitle() + ", author=" + getAuthor()
                + ", year=" + getYear() + "]";
    }
}