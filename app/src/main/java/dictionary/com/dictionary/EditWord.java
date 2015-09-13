package dictionary.com.dictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import dictionary.com.dictionary.db.pojo.Word;

/**
 * Created by artur on 11.08.2015.
 */
public class EditWord extends Activity implements View.OnClickListener {

    private static final String LOG_TAG = "myLogs";

    private EditText etWord;
    private EditText etTranscription;
    private EditText etTranslation;

    private Button btnUpdate;

    private Word word;

    private DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_word);
        Intent intent = getIntent();

        word = (Word) intent.getSerializableExtra(HomeScreen.WORD);

        etWord = (EditText) findViewById(R.id.word);
        etWord.setText(word.getWord());
        etTranscription = (EditText) findViewById(R.id.transcription);
        etTranscription.setText(word.getTranscription());
        etTranslation = (EditText) findViewById(R.id.translation);
        etTranslation.setText(word.getTranslation());
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        dataBaseHelper = new DataBaseHelper(this);
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
            case R.id.btn_update:
                // кнопка ОК
                //Log.d(TAG, "кнопка ОК");
                ContentValues cv = new ContentValues();

                // получаем данные из полей ввода
                String en = etWord.getText().toString();
                String tr = etTranscription.getText().toString();
                String ru = etTranslation.getText().toString();

                if(en == null || en.equals("")) {
                    Toast.makeText(this, R.string.word_is_empty, Toast.LENGTH_SHORT).show();
                    etWord.requestFocus();
                } else if(tr == null || tr.equals("")) {
                    Toast.makeText(this, R.string.tr_is_empty, Toast.LENGTH_SHORT).show();
                    etTranscription.requestFocus();
                } else if(ru == null || ru.equals("")) {
                    Toast.makeText(this, R.string.ru_is_empty, Toast.LENGTH_SHORT).show();
                    etTranslation.requestFocus();
                } else {
                    cv.put("word", en);
                    cv.put("transcription", tr);
                    cv.put("translation", ru);
                    cv.put("status", false);

                    // подключаемся к БД



                    // вставляем запись и получаем ее ID
                    int updCount = db.update("words", cv, "_id = ?", new String[]{"" + word.get_id()});
                    Log.d(LOG_TAG, "row updated, ID = " + updCount);
                    int messageResId = R.string.update;
                    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

}