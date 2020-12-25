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

    public void getLodgingsBySearch(String search, final GetLodgingsResult getLodgingsResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/lodging/get_lodgings_by_search_resp.php";
        keys[0] = "search";

        params[0] = search;

        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                ArrayList<Lodging> lodgings = new ArrayList();
                if(result.length() > 0){

                    for(int i = 0; i < result.length(); i++) {
                        try {
                            lodgings.add(parseJsonObjectToLodging(result.getJSONObject(i)));
                        } catch (Exception e) {
                            System.out.println("Error parsing to lodging: " + e.getMessage());
                        }
                    }
                }

                getLodgingsResult.onReady(lodgings);
            }

            @Override
            public void onFail() {
                getLodgingsResult.onReady(new ArrayList<Lodging>());
            }
        });

    }

    public void getLodgingById(int id, final GetLodgingResult getLodgingResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/lodging/get_lodgings_by_id_resp.php";
        keys[0] = "id";

        params[0] = Integer.toString(id);

        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    getLodgingResult.onReady(parseJsonToLodging(result));
                }
                else{
                    getLodgingResult.onReady(null);
                }
            }

            @Override
            public void onFail() {
                getLodgingResult.onReady(null);
            }
        });

    }

    public void getLodgingsOfUser(int userID, final GetLodgingsResult getLodgingsResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/lodging/get_lodgings_of_user_resp.php";
        keys[0] = "userID";

        params[0] = Integer.toString(userID);

        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                ArrayList<Lodging> lodgings = new ArrayList();
                if(result.length() > 0){

                    for(int i = 0; i < result.length(); i++) {
                        try {
                            lodgings.add(parseJsonObjectToLodging(result.getJSONObject(i)));
                        } catch (Exception e) {
                            System.out.println("Error parsing to lodging: " + e.getMessage());
                        }
                    }
                }

                getLodgingsResult.onReady(lodgings);
            }

            @Override
            public void onFail() {
                getLodgingsResult.onReady(new ArrayList<Lodging>());
            }
        });



    }

    public void pushLodging(Lodging newLodging, boolean isUpdate, final GetSuccessResult getSuccessResult){

        String[] keys = new String[9];
        String[] params = new String[9];

        String url = isUpdate
                ? "https://zeakservices.com/software/projects/lodging/responses/lodging/update_lodging_resp.php"   // URL UPDATE
                : "https://zeakservices.com/software/projects/lodging/responses/lodging/push_lodging_resp.php";  // URL ADD

        keys[0] = "id";
        keys[1] = "userID";
        keys[2] = "city";
        keys[3] = "latCoords";
        keys[4] = "longCoords";
        keys[5] = "description";
        keys[6] = "kind";
        keys[7] = "price";
        keys[8] = "rating";

        params[0] = Integer.toString(newLodging.getId());
        params[1] = Integer.toString(newLodging.getUserID());
        params[2] = newLodging.getCity();
        params[3] = Integer.toString(newLodging.getLatCoords());
        params[4] = Integer.toString(newLodging.getLongCoords());
        params[5] = newLodging.getDescription();
        params[6] = newLodging.getKind();
        params[7] = Integer.toString(newLodging.getPrice());
        params[8] = Integer.toString(newLodging.getRating());

        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    getSuccessResult.onReady(true);
                }
                else{
                    getSuccessResult.onReady(false);
                }
            }

            @Override
            public void onFail() {
                getSuccessResult.onReady(false);
            }
        });



    }

    public void deleteLodging(Lodging lodging, final GetSuccessResult getSuccessResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/lodging/delete_lodging_resp.php";

        keys[0] = "id";
        params[0] = Integer.toString(lodging.getId());

        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    getSuccessResult.onReady(true);
                }
                else{
                    getSuccessResult.onReady(false);
                }
            }

            @Override
            public void onFail() {
                getSuccessResult.onReady(false);
            }
        });

    }
}
