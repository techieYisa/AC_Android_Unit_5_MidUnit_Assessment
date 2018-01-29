package nyc.c4q.mid_unit_5_practical_assessment.services.network.json;

/**
 * Models the location object for randomly generated fake user provided from https://randomuser.me
 * <p>
 * Created by charlie on 1/18/18.
 */

public class LocationJson {
    private final String street, city, state;
    private final int postcode;

    public LocationJson(String street, String city, String state, int postcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getPostcode() {
        return postcode;
    }
}
