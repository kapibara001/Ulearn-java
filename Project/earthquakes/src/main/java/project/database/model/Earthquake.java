package project.database.model;

import java.sql.Timestamp;

public class Earthquake {
    private int id_earthquake;
    private int noun;
    private short magnitude_type_id;
    private float magnitude;
    private short state_id;
    private Timestamp time;

    public Earthquake(int id_earthquake, int noun, short magnitude_type_id, float magnitude, short state_id, Timestamp time) {
        this.id_earthquake = id_earthquake;
        this.noun = noun;
        this.magnitude_type_id = magnitude_type_id;
        this.magnitude = magnitude;
        this.state_id = state_id;
        this.time = time;
    }
}
