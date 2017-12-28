package com.example.bg50xx.assignment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CursorAdapter;

import java.io.ByteArrayInputStream;

/**
 * Created by Lewis on 26/12/2017.
 */

public class ArtCursorAdapter extends CursorAdapter {

    public ArtCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.contact_list_item,parent,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE));
        String author = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_AUTHOR));
        int year = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_YEAR));
        String description = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_DESCRIPTION));
        int room = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_ROOM));
        float rating = cursor.getFloat(
                cursor.getColumnIndex(MySQLiteHelper.KEY_RATING));
        byte[] image = cursor.getBlob(
                cursor.getColumnIndex(MySQLiteHelper.KEY_IMAGE));
        ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
        Bitmap picture = BitmapFactory.decodeStream(imageStream);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
        TextView txtYear = (TextView) view.findViewById(R.id.txtYear);
        TextView txtRoom = (TextView) view.findViewById(R.id.txtRoom);
        TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        RatingBar ratingbar = (RatingBar) view.findViewById(R.id.ratingBar);
        ImageView pic = (ImageView) view.findViewById(R.id.imageDocIcon);
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtYear.setText(String.valueOf(year));
        txtRoom.setText(String.valueOf(room));
        txtDescription.setText(description);
        ratingbar.setRating(rating);
        pic.setImageBitmap(picture);
    }
}
