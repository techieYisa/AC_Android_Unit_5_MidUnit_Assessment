package nyc.c4q.mid_unit_5_practical_assessment.model;

/**
 * Models a User object. Provide a builder rather than use the constructor with so many params.
 * <p>
 * Created by charlie on 1/18/18.
 */

public class User {

    // The primary key from the database will be the ID.
    // The mapper will use this if User not added to database yet.
    static final int UNKNOWN_ID = -1;

    private final int id;
    private final String title, firstName, lastName, street, city, state, postCode, email, cell;
    private final String dob, largeImageUrl, mediumImageUrl, thumbnailImageUrl;

    // Private because we're using the builder pattern
    private User(int id, String title, String firstName, String lastName, String street,
                 String city, String state, String postCode, String email, String cell, String dob,
                 String largeImageUrl, String mediumImageUrl, String thumbnailImageUrl) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.email = email;
        this.cell = cell;
        this.dob = dob;
        this.largeImageUrl = largeImageUrl;
        this.mediumImageUrl = mediumImageUrl;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getPostCode() {
        return postCode;
    }

    public String getEmail() {
        return email;
    }

    public String getCell() {
        return cell;
    }

    public String getDob() {
        return dob;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    // Nested Builder class
    public static class Builder {
        private final int id;
        private final String firstName, lastName;
        private String title, street, city, state, postCode, email, cell, dob;
        private String largeImageUrl, mediumImageUrl, thumbnailImageUrl;

        // Let's make id, firstName, and lastName required, but the rest not required.
        // This is a little arbitrary, just to illustrate that you can force some fields to be set
        // via the Builder's constructor parameters.
        public Builder(int id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setCell(String cell) {
            this.cell = cell;
            return this;
        }

        public Builder setDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder setLargeImageUrl(String largeImageUrl) {
            this.largeImageUrl = largeImageUrl;
            return this;
        }

        public Builder setMediumImageUrl(String mediumImageUrl) {
            this.mediumImageUrl = mediumImageUrl;
            return this;
        }

        public Builder setThumbnailImageUrl(String thumbnailImageUrl) {
            this.thumbnailImageUrl = thumbnailImageUrl;
            return this;
        }

        public User build() {
            return new User(id, title, firstName, lastName, street, city, state, postCode, email,
                    cell, dob, largeImageUrl, mediumImageUrl, thumbnailImageUrl);
        }
    }
}
