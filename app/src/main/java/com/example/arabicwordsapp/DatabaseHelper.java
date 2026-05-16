package com.example.arabicwordsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";
    private static final String COL_ID = "id";
    private static final String COL_ARABIC = "arabic";
    private static final String COL_TRANSLATION = "translation";
    private static final String COL_TRANSCRIPTION = "transcription";
    private static final String COL_GRAMMAR = "grammar";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ARABIC + " TEXT NOT NULL, " +
                COL_TRANSLATION + " TEXT NOT NULL, " +
                COL_TRANSCRIPTION + " TEXT, " +
                COL_GRAMMAR + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Добавление слова
    public boolean addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ARABIC, word.getArabic());
        cv.put(COL_TRANSLATION, word.getTranslation());
        cv.put(COL_TRANSCRIPTION, word.getTranscription());
        cv.put(COL_GRAMMAR, word.getGrammar());
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    // Получение всех слов
    public List<Word> getAllWords() {
        List<Word> words = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String arabic = cursor.getString(1);
                String translation = cursor.getString(2);
                String transcription = cursor.getString(3);
                String grammar = cursor.getString(4);
                words.add(new Word(id, arabic, translation, transcription, grammar));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return words;
    }

    // Получение одного слова по id
    public Word getWordById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            String arabic = cursor.getString(1);
            String translation = cursor.getString(2);
            String transcription = cursor.getString(3);
            String grammar = cursor.getString(4);
            cursor.close();
            return new Word(id, arabic, translation, transcription, grammar);
        }
        cursor.close();
        return null;
    }

    // Обновление слова
    public boolean updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ARABIC, word.getArabic());
        cv.put(COL_TRANSLATION, word.getTranslation());
        cv.put(COL_TRANSCRIPTION, word.getTranscription());
        cv.put(COL_GRAMMAR, word.getGrammar());
        int result = db.update(TABLE_NAME, cv, COL_ID + " = ?", new String[]{String.valueOf(word.getId())});
        return result > 0;
    }

    // Удаление слова
    public boolean deleteWord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}