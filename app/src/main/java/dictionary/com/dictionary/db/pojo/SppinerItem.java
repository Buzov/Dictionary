package dictionary.com.dictionary.db.pojo;

/**
 * Created by artur on 12.08.2015.
 */
public class SppinerItem {
    int id;
    String name;

    public SppinerItem() {

    }

    public SppinerItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
