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

    public void getUserByEmailAndPassword(String email, String password, final GetUserResult userResult){

        String[] keys = new String[2];
        String[] params = new String[2];

        String url = "https://zeakservices.com/software/projects/lodging/responses/user/get_user_by_email_pass_resp.php";
        keys[0] = "email";
        keys[1] = "password";

        params[0] = email;
        params[1] = password;

        // TODO: CAMBIAR URL
        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    userResult.onReady(parseJsonToUser(result));
                }
                else{
                    userResult.onReady(null);
                }
            }

            @Override
            public void onFail() {
                userResult.onReady(null);
            }
        });



    }

    public void getUserById(int id, final GetUserResult userResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/user/get_user_by_id_resp.php";
        keys[0] = "id";

        params[0] = Integer.toString(id);

        // TODO: CAMBIAR URL
        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    userResult.onReady(parseJsonToUser(result));
                }
                else{
                    userResult.onReady(null);
                }
            }

            @Override
            public void onFail() {
                userResult.onReady(null);
            }
        });

    }

    public void getUserByEmail(String email, final GetUserResult userResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/user/get_user_by_email_resp.php";
        keys[0] = "email";

        params[0] = email;

        // TODO: CAMBIAR URL
        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    userResult.onReady(parseJsonToUser(result));
                }
                else{
                    userResult.onReady(null);
                }
            }

            @Override
            public void onFail() {
                userResult.onReady(null);
            }
        });

    }

    public void pushUser(User newUser, String password, boolean isUpdate, final GetSuccessResult pushResult){

        String[] keys = new String[8];
        String[] params = new String[8];

        String url = isUpdate
                ? "https://zeakservices.com/software/projects/lodging/responses/lodging/update_user_resp.php"   // URL UPDATE
                : "https://zeakservices.com/software/projects/lodging/responses/user/push_user_resp.php";  // URL ADD

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
        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    // Return true
                    pushResult.onReady(true);
                }
                else{
                    // Return false
                    pushResult.onReady(false);
                }
            }

            @Override
            public void onFail() {
                // Return false
                pushResult.onReady(false);
            }
        });

    }

    public void deleteUser(User user, final GetUserResult userResult, final GetSuccessResult pushResult){

        String[] keys = new String[1];
        String[] params = new String[1];

        String url = "https://zeakservices.com/software/projects/lodging/responses/user/delete_user_resp.php";

        keys[0] = "id";
        params[0] = Integer.toString(user.getId());

        // TODO: CAMBIAR URL
        RemoteConnection.remoteConnection.getDataFromUrl(url, keys, params, new GetDataResult() {
            @Override
            public void onGetData(JSONArray result) {
                if(result.length() > 0){
                    // Return true
                    pushResult.onReady(true);
                }
                else{
                    // Return false
                    pushResult.onReady(false);
                }
            }

            @Override
            public void onFail() {
                // Return false
                pushResult.onReady(false);
            }
        });

    }
}
