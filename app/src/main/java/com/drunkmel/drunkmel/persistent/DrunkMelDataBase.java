package com.drunkmel.drunkmel.persistent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.drunkmel.drunkmel.model.PlayerHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gm on 01/04/17.
 */
public class DrunkMelDataBase extends SQLiteOpenHelper {
    private static final String TAG = "DrunkMel";
    private static DrunkMelDataBase instance;

    // Data base information
    private static final String DATABASE_NAME = "drunkMelDataBase";
    private static final int DATABASE_VERSION = 1;

    // Tables information
    private static final String TABLE_PLAYER_HISTORY = "playerHistory";

    // Row information
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SCORE = "score";
    private static final String POINTS = "points";

    public static synchronized DrunkMelDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new DrunkMelDataBase(context.getApplicationContext());
        }
        return instance;
    }

    private DrunkMelDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_HISTORY_TABLE = "CREATE TABLE " + TABLE_PLAYER_HISTORY +
                "(" +
                ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT, " +
                SCORE + " INTEGER, " +
                POINTS + " INTEGER" +
                ")";

        db.execSQL(CREATE_PLAYER_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_HISTORY);
            onCreate(db);
        }
    }

    public void addPlayerHistory(PlayerHistory ph) {
        // Create or open the DB
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(NAME, ph.getName().toUpperCase());
            values.put(SCORE, ph.getScore());
            values.put(POINTS, ph.getPoints());

            String playerSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                    ID, TABLE_PLAYER_HISTORY, NAME);
            Cursor cursor = db.rawQuery(playerSelectQuery, new String[]{String.valueOf(ph.getName().toUpperCase())});

            try {
                if (!cursor.moveToFirst()) {
                    // The player didn't exit, create a new one
                    db.insertOrThrow(TABLE_PLAYER_HISTORY, null, values);
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.d(TAG, "Error updating player information: " + e.getMessage());
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error updating player information: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public List<PlayerHistory> getCompletePlayerHistory() {
        List<PlayerHistory> playerHistoryList = new ArrayList<>();

        String PLAYER_HISTORY_QUERY = String.format("SELECT * FROM %s ORDER BY %s DESC",
                TABLE_PLAYER_HISTORY,
                SCORE
        );
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PLAYER_HISTORY_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    PlayerHistory newPH = new PlayerHistory();
                    newPH.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                    newPH.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
                    newPH.setPoints(cursor.getInt(cursor.getColumnIndex(POINTS)));

                    playerHistoryList.add(newPH);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error reading data base: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return playerHistoryList;
    }

    public void updatePlayerHistory(String playerName, int points) {
        String name = playerName.toUpperCase();
        SQLiteDatabase db = this.getReadableDatabase();

        String playerSelectQuery = String.format("UPDATE %s SET %s = %s + 1, %s = %s + %s WHERE %s = '%s'",
                TABLE_PLAYER_HISTORY, SCORE, SCORE, POINTS, POINTS, points, NAME, name);
        Cursor cursor = db.rawQuery(playerSelectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error updating player information: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
