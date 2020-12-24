package com.iquinteros.lodgefinder.models;

public class Lodging {

    private int id;
    private int userID;
    private String city;
    private int latCoords;
    private int longCoords;
    private String description;
    private String kind;
    private int price;
    private int rating;
    private String publishDate;
    private String modifyDate;

    public Lodging(int id, int userID, String city, int latCoords, int longCoords, String description, String kind, int price, int rating, String publishDate, String modifyDate) {
        this.id = id;
        this.userID = userID;
        this.city = city;
        this.latCoords = latCoords;
        this.longCoords = longCoords;
        this.description = description;
        this.kind = kind;
        this.price = price;
        this.rating = rating;
        this.publishDate = publishDate;
        this.modifyDate = modifyDate;
    }

    public Lodging(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getLatCoords() {
        return latCoords;
    }

    public void setLatCoords(int latCoords) {
        this.latCoords = latCoords;
    }

    public int getLongCoords() {
        return longCoords;
    }

    public void setLongCoords(int longCoords) {
        this.longCoords = longCoords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return id + ") " + city;
    }

    public String toComplexString(){
        return description + "\n\n" +
               "Tipo: " + kind + "\n" +
               "$" + price + "\n" +
               "Rating: " + rating;
    }
}
