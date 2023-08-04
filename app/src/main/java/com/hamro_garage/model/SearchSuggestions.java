package com.hamro_garage.model;

public class SearchSuggestions {
    public String garageName;
    public String id;
    public String location;
    public SearchSuggestions(String garageName, String id, String location) {
        this.garageName = garageName;
        this.id = id;
        this.location = location;
    }



    public String getGarageName() {
        return garageName;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
}
