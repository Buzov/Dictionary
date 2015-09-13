package dictionary.com.dictionary.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.rtp.AudioStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dictionary.com.dictionary.R;
import dictionary.com.dictionary.db.pojo.Word;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class WordListViewAdapter extends BaseAdapter {

    private static final String HTTP_GOOGLE = "http://translate.google.com/translate_tts?tl=en&q=";
    private static final String HTTP_GSTATIC = "https://ssl.gstatic.com/dictionary/static/sounds/de/0/";
    private static final String MP3 = ".mp3";

    private class ViewHolder {
        Button btnPlaySound;
        TextView word;
        TextView translation;
        CheckBox checkBox;
    }

    private LayoutInflater lInflater;
    private List<Word> listOfWords;
    private Context context;

    public WordListViewAdapter(Context context, List<Word> listOfWords) {
        this.listOfWords = listOfWords;
        this.context = context;
        lInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listOfWords.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfWords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.word_listview_item, null);
            holder.btnPlaySound = (Button) convertView.findViewById(R.id.btn_play_sound);
            holder.word = (TextView) convertView.findViewById(R.id.word);
            holder.translation = (TextView) convertView.findViewById(R.id.translation);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.word.setText(listOfWords.get(position).getWord());
        holder.translation.setText(listOfWords.get(position).getTranslation());
        holder.checkBox.setChecked(listOfWords.get(position).isChecked());
        holder.checkBox.setOnClickListener(new MyOnClickListener(position, holder));
        holder.btnPlaySound.setOnClickListener(new SoundButtonOnClickListener(position));

        return convertView;
    }

    class MyOnClickListener implements View.OnClickListener {

        int position;
        ViewHolder holder;

        MyOnClickListener(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            Word word = listOfWords.get(position);
            boolean temp = word.isChecked();
            word.setIsChecked(!temp);
            holder.checkBox.setChecked(!temp);
        }
    }

    public String getCacheDir() {
        return "/data/data/dictionary.com.dictionary/";
    }

    class SoundButtonOnClickListener implements View.OnClickListener {

        int position;

        SoundButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            try {
                MediaPlayer player = new MediaPlayer();
                //player.release();
                String urlTemp = HTTP_GSTATIC + listOfWords.get(position).getWord() + MP3;
                String urlTemp1 = "https://dl.dropboxusercontent.com/u/6197740/explosion.mp3";
                player.setDataSource(urlTemp);
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);

                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // TODO Auto-generated method stub

                        mp.start();

                    }
                });

                player.prepareAsync();

                /*
                File tempMp3 = File.createTempFile("soundTemp", "mp3", context.getCacheDir());
                //File file = new File("/data/data/dictionary.com.dictionary/temp.mp3");
                tempMp3.deleteOnExit();
                byte[] data = listOfWords.get(position).getSound();
                FileOutputStream fos = new FileOutputStream(tempMp3);
                fos.write(data);
                FileInputStream fis = new FileInputStream(tempMp3);
                MediaPlayer player = new MediaPlayer();

                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.setDataSource(fis.getFD());
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // TODO Auto-generated method stub

                        mp.start();

                    }
                });
                player.prepareAsync();*/

            } catch (Exception e) {
                Log.e("fg", e.toString());
            }


        }


    }




}