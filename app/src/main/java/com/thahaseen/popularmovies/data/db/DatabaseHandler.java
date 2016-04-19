package com.thahaseen.popularmovies.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thahaseen.popularmovies.data.entities.Movie;

import java.util.ArrayList;

/**
 * Created by Thahaseen on 3/5/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "popularmovies.db";

    public static final String TABLE_MOVIE = "movie";

    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_POSTER_PATH = "poster_path";
    public static final String MOVIE_OVERVIEW = "overview";
    public static final String MOVIE_BACK_DROP_PATH = "backdrop_path";
    public static final String MOVIE_ORIGINAL_TITLE = "original_title";
    public static final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    public static final String MOVIE_RATING = "rating";
    public static final String MOVIE_RELEASE_DATE = "release_date";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE + "("
                + MOVIE_ID + " TEXT NOT NULL,"
                + MOVIE_TITLE + " TEXT NOT NULL,"
                + MOVIE_POSTER_PATH + " TEXT,"
                + MOVIE_OVERVIEW + " TEXT NOT NULL,"
                + MOVIE_BACK_DROP_PATH + " TEXT,"
                + MOVIE_ORIGINAL_TITLE + " TEXT,"
                + MOVIE_ORIGINAL_LANGUAGE + " TEXT,"
                + MOVIE_RATING + " TEXT NOT NULL,"
                + MOVIE_RELEASE_DATE + " TEXT NOT NULL)";

        db.execSQL(CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMovie(String id, String title, String poster_path, String overview, String backdrop_path,
                          String original_title, String original_language, Double rating, String release_date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, id);
        values.put(MOVIE_TITLE, title);
        values.put(MOVIE_POSTER_PATH, poster_path);
        values.put(MOVIE_OVERVIEW, overview);
        values.put(MOVIE_BACK_DROP_PATH, backdrop_path);
        values.put(MOVIE_ORIGINAL_TITLE, original_title);
        values.put(MOVIE_ORIGINAL_LANGUAGE, original_language);
        values.put(MOVIE_RATING, rating.toString());
        values.put(MOVIE_RELEASE_DATE, release_date);
        db.insert(TABLE_MOVIE, null, values);
        db.close();
    }

    //delete contents of movie table
    public void resetMovieTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIE, null, null);
        db.close();
    }

    public int getMovieRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOVIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }

    public ArrayList<Movie> getOfflineMovieList()
    {
        ArrayList<Movie> items = new ArrayList<Movie>();
        String selectQuery = "SELECT * FROM " + TABLE_MOVIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do{
                Movie movie = new Movie();
                movie.setId((c.getString(c.getColumnIndex(MOVIE_ID))));
                movie.setTitle((c.getString(c.getColumnIndex(MOVIE_TITLE))));
                movie.setPoster_path((c.getString(c.getColumnIndex(MOVIE_POSTER_PATH))));
                movie.setOverview((c.getString(c.getColumnIndex(MOVIE_OVERVIEW))));
                movie.setBackdrop_path((c.getString(c.getColumnIndex(MOVIE_BACK_DROP_PATH))));
                movie.setOriginal_title((c.getString(c.getColumnIndex(MOVIE_ORIGINAL_TITLE))));
                movie.setOriginal_language((c.getString(c.getColumnIndex(MOVIE_ORIGINAL_LANGUAGE))));
                movie.setVote_average(Double.parseDouble(c.getString(c.getColumnIndex(MOVIE_RATING))));
                movie.setRelease_date((c.getString(c.getColumnIndex(MOVIE_RELEASE_DATE))));
                items.add(movie);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return items;
    }
}
