package com.example.bg50xx.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CursorAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Lewis on 26/12/2017.
 */

public class ArtCursorAdapter extends CursorAdapter {

    MySQLiteHelper db;
    Artwork art;
    String title, description, author;
    int year, room;
    float newrating;
    byte[] image;
    static class ViewHolder{
        RatingBar ratingBar1;

    }

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
        ViewHolder holder = (ViewHolder) view.getTag();
        title = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE));
        author = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_AUTHOR));
        year = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_YEAR));
        room = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_ROOM));
        newrating = cursor.getFloat(
                cursor.getColumnIndex(MySQLiteHelper.KEY_RATING));
        image = cursor.getBlob(
                cursor.getColumnIndex(MySQLiteHelper.KEY_IMAGE));
        ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
        Bitmap picture = BitmapFactory.decodeStream(imageStream);
        final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        final TextView txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
        final TextView txtYear = (TextView) view.findViewById(R.id.txtYear);
        final TextView txtRoom = (TextView) view.findViewById(R.id.txtRoom);
        final RatingBar ratingbar = (RatingBar) view.findViewById(R.id.ratingBar);
        final ImageView pic = (ImageView) view.findViewById(R.id.imageDocIcon);
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtYear.setText(String.valueOf(year));
        txtRoom.setText(String.valueOf(room));
        ratingbar.setRating(newrating);
        pic.setImageBitmap(picture);

        Log.d("artTitle", title);
//
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser) {
                //art = db.getArtByTitle(title);

                String currentTitle = txtTitle.getText().toString();
                String currentAuthor = txtAuthor.getText().toString();
                int currentYear = Integer.parseInt(txtYear.getText().toString());
                int currentRoom = Integer.parseInt(txtRoom.getText().toString());
                Bitmap bitmap = ((BitmapDrawable) pic.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                float currentRating = rating;
                art = new Artwork(currentTitle,currentAuthor, "description",currentYear,currentRoom,imageInByte,currentRating, 1);
                //art = db.getArtByTitle(txtTitle.getText().toString());
                Log.d("artTitle", art.toString());
                //art.setRating(test);
                ratingbar.setRating(currentRating);
                //db.updateArt(art);
            }
        });

    }
}
