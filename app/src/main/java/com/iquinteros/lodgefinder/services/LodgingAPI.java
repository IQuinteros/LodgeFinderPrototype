package com.iquinteros.lodgefinder.services;

import com.iquinteros.lodgefinder.models.Lodging;
import com.iquinteros.lodgefinder.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LodgingAPI {

    // Singleton
    public static LodgingAPI lodgingAPI = new LodgingAPI();


    public Lodging parseJsonToLodging(JSONArray json){
        try {
            return parseJsonObjectToLodging(json.getJSONObject(0));
        }
        catch(Exception e){
            return null;
        }
    }

    public Lodging parseJsonObjectToLodging(JSONObject jsonObject){
        try {
            Lodging lodging = new Lodging();

            lodging.setId(jsonObject.getInt("id"));
            lodging.setUserID(jsonObject.getInt("userID"));
            lodging.setCity(jsonObject.getString("city"));
            lodging.setLatCoords(jsonObject.getInt("latCoords"));
            lodging.setLongCoords(jsonObject.getInt("longCoords"));
            lodging.setDescription(jsonObject.getString("description"));
            lodging.setKind(jsonObject.getString("kind"));
            lodging.setPrice(jsonObject.getInt("price"));
            lodging.setRating(jsonObject.getInt("rating"));
            lodging.setPublishDate(jsonObject.getString("publishDate"));
            lodging.setModifyDate(jsonObject.getString("modifyDate"));

            return lodging;
        }
        catch(Exception e){
            return null;
        }
    }

    public ArrayList<Lodging> getLodgingsBySearch(String search){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";
        keys[0] = "search";

        params[0] = search;

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        ArrayList<Lodging> lodgings = new ArrayList();
        if(response.length() > 0){

            for(int i = 0; i < response.length(); i++) {
                try {
                    lodgings.add(parseJsonObjectToLodging(response.getJSONObject(i)));
                } catch (Exception e) {
                    System.out.println("Error parsing to lodging: " + e.getMessage());
                }
            }
        }

        return lodgings;

    }

    public Lodging getLodgingById(int id){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";
        keys[0] = "id";

        params[0] = Integer.toString(id);

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return parseJsonToLodging(response);
        }
        else{
            return null;
        }

    }

    public ArrayList<Lodging> getLodgingsOfUser(int userID){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";
        keys[0] = "userID";

        params[0] = Integer.toString(userID);

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        ArrayList<Lodging> lodgings = new ArrayList();
        if(response.length() > 0){

            for(int i = 0; i < response.length(); i++) {
                try {
                    lodgings.add(parseJsonObjectToLodging(response.getJSONObject(i)));
                } catch (Exception e) {
                    System.out.println("Error parsing to lodging: " + e.getMessage());
                }
            }
        }

        return lodgings;

    }

    public boolean pushLodging(Lodging newLodging, boolean isUpdate){

        String[] keys = new String[8];
        String[] params = new String[8];

        String url = isUpdate
                ? "http://zeakservices.com/ev3/getUserByEmailAndPassword.php"   // URL UPDATE
                : "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";  // URL ADD

        keys[0] = "userID";
        keys[1] = "city";
        keys[2] = "latCoords";
        keys[3] = "longCoords";
        keys[4] = "description";
        keys[5] = "king";
        keys[6] = "price";
        keys[7] = "rating";
        keys[8] = "publishDate";
        keys[9] = "modifyDate";


        params[0] = Integer.toString(newLodging.getUserID());
        params[1] = newLodging.getCity();
        params[2] = Integer.toString(newLodging.getLatCoords());
        params[3] = Integer.toString(newLodging.getLongCoords());
        params[4] = newLodging.getDescription();
        params[5] = newLodging.getKind();
        params[6] = Integer.toString(newLodging.getPrice());
        params[7] = Integer.toString(newLodging.getRating());
        params[8] = newLodging.getPublishDate();
        params[9] = newLodging.getModifyDate();

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean deleteLodging(Lodging lodging){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";

        keys[0] = "id";
        params[0] = Integer.toString(lodging.getId());

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return true;
        }
        else{
            return false;
        }

    }
}
