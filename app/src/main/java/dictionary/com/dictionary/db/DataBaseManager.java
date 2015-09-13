package dictionary.com.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dictionary.com.dictionary.db.pojo.Word;

/**
 * Created by artur on 23.08.15.
 */
public class DataBaseManager {

    private static final String ALL = "All";

    private static final String TABLE_NAME = "words";
    private static final String TABLE_NAME_SP = "part_of_speech";
    private static final String TABLE_NAME_SP_C = "category";
    private static final String COLUMN_DATE = "word";
    private static final String COLUMN_DATE_SP = "name_en";
    private static final String COLUMN_DATE_SP_C = "name";

    private DataBaseHelper dataBaseHelper = null;
    private static DataBaseManager dataBaseManager = null;

    private SQLiteDatabase db = null;
    private List<Word> listOfWords;

    private DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getReadableDatabase();
    }

    public static DataBaseManager getInstance(Context context) {
        if (dataBaseManager == null) {
            dataBaseManager = new DataBaseManager(context);
        }
        return dataBaseManager;
    }

    public List<Word> getListOfWords() {
        boolean check = ((listOfWords == null) || (listOfWords.size() == 0));
        if(check) {
            List<Word> words = new ArrayList<Word>();
            db = dataBaseHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE);
            if (cursor.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int idColIndex = cursor.getColumnIndex("_id");
                int enColIndex = cursor.getColumnIndex("word");
                int trColIndex = cursor.getColumnIndex("transcription");
                int ruColIndex = cursor.getColumnIndex("translation");
                int soundColIndex = cursor.getColumnIndex("sound");

                do {
                    Word word = new Word(cursor.getInt(idColIndex),
                            cursor.getString(enColIndex),
                            cursor.getString(trColIndex),
                            cursor.getString(ruColIndex));
                    word.setSound(cursor.getBlob(soundColIndex));
                    words.add(word);
                } while (cursor.moveToNext());
            } else {
                cursor.close();
            }
            listOfWords = words;
        }
        return listOfWords;
    }

    public void setListOfWords(List<Word> listOfWords) {
        this.listOfWords = listOfWords;
    }

    public String[] getAllSP() {
        ArrayList<String> words = new ArrayList<String>();
        words.add(ALL);
        db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SP, null, null, null, null, null, COLUMN_DATE_SP);
        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int nameColIndex = cursor.getColumnIndex("name_en");
            do {
                words.add(cursor.getString(nameColIndex));
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        String[] strings = new String[words.size()];
        for(int i = 0; i < words.size(); i++) {
            strings[i] = words.get(i);
        }
        return strings;
    }

    public String[] getAllSpCategory() {
        ArrayList<String> words = new ArrayList<String>();
        words.add(ALL);
        db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_SP_C, null, null, null, null, null, COLUMN_DATE_SP_C);
        if (cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int nameColIndex = cursor.getColumnIndex("name");
            do {
                words.add(cursor.getString(nameColIndex));
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        String[] strings = new String[words.size()];
        for(int i = 0; i < words.size(); i++) {
            strings[i] = words.get(i);
        }
        return strings;
    }

    public void deleteWords() {
        db = dataBaseHelper.getWritableDatabase();
        //listViewOfWords.getAdapter().getItem();
        int size = listOfWords.size();
        boolean isChecked;
        for(int i = 0; i < size; i++) {
            isChecked = listOfWords.get(i).isChecked();
            if(isChecked) {
                db.delete("words", "_id = " + listOfWords.get(i).get_id(), null);
                //size--;
            }
        }
    }

    public void inputWord(Word word) {
        db = dataBaseHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();


        // вставляем запись и получаем ее ID
        long rowID = db.insert("words", null, cv);
    }



}
