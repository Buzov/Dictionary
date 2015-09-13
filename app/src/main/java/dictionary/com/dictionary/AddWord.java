package dictionary.com.dictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dictionary.com.dictionary.db.DataBaseHelper;
import dictionary.com.dictionary.db.DataBaseManager;

/**
 * Created by artur on 07.08.2015.
 */
public class AddWord extends Activity implements View.OnClickListener {

    private static final String LOG_TAG = "myLogs";

    private EditText word;
    private EditText transcription;
    private EditText translation;

    private Button save;

    private static DataBaseManager dataBaseManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word);
        word = (EditText) findViewById(R.id.word);
        transcription = (EditText) findViewById(R.id.transcription);
        translation = (EditText) findViewById(R.id.translation);
        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(this);
        dataBaseManager = DataBaseManager.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        /*menu.add(0, 1, 0, "Словарь");
        menu.add(0, 2, 0, "Тренировки");
        menu.add(1, 3, 0, "Справка");*/

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчи
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.save_button:
                // кнопка ОК
                //Log.d(TAG, "кнопка ОК");
                ContentValues cv = new ContentValues();

                // получаем данные из полей ввода
                String en = word.getText().toString();
                String tr = transcription.getText().toString();
                String ru = translation.getText().toString();

                if(en == null || en.equals("")) {
                    Toast.makeText(this, R.string.word_is_empty, Toast.LENGTH_SHORT).show();
                    word.requestFocus();
                } else if(tr == null || tr.equals("")) {
                    Toast.makeText(this, R.string.tr_is_empty, Toast.LENGTH_SHORT).show();
                    transcription.requestFocus();
                } else if(ru == null || ru.equals("")) {
                    Toast.makeText(this, R.string.ru_is_empty, Toast.LENGTH_SHORT).show();
                    translation.requestFocus();
                } else {
                    cv.put("word", en);
                    cv.put("transcription", tr);
                    cv.put("translation", ru);
                    cv.put("status", false);

                    // подключаемся к БД


                    // вставляем запись и получаем ее ID
                    long rowID = db.insert("words", null, cv);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                    int messageResId = R.string.save;
                    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
                    word.setText("");
                    transcription.setText("");
                    translation.setText("");
                    word.requestFocus();
                }

                break;
            case 10:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("words", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("_id");
                    int enColIndex = c.getColumnIndex("en");
                    int trColIndex = c.getColumnIndex("tr");
                    int ruColIndex = c.getColumnIndex("ru");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", en = " + c.getString(enColIndex) +
                                        ", tr = " + c.getString(trColIndex) +
                                        ", ru = " + c.getString(ruColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
        }
    }

}
