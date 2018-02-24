package nyc.c4q.mid_unit_5_practical_assessment.services.network.json;

/**
 * Models a randomly generated fake user provided by https://randomuser.me, specifically
 * from the call https://randomuser.me/api/?nat=us&inc=name,location,cell,email,dob,picture
 * <p>
 * Created by charlie on 1/18/18.
 */

public class UserJson {
    private final NameJson name;
    private final LocationJson location;
    private final String email, dob, cell;
    private final PictureJson picture;

    public UserJson(NameJson name, LocationJson location, String email, String dob, String cell, PictureJson picture) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.dob = dob;
        this.cell = cell;
        this.picture = picture;
    }

    public NameJson getName() {
        return name;
    }

    public LocationJson getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getCell() {
        return cell;
    }

    public PictureJson getPicture() {
        return picture;
    }
}
