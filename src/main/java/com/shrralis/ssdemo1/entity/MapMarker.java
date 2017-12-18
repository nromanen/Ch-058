package com.shrralis.ssdemo1.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.shrralis.ssdemo1.entity.MapMarker.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class MapMarker {
    public static final String TABLE_NAME = "map_markers";
    public static final String ID_COLUMN_NAME = "id";
    public static final String LAT_COLUMN_NAME = "lat";
    public static final String LNG_COLUMN_NAME = "lng";

    private Integer id;
    private Double lat;
    private Double lng;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "map_markers_seq_gen")
    @SequenceGenerator(name = "map_markers_seq_gen", sequenceName = "map_markers_id_seq")
    @Column(name = ID_COLUMN_NAME, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Column(name = LAT_COLUMN_NAME, nullable = false)
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @NotNull
    @Column(name = LNG_COLUMN_NAME, nullable = false)
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
