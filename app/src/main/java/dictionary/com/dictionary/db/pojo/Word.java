package dictionary.com.dictionary.db.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by artur on 07.08.2015.
 */
public class Word implements Serializable{

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_TRANSCRIPTION = "transcription";
    public static final String COLUMN_TRANSLATION = "translation";
    public static final String COLUMN_PART_OF_SPEECH = "part_of_speech";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_SOUND = "sound";
    public static final String COLUMN_IMAGE = "image";

    private int _id;
    private String word;
    private String transcription;
    private String translation;
    private Date date;
    private int type;
    private boolean state;
    private byte[] image;
    private byte[] sound;
    private boolean isChecked = false;

    public Word() {

    }

    public Word(int _id, String word, String transcription, String translation) {
        this._id = _id;
        this.word = word;
        this.transcription = transcription;
        this.translation = translation;
    }

    public Word(int _id, String word, String transcription, String translation, Date date, int type, boolean state, byte[] image, byte[] sound) {
        this._id = _id;
        this.word = word;
        this.transcription = transcription;
        this.translation = translation;
        this.date = date;
        this.type = type;
        this.state = state;
        this.image = image;
        this.sound = sound;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getSound() {
        return sound;
    }

    public void setSound(byte[] sound) {
        this.sound = sound;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
