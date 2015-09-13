package dictionary.com.dictionary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dictionary.com.dictionary.adapter.WordListViewAdapter;
import dictionary.com.dictionary.db.DataBaseHelper;
import dictionary.com.dictionary.db.DataBaseManager;
import dictionary.com.dictionary.db.pojo.Word;

/**
 * Created by artur on 06.08.2015.
 */
public class HomeScreen extends Activity {

    private static final String LOG_TAG = "Home";
    public static final String WORD = "dictionary.com.dictionary.HomeScreen.Word";

    private Button btnAdd;
    private Button btnDelete;
    private ListView listViewOfWords;

    private Spinner spinner;
    private Spinner spinnerCategory;

    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        dataBaseManager = DataBaseManager.getInstance(this);

        btnAdd = (Button)findViewById(R.id.add_button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, AddWord.class);
                HomeScreen.this.startActivity(intent);
                overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha2);
            }
        });

        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                        HomeScreen.this);
                quitDialog.setTitle("Вы уверены?");

                quitDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseManager.deleteWords();
                    }
                });

                quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });

                quitDialog.show();


            }
        });

        /*listOfWords.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(getBaseContext(), "itemSelect: position = " + position + ", id = "
                        + id, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "itemSelect: nothing", Toast.LENGTH_SHORT).show();
            }
        });*/

        // адаптер
        ArrayAdapter<String> adapterSpin = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getAllSP());
        adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapterSpin);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // адаптер
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getAllSpCategory());
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory = (Spinner) findViewById(R.id.spinner2);
        spinnerCategory.setAdapter(adapterCategory);
        // заголовок
        spinnerCategory.setPrompt("Title");
        // выделяем элемент
        spinnerCategory.setSelection(1);
        // устанавливаем обработчик нажатия
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataBaseManager = DataBaseManager.getInstance(this);

        listViewOfWords = (ListView) findViewById(R.id.listView2);
        // создаем адаптер
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.word_listview_item, getAllWordS());
        WordListViewAdapter wordListViewAdapter = new WordListViewAdapter(this, getListOfWords());

        // присваиваем адаптер списку
        //listOfWords.setAdapter(adapter);
        listViewOfWords.setAdapter(wordListViewAdapter);

        listViewOfWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), "itemClick: position = " + position + ", id = " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeScreen.this, EditWord.class);
                intent.putExtra(WORD, getListOfWords().get(position));
                HomeScreen.this.startActivity(intent);
                overridePendingTransition(R.anim.diagonaltranslate, R.anim.alpha2);
                //dataBaseManager.setListOfWords(null);
            }
        });
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
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                HomeScreen.this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }

    private String[] getAllSP() {
        return DataBaseManager.getInstance(this).getAllSP();
    }

    private List<Word> getListOfWords() {
        return DataBaseManager.getInstance(this).getListOfWords();
    }

    private String[] getAllSpCategory() {
        return DataBaseManager.getInstance(this).getAllSpCategory();
    }

}
