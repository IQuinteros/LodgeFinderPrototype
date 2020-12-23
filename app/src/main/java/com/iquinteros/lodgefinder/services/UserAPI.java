package com.iquinteros.lodgefinder.services;

import com.iquinteros.lodgefinder.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserAPI {

    // Singleton
    public static UserAPI userAPI = new UserAPI();

    public User parseJsonToUser(JSONArray json){
        try {
            JSONObject responseObject = json.getJSONObject(0);
            User user = new User();

            user.setId(responseObject.getInt("id"));
            user.setNombres(responseObject.getString("names"));
            user.setApellidos(responseObject.getString("lastNames"));
            user.setEmail(responseObject.getString("email"));
            user.setRut(responseObject.getInt("rut"));
            user.setNumeroContacto(responseObject.getInt("contactNumber"));
            user.setFoto(responseObject.getString("photoUrl"));
            user.setEmpresa(responseObject.getBoolean("isBusiness"));

            return user;
        }
        catch(Exception e){
            return null;
        }
    }

    public User getUserByEmailAndPassword(String email, String password){

        String[] keys = new String[2];
        String[] params = new String[2];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";
        keys[0] = "email";
        keys[1] = "password";

        params[0] = email;
        params[1] = password;

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return parseJsonToUser(response);
        }
        else{
            return null;
        }

    }

    public User getUserById(int id){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";
        keys[0] = "id";

        params[0] = Integer.toString(id);

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return parseJsonToUser(response);
        }
        else{
            return null;
        }

    }

    public User getUserByEmail(String email){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";
        keys[0] = "email";

        params[0] = email;

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return parseJsonToUser(response);
        }
        else{
            return null;
        }

    }

    public boolean pushUser(User newUser, String password, boolean isUpdate){

        String[] keys = new String[8];
        String[] params = new String[8];

        String url = isUpdate
                ? "http://zeakservices.com/ev3/getUserByEmailAndPassword.php"   // URL UPDATE
                : "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";  // URL ADD

        keys[0] = "names";
        keys[1] = "lastNames";
        keys[2] = "email";
        keys[3] = "password";
        keys[4] = "rut";
        keys[5] = "contactNumber";
        // TODO: REVIEW PHOTO
        keys[6] = "photoUrl";
        keys[7] = "isBusiness";


        params[0] = newUser.getNombres();
        params[1] = newUser.getApellidos();
        params[2] = newUser.getEmail();
        params[3] = password;
        params[4] = Integer.toString(newUser.getRut());
        params[5] = Integer.toString(newUser.getNumeroContacto());
        // REVIEW PHOTO
        params[6] = newUser.getFoto();
        params[7] = newUser.isEmpresa()? "1" : "0";

        // TODO: CAMBIAR URL
        JSONArray response = RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params);

        if(response.length() > 0){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean deleteUser(User user){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "http://zeakservices.com/ev3/getUserByEmailAndPassword.php";

        keys[0] = "id";
        params[0] = Integer.toString(user.getId());

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
