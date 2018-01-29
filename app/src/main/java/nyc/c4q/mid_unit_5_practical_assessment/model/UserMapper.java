package nyc.c4q.mid_unit_5_practical_assessment.model;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.services.network.json.UserJson;

/**
 * Utility class to create User instances from other types of objects
 * <p>
 * Created by charlie on 1/18/18.
 */

public class UserMapper {

    /**
     * This utility method converts an instance of our GSON/POJO class to our User model class.
     *
     * @param userJson is an object created by GSON to represent data received in JSON format.
     * @return a new instance of the User class.
     */
    public static User from(UserJson userJson) {

        // Use UNKNOWN_ID as the id because we haven't added this user to the database yet,
        // and therefore don't yet have a database primary key value to use as id.
        return new User.Builder(User.UNKNOWN_ID, userJson.getName().getFirst(), userJson.getName().getLast())
                .setTitle(userJson.getName().getTitle())
                .setStreet(userJson.getLocation().getStreet())
                .setCity(userJson.getLocation().getCity())
                .setState(userJson.getLocation().getState())
                .setPostCode(Integer.toString(userJson.getLocation().getPostcode()))
                .setEmail(userJson.getEmail())
                .setCell(userJson.getCell())
                .setDob(userJson.getDob())
                .setLargeImageUrl(userJson.getPicture().getLarge())
                .setMediumImageUrl(userJson.getPicture().getMedium())
                .setThumbnailImageUrl(userJson.getPicture().getThumbnail())
                .build();
    }

    /**
     * This utility method converts a List of our GSON/POJO instances to a List of User instances.
     *
     * @param userJsons is a List of objects created by GSON to represent data received as JSON.
     * @return a new List of User objects.
     */
    public static List<User> from(List<UserJson> userJsons) {
        List<User> users = new ArrayList<>(userJsons.size());

        for (UserJson userJson : userJsons) {
            users.add(from(userJson));
        }

        return users;
    }
}
