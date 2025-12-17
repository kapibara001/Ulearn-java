package project.database.model;

public class Magnitude {
    private short magnitude_type_id;
    private String magnitude_type_name;

    public Magnitude(short magnitude_type_id, String magnitude_type_name) {
        this.magnitude_type_id = magnitude_type_id;
        this.magnitude_type_name = magnitude_type_name;
    }
}