package com.iquinteros.lodgefinder.services;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class RemoteConnection {
    // Singleton
    public static RemoteConnection remoteConnection = new RemoteConnection();

    private AsyncHttpClient client = new AsyncHttpClient();

    public JSONArray getDataFromUrl(String url, String[] keys, String[] params){
        RequestParams requestParams = new RequestParams();

        for (int i = 0; i < keys.length; i++){
            requestParams.add(keys[i], params[i]);
        }

        final String[] toReturn = new String[1];

        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response;
                response = new String(responseBody);

                if(response.isEmpty()){
                    System.out.println("Error HTTP: Nada encontrado");
                }
                else{
                    toReturn[0] = response;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error HTTP: " + error.getMessage());
            }
        });

        try {
            JSONArray jsonResponse = new JSONArray(toReturn[0]);

            return jsonResponse;
        }
        catch(Exception e) {
            System.out.println("Error JSON: " + e.toString());
            e.printStackTrace();
            return null;
        }

    }
}
