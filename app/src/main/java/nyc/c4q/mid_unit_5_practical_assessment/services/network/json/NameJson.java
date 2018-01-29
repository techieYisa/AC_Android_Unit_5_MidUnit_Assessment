package nyc.c4q.mid_unit_5_practical_assessment.services.network.json;

/**
 * Models the name object for randomly generated fake user provided from https://randomuser.me
 * <p>
 * Created by charlie on 1/18/18.
 */

public class NameJson {
    private final String title, first, last;

    public NameJson(String title, String first, String last) {
        this.title = title;
        this.first = first;
        this.last = last;
    }

    public String getTitle() {
        return title;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
