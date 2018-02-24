package nyc.c4q.mid_unit_5_practical_assessment.services.network.json;

/**
 * Models the picture object for randomly generated fake user provided from https://randomuser.me
 * <p>
 * Created by charlie on 1/18/18.
 */

public class PictureJson {
    private final String large, medium, thumbnail;

    public PictureJson(String large, String medium, String thumbnail) {
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
