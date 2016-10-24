package erica.locationscrapbook;


import com.google.android.gms.maps.model.LatLng;

public class Location {
    private long ID;
    private String name;
    private String description;
    private LatLng coord;

    public Location(long ID, String name, String description, LatLng latLng) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.coord = latLng;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLatLng() {
        return coord;

    }

    public void setLatLng(LatLng latLng) {
        this.coord = latLng;
    }


}
